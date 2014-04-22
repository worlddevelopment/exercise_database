//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:03 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlDocument;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideLayoutPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.SlideMasterPart;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PlaceholderEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideLayoutType;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.TitleMasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.Utils;
import java.util.HashMap;

public class MasterLayoutManager   
{
    protected ConversionContext _ctx;
    protected long MasterId;
    /**
    * PPT2007 layouts are stored inline with the master and
    * have an instance id for associating them with slides.
    */
    public HashMap<Long,SlideLayoutPart> InstanceIdToLayoutPart = new HashMap<Long,SlideLayoutPart>();
    /**
    * Pre-PPT2007 layouts are specified in SSlideLayoutAtom
    * as a SlideLayoutType integer value. Each SlideLayoutType
    * can be mapped to a layout XML file together with a list of
    * placeholder types.
    * 
    * This dictionary is used for associating default layout
    * part filenames with layout parts.
    */
    public HashMap<String,SlideLayoutPart> LayoutFilenameToLayoutPart = new HashMap<String,SlideLayoutPart>();
    /**
    * Pre-PPT2007 TitleMaster slides need to be converted to
    * SlideLayoutParts for OOXML. The SlideLayoutParts for
    * TitleMaster slides are stored in this dictionary.
    */
    public HashMap<Long,SlideLayoutPart> TitleMasterIdToLayoutPart = new HashMap<Long,SlideLayoutPart>();
    public HashMap<String,SlideLayoutPart> CodeToLayoutPart = new HashMap<String,SlideLayoutPart>();
    public MasterLayoutManager(ConversionContext ctx, long masterId) throws Exception {
        this._ctx = ctx;
        this.MasterId = masterId;
    }

    public CSList<SlideLayoutPart> getAllLayoutParts() throws Exception {
        CSList<SlideLayoutPart> result = new CSList<SlideLayoutPart>();
        result.addRange(this.InstanceIdToLayoutPart.values());
        result.addRange(this.LayoutFilenameToLayoutPart.values());
        result.addRange(this.TitleMasterIdToLayoutPart.values());
        result.addRange(this.CodeToLayoutPart.values());
        return result;
    }

    public SlideLayoutPart addLayoutPartWithInstanceId(long instanceId) throws Exception {
        SlideMasterPart masterPart = _ctx.getOrCreateMasterMappingByMasterId(this.MasterId).MasterPart;
        SlideLayoutPart layoutPart = masterPart.addSlideLayoutPart();
        this.InstanceIdToLayoutPart.put(instanceId, layoutPart);
        return layoutPart;
    }

    public SlideLayoutPart getLayoutPartByInstanceId(long instanceId) throws Exception {
        return this.InstanceIdToLayoutPart.get(instanceId);
    }

    public SlideLayoutPart getOrCreateLayoutPartByLayoutType(SlideLayoutType type, PlaceholderEnum[] placeholderTypes) throws Exception {
        SlideMasterPart masterPart = _ctx.getOrCreateMasterMappingByMasterId(this.MasterId).MasterPart;
        String layoutFilename = Utils.slideLayoutTypeToFilename(type,placeholderTypes);
        if (!this.LayoutFilenameToLayoutPart.containsKey(layoutFilename))
        {
            XmlDocument slideLayoutDoc = Utils.getDefaultDocument("slideLayouts." + layoutFilename);
            SlideLayoutPart layoutPart = masterPart.addSlideLayoutPart();
            slideLayoutDoc.WriteTo(layoutPart.XmlWriter);
            layoutPart.XmlWriter.Flush();
            this.LayoutFilenameToLayoutPart.put(layoutFilename, layoutPart);
        }
         
        return this.LayoutFilenameToLayoutPart.get(layoutFilename);
    }

    public SlideLayoutPart getOrCreateLayoutPartByCode(String xml) throws Exception {
        SlideMasterPart masterPart = _ctx.getOrCreateMasterMappingByMasterId(this.MasterId).MasterPart;
        if (!this.CodeToLayoutPart.containsKey(xml))
        {
            XmlDocument doc = new XmlDocument();
            doc.loadXml(xml);
            SlideLayoutPart layoutPart = masterPart.addSlideLayoutPart();
            doc.WriteTo(layoutPart.XmlWriter);
            layoutPart.XmlWriter.Flush();
            CodeToLayoutPart.put(xml, layoutPart);
        }
         
        return CodeToLayoutPart.get(xml);
    }

    public SlideLayoutPart getOrCreateLayoutPartForTitleMasterId(long titleMasterId) throws Exception {
        SlideMasterPart masterPart = _ctx.getOrCreateMasterMappingByMasterId(this.MasterId).MasterPart;
        if (!this.TitleMasterIdToLayoutPart.containsKey(titleMasterId))
        {
            Slide titleMaster = _ctx.getPpt().findMasterRecordById(titleMasterId);
            SlideLayoutPart layoutPart = masterPart.addSlideLayoutPart();
            new TitleMasterMapping(_ctx,layoutPart).apply(titleMaster);
            this.TitleMasterIdToLayoutPart.put(titleMasterId, layoutPart);
        }
         
        return this.TitleMasterIdToLayoutPart.get(titleMasterId);
    }

}


