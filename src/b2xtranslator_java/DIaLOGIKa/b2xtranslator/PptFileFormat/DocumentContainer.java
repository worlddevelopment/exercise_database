//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.List;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlidePersistAtom;

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
public class DocumentContainer  extends RegularContainer 
{
    /**
    * Collection of SlidePersistAtoms for master slides.
    */
    public CSList<SlidePersistAtom> MasterPersistList = new CSList<SlidePersistAtom>();
    /**
    * Collection of SlidePersistAtoms for notes slides.
    */
    public CSList<SlidePersistAtom> NotesPersistList = new CSList<SlidePersistAtom>();
    /**
    * Collection of SlidePersistAtoms for regular slides.
    */
    public CSList<SlidePersistAtom> SlidePersistList = new CSList<SlidePersistAtom>();
    public SlideListWithText RegularSlideListWithText;
    public List DocInfoListContainer;
    public DocumentContainer(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        for (Object __dummyForeachVar1 : this.allChildrenWithType())
        {
            SlideListWithText collection = (SlideListWithText)__dummyForeachVar1;
            CSList<SlidePersistAtom> target = null;
            switch((DIaLOGIKa.b2xtranslator.PptFileFormat.SlideListWithText.Instances)collection.Instance)
            {
                case CollectionOfMasterSlides: 
                    target = this.MasterPersistList;
                    break;
                case CollectionOfNotesSlides: 
                    target = this.NotesPersistList;
                    break;
                case CollectionOfSlides: 
                    this.RegularSlideListWithText = collection;
                    target = this.SlidePersistList;
                    break;
            
            }
            if (target != null)
            {
                for (Object __dummyForeachVar0 : collection.allChildrenWithType())
                {
                    SlidePersistAtom atom = (SlidePersistAtom)__dummyForeachVar0;
                    target.add(atom);
                }
            }
             
        }
        this.MasterPersistList.Sort();
        this.NotesPersistList.Sort();
        this.SlidePersistList.Sort();
        this.DocInfoListContainer = firstChildWithType();
    }

    public SlidePersistAtom slidePersistAtomForSlideWithIdx(uint idx) throws Exception {
        for (SlidePersistAtom atom : this.SlidePersistList)
            // idx is zero-based, psr-reference is one-based
            if (atom.PersistIdRef == idx + 1)
                return atom;
             
        return null;
    }

}


