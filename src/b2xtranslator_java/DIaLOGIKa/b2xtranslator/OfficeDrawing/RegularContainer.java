//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.IO.FileMode;
import CS2JNet.System.IO.FileStreamSupport;
import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of DIaLOGIKa nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DIaLOGIKa ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DIaLOGIKa BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
* Regular containers are containers with Record children.
* (There also is containers that only have a zipped XML payload.
*/
public class RegularContainer  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    private static final boolean WRITE_DEBUG_DUMPS = false;
    public CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> Children = new CSList<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>();
    public RegularContainer(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        uint readSize = 0;
        uint idx = 0;
        while (readSize < this.BodySize)
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record child = null;
            try
            {
                child = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.Reader);
                child.SiblingIdx = idx;
                this.Children.add(child);
                child.setParentRecord(this);
                if (child.getDoAutomaticVerifyReadToEnd())
                    child.verifyReadToEnd();
                 
                readSize += child.getTotalSize();
                idx++;
            }
            catch (Exception e)
            {
                if (WRITE_DEBUG_DUMPS)
                {
                    if (child != null)
                    {
                        String filename = String.format(StringSupport.CSFmtStrToJFmtStr("{0}\\{1}.record"),"dumps",child.getIdentifier());
                        FileStreamSupport fs = new FileStreamSupport(filename, FileMode.Create);
                        try
                        {
                            {
                                child.dumpToStream(fs);
                            }
                        }
                        finally
                        {
                            if (fs != null)
                                Disposable.mkDisposable(fs).dispose();
                             
                        }
                    }
                     
                }
                 
                throw e;
            }
        
        }
    }

    public String toString(uint depth) throws Exception {
        StringBuilder result = new StringBuilder(super.toString(depth));
        depth++;
        if (this.Children.size() > 0)
        {
            result.AppendLine();
            result.append(indentationForDepth(depth));
            result.append("Children:");
        }
         
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : this.Children)
        {
            result.AppendLine();
            result.append(record.toString(depth + 1));
        }
        return result.toString();
    }

    /**
    * Finds all children of the given type.
    * Type of child to search for
    *  @return List of children with appropriate type or null if none were found
    */
    public <T extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>CSList<T> allChildrenWithType() throws Exception {
        return (CSList<T>)this.Children.FindAll().<T>ConvertAll();
    }

    /**
    * Finds the first child of the given type.
    * Type of child to search for
    *  @return First child with appropriate type or null if none was found
    */
    public <T extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>T firstChildWithType() throws Exception {
        return (T)this.Children.Find();
    }

    public <T extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>T firstDescendantWithType() throws Exception {
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record child : this.Children)
        {
            if (child instanceof T)
            {
                return child instanceof T ? (T)child : (T)null;
            }
            else if (child instanceof RegularContainer)
            {
                RegularContainer container = child instanceof RegularContainer ? (RegularContainer)child : (RegularContainer)null;
                T hit = container.firstDescendantWithType();
                if (hit != null)
                {
                    return hit;
                }
                 
            }
              
        }
        return null;
    }

    public IEnumerator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> getEnumerator() throws Exception {

        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record recordChild : this.Children)
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record record : recordChild)
    
    }

}


