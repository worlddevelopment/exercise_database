//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:03 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.PresentationML.PresentationDocument;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.HandoutMasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.MasterLayoutManager;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.MasterMapping;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.NotesMasterMapping;
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
public class ConversionContext   
{
    private PresentationDocument _pptx;
    private XmlWriterSettings _writerSettings = new XmlWriterSettings();
    private PowerpointDocument _ppt;
    private HashMap<Long,MasterMapping> MasterIdToMapping = new HashMap<Long,MasterMapping>();
    private HashMap<Long,NotesMasterMapping> NotesMasterIdToMapping = new HashMap<Long,NotesMasterMapping>();
    private HashMap<Long,HandoutMasterMapping> HandoutMasterIdToMapping = new HashMap<Long,HandoutMasterMapping>();
    public HashMap<Long,String> AddedImages = new HashMap<Long,String>();
    public int lastImageID = 0;
    /**
    * The source of the conversion.
    */
    public PowerpointDocument getPpt() throws Exception {
        return _ppt;
    }

    public void setPpt(PowerpointDocument value) throws Exception {
        _ppt = value;
    }

    /**
    * This is the target of the conversion.
    * The result will be written to the parts of this document.
    */
    public PresentationDocument getPptx() throws Exception {
        return _pptx;
    }

    public void setPptx(PresentationDocument value) throws Exception {
        _pptx = value;
    }

    /**
    * The settings of the XmlWriter which writes to the part
    */
    public XmlWriterSettings getWriterSettings() throws Exception {
        return _writerSettings;
    }

    public void setWriterSettings(XmlWriterSettings value) throws Exception {
        _writerSettings = value;
    }

    public ConversionContext(PowerpointDocument ppt) throws Exception {
        this.setPpt(ppt);
    }

    /**
    * Registers a NotesMasterMapping so it can later be looked up by its master ID.
    * 
    *  @param masterId Master id with which to associate the MasterMapping.
    *  @param mapping MasterMapping to be registered.
    */
    public void registerNotesMasterMapping(long masterId, NotesMasterMapping mapping) throws Exception {
        this.NotesMasterIdToMapping.put(masterId, mapping);
    }

    /**
    * Registers a HandoutMasterMapping so it can later be looked up by its master ID.
    * 
    *  @param masterId Master id with which to associate the MasterMapping.
    *  @param mapping MasterMapping to be registered.
    */
    public void registerHandoutMasterMapping(long masterId, HandoutMasterMapping mapping) throws Exception {
        this.HandoutMasterIdToMapping.put(masterId, mapping);
    }

    /**
    * Returns the NotesMasterMapping associated with the specified master ID.
    * 
    *  @param masterId Master ID for which to find a MasterMapping.
    *  @return Found MasterMapping or null if none was found.
    */
    public NotesMasterMapping getNotesMasterMappingByMasterId(long masterId) throws Exception {
        return this.NotesMasterIdToMapping.get(masterId);
    }

    /**
    * Returns the HandoutMasterMapping associated with the specified master ID.
    * 
    *  @param masterId Master ID for which to find a MasterMapping.
    *  @return Found MasterMapping or null if none was found.
    */
    public HandoutMasterMapping getHandoutMasterMappingByMasterId(long masterId) throws Exception {
        return this.HandoutMasterIdToMapping.get(masterId);
    }

    /**
    * Registers a MasterMapping so it can later be looked up by its master ID.
    * 
    *  @param masterId Master id with which to associate the MasterMapping.
    *  @param mapping MasterMapping to be registered.
    */
    public void registerMasterMapping(long masterId, MasterMapping mapping) throws Exception {
        this.MasterIdToMapping.put(masterId, mapping);
    }

    /**
    * Returns the MasterMapping associated with the specified master ID.
    * 
    *  @param masterId Master ID for which to find a MasterMapping.
    *  @return Found MasterMapping or null if none was found.
    */
    public MasterMapping getMasterMappingByMasterId(long masterId) throws Exception {
        return this.MasterIdToMapping.get(masterId);
    }

    /**
    * Returns the MasterMapping associated with the specified master ID if it exists.
    * Else a new MasterMapping is created.
    * 
    *  @param masterId Master ID for which to find or create a MasterMapping.
    *  @return Found or created MasterMapping.
    */
    public MasterMapping getOrCreateMasterMappingByMasterId(long masterId) throws Exception {
        if (!this.MasterIdToMapping.containsKey(masterId))
            this.MasterIdToMapping.put(masterId, new MasterMapping(this));
         
        return this.MasterIdToMapping.get(masterId);
    }

    /**
    * Returns the NotesMasterMapping associated with the specified master ID if it exists.
    * Else a new NotesMasterMapping is created.
    * 
    *  @param masterId Master ID for which to find or create a NotesMasterMapping.
    *  @return Found or created NotesMasterMapping.
    */
    public NotesMasterMapping getOrCreateNotesMasterMappingByMasterId(long masterId) throws Exception {
        if (!this.NotesMasterIdToMapping.containsKey(masterId))
            this.NotesMasterIdToMapping.put(masterId, new NotesMasterMapping(this));
         
        return this.NotesMasterIdToMapping.get(masterId);
    }

    /**
    * Returns the HandoutMasterMapping associated with the specified master ID if it exists.
    * Else a new HandoutMasterMapping is created.
    * 
    *  @param masterId Master ID for which to find or create a HandoutMasterMapping.
    *  @return Found or created HandoutMasterMapping.
    */
    public HandoutMasterMapping getOrCreateHandoutMasterMappingByMasterId(long masterId) throws Exception {
        if (!this.HandoutMasterIdToMapping.containsKey(masterId))
            this.HandoutMasterIdToMapping.put(masterId, new HandoutMasterMapping(this));
         
        return this.HandoutMasterIdToMapping.get(masterId);
    }

    protected HashMap<Long,MasterLayoutManager> MasterIdToLayoutManager = new HashMap<Long,MasterLayoutManager>();
    public MasterLayoutManager getOrCreateLayoutManagerByMasterId(long masterId) throws Exception {
        if (!this.MasterIdToLayoutManager.containsKey(masterId))
            this.MasterIdToLayoutManager.put(masterId, new MasterLayoutManager(this,masterId));
         
        return this.MasterIdToLayoutManager.get(masterId);
    }

}


