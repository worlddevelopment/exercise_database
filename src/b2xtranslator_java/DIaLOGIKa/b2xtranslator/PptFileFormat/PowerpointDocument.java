//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:57 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import CS2JNet.JavaSupport.Collections.Generic.IteratorSupport;
import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Collections.LCC.IEnumerable;
import CS2JNet.System.Collections.LCC.IEnumerator;
import CS2JNet.System.Reflection.Assembly;
import CS2JNet.System.Text.EncodingSupport;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.BinaryDocument;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IVisitable;
import DIaLOGIKa.b2xtranslator.OfficeDrawing.InvalidRecordException;
import DIaLOGIKa.b2xtranslator.PptFileFormat.CurrentUserAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DocumentAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.DocumentContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExObjListContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleEmbedContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleObjAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExOleObjStgAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Handout;
import DIaLOGIKa.b2xtranslator.PptFileFormat.InvalidStreamException;
import DIaLOGIKa.b2xtranslator.PptFileFormat.MainMaster;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Note;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PersistDirectoryAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PersistDirectoryEntry;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Pictures;
import DIaLOGIKa.b2xtranslator.PptFileFormat.PowerpointDocument;
import DIaLOGIKa.b2xtranslator.PptFileFormat.Slide;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlidePersistAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.UserEditAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.VBAInfoContainer;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Common.StreamNotFoundException;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.StructuredStorageReader;
import DIaLOGIKa.b2xtranslator.StructuredStorage.Reader.VirtualStream;
import java.util.HashMap;
import java.util.Iterator;

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
public class PowerpointDocument  extends BinaryDocument implements IVisitable, IEnumerable<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>
{
    static {
        try
        {
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.UpdateTypeToRecordClassMapping(null /* getExecutingAssembly() */, PowerpointDocument.class.Namespace);
        }
        catch (Exception __dummyStaticConstructorCatchVar0)
        {
            throw new ExceptionInInitializerError(__dummyStaticConstructorCatchVar0);
        }
    
    }

    /**
    * The stream "Document Summary Information"
    */
    public VirtualStream DocumentSummaryInformationStream;
    /**
    * The stream "PowerPoint Document"
    */
    public VirtualStream PowerpointDocumentStream;
    /**
    * The stream "Current User"
    */
    public VirtualStream CurrentUserStream;
    /**
    * Atom containing information about the last user to edit this document and a reference to that last edit.
    */
    public CurrentUserAtom CurrentUserAtom;
    /**
    * The stream "Pictures"
    */
    public VirtualStream PicturesStream;
    /**
    * Container containing all media elements inside the Pictures stream.
    */
    public Pictures PicturesContainer;
    /**
    * The last edit done to this document.
    */
    public UserEditAtom LastUserEdit;
    /**
    * The persist object directory is used for mapping persist object identifiers to document stream offsets.
    */
    public HashMap<Long,Long> PersistObjectDirectory = new HashMap<Long,Long>();
    /**
    * The DocumentContainer record for this document.
    */
    public DocumentContainer DocumentRecord;
    /**
    * List of all main (regular) master records for this document.
    */
    public CSList<MainMaster> MainMasterRecords = new CSList<MainMaster>();
    /**
    * List of all notes master records for this document.
    */
    public CSList<Note> NotesMasterRecords = new CSList<Note>();
    /**
    * List of all notes master records for this document.
    */
    public CSList<Handout> HandoutMasterRecords = new CSList<Handout>();
    /**
    * List of title master records for this document.
    */
    public CSList<Slide> TitleMasterRecords = new CSList<Slide>();
    /**
    * Dictionary used for finding MasterRecords (title / main masters) by master id.
    */
    private HashMap<Long,Slide> MasterRecordsById = new HashMap<Long,Slide>();
    /**
    * List of all slide records for this document.
    */
    public CSList<Slide> SlideRecords = new CSList<Slide>();
    /**
    * List of all note records for this document.
    */
    public CSList<Note> NoteRecords = new CSList<Note>();
    /**
    * List of all external OLE object records for this document.
    */
    public HashMap<Integer,ExOleEmbedContainer> OleObjects = new HashMap<Integer,ExOleEmbedContainer>();
    /**
    * The VBA Project Structured Storage
    */
    public ExOleObjStgAtom VbaProject;
    public PowerpointDocument(StructuredStorageReader file) throws Exception {
        try
        {
            this.CurrentUserStream = file.getStream("Current User");
            DIaLOGIKa.b2xtranslator.OfficeDrawing.Record rec = DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.CurrentUserStream);
            if (rec instanceof CurrentUserAtom)
            {
                this.CurrentUserAtom = (CurrentUserAtom)rec;
            }
            else
            {
                this.CurrentUserStream.setPosition(0);
                byte[] bytes = new byte[this.CurrentUserStream.getLength()];
                this.CurrentUserStream.read(bytes);
                String s = new String(bytes, EncodingSupport.GetEncoder("UTF-8").getString()).replace("\0", "");
            } 
        }
        catch (InvalidRecordException e)
        {
            throw new InvalidStreamException("Current user stream is not valid",e);
        }

        // Optional 'Pictures' stream
        if (file.getFullNameOfAllStreamEntries().Contains("\\Pictures"))
        {
            try
            {
                this.PicturesStream = file.getStream("Pictures");
                this.PicturesContainer = new Pictures(new BinaryReader(this.PicturesStream), (uint)this.PicturesStream.getLength(), 0, 0, 0);
            }
            catch (InvalidRecordException e)
            {
                throw new InvalidStreamException("Pictures stream is not valid",e);
            }
        
        }
         
        this.PowerpointDocumentStream = file.getStream("PowerPoint Document");
        try
        {
            this.DocumentSummaryInformationStream = file.getStream("DocumentSummaryInformation");
            scanDocumentSummaryInformation();
        }
        catch (StreamNotFoundException e)
        {
        }

        //ignore
        if (this.CurrentUserAtom != null)
        {
            this.PowerpointDocumentStream.Seek(this.CurrentUserAtom.OffsetToCurrentEdit, SeekOrigin.Begin);
            this.LastUserEdit = (UserEditAtom)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.PowerpointDocumentStream);
        }
         
        this.constructPersistObjectDirectory();
        this.identifyDocumentPersistObject();
        this.identifyMasterPersistObjects();
        this.identifySlidePersistObjects();
        this.identifyOlePersistObjects();
        this.identifyVbaProjectObject();
    }

    private void scanDocumentSummaryInformation() throws Exception {
        BinaryReader s = new BinaryReader(this.DocumentSummaryInformationStream);
        int ByteOrder = s.ReadInt16();
        uint Version = s.ReadUInt16();
        int SystemIdentifier = s.ReadInt32();
        byte[] CLSID = new byte[16];
        CLSID = s.ReadBytes(16);
        uint NumPropertySets = s.ReadUInt32();
        byte[] FMTID0 = new byte[16];
        FMTID0 = s.ReadBytes(16);
        uint Offset0 = s.ReadUInt32();
        uint Offset1 = 0;
        if (NumPropertySets > 1)
        {
            byte[] FMTID1 = new byte[16];
            FMTID1 = s.ReadBytes(16);
            Offset1 = s.ReadUInt32();
        }
         
        //start of PropertySet
        uint Size = s.ReadUInt32();
        uint NumProperties = s.ReadUInt32();
        uint id;
        uint offset;
        HashMap<Long,Long> Offsets = new HashMap<Long,Long>();
        for (int i = 0;i < NumProperties;i++)
        {
            id = s.ReadUInt32();
            offset = s.ReadUInt32();
            Offsets.put(id, offset);
        }
        //start of PropertySet2
        if (Offset1 > 0)
        {
            s.BaseStream.Seek(Offset1, 0);
            Size = s.ReadUInt32();
            NumProperties = s.ReadUInt32();
            HashMap<Long,Long> Offsets2 = new HashMap<Long,Long>();
            for (int i = 0;i < NumProperties;i++)
            {
                id = s.ReadUInt32();
                offset = s.ReadUInt32();
                Offsets2.put(id, offset);
            }
        }
         
        for (uint idKey : CollectionSupport.mk(Offsets.keySet()))
        {
            s.BaseStream.Seek(Offsets.get(idKey) + Offset0, 0);
            int Type = s.ReadInt16();
            int Padding = s.ReadInt16();
            switch(Type)
            {
                case 0x0: 
                case 0x1: 
                    break;
                case 0x2: 
                    //empty
                    //16 bit signed int followed by zero padding to 4 bytes
                    int v = s.ReadInt16();
                    break;
                case 0x3: 
                    //32 bit signed integer
                    int v2 = s.ReadInt32();
                    if (idKey == 23)
                    {
                        int version = BitConverter.ToInt16(BitConverter.GetBytes(v2), 2);
                    }
                     
                    break;
                case 0x4: 
                    //4 byte float
                    float v3 = s.ReadSingle();
                    break;
                case 0x5: 
                    //8 byte float
                    double v4 = s.ReadDouble();
                    break;
                case 0x6: 
                    //CURRENCY
                    long v5 = s.ReadInt64();
                    break;
                case 0x7: 
                    //DATE
                    double v6 = s.ReadDouble();
                    break;
                case 0x8: 
                case 0x1e: 
                    //CodePageString
                    int v7 = s.ReadInt32();
                    //if CodePage is CP_WINUNICODE: 16 bit characters, else 8 bit characters
                    String st = EncodingSupport.GetEncoder("ASCII").GetString(s.ReadBytes(v7));
                    break;
                case 0xA: 
                    //32 bit uint
                    uint v8 = s.ReadUInt32();
                    break;
                case 0xB: 
                    //VARIANT_BOOL
                    boolean v9 = s.ReadBoolean();
                    break;
                case 0xE: 
                    //DECIMAL
                    int wReserved = s.ReadInt16();
                    byte scale = s.ReadByte();
                    byte sign = s.ReadByte();
                    int Hi32 = s.ReadInt32();
                    long Lo64 = s.ReadInt64();
                    break;
                case 0x10: 
                    //1 byte signed int
                    int v10 = (int)s.ReadByte();
                    break;
                case 0x11: 
                    //1 byte unsigned int
                    uint v11 = (uint)s.ReadByte();
                    break;
                case 0x12: 
                    //2 byte unsigned int
                    uint v12 = s.ReadUInt16();
                    break;
                case 0x13: 
                case 0x17: 
                    //4 byte unsigned int
                    uint v13 = s.ReadUInt32();
                    break;
                case 0x14: 
                    //8 byte int
                    long v14 = s.ReadInt64();
                    break;
                case 0x15: 
                    //8 byte unsigned int
                    UInt64 v15 = s.ReadUInt64();
                    break;
                case 0x16: 
                    //4 byte int
                    int v16 = s.ReadInt32();
                    break;
                case 0x1f: 
                    //UnicodeString
                    String st2 = s.ReadString();
                    break;
                case 0x40: 
                    //FILETIME
                    int dwLowDateTime = s.ReadInt32();
                    int dwHighDateTime = s.ReadInt32();
                    break;
                default: 
                    break;
            
            }
        }
    }

    /**
    * Returns the slide or main master with the specified masterId or null if none exists.
    * 
    *  @param masterId id of master to find
    *  @return Slide or main master with the specified masterId or null if none exists
    */
    public Slide findMasterRecordById(long masterId) throws Exception {
        return this.MasterRecordsById.get(masterId);
    }

    /**
    * Tries to find a record with the supplied persistId and type in the PersistObjectDirectory, reads it and returns it.
    * Type of record
    *  @param persistId persist id of record to look up
    *  @return Matching record of given type or null
    */
    public <T extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record>T getPersistObject(uint persistId) throws Exception {
        if (!this.PersistObjectDirectory.containsKey(persistId))
            return null;
         
        long offset = this.PersistObjectDirectory.get(persistId);
        this.PowerpointDocumentStream.Seek(offset, SeekOrigin.Begin);
        return (T)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.PowerpointDocumentStream);
    }

    /**
    * Find the root DocumentContainer record for this presentation.
    * 
    * This is done by looking up the document persist id reference of the last user edit in the persist object directory.
    */
    private void identifyDocumentPersistObject() throws Exception {
        try
        {
            this.DocumentRecord = this.getPersistObject(this.LastUserEdit.DocPersistIdRef);
        }
        catch (Exception e)
        {
            throw new InvalidStreamException();
        }
    
    }

    /**
    * Find all master records for this presentation.
    * 
    * This is done by looking up all persist id references of all SlidePersistAtoms of the DocumentRecord's MasterPersistList
    * in the persist object directory.
    */
    private void identifyMasterPersistObjects() throws Exception {
        for (SlidePersistAtom masterPersistAtom : this.DocumentRecord.MasterPersistList)
        {
            Slide master = this.getPersistObject(masterPersistAtom.PersistIdRef);
            master.PersistAtom = masterPersistAtom;
            if (master instanceof MainMaster)
                this.MainMasterRecords.add((MainMaster)master);
            else
                this.TitleMasterRecords.add(master); 
            this.MasterRecordsById.put(master.PersistAtom.SlideId, master);
        }
        Note noteMaster = this.<Note>GetPersistObject(this.DocumentRecord.firstChildWithType().NotesMasterPersist);
        if (noteMaster != null)
            this.NotesMasterRecords.add(noteMaster);
         
        Handout handoutMaster = this.<Handout>GetPersistObject(this.DocumentRecord.firstChildWithType().HandoutMasterPersist);
        if (handoutMaster != null)
            this.HandoutMasterRecords.add(handoutMaster);
         
    }

    /**
    * Find all Slide records for this presentation.
    * 
    * This is done by looking up all persist id references of all SlidePersistAtoms of the DocumentRecord's MasterPersistList
    * in the persist object directory.
    */
    private void identifySlidePersistObjects() throws Exception {
        for (SlidePersistAtom slidePersistAtom : this.DocumentRecord.SlidePersistList)
        {
            Slide slide = this.getPersistObject(slidePersistAtom.PersistIdRef);
            slide.PersistAtom = slidePersistAtom;
            this.SlideRecords.add(slide);
        }
        for (SlidePersistAtom slidePersistAtom : this.DocumentRecord.NotesPersistList)
        {
            Note note = this.getPersistObject(slidePersistAtom.PersistIdRef);
            note.PersistAtom = slidePersistAtom;
            this.NoteRecords.add(note);
        }
    }

    private void identifyVbaProjectObject() throws Exception {
        try
        {
            VBAInfoContainer vbaInfo = this.DocumentRecord.DocInfoListContainer.firstChildWithType();
            this.VbaProject = getPersistObject(vbaInfo.objStgDataRef);
        }
        catch (Exception e)
        {
        }
    
    }

    private void identifyOlePersistObjects() throws Exception {
        for (Object __dummyForeachVar5 : this.DocumentRecord.allChildrenWithType())
        {
            ExObjListContainer listcontainer = (ExObjListContainer)__dummyForeachVar5;
            for (Object __dummyForeachVar4 : listcontainer.allChildrenWithType())
            {
                ExOleEmbedContainer container = (ExOleEmbedContainer)__dummyForeachVar4;
                ExOleObjAtom atom = container.firstChildWithType();
                if (atom != null)
                {
                    ExOleObjStgAtom stgAtom = this.getPersistObject(atom.persistIdRef);
                    container.stgAtom = stgAtom;
                    this.OleObjects.put(atom.exObjId, container);
                }
                 
            }
        }
    }

    /**
    * Construct the complete persist object directory by traversing all PersistDirectoryAtoms
    * from all UserEditAtoms from the last edit to the first one and adding all _entries of
    * all encountered persist directories to the resulting persist object directory.
    * 
    * When the same persist id occurs multiple times with different offsets the one from the
    * last user edit will end up in the persist object directory, overwriting earlier edits.
    */
    private void constructPersistObjectDirectory() throws Exception {
        CSList<PersistDirectoryAtom> pdAtoms = findLivePersistDirectoryAtoms();
        for (PersistDirectoryAtom pdAtom : pdAtoms)
        {
            for (PersistDirectoryEntry pdEntry : pdAtom.PersistDirEntries)
            {
                uint pid = pdEntry.StartPersistId;
                for (long poff : pdEntry.PersistOffsetEntries)
                {
                    this.PersistObjectDirectory.put(pid, poff);
                    pid++;
                }
            }
        }
    }

    /**
    * Find all live PersistDirectoryAtoms by traversing all UserEditAtoms starting from CurrentUserAtom.
    * 
    *  @return List of PersistDirectoryAtoms. The oldest PersitDirectoryAtom will be the first element of the list.
    */
    private CSList<PersistDirectoryAtom> findLivePersistDirectoryAtoms() throws Exception {
        CSList<PersistDirectoryAtom> result = new CSList<PersistDirectoryAtom>();
        UserEditAtom userEditAtom = this.LastUserEdit;
        while (userEditAtom != null)
        {
            this.PowerpointDocumentStream.Seek(userEditAtom.OffsetPersistDirectory, SeekOrigin.Begin);
            PersistDirectoryAtom pdAtom = (PersistDirectoryAtom)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.PowerpointDocumentStream);
            result.add(0, pdAtom);
            this.PowerpointDocumentStream.Seek(userEditAtom.OffsetLastEdit, SeekOrigin.Begin);
            if (userEditAtom.OffsetLastEdit != 0)
                userEditAtom = (UserEditAtom)DIaLOGIKa.b2xtranslator.OfficeDrawing.Record.readRecord(this.PowerpointDocumentStream);
            else
                userEditAtom = null; 
        }
        return result;
    }

    public String toString() {
        try
        {
            StringBuilder result = new StringBuilder(super.toString());
            result.append("CurrentUserAtom: ");
            result.append((this.CurrentUserAtom.toString()) + System.getProperty("line.separator"));
            result.AppendLine();
            result.append("DocumentRecord: ");
            result.append((this.DocumentRecord.toString()) + System.getProperty("line.separator"));
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r : this.MainMasterRecords)
            {
                result.AppendLine();
                result.append("MainMasterRecord: ");
                result.append((r.toString()) + System.getProperty("line.separator"));
            }
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r : this.TitleMasterRecords)
            {
                result.AppendLine();
                result.append("TitleMasterRecord: ");
                result.append((r.toString()) + System.getProperty("line.separator"));
            }
            for (DIaLOGIKa.b2xtranslator.OfficeDrawing.Record r : this.SlideRecords)
            {
                result.AppendLine();
                result.append("SlideRecord: ");
                result.append((r.toString()) + System.getProperty("line.separator"));
            }
            return result.toString();
        }
        catch (RuntimeException __dummyCatchVar0)
        {
            throw __dummyCatchVar0;
        }
        catch (Exception __dummyCatchVar0)
        {
            throw new RuntimeException(__dummyCatchVar0);
        }
    
    }

    public <T>void convert(T mapping) throws Exception {
        ((IMapping<PowerpointDocument>)mapping).apply(this);
    }

    public IEnumerator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> getEnumerator() throws Exception {
        for (long persistId : CollectionSupport.mk(this.PersistObjectDirectory.keySet()))
        {
        }
    }

    IEnumerator system___Collections___IEnumerable___GetEnumerator() throws Exception {
        IEnumerator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> e = this.getEnumerator();
        return (IEnumerator)e;
    }

    public Iterator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> iterator() {
        Iterator<DIaLOGIKa.b2xtranslator.OfficeDrawing.Record> ret = null;
        try
        {
            ret = IteratorSupport.mk(this.getEnumerator());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ret;
    }

}


