//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:58 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;


public enum SlideLayoutType
{
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
    * The slide is a title slide
    */
    TitleSlide,
    /**
    * Title and body slide
    */
    TitleAndBody,
    /**
    * Title master slide
    */
    TitleMaster,
    __dummyEnum__0,
    // 3 is unused
    /**
    * Master notes layout
    */
    MasterNotes,
    /**
    * Notes title/body layout
    */
    NotesTitleAndBody,
    /**
    * Handout layout, therefore it doesn't have placeholders except header, footer, and date
    */
    Handout,
    /**
    * Only title placeholder
    */
    TitleOnly,
    /**
    * Body of the slide has 2 columns and a title
    */
    TwoColumnsAndTitle,
    /**
    * Slide?s body has 2 rows and a title
    */
    TwoRowsAndTitle,
    /**
    * Body contains 2 columns, right column has 2 rows
    */
    TwoColumnsRightTwoRows,
    /**
    * Body contains 2 columns, left column has 2 rows
    */
    TwoColumnsLeftTwoRows,
    /**
    * Body contains 2 rows, bottom row has 2 columns
    */
    TwoRowsBottomTwoColumns,
    /**
    * Body contains 2 rows, top row has 2 columns
    */
    TwoRowsTopTwoColumns,
    /**
    * 4 objects
    */
    FourObjects,
    /**
    * Big object
    */
    BigObject,
    /**
    * Blank slide
    */
    Blank,
    /**
    * Vertical title on the right, body on the left
    */
    VerticalTitleRightBodyLeft,
    /**
    * Vertical title on the right, body on the left split into 2 rows
    */
    VerticalTitleRightBodyLeftTwoRows
}

