//
// Translated by CS2J (http://www.cs2j.com): 1/23/2014 2:21:57 AM
//

package docx2tex.Library.Data;

import docx2tex.Library.Data.Breaks;
import docx2tex.Library.Data.Docx2TexAutoConfig;
import docx2tex.Library.Data.LaTeXTags;
import docx2tex.Library.Data.StylePair;
import docx2tex.Library.Data.TagPair;

/**
* Main latex tags
*/
final public class LaTeXTags  extends Docx2TexAutoConfig 
{
    public LaTeXTags() throws Exception {
        setBreaks(new Breaks());
        setStylePair(new StylePair());
        setTagPair(new TagPair());
    }

    public LaTeXTags(LaTeXTags system, LaTeXTags user, LaTeXTags document) throws Exception {
        super(system, user, document);
        setBreaks(new Breaks(system.getBreaks(),user != null ? user.getBreaks() : null,document != null ? document.getBreaks() : null));
        setStylePair(new StylePair(system.getStylePair(),user != null ? user.getStylePair() : null,document != null ? document.getStylePair() : null));
        setTagPair(new TagPair(system.getTagPair(),user != null ? user.getTagPair() : null,document != null ? document.getTagPair() : null));
    }

    /**
    * Heading 1
    */
    private String __Section;
    public String getSection() {
        return __Section;
    }

    public void setSection(String value) {
        __Section = value;
    }

    /**
    * Heading 2
    */
    private String __SubSection;
    public String getSubSection() {
        return __SubSection;
    }

    public void setSubSection(String value) {
        __SubSection = value;
    }

    /**
    * Heading 3
    */
    private String __SubSubSection;
    public String getSubSubSection() {
        return __SubSubSection;
    }

    public void setSubSubSection(String value) {
        __SubSubSection = value;
    }

    /**
    * Breaks
    */
    private Breaks __Breaks;
    public Breaks getBreaks() {
        return __Breaks;
    }

    public void setBreaks(Breaks value) {
        __Breaks = value;
    }

    /**
    * Styles
    */
    private StylePair __StylePair;
    public StylePair getStylePair() {
        return __StylePair;
    }

    public void setStylePair(StylePair value) {
        __StylePair = value;
    }

    /**
    * Tags
    */
    private TagPair __TagPair;
    public TagPair getTagPair() {
        return __TagPair;
    }

    public void setTagPair(TagPair value) {
        __TagPair = value;
    }

    private Boolean __ProcessFigures;
    /**
    * Process figures
    */
    public Boolean getProcessFigures() {
        return __ProcessFigures;
    }

    public void setProcessFigures(Boolean value) {
        __ProcessFigures = value;
    }

    private Boolean __CenterFigures;
    /**
    * Center figures
    */
    public Boolean getCenterFigures() {
        return __CenterFigures;
    }

    public void setCenterFigures(Boolean value) {
        __CenterFigures = value;
    }

    /**
    * Placement of figure
    */
    private String __FigurePlacement;
    public String getFigurePlacement() {
        return __FigurePlacement;
    }

    public void setFigurePlacement(String value) {
        __FigurePlacement = value;
    }

    private Boolean __CenterTables;
    /**
    * Center tables
    */
    public Boolean getCenterTables() {
        return __CenterTables;
    }

    public void setCenterTables(Boolean value) {
        __CenterTables = value;
    }

    /**
    * Placement of table
    */
    private String __TablePlacement;
    public String getTablePlacement() {
        return __TablePlacement;
    }

    public void setTablePlacement(String value) {
        __TablePlacement = value;
    }

    private Boolean __AllowContinuousLists;
    /**
    * Allow continuous lists
    */
    public Boolean getAllowContinuousLists() {
        return __AllowContinuousLists;
    }

    public void setAllowContinuousLists(Boolean value) {
        __AllowContinuousLists = value;
    }

    private Boolean __PutFigureReferences;
    /**
    * Put figure cross-references
    */
    public Boolean getPutFigureReferences() {
        return __PutFigureReferences;
    }

    public void setPutFigureReferences(Boolean value) {
        __PutFigureReferences = value;
    }

    private Boolean __PutTableReferences;
    /**
    * Put table cross-references
    */
    public Boolean getPutTableReferences() {
        return __PutTableReferences;
    }

    public void setPutTableReferences(Boolean value) {
        __PutTableReferences = value;
    }

    private Boolean __PutSectionReferences;
    /**
    * Put section cross-references
    */
    public Boolean getPutSectionReferences() {
        return __PutSectionReferences;
    }

    public void setPutSectionReferences(Boolean value) {
        __PutSectionReferences = value;
    }

    private Boolean __PutListingReferences;
    /**
    * Put listing cross-references
    */
    public Boolean getPutListingReferences() {
        return __PutListingReferences;
    }

    public void setPutListingReferences(Boolean value) {
        __PutListingReferences = value;
    }

}


