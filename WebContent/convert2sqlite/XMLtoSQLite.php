<?php

/* written as a quick and dirty solution by
Ben Marchbanks - alQemy.com / Magazooms.com
may not run as written - requires editing to process
your specific XML and target SQLite DB Table
*/

	
require_once('databaseConnection.php');
		
foreach($_POST AS $key => $value) { ${$key} = $value; }
foreach($_GET AS $key => $value) { ${$key} = $value; }

error_reporting(E_ERROR | E_PARSE  );
set_time_limit(120);


iconv_set_encoding("input_encoding", "UTF-8");
iconv_set_encoding("output_encoding", "UTF-8");
iconv_set_encoding("internal_encoding", "UTF-8");


$sqliteDB = "my.db";

$newDB = "/var/www/www_magazooms/sqlite/".$sqliteDB;

if(is_file($newDB)){
unlink($newDB);
}

$db = new PDO('sqlite:'.$newDB); 

chmod($newDB, 0775);
chown($newDB,'apache');

$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$table="myTable";
			
$dropTable = "DROP TABLE IF EXISTS ".$table;
$dropRes = $db->exec($dropTable);

//known nodes in the XML to be created as columns in the table
$fldArr = array('col1','col2','col2');

// create the new SQLite table
$sqlCreateStr = "CREATE TABLE ".$table."(";
$sqlCreateStr .= implode(",",$fldArr);
$sqlCreateStr .= ")";
		
$createRes = $db->exec($sqlCreateStr);

//location of xml file to be imported
$xmlPath="my.xml";
		
if(file_exists($xmlPath)){

	//read in the XML data soure
	$xmlText = simplexml_load_file($xmlPath, null, LIBXML_NOCDATA);
	// loop through each child node that represents a record
	foreach ($xmlText -> record as $record) {
    	$f1 = $record->col1;
    	$f2 = $record->col2;
    	$f3 = $record->col3;
    	    	 
    $query = "insert into ".$table." (". implode(',',$fldArr ).") VALUES (\"".$f1."\",\"".$f2."\",\"".$f3."\");";
	$insert = $db->prepare($query);
	
		try{
			$insertRes = $insert->execute();
			$inserted += $insertRes;
			
			}
		catch (PDOException $e){
			die('execute err: '.$e->getMessage());
 				
 			}
		
    }
    // should display the record count to compare with expected result
    print $inserted;
}