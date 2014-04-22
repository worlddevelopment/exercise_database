//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:39 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeShapeTypeAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.ProtectionBooleans;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.Shapetypes.ShapeType;
import DIaLOGIKa.b2xtranslator.Tools.TraceLogger;
import java.util.HashMap;

public class ShapeType   implements IVisitable
{
    public enum JoinStyle
    {
        miter,
        round,
        bevel,
        none
    }
    //inner Class
    public static class Handle   
    {
        public Handle() throws Exception {
        }

        public Handle(String pos, String xRange) throws Exception {
            this.position = pos;
            this.xrange = xRange;
        }

        public String position = null;
        public String xrange = null;
        public String switchHandle = null;
        public String yrange = null;
        public String polar = null;
        public String radiusrange = null;
    }

    /**
    * This string describes a sequence of commands that define the shape�s path.
    * This string describes both the pSegmentInfo array and pVertices array in the shape�s geometry properties.
    */
    public String Path;
    /**
    * This specifies a list of formulas whose calculated values are referenced by other properties. 
    * Each formula is listed on a separate line. Formulas are ordered, with the first formula having index 0. 
    * This section can be omitted if the shape doesn�t need any guides.
    */
    public CSList<String> Formulas;
    /**
    * Specifies a comma-delimited list of parameters, or adjustment values,
    * used to define values for a parameterized formula. 
    * These values represent the location of an adjust handle and may be
    * referenced by the geometry of an adjust handle or as a parameter guide function.
    */
    public String AdjustmentValues;
    /**
    * These values specify the location of connection points on the shape�s path. 
    * The connection points are defined by a string consisting of pairs of x and y values, delimited by commas.
    */
    public String ConnectorLocations;
    public String ConnectorType;
    public boolean PreferRelative;
    public boolean ExtrusionOk = false;
    /**
    * This section specifies the properties of each adjust handle on the shape. 
    * One adjust handle is specified per line. 
    * The properties for each handle correspond to values of the ADJH structure
    * contained in the pAdjustHandles array in the shape�s geometry properties.
    */
    public CSList<Handle> Handles;
    /**
    * Specifies one or more text boxes inscribed inside the shape. 
    * A textbox is defined by one or more sets of numbers specifying (in order) the left, top, right, and bottom points of the rectangle. 
    * Multiple sets are delimited by a semicolon. 
    * If omitted, the text box is the same as the geometry�s bounding box.
    */
    public String TextboxRectangle;
    /**
    * 
    */
    public boolean ShapeConcentricFill;
    /**
    * Specifies what join style the shape has. 
    * Since there is no UI for changing the join style,
    * all shapes of this type will always have the specified join style.
    */
    public JoinStyle Joins = JoinStyle.miter;
    /**
    * Specifies the (x,y) coordinates of the limo stretch point.
    * Some shapes that have portions that should be constrained to a fixed aspect ratio, are designed with limo-stretch to keep those portions at the fixed aspect ratio.
    */
    public String Limo;
    /**
    * Associated with each connection site, there is a direction which specifies at what angle elbow and curved connectors should attach to it
    */
    public String ConnectorAngles;
    /**
    * Specifies if a shape of this type is filled by default
    */
    public boolean Filled = true;
    /**
    * Specifies if a shape of this type is stroked by default
    */
    public boolean Stroked = true;
    /**
    * Speicfies the locked properties of teh shape.
    * By default nothing is locked.
    */
    public ProtectionBooleans Lock = new ProtectionBooleans(0);
    public boolean LockShapeType;
    public boolean TextPath;
    public boolean TextKerning;
    public long getTypeCode() throws Exception {
        long ret = 0;
        Object[] attrs = this.getClass().GetCustomAttributes(OfficeShapeTypeAttribute.class, false);
        OfficeShapeTypeAttribute attr = null;
        if (attrs.length > 0)
        {
            attr = attrs[0] instanceof OfficeShapeTypeAttribute ? (OfficeShapeTypeAttribute)attrs[0] : (OfficeShapeTypeAttribute)null;
        }
         
        if (attr != null)
        {
            ret = attr.TypeCode;
        }
         
        return ret;
    }

    private static HashMap<Long,Class> TypeToShapeClassMapping = new HashMap<Long,Class>();
    static {
        try
        {
            UpdateTypeToShapeClassMapping(null /* getExecutingAssembly() */, ShapeType.class.Namespace);
        }
        catch (Exception __dummyStaticConstructorCatchVar2)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar2);
        }
    
    }

    public static ShapeType getShapeType(long typeCode) throws Exception {
        ShapeType result;
        Class cls;
        /**
        * Updates the Dictionary used for mapping Office shape type codes to Office ShapeType classes.
        * This is done by querying all classes in the specified assembly filtered by the specified
        * namespace and looking for attributes of type OfficeShapeTypeAttribute.
        * 
        *  @param assembly Assembly to scan
        *  @param ns Namespace to scan or null for all namespaces
        */
        RefSupport<Class> refVar___0 = new RefSupport<Class>();
        boolean boolVar___0 = TypeToShapeClassMapping.TryGetValue(typeCode, refVar___0);
        cls = refVar___0.getValue();
        if (boolVar___0)
        {
            ConstructorInfo constructor = cls.GetConstructor(new Class[]{  });
            if (constructor == null)
            {
                throw new Exception(String.format(StringSupport.CSFmtStrToJFmtStr("Internal error: Could not find a matching constructor for class {0}"),cls));
            }
             
            try
            {
                result = (ShapeType)constructor.Invoke(new Object[]{  });
            }
            catch (TargetInvocationException e)
            {
                TraceLogger.debugInternal(e.InnerException.toString());
                throw e.InnerException;
            }
        
        }
        else
        {
            result = null;
        } 
        return result;
    }

    public static void updateTypeToShapeClassMapping(Assembly assembly, String ns) throws Exception {
        for (Object __dummyForeachVar0 : assembly.GetTypes())
        {
            Class t = (Class)__dummyForeachVar0;
            if (ns == null || StringSupport.equals(t.Namespace, ns))
            {
                Object[] attrs = t.GetCustomAttributes(OfficeShapeTypeAttribute.class, false);
                OfficeShapeTypeAttribute attr = null;
                if (attrs.length > 0)
                {
                    attr = attrs[0] instanceof OfficeShapeTypeAttribute ? (OfficeShapeTypeAttribute)attrs[0] : (OfficeShapeTypeAttribute)null;
                }
                 
                if (attr != null)
                {
                    TypeToShapeClassMapping.put(attr.TypeCode, t);
                }
                 
            }
             
        }
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<ShapeType>)mapping).apply(this);
    }

}


