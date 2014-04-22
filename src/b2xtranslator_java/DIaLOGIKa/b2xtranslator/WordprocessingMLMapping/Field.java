//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:13 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Text.RegularExpressions.GroupCollection;
import CS2JNet.System.Text.RegularExpressions.Match;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.TextMark;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.Field;
import java.util.regex.Pattern;

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
public class Field   implements IVisitable
{
    public String FieldCode;
    public String FieldExpansion;
    private Pattern classicFieldFormat = Pattern.compile("^(" + TextMark.FieldBeginMark + ")(.*)(" + TextMark.FieldSeperator + ")(.*)(" + TextMark.FieldEndMark + ")");
    private Pattern shortFieldFormat = Pattern.compile("^(" + TextMark.FieldBeginMark + ")(.*)(" + TextMark.FieldEndMark + ")");
    public Field(char[] fieldChars) throws Exception {
        parse(new String(fieldChars));
    }

    public Field(CSList<Character> fieldChars) throws Exception {
        parse(new String(((char[]) fieldChars.toArray())));
    }

    public Field(String fieldString) throws Exception {
        parse(fieldString);
    }

    private void parse(String field) throws Exception {
        if (this.classicFieldFormat.matcher(field).matches())
        {
            Match classic = Match.mk(this.classicFieldFormat, field);
            this.FieldCode = GroupCollection.mk(classic).get(2).getValue();
            this.FieldExpansion = GroupCollection.mk(classic).get(4).getValue();
        }
        else if (this.shortFieldFormat.matcher(field).matches())
        {
            Match shortField = Match.mk(this.shortFieldFormat, field);
            this.FieldCode = GroupCollection.mk(shortField).get(2).getValue();
        }
          
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<Field>)mapping).apply(this);
    }

}


