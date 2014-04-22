//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:08 AM
//

package DIaLOGIKa.b2xtranslator.DocFileFormat;


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
public class TextBoundary   
{
    public static final char ParagraphEnd = (char)13;
    public static final char HardLineBreak = (char)11;
    public static final char BreakingHyphen = (char)4;
    public static final char NonRequiredHyphen = (char)31;
    public static final char NonBreakingHyphen = (char)30;
    public static final char NonBreakingSpace = (char)160;
    public static final char Space = (char)32;
    public static final char PageBreakOrSectionMark = (char)12;
    public static final char ColumnBreak = (char)14;
    public static final char Tab = (char)9;
    public static final char FieldBeginMark = (char)19;
    public static final char FieldEndMark = (char)21;
    public static final char FieldSeperator = (char)20;
    public static final char CellOrRowMark = (char)7;
    //Special Characters (chp.fSpec == 1)
    public static final char CurrentPageNumber = (char)0;
    public static final char Picture = (char)1;
    public static final char AutoNumberedFootnoteReference = (char)2;
    public static final char FootnoteSeparator = (char)3;
    public static final char FootnoteContinuation = (char)4;
    public static final char AnnotationReference = (char)5;
    public static final char LineNumber = (char)6;
    public static final char HandAnnotationPicture = (char)7;
    public static final char Symbol = (char)40;
}


