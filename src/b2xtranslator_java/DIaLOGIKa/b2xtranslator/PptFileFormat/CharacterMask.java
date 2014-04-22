//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:55 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;


public enum CharacterMask
{
    // Bit 10 - 13
    // Bit 14 - 15 are unused
    None,
    IsBold,
    IsItalic,
    IsUnderlined,
    unused3,
    // Bit 3 is unused
    HasShadow,
    HasAsianSmartQuotes,
    //this should be fehint?
    unused6,
    // Bit 6 is unused
    HasHorizonNumRendering,
    //this should be kumi?
    unused8,
    // Bit 8 is unused
    IsEmbossed,
    unused10,
    // Bit 10 is unused
    unused11,
    // Bit 11 is unused
    unused12,
    // Bit 12 is unused
    unused13,
    // Bit 13 is unused
    unused14,
    // Bit 14 is unused
    unused15,
    // Bit 15 is unused
    // Bit 0 - 15 are used for marking style flag presence
    //StyleFlagsFieldPresent = 0xFFFF,
    TypefacePresent,
    SizePresent,
    ColorPresent,
    PositionPresent,
    unused20,
    // Bit 20 is unused
    FEOldTypefacePresent,
    ANSITypefacePresent,
    SymbolTypefacePresent,
    unused24,
    // Bit 24 is unused
    unused25,
    // Bit 25 is unused
    pp11ext,
    StyleFlagsFieldPresent
}

