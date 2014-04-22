//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:56 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;


public enum ParagraphMask
{
    None,
    /**
    * A bit that specifies whether the bulletFlags field of the TextPFException structure that
    * contains this PFMasks exists and whether bulletFlags.fHasBullet is valid.
    */
    HasBullet,
    /**
    * A bit that specifies whether the bulletFlags field of the TextPFException structure that
    * contains this PFMasks exists and whether bulletFlags.fBulletHasFont is valid.
    */
    BulletHasFont,
    /**
    * A bit that specifies whether the bulletFlags field of the TextPFException structure that
    * contains this PFMasks exists and whether bulletFlags. fBulletHasColor is valid.
    */
    BulletHasColor,
    /**
    * A bit that specifies whether the bulletFlags field of the TextPFException structure that
    * contains this PFMasks exists and whether bulletFlags.fBulletHasSize is valid.
    */
    BulletHasSize,
    BulletFlagsFieldExists,
    /**
    * A bit that specifies whether the bulletFontRef field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    BulletFont,
    /**
    * A bit that specifies whether the bulletColor field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    BulletColor,
    /**
    * A bit that specifies whether the bulletSize field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    BulletSize,
    /**
    * A bit that specifies whether the bulletChar field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    BulletChar,
    /**
    * A bit that specifies whether the leftMargin field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    LeftMargin,
    unused9,
    // Bit 9 is reserved
    /**
    * A bit that specifies whether the indent field of the TextPFException structure that
    * contains this PFMasks exists.
    */
    Indent,
    /**
    * A bit that specifies whether the textAlignment field of the TextPFException structure
    * that contains this PFMasks exists.
    */
    Align,
    /**
    * A bit that specifies whether the lineSpacing field of the TextPFException structure
    * that contains this PFMasks exists.
    */
    LineSpacing,
    /**
    * A bit that specifies whether the spaceBefore field of the TextPFException that
    * contains this PFMasks exists.
    */
    SpaceBefore,
    /**
    * A bit that specifies whether the spaceAfter field of the TextPFException
    * structure that contains this PFMasks exists.
    */
    SpaceAfter,
    /**
    * A bit that specifies whether the defaultTabSize field of the TextPFException
    * structure that contains this PFMasks exists.
    */
    DefaultTabSize,
    /**
    * A bit that specifies whether the fontAlign field of the TextPFException
    * structure that contains this PFMasks exists.
    */
    FontAlign,
    /**
    * A bit that specifies whether the wrapFlags field of the TextPFException
    * structure that contains this PFMasks exists and whether wrapFlags.charWrap is valid.
    */
    CharWrap,
    /**
    * A bit that specifies whether the wrapFlags field of the TextPFException
    * structure that contains this PFMasks exists and whether wrapFlags.wordWrap is valid.
    */
    WordWrap,
    /**
    * A bit that specifies whether the wrapFlags field of the TextPFException
    * structure that contains this PFMasks exists and whether wrapFlags.overflow is valid.
    */
    Overflow,
    WrapFlagsFieldExists,
    /**
    * A bit that specifies whether the tabStops field of the TextPFException
    * structure that contains this PFMasks exists.
    */
    TabStops,
    /**
    * A bit that specifies whether the textDirection field of the TextPFException
    * structure that contains this PFMasks exists.
    */
    TextDirection,
    unused22,
    // Bit 22 is reserved
    /**
    * A bit that specifies whether the bulletBlipRef field of the TextPFException9
    * structure that contains this PFMasks exists.
    */
    BulletBlip,
    /**
    * A bit that specifies whether the bulletAutoNumberScheme field of the TextPFException9
    * structure that contains this PFMasks exists.
    */
    BulletScheme,
    /**
    * A bit that specifies whether the fBulletHasAutoNumber field of the TextPFException9
    * structure that contains this PFMasks exists.
    */
    BulletHasScheme
}

