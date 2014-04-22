package db;
package test;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class implements a JDBC bridge between databases, allowing data to be
 * copied from one place to another.
 * <p>This implementation is threaded, as it uses a {@link BlockingQueue} to pass
 * data between a producer and a consumer.
 * http://stackoverflow.com/questions/10094055/possible-to-open-two-persistent-connections-to-different-remote-mysql-servers-us
 */
//public class DBBridge {
public class ReadFromDBaFillQueueReadQueueWithOtherThreadThenWriteToDBb {


    public static void main( String[] args ) {

        Adaptor fromAdaptor = null;
        Adaptor toAdaptor = null;

        BridgeConfig config = null;

        try {
            /* BridgeConfig is essentially a wrapper around the Simple XML serialisation library.
             * http://simple.sourceforge.net/
             */
            config = BridgeConfig.loadConfig( new File( "db-bridge.xml" ) );
        }
        catch ( Exception e ) {
            System.err.println( "Failed to read or parse db-bridge.xml: " + e.getLocalizedMessage() );
            System.exit( 1 );
        }

        BlockingQueue<Object> b = new ArrayBlockingQueue<Object>( config.getQueueSize() );

        try {
            HashMap<String, DatabaseConfig> dbs = config.getDbs();

            System.err.println( "Configured DBs" );

            final String sourceName = config.getSource();
            final String destinationName = config.getDestination();

            if ( !dbs.containsKey( sourceName ) ) {
                System.err.println( sourceName + " is not a configured database connection" );
                System.exit( 1 );
            }

            if ( !dbs.containsKey( destinationName ) ) {
                System.err.println( destinationName + " is not a configured database connection" );
                System.exit( 1 );
            }

            DatabaseConfig sourceConfig = dbs.get( sourceName );
            DatabaseConfig destinationConfig = dbs.get( destinationName );

            try {
                /*
                 * Both adaptors must be created before attempting a connection,
                 * as otherwise I've seen DriverManager pick the wrong driver!
                 */
                fromAdaptor = AdaptorFactory.buildAdaptor( sourceConfig, sourceConfig );
                toAdaptor = AdaptorFactory.buildAdaptor( destinationConfig, destinationConfig );

                System.err.println( "Connecting to " + sourceName );
                fromAdaptor.connect();

                System.err.println( "Connecting to " + destinationName );
                toAdaptor.connect();

                /* We'll send our updates to the destination explicitly */
                toAdaptor.getConn().setAutoCommit( false );
            }
            catch ( SQLException e ) {
                System.err.println();
                System.err.println( "Failed to create and configure adaptors" );
                e.printStackTrace();
                System.exit( 1 );
            }
            catch ( ClassNotFoundException e ) {
                System.err.println( "Failed to load JDBC driver due to error: " + e.getLocalizedMessage() );
                System.exit( 1 );
            }

            DataProducer producer = null;
            DataConsumer consumer = null;

            try {
                producer = new DataProducer( config, fromAdaptor, b );
                consumer = new DataConsumer( config, toAdaptor, b );
            }
            catch ( SQLException e ) {
                System.err.println();
                System.err.println( "Failed to create and configure data producer or consumer" );
                e.printStackTrace();
                System.exit( 1 );
            }

            consumer.start();
            producer.start();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static class DataProducer extends DataLogger {

        private BridgeConfig config;
        private Adaptor adaptor;
        private BlockingQueue<Object> queue;


        public DataProducer(BridgeConfig c, Adaptor a, BlockingQueue<Object> bq) {
            super( "Producer" );
            this.config = c;
            this.adaptor = a;
            this.queue = bq;
        }


        @Override
        public void run() {
            /* The tables to copy are listed in BridgeConfig */
            for ( Table table : this.config.getManifest() ) {

                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    String sql = table.buildSourceSelect();
                    this.log( "executing: " + sql );
                    stmt = this.adaptor.getConn().prepareStatement( sql );

                    stmt.execute();

                    rs = stmt.getResultSet();

                    ResultSetMetaData meta = rs.getMetaData();

                    /* Notify consumer that a new table is to be processed */
                    this.queue.put( table );
                    this.queue.put( meta );

                    final int columnCount = meta.getColumnCount();

                    while ( rs.next() ) {
                        ArrayList<Object> a = new ArrayList<Object>( columnCount );

                        for ( int i = 0; i < columnCount; i++ ) {
                            a.add( rs.getObject( i + 1 ) );
                        }

                        this.queue.put( a );
                    }
                }
                catch ( InterruptedException ex ) {
                    ex.printStackTrace();
                }
                catch ( SQLException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    /* refresh the connection */
                    /* Can't remember why I can this line - maybe the other
                     * end kept closing the connection. */
                    this.adaptor.reconnect();
                }
                catch ( SQLException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                /* Use an object of a specific type to "poison" the queue
                 * and instruct the consumer to terminate. */
                this.log( "putting finished object into queue" );
                this.queue.put( new QueueFinished() );

                this.adaptor.close();
            }
            catch ( InterruptedException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch ( SQLException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    /* Superclass for producer and consumer */
    public static abstract class DataLogger extends Thread {

        private String prefix;


        public DataLogger(String p) {
            this.prefix = p;
        }


        protected void log( String s ) {
            System.err.printf( "%d %s: %s%n", System.currentTimeMillis(), this.prefix, s );
        }


        protected void log() {
            System.err.println();
        }
    }

    public static class DataConsumer extends DataLogger {

        private BridgeConfig config;
        private Adaptor adaptor;
        private BlockingQueue<Object> queue;
        private int currentRowNumber = 0;
        private int currentBatchSize = 0;
        private long tableStartTs = -1;


        public DataConsumer(BridgeConfig c, Adaptor a, BlockingQueue<Object> bq) throws SQLException {

            super( "Consumer" );

            this.config = c;
            this.adaptor = a;
            this.queue = bq;

            /* We'll send our updates to the destination explicitly */
            this.adaptor.getConn().setAutoCommit( false );
        }


        public void printThroughput() {
            double duration = ( System.currentTimeMillis() - this.tableStartTs ) / 1000.0;
            long rowsPerSec = Math.round( this.currentRowNumber / duration );
            this.log( String.format( "%d rows processed, %d rows/s", this.currentRowNumber, rowsPerSec ) );
        }


        @Override
        public void run() {

            this.log( "running" );

            Table currentTable = null;
            ResultSetMetaData meta = null;

            int columnCount = -1;

            PreparedStatement stmt = null;

            while ( true ) {
                try {
                    Object o = this.queue.take();

                    if ( o instanceof Table ) {
                        currentTable = (Table) o;

                        this.log( "processing" + currentTable );

                        if ( this.currentBatchSize > 0 ) {
                            /* Commit outstanding rows from previous table */

                            this.adaptor.getConn().commit();

                            this.printThroughput();
                            this.currentBatchSize = 0;
                        }

                        /* refresh the connection */
                        this.adaptor.reconnect();
                        this.adaptor.getConn().setAutoCommit( false );

                        /*
                         * Arguably, there's no need to flush the commit buffer
                         * after every table, but I like it because it feels
                         * tidy.
                         */
                        this.currentBatchSize = 0;
                        this.currentRowNumber = 0;

                        if ( currentTable.isTruncate() ) {
                            this.log( "truncating " + currentTable );
                            stmt = this.adaptor.getConn().prepareStatement( "TRUNCATE TABLE " + currentTable );
                            stmt.execute();
                        }

                        this.tableStartTs = System.currentTimeMillis();
                    }
                    else if ( o instanceof ResultSetMetaData ) {

                        this.log( "received metadata for " + currentTable );

                        meta = (ResultSetMetaData) o;
                        columnCount = meta.getColumnCount();

                        String sql = currentTable.buildDestinationInsert( columnCount );
                        stmt = this.adaptor.getConn().prepareStatement( sql );
                    }
                    else if ( o instanceof ArrayList ) {

                        ArrayList<?> a = (ArrayList<?>) o;

                        /* One counter for ArrayList access, one for JDBC access */
                        for ( int i = 0, j = 1; i < columnCount; i++, j++ ) {

                            try {
                                stmt.setObject( j, a.get( i ), meta.getColumnType( j ) );
                            }
                            catch ( SQLException e ) {
                                /* Sometimes data in a shonky remote system
                                 * is rejected by a more sane destination
                                 * system. Translate this data into
                                 * something that will fit. */
                                if ( e.getMessage().contains( "Only dates between" ) ) {

                                    if ( meta.isNullable( j ) == ResultSetMetaData.columnNullable ) {
                                        this.log( "Casting bad data to null: " + a.get( i ) );
                                        stmt.setObject( j, null, meta.getColumnType( j ) );
                                    }
                                    else {
                                        this.log( "Casting bad data to 0000-01-01: " + a.get( i ) );
                                        stmt.setObject( j, new java.sql.Date( -64376208000L ), meta.getColumnType( j ) );
                                    }
                                }
                                else {
                                    throw e;
                                }
                            }
                        }

                        stmt.execute();

                        this.currentBatchSize++;
                        this.currentRowNumber++;

                        if ( this.currentBatchSize == this.config.getBatchSize() ) {
                            /*
                             * We've reached our non-committed limit. Send the
                             * requests to the destination server.
                             */

                            this.adaptor.getConn().commit();

                            this.printThroughput();
                            this.currentBatchSize = 0;
                        }
                    }
                    else if ( o instanceof QueueFinished ) {
                        if ( this.currentBatchSize > 0 ) {
                            /* Commit outstanding rows from previous table */

                            this.adaptor.getConn().commit();

                            this.printThroughput();

                            this.log();
                            this.log( "completed" );
                        }

                        /* Exit while loop */
                        break;
                    }
                    else {
                        throw new RuntimeException( "Unexpected obeject in queue: " + o.getClass() );
                    }
                }
                catch ( InterruptedException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch ( SQLException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                this.adaptor.close();
            }
            catch ( SQLException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static final class QueueFinished {
        /*
         * This only exists as a completely type-safe value in "instanceof"
         * expressions
         */
    }
}
