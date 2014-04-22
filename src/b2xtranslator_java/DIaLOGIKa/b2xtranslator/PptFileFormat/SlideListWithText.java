//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.System.Collections.LCC.CSList;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.RegularContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ITextDataRecord;
import DIaLOGIKa.b2xtranslator.PptFileFormat.OutlineTextRefAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlidePersistAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TextHeaderAtom;
import java.util.HashMap;

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
public class SlideListWithText  extends RegularContainer 
{
    public enum Instances
    {
        CollectionOfSlides,
        CollectionOfMasterSlides,
        CollectionOfNotesSlides
    }
    /**
    * List of all SlidePersistAtoms of this SlideListWithText.
    */
    public CSList<SlidePersistAtom> SlidePersistAtoms = new CSList<SlidePersistAtom>();
    /**
    * This dictionary manages a list of TextHeaderAtoms for each SlidePersistAtom.
    * 
    * Text of placeholders can appear in the SlideListWithText record.
    * This dictionary is used for associating such text records with the slide they appear on.
    */
    public HashMap<SlidePersistAtom,CSList<TextHeaderAtom>> SlideToPlaceholderTextHeaders = new HashMap<SlidePersistAtom,CSList<TextHeaderAtom>>();
    public SlideListWithText(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        SlidePersistAtom curSpAtom = null;
        TextHeaderAtom curThAtom = null;
        for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r : this.Children)
        {
            SlidePersistAtom spAtom = r instanceof SlidePersistAtom ? (SlidePersistAtom)r : (SlidePersistAtom)null;
            TextHeaderAtom thAtom = r instanceof TextHeaderAtom ? (TextHeaderAtom)r : (TextHeaderAtom)null;
            ITextDataRecord tdRecord = r instanceof ITextDataRecord ? (ITextDataRecord)r : (ITextDataRecord)null;
            if (spAtom != null)
            {
                curSpAtom = spAtom;
                this.SlidePersistAtoms.add(spAtom);
            }
            else if (thAtom != null)
            {
                curThAtom = thAtom;
                if (!this.SlideToPlaceholderTextHeaders.containsKey(curSpAtom))
                    this.SlideToPlaceholderTextHeaders.put(curSpAtom, new CSList<TextHeaderAtom>());
                 
                this.SlideToPlaceholderTextHeaders.get(curSpAtom).add(thAtom);
            }
            else if (tdRecord != null)
            {
                curThAtom.handleTextDataRecord(tdRecord);
            }
               
        }
    }

    public TextHeaderAtom findTextHeaderForOutlineTextRef(OutlineTextRefAtom otrAtom) throws Exception {
        Slide slide = otrAtom.firstAncestorWithType();
        if (slide == null)
            throw new NotSupportedException("Can't find TextHeaderAtom for OutlineTextRefAtom which has no Slide ancestor");
         
        CSList<TextHeaderAtom> thAtoms = this.SlideToPlaceholderTextHeaders.get(slide.PersistAtom);
        return thAtoms.get(otrAtom.Index);
    }

}


