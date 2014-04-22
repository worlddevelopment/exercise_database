//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:10 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.StringSupport;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CommandTable;
import DIaLOGIKa.b2xtranslator.DocFileFormat.CustomToolbarWrapper;
import DIaLOGIKa.b2xtranslator.DocFileFormat.KeyMapEntry;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.ConversionContext;
import java.io.InputStream;

public class CommandTableMapping  extends AbstractOpenXmlMapping implements IMapping<CommandTable>
{
    private CommandTable _tcg;
    private ConversionContext _ctx;
    public CommandTableMapping(ConversionContext ctx) throws Exception {
        super(XmlWriter.Create(ctx.getDocx().getMainDocumentPart().getCustomizationsPart().GetStream(), ctx.getWriterSettings()));
        _ctx = ctx;
    }

    public void apply(CommandTable tcg) throws Exception {
        _tcg = tcg;
        _writer.WriteStartElement("wne", "tcg", OpenXmlNamespaces.MicrosoftWordML);
        //write the keymaps
        _writer.WriteStartElement("wne", "keymaps", OpenXmlNamespaces.MicrosoftWordML);
        for (int i = 0;i < tcg.KeyMapEntries.size();i++)
        {
            writeKeyMapEntry(tcg.KeyMapEntries.get(i));
        }
        _writer.writeEndElement();
        //write the toolbars
        if (tcg.CustomToolbars != null)
        {
            _writer.WriteStartElement("wne", "toolbars", OpenXmlNamespaces.MicrosoftWordML);
            writeToolbar(tcg.CustomToolbars);
            _writer.writeEndElement();
        }
         
        _writer.writeEndElement();
        _writer.Flush();
    }

    private void writeToolbar(CustomToolbarWrapper toolbars) throws Exception {
        //write the xml
        _writer.WriteStartElement("wne", "toolbarData", OpenXmlNamespaces.MicrosoftWordML);
        _writer.WriteAttributeString("r", "id", OpenXmlNamespaces.Relationships, _ctx.getDocx().getMainDocumentPart().getCustomizationsPart().getToolbarsPart().RelIdToString);
        _writer.writeEndElement();
        //copy the toolbar
        InputStream s = _ctx.getDocx().getMainDocumentPart().getCustomizationsPart().getToolbarsPart().GetStream();
        s.write(toolbars.getRawBytes(),0,toolbars.getRawBytes().length);
    }

    private void writeKeyMapEntry(KeyMapEntry kme) throws Exception {
        _writer.WriteStartElement("wne", "keymap", OpenXmlNamespaces.MicrosoftWordML);
        //primary KCM
        if (kme.kcm1 > 0)
        {
            _writer.WriteAttributeString("wne", "kcmPrimary", OpenXmlNamespaces.MicrosoftWordML, String.format(StringSupport.CSFmtStrToJFmtStr("{0:x4}"),kme.kcm1));
        }
         
        _writer.WriteStartElement("wne", "macro", OpenXmlNamespaces.MicrosoftWordML);
        _writer.WriteAttributeString("wne", "macroName", OpenXmlNamespaces.MicrosoftWordML, _tcg.MacroNames.get(kme.paramCid.ibstMacro));
        _writer.writeEndElement();
        _writer.writeEndElement();
    }

}


