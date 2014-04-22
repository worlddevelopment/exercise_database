//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:48:01 AM
//

package DIaLOGIKa.b2xtranslator.PresentationMLMapping;

import CS2JNet.JavaSupport.Collections.Generic.LCC.CollectionSupport;
import CS2JNet.System.Collections.LCC.CSList;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.AbstractOpenXmlMapping;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.PptFileFormat.AnimationInfoAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.AnimationInfoAtom.AnimBuildTypeEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.AnimationInfoContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ClientVisualElementContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ExtTimeNodeContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.ProgBinaryTagDataBlob;
import DIaLOGIKa.b2xtranslator.PptFileFormat.SlideShowSlideInfoAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeCommandBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeConditionAtom;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeConditionAtom.TriggerObjectEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeConditionContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeEffectBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeMotionBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimePropertyList4TimeNodeContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeRotationBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeScaleBehaviorContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeSetBehaviourContainer;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeVariantTypeEnum;
import DIaLOGIKa.b2xtranslator.PptFileFormat.TimeVariantValue;
import DIaLOGIKa.b2xtranslator.PptFileFormat.VisualShapeAtom;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.ConversionContext;
import DIaLOGIKa.b2xtranslator.PresentationMLMapping.SlideMapping;
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
public class AnimationMapping  extends AbstractOpenXmlMapping 
{
    //,
    //IMapping<Dictionary<AnimationInfoContainer,int>>
    protected ConversionContext _ctx;
    public AnimationMapping(ConversionContext ctx, XmlWriter writer) throws Exception {
        super(writer);
        _ctx = ctx;
    }

    public void apply(SlideShowSlideInfoAtom slideshow) throws Exception {
        if (slideshow.fAutoAdvance)
        {
            _writer.WriteStartElement("p", "transition", OpenXmlNamespaces.PresentationML);
            _writer.writeAttributeString("advTm", String.valueOf(slideshow.slideTime));
            _writer.writeEndElement();
        }
         
    }

    public void apply(ProgBinaryTagDataBlob blob, SlideMapping parentMapping, HashMap<AnimationInfoContainer,Integer> animations) throws Exception {
        _parentMapping = parentMapping;
        HashMap<AnimationInfoAtom,Integer> animAtoms = new HashMap<AnimationInfoAtom,Integer>();
        for (AnimationInfoContainer container : CollectionSupport.mk(animations.keySet()))
        {
            AnimationInfoAtom anim = container.firstChildWithType();
            animAtoms.put(anim, animations.get(container));
        }
        ExtTimeNodeContainer c1 = blob.firstChildWithType();
        //if (animAtoms.Count > 0)
        //{
        //    writeTiming(animAtoms, blob);
        //}
        //else
        //{
        if (c1 != null)
        {
            ExtTimeNodeContainer c2 = c1.firstChildWithType();
            if (c2 != null)
            {
                ExtTimeNodeContainer c3 = c2.firstChildWithType();
                if (c3 != null)
                {
                    writeTiming(animAtoms,blob);
                }
                 
            }
             
        }
         
    }

    //}
    private SlideMapping _parentMapping;
    //public void Apply(ProgBinaryTagDataBlob blob, SlideMapping parentMapping)
    //{
    //    _parentMapping = parentMapping;
    //    Dictionary<AnimationInfoAtom, int> animAtoms = new Dictionary<AnimationInfoAtom, int>();
    //    writeTiming(animAtoms, blob);
    //}
    private uint getShapeID(ExtTimeNodeContainer c) throws Exception {
        CSList<Long> lst = new CSList<Long>();
        for (Object __dummyForeachVar26 : c.allChildrenWithType())
        {
            ExtTimeNodeContainer c8 = (ExtTimeNodeContainer)__dummyForeachVar26;
            for (Object __dummyForeachVar25 : c8.allChildrenWithType())
            {
                ExtTimeNodeContainer c9 = (ExtTimeNodeContainer)__dummyForeachVar25;
                for (Object __dummyForeachVar4 : c9.allChildrenWithType())
                {
                    TimeEffectBehaviorContainer c10a = (TimeEffectBehaviorContainer)__dummyForeachVar4;
                    for (Object __dummyForeachVar3 : c10a.allChildrenWithType())
                    {
                        TimeBehaviorContainer c10aa = (TimeBehaviorContainer)__dummyForeachVar3;
                        for (Object __dummyForeachVar2 : c10aa.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar2;
                            for (Object __dummyForeachVar1 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar1;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
                for (Object __dummyForeachVar8 : c9.allChildrenWithType())
                {
                    //return c10aaaa.shapeIdRef;
                    TimeSetBehaviourContainer c10b = (TimeSetBehaviourContainer)__dummyForeachVar8;
                    for (Object __dummyForeachVar7 : c10b.allChildrenWithType())
                    {
                        TimeBehaviorContainer c10aa = (TimeBehaviorContainer)__dummyForeachVar7;
                        for (Object __dummyForeachVar6 : c10aa.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar6;
                            for (Object __dummyForeachVar5 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar5;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
                for (Object __dummyForeachVar12 : c9.allChildrenWithType())
                {
                    //return c10aaaa.shapeIdRef;
                    TimeRotationBehaviorContainer r = (TimeRotationBehaviorContainer)__dummyForeachVar12;
                    for (Object __dummyForeachVar11 : r.allChildrenWithType())
                    {
                        TimeBehaviorContainer rr = (TimeBehaviorContainer)__dummyForeachVar11;
                        for (Object __dummyForeachVar10 : rr.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar10;
                            for (Object __dummyForeachVar9 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar9;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
                for (Object __dummyForeachVar16 : c9.allChildrenWithType())
                {
                    //return c10aaaa.shapeIdRef;
                    TimeCommandBehaviorContainer r = (TimeCommandBehaviorContainer)__dummyForeachVar16;
                    for (Object __dummyForeachVar15 : r.allChildrenWithType())
                    {
                        TimeBehaviorContainer rr = (TimeBehaviorContainer)__dummyForeachVar15;
                        for (Object __dummyForeachVar14 : rr.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar14;
                            for (Object __dummyForeachVar13 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar13;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
                for (Object __dummyForeachVar20 : c9.allChildrenWithType())
                {
                    //return c10aaaa.shapeIdRef;
                    TimeMotionBehaviorContainer r = (TimeMotionBehaviorContainer)__dummyForeachVar20;
                    for (Object __dummyForeachVar19 : r.allChildrenWithType())
                    {
                        TimeBehaviorContainer rr = (TimeBehaviorContainer)__dummyForeachVar19;
                        for (Object __dummyForeachVar18 : rr.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar18;
                            for (Object __dummyForeachVar17 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar17;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
                for (Object __dummyForeachVar24 : c9.allChildrenWithType())
                {
                    //return c10aaaa.shapeIdRef;
                    TimeScaleBehaviorContainer c10a = (TimeScaleBehaviorContainer)__dummyForeachVar24;
                    for (Object __dummyForeachVar23 : c10a.allChildrenWithType())
                    {
                        TimeBehaviorContainer c10aa = (TimeBehaviorContainer)__dummyForeachVar23;
                        for (Object __dummyForeachVar22 : c10aa.allChildrenWithType())
                        {
                            ClientVisualElementContainer c10aaa = (ClientVisualElementContainer)__dummyForeachVar22;
                            for (Object __dummyForeachVar21 : c10aaa.allChildrenWithType())
                            {
                                VisualShapeAtom c10aaaa = (VisualShapeAtom)__dummyForeachVar21;
                                lst.add(c10aaaa.shapeIdRef);
                            }
                        }
                    }
                }
            }
        }
        return lst.get(0);
    }

    //return c10aaaa.shapeIdRef;
    //return 0;
    //public void Apply(Dictionary<AnimationInfoContainer, int> animations)
    //{
    //    Dictionary<AnimationInfoAtom, int> animAtoms = new Dictionary<AnimationInfoAtom, int>();
    //    foreach (AnimationInfoContainer container in animations.Keys)
    //    {
    //        AnimationInfoAtom anim = container.FirstChildWithType<AnimationInfoAtom>();
    //        animAtoms.Add(anim, animations[container]);
    //    }
    //    writeTiming(animAtoms, null);
    //}
    private int lastID = 0;
    private void writeTiming(HashMap<AnimationInfoAtom,Integer> blindAtoms, ProgBinaryTagDataBlob blob) throws Exception {
        lastID = 0;
        _writer.WriteStartElement("p", "timing", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "tnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("dur", "indefinite");
        //_writer.WriteAttributeString("restart", "never");
        _writer.writeAttributeString("nodeType", "tmRoot");
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "seq", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("concurrent", "1");
        _writer.writeAttributeString("nextAc", "seek");
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("dur", "indefinite");
        _writer.writeAttributeString("nodeType", "mainSeq");
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        //foreach (AnimationInfoAtom animinfo in blindAtoms.Keys)
        //{
        //    writePar(animinfo, blindAtoms[animinfo].ToString());
        //}
        if (blob != null)
        {
            ExtTimeNodeContainer c1 = blob.firstChildWithType();
            if (c1 != null)
            {
                ExtTimeNodeContainer c2 = c1.firstChildWithType();
                if (c2 != null)
                {
                    for (Object __dummyForeachVar30 : c2.allChildrenWithType())
                    {
                        //ExtTimeNodeContainer c3 = c2.FirstChildWithType<ExtTimeNodeContainer>();
                        ExtTimeNodeContainer c3 = (ExtTimeNodeContainer)__dummyForeachVar30;
                        if (c3 != null)
                        {
                            int counter = 0;
                            AnimationInfoAtom a;
                            CSList<AnimationInfoAtom> atoms = new CSList<AnimationInfoAtom>();
                            for (AnimationInfoAtom key : CollectionSupport.mk(blindAtoms.keySet()))
                                atoms.add(key);
                            _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
                            _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
                            _writer.writeAttributeString("id", String.valueOf((++lastID)));
                            _writer.writeAttributeString("fill", "hold");
                            _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
                            for (Object __dummyForeachVar28 : c3.allChildrenWithType())
                            {
                                TimeConditionContainer c = (TimeConditionContainer)__dummyForeachVar28;
                                TimeConditionAtom t = c.firstChildWithType();
                                _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
                                long __dummyScrutVar0 = t.triggerEvent;
                                if (__dummyScrutVar0.equals(0x0))
                                {
                                }
                                else //none
                                if (__dummyScrutVar0.equals(0x1))
                                {
                                    //onBegin
                                    _writer.writeAttributeString("evt", "onBegin");
                                }
                                else if (__dummyScrutVar0.equals(0x3))
                                {
                                    //Start
                                    _writer.writeAttributeString("evt", "begin");
                                }
                                else if (__dummyScrutVar0.equals(0x4))
                                {
                                    //End
                                    _writer.writeAttributeString("evt", "end");
                                }
                                else if (__dummyScrutVar0.equals(0x5))
                                {
                                    //Mouse click
                                    _writer.writeAttributeString("evt", "onClick");
                                }
                                else if (__dummyScrutVar0.equals(0x7))
                                {
                                    //Mouse over
                                    _writer.writeAttributeString("evt", "onMouseOver");
                                }
                                else if (__dummyScrutVar0.equals(0x9))
                                {
                                    //OnNext
                                    _writer.writeAttributeString("evt", "onNext");
                                }
                                else if (__dummyScrutVar0.equals(0xa))
                                {
                                    //OnPrev
                                    _writer.writeAttributeString("evt", "onPrev");
                                }
                                else if (__dummyScrutVar0.equals(0xb))
                                {
                                    //Stop audio
                                    _writer.writeAttributeString("evt", "onStopAudio");
                                }
                                else
                                {
                                }         
                                if (t.delay == -1)
                                {
                                    _writer.writeAttributeString("delay", "indefinite");
                                }
                                else
                                {
                                    _writer.writeAttributeString("delay", String.valueOf(t.delay));
                                } 
                                if (t.triggerObject == TriggerObjectEnum.TimeNode)
                                {
                                    _writer.WriteStartElement("p", "tn", OpenXmlNamespaces.PresentationML);
                                    _writer.writeAttributeString("val", String.valueOf(t.id));
                                    _writer.writeEndElement();
                                }
                                 
                                _writer.writeEndElement();
                            }
                            //cond
                            _writer.writeEndElement();
                            //stCondLst
                            _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
                            for (Object __dummyForeachVar29 : c3.allChildrenWithType())
                            {
                                ExtTimeNodeContainer c4 = (ExtTimeNodeContainer)__dummyForeachVar29;
                                a = null;
                                if (atoms.size() > counter)
                                    a = atoms.get(counter);
                                 
                                writePar2(c4,a);
                                counter++;
                            }
                            _writer.writeEndElement();
                            //childTnLst
                            _writer.writeEndElement();
                            //cTn
                            _writer.writeEndElement();
                        }
                         
                    }
                }
                 
            }
             
        }
         
        //par
        //writePar(ShapeID);
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "prevCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("evt", "onPrev");
        _writer.writeAttributeString("delay", "0");
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteElementString("p", "sldTgt", OpenXmlNamespaces.PresentationML, "");
        _writer.writeEndElement();
        //tgtEl
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //prevCondLst
        _writer.WriteStartElement("p", "nextCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("evt", "onNext");
        _writer.writeAttributeString("delay", "0");
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteElementString("p", "sldTgt", OpenXmlNamespaces.PresentationML, "");
        _writer.writeEndElement();
        //tgtEl
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //nextCondLst
        _writer.writeEndElement();
        //seq
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
        //par
        _writer.writeEndElement();
        //tnLst
        if (blindAtoms.size() > 0)
        {
            _writer.WriteStartElement("p", "bldLst", OpenXmlNamespaces.PresentationML);
            for (AnimationInfoAtom animinfo : CollectionSupport.mk(blindAtoms.keySet()))
            {
                _writer.WriteStartElement("p", "bldP", OpenXmlNamespaces.PresentationML);
                _writer.writeAttributeString("spid", String.valueOf(blindAtoms.get(animinfo)));
                _writer.writeAttributeString("grpId", "0");
                if (animinfo.animBuildType == AnimBuildTypeEnum.Level1Build)
                    _writer.writeAttributeString("build", "p");
                 
                if (animinfo.fAnimateBg)
                    _writer.writeAttributeString("animBg", "1");
                 
                _writer.writeEndElement();
            }
            //bldP
            _writer.writeEndElement();
        }
         
        //bldLst
        _writer.writeEndElement();
    }

    //timing
    private void writePar(AnimationInfoAtom animinfo, String ShapeID) throws Exception {
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("fill", "hold");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        if (animinfo.fAutomatic)
        {
            _writer.writeAttributeString("delay", "0");
        }
        else
        {
            _writer.writeAttributeString("delay", "indefinite");
        } 
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("fill", "hold");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("delay", "0");
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("presetID", String.valueOf((animinfo.animEffect + 1)));
        //3
        _writer.writeAttributeString("presetClass", "entr");
        _writer.writeAttributeString("presetSubtype", "10");
        _writer.writeAttributeString("fill", "hold");
        _writer.writeAttributeString("grpId", "0");
        _writer.writeAttributeString("nodeType", "clickEffect");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("delay", "0");
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "set", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("dur", "1");
        _writer.writeAttributeString("fill", "hold");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("delay", "0");
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("spid", ShapeID);
        _writer.writeEndElement();
        //spTgt
        _writer.writeEndElement();
        //tgtEl
        _writer.WriteStartElement("p", "attrNameLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "style.visibility");
        _writer.writeEndElement();
        //attrNameLst
        _writer.writeEndElement();
        //cBhvr
        _writer.WriteStartElement("p", "to", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("val", "visible");
        _writer.writeEndElement();
        //str
        _writer.writeEndElement();
        //to
        _writer.writeEndElement();
        if (true)
        {
            //set
            //TODO: when?
            writeAnimEffect(animinfo,ShapeID);
        }
        else
        {
            writeFlyAnim(animinfo,ShapeID);
        } 
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
        //par
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
        //par
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
    }

    //par
    private void writePar2(ExtTimeNodeContainer container, AnimationInfoAtom animinfo) throws Exception {
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("fill", "hold");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        if (container.firstChildWithType() != null)
        {
            _writer.writeAttributeString("delay", container.firstChildWithType().<TimeConditionAtom>FirstChildWithType().delay.toString());
        }
        else
        {
            _writer.writeAttributeString("delay", "0");
        } 
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "par", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        String filter = "";
        for (Object __dummyForeachVar35 : container.allChildrenWithType())
        {
            ExtTimeNodeContainer c2 = (ExtTimeNodeContainer)__dummyForeachVar35;
            for (Object __dummyForeachVar34 : c2.allChildrenWithType())
            {
                ExtTimeNodeContainer c3 = (ExtTimeNodeContainer)__dummyForeachVar34;
                for (Object __dummyForeachVar33 : c3.allChildrenWithType())
                {
                    TimeEffectBehaviorContainer c4 = (TimeEffectBehaviorContainer)__dummyForeachVar33;
                    for (Object __dummyForeachVar32 : c4.allChildrenWithType())
                    {
                        TimeVariantValue v = (TimeVariantValue)__dummyForeachVar32;
                        if (v.type == TimeVariantTypeEnum.String)
                        {
                            filter = v.stringValue;
                        }
                         
                    }
                }
            }
        }
        if (animinfo != null)
        {
            _writer.writeAttributeString("presetID", String.valueOf((animinfo.animEffect + 1)));
        }
        else
        {
            //3
            _writer.writeAttributeString("presetID", "12");
        } 
        //3
        _writer.writeAttributeString("presetClass", "entr");
        _writer.writeAttributeString("presetSubtype", "4");
        _writer.writeAttributeString("fill", "hold");
        //_writer.WriteAttributeString("grpId", "0");
        boolean nodeTypeWritten = false;
        if (container.firstChildWithType() != null)
        {
            ExtTimeNodeContainer c2 = container.firstChildWithType();
            if (c2.firstChildWithType() != null)
            {
                TimePropertyList4TimeNodeContainer c3 = c2.firstChildWithType();
                TimeVariantValue v = c3.firstChildWithType();
                System.Int32? __dummyScrutVar1 = v.intValue;
                if (__dummyScrutVar1.equals(1))
                {
                    _writer.writeAttributeString("nodeType", "clickEffect");
                    nodeTypeWritten = true;
                }
                else if (__dummyScrutVar1.equals(2))
                {
                    nodeTypeWritten = true;
                    _writer.writeAttributeString("nodeType", "withEffect");
                }
                else if (__dummyScrutVar1.equals(3))
                {
                    _writer.writeAttributeString("nodeType", "afterEffect");
                    nodeTypeWritten = true;
                }
                else
                {
                }   
            }
             
        }
         
        if (!nodeTypeWritten)
            _writer.writeAttributeString("nodeType", "clickEffect");
         
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("delay", "0");
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.WriteStartElement("p", "childTnLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "set", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("additive", "repl");
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("dur", "1000");
        _writer.writeAttributeString("fill", "hold");
        _writer.WriteStartElement("p", "stCondLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cond", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("delay", "0");
        _writer.writeEndElement();
        //cond
        _writer.writeEndElement();
        //stCondLst
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
        uint c4Id = getShapeID(container);
        String ShapeID = "";
        if (this._parentMapping.shapeTreeMapping.spidToId.containsKey((int)c4Id))
        {
            ShapeID = String.valueOf(this._parentMapping.shapeTreeMapping.spidToId.get((int)c4Id));
        }
        else
        {
            for (int sId : CollectionSupport.mk(this._parentMapping.shapeTreeMapping.spidToId.keySet()))
            {
                if (sId > 0)
                {
                    ShapeID = String.valueOf(this._parentMapping.shapeTreeMapping.spidToId.get(sId));
                    break;
                }
                 
            }
        } 
        _writer.writeAttributeString("spid", ShapeID);
        _writer.writeEndElement();
        //spTgt
        _writer.writeEndElement();
        //tgtEl
        _writer.WriteStartElement("p", "attrNameLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "style.visibility");
        _writer.writeEndElement();
        //attrNameLst
        _writer.writeEndElement();
        //cBhvr
        _writer.WriteStartElement("p", "to", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("val", "visible");
        _writer.writeEndElement();
        //str
        _writer.writeEndElement();
        //to
        _writer.writeEndElement();
        //set
        if (filter.length() > 0)
        {
            _writer.WriteStartElement("p", "animEffect", OpenXmlNamespaces.PresentationML);
            _writer.writeAttributeString("transition", "in");
            _writer.writeAttributeString("filter", filter);
            _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
            _writer.writeAttributeString("additive", "repl");
            _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
            _writer.writeAttributeString("id", String.valueOf((++lastID)));
            _writer.writeAttributeString("dur", "500");
            _writer.writeEndElement();
            //cTn
            _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
            _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
            _writer.writeAttributeString("spid", ShapeID);
            _writer.writeEndElement();
            //spTgt
            _writer.writeEndElement();
            //tgtEl
            _writer.writeEndElement();
            //cBhvr
            _writer.writeEndElement();
        }
        else
        {
            //animEffect
            if (animinfo != null)
                if (animinfo.animEffect == 0x0c & animinfo.animEffectDirection > 0x3)
                {
                    writeFlyAnim(animinfo,ShapeID);
                }
                else
                {
                    writeAnimEffect(animinfo,ShapeID);
                } 
             
        } 
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
        //par
        _writer.writeEndElement();
        //childTnLst
        _writer.writeEndElement();
        //cTn
        _writer.writeEndElement();
    }

    //par
    public void writeFlyAnim(AnimationInfoAtom animinfo, String ShapeID) throws Exception {
        //X
        _writer.WriteStartElement("p", "anim", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("calcmode", "lin");
        _writer.writeAttributeString("valueType", "num");
        _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("additive", "base");
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        byte __dummyScrutVar2 = animinfo.animEffectDirection;
        if (__dummyScrutVar2.equals(0xC) || __dummyScrutVar2.equals(0xD) || __dummyScrutVar2.equals(0xE) || __dummyScrutVar2.equals(0xF))
        {
            _writer.writeAttributeString("dur", "5000");
        }
        else
        {
            _writer.writeAttributeString("dur", "500");
        } 
        _writer.writeAttributeString("fill", "hold");
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("spid", ShapeID);
        _writer.writeEndElement();
        //spTgt
        _writer.writeEndElement();
        //tgtEl
        _writer.WriteStartElement("p", "attrNameLst", OpenXmlNamespaces.PresentationML);
        byte __dummyScrutVar3 = animinfo.animEffectDirection;
        if (__dummyScrutVar3.equals(0x10) || __dummyScrutVar3.equals(0x11) || __dummyScrutVar3.equals(0x12) || __dummyScrutVar3.equals(0x13) || __dummyScrutVar3.equals(0x14) || __dummyScrutVar3.equals(0x15))
        {
            _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "ppt_w");
        }
        else
        {
            _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "ppt_x");
        } 
        _writer.writeEndElement();
        //attrNameLst
        _writer.writeEndElement();
        //cBhvr
        _writer.WriteStartElement("p", "tavLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "tav", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("tm", "0");
        _writer.WriteStartElement("p", "val", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        byte __dummyScrutVar4 = animinfo.animEffectDirection;
        if (__dummyScrutVar4.equals(0x0) || __dummyScrutVar4.equals(0x4) || __dummyScrutVar4.equals(0x6))
        {
            _writer.writeAttributeString("val", "0-#ppt_w/2");
        }
        else if (__dummyScrutVar4.equals(0x2) || __dummyScrutVar4.equals(0x5) || __dummyScrutVar4.equals(0x7))
        {
            _writer.writeAttributeString("val", "1+#ppt_w/2");
        }
        else if (__dummyScrutVar4.equals(0x10) || __dummyScrutVar4.equals(0x11) || __dummyScrutVar4.equals(0x12) || __dummyScrutVar4.equals(0x13) || __dummyScrutVar4.equals(0x14) || __dummyScrutVar4.equals(0x15))
        {
            //zoom
            _writer.writeAttributeString("val", "0");
        }
        else
        {
            _writer.writeAttributeString("val", "#ppt_x");
        }   
        _writer.writeEndElement();
        //strVal
        _writer.writeEndElement();
        //val
        _writer.writeEndElement();
        //tav
        _writer.WriteStartElement("p", "tav", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("tm", "100000");
        _writer.WriteStartElement("p", "val", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        byte __dummyScrutVar5 = animinfo.animEffectDirection;
        if (__dummyScrutVar5.equals(0x10) || __dummyScrutVar5.equals(0x11) || __dummyScrutVar5.equals(0x12) || __dummyScrutVar5.equals(0x13) || __dummyScrutVar5.equals(0x14) || __dummyScrutVar5.equals(0x15))
        {
            //zoom
            _writer.writeAttributeString("val", "#ppt_h");
        }
        else
        {
            _writer.writeAttributeString("val", "#ppt_x");
        } 
        _writer.writeEndElement();
        //strVal
        _writer.writeEndElement();
        //val
        _writer.writeEndElement();
        //tav
        _writer.writeEndElement();
        //tavLst
        _writer.writeEndElement();
        //anim
        //Y
        _writer.WriteStartElement("p", "anim", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("calcmode", "lin");
        _writer.writeAttributeString("valueType", "num");
        _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("additive", "base");
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        byte __dummyScrutVar6 = animinfo.animEffectDirection;
        if (__dummyScrutVar6.equals(0xC) || __dummyScrutVar6.equals(0xD) || __dummyScrutVar6.equals(0xE) || __dummyScrutVar6.equals(0xF))
        {
            _writer.writeAttributeString("dur", "5000");
        }
        else
        {
            _writer.writeAttributeString("dur", "500");
        } 
        _writer.writeAttributeString("fill", "hold");
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("spid", ShapeID);
        _writer.writeEndElement();
        //spTgt
        _writer.writeEndElement();
        //tgtEl
        _writer.WriteStartElement("p", "attrNameLst", OpenXmlNamespaces.PresentationML);
        byte __dummyScrutVar7 = animinfo.animEffectDirection;
        if (__dummyScrutVar7.equals(0x10) || __dummyScrutVar7.equals(0x11) || __dummyScrutVar7.equals(0x12) || __dummyScrutVar7.equals(0x13) || __dummyScrutVar7.equals(0x14) || __dummyScrutVar7.equals(0x15))
        {
            _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "ppt_h");
        }
        else
        {
            _writer.WriteElementString("p", "attrName", OpenXmlNamespaces.PresentationML, "ppt_y");
        } 
        _writer.writeEndElement();
        //attrNameLst
        _writer.writeEndElement();
        //cBhvr
        _writer.WriteStartElement("p", "tavLst", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "tav", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("tm", "0");
        _writer.WriteStartElement("p", "val", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        byte __dummyScrutVar8 = animinfo.animEffectDirection;
        if (__dummyScrutVar8.equals(0x1) || __dummyScrutVar8.equals(0x6) || __dummyScrutVar8.equals(0x5) || __dummyScrutVar8.equals(0xd))
        {
            //top
            _writer.writeAttributeString("val", "0-#ppt_h/2");
        }
        else if (__dummyScrutVar8.equals(0x3) || __dummyScrutVar8.equals(0x4) || __dummyScrutVar8.equals(0x7) || __dummyScrutVar8.equals(0xf))
        {
            //bottom
            _writer.writeAttributeString("val", "1+#ppt_h/2");
        }
        else if (__dummyScrutVar8.equals(0x10) || __dummyScrutVar8.equals(0x11) || __dummyScrutVar8.equals(0x12) || __dummyScrutVar8.equals(0x13) || __dummyScrutVar8.equals(0x14) || __dummyScrutVar8.equals(0x15))
        {
            //zoom
            _writer.writeAttributeString("val", "#ppt_h");
        }
        else
        {
            _writer.writeAttributeString("val", "#ppt_y");
        }   
        _writer.writeEndElement();
        //strVal
        _writer.writeEndElement();
        //val
        _writer.writeEndElement();
        //tav
        _writer.WriteStartElement("p", "tav", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("tm", "100000");
        _writer.WriteStartElement("p", "val", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "strVal", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("val", "#ppt_y");
        _writer.writeEndElement();
        //strVal
        _writer.writeEndElement();
        //val
        _writer.writeEndElement();
        //tav
        _writer.writeEndElement();
        //tavLst
        _writer.writeEndElement();
    }

    //anim
    public void writeAnimEffect(AnimationInfoAtom animinfo, String ShapeID) throws Exception {
        _writer.WriteStartElement("p", "animEffect", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("transition", "in");
        byte __dummyScrutVar9 = animinfo.animEffect;
        if (__dummyScrutVar9.equals(0x00))
        {
            //Cut
            byte __dummyScrutVar10 = animinfo.animEffectDirection;
            //not through black
            if (__dummyScrutVar10.equals(0x00) || __dummyScrutVar10.equals(0x02))
            {
                //same as 0x00
                _writer.writeAttributeString("filter", "cut(false)");
            }
            else if (__dummyScrutVar10.equals(0x01))
            {
                //through black
                _writer.writeAttributeString("filter", "cut(true)");
            }
              
        }
        else if (__dummyScrutVar9.equals(0x01))
        {
            //Random
            _writer.writeAttributeString("filter", "random");
        }
        else if (__dummyScrutVar9.equals(0x02))
        {
            //Blinds
            if (animinfo.animEffectDirection == 0x01)
            {
                _writer.writeAttributeString("filter", "blinds(horizontal)");
            }
            else
            {
                _writer.writeAttributeString("filter", "blinds(vertical)");
            } 
        }
        else if (__dummyScrutVar9.equals(0x03))
        {
            //Checker
            if (animinfo.animEffectDirection == 0x00)
            {
                _writer.writeAttributeString("filter", "checkerboard(across)");
            }
            else
            {
                _writer.writeAttributeString("filter", "checkerboard(down)");
            } 
        }
        else if (__dummyScrutVar9.equals(0x04))
        {
            //Cover
            byte __dummyScrutVar11 = animinfo.animEffectDirection;
            if (__dummyScrutVar11.equals(0x00))
            {
                //r->l
                _writer.writeAttributeString("filter", "cover(l)");
            }
            else if (__dummyScrutVar11.equals(0x01))
            {
                //b->t
                _writer.writeAttributeString("filter", "cover(u)");
            }
            else if (__dummyScrutVar11.equals(0x02))
            {
                //l->r
                _writer.writeAttributeString("filter", "cover(r)");
            }
            else if (__dummyScrutVar11.equals(0x03))
            {
                //t->b
                _writer.writeAttributeString("filter", "cover(d)");
            }
            else if (__dummyScrutVar11.equals(0x04))
            {
                //br->tl
                _writer.writeAttributeString("filter", "cover(lu)");
            }
            else if (__dummyScrutVar11.equals(0x05))
            {
                //bl->tr
                _writer.writeAttributeString("filter", "cover(ru)");
            }
            else if (__dummyScrutVar11.equals(0x06))
            {
                //tr->bl
                _writer.writeAttributeString("filter", "cover(ld)");
            }
            else if (__dummyScrutVar11.equals(0x07))
            {
                //tl->br
                _writer.writeAttributeString("filter", "cover(rd)");
            }
                    
        }
        else if (__dummyScrutVar9.equals(0x05))
        {
            //Dissolve
            _writer.writeAttributeString("filter", "dissolve");
        }
        else if (__dummyScrutVar9.equals(0x06))
        {
            //Fade
            _writer.writeAttributeString("filter", "fade");
        }
        else if (__dummyScrutVar9.equals(0x07))
        {
            //Pull
            byte __dummyScrutVar12 = animinfo.animEffectDirection;
            if (__dummyScrutVar12.equals(0x00))
            {
                //r->l
                _writer.writeAttributeString("filter", "pull(l)");
            }
            else if (__dummyScrutVar12.equals(0x01))
            {
                //b->t
                _writer.writeAttributeString("filter", "pull(u)");
            }
            else if (__dummyScrutVar12.equals(0x02))
            {
                //l->r
                _writer.writeAttributeString("filter", "pull(r)");
            }
            else if (__dummyScrutVar12.equals(0x03))
            {
                //t->b
                _writer.writeAttributeString("filter", "pull(d)");
            }
            else if (__dummyScrutVar12.equals(0x04))
            {
                //br->tl
                _writer.writeAttributeString("filter", "pull(lu)");
            }
            else if (__dummyScrutVar12.equals(0x05))
            {
                //bl->tr
                _writer.writeAttributeString("filter", "pull(ru)");
            }
            else if (__dummyScrutVar12.equals(0x06))
            {
                //tr->bl
                _writer.writeAttributeString("filter", "pull(ld)");
            }
            else if (__dummyScrutVar12.equals(0x07))
            {
                //tl->br
                _writer.writeAttributeString("filter", "pull(rd)");
            }
                    
        }
        else if (__dummyScrutVar9.equals(0x08))
        {
            //Random bar
            if (animinfo.animEffectDirection == 0x01)
            {
                _writer.writeAttributeString("filter", "randomBar(horz)");
            }
            else
            {
                _writer.writeAttributeString("filter", "randomBar(vert)");
            } 
        }
        else if (__dummyScrutVar9.equals(0x09))
        {
            //Strips
            byte __dummyScrutVar13 = animinfo.animEffectDirection;
            if (__dummyScrutVar13.equals(0x04))
            {
                //br->tl
                _writer.writeAttributeString("filter", "strips(lu)");
            }
            else if (__dummyScrutVar13.equals(0x05))
            {
                //bl->tr
                _writer.writeAttributeString("filter", "strips(ru)");
            }
            else if (__dummyScrutVar13.equals(0x06))
            {
                //tr->bl
                _writer.writeAttributeString("filter", "strips(ld)");
            }
            else if (__dummyScrutVar13.equals(0x07))
            {
                //tl->br
                _writer.writeAttributeString("filter", "strips(rd)");
            }
                
        }
        else if (__dummyScrutVar9.equals(0x0a))
        {
            //Wipe
            byte __dummyScrutVar14 = animinfo.animEffectDirection;
            if (__dummyScrutVar14.equals(0x00))
            {
                //r->l
                _writer.writeAttributeString("filter", "wipe(l)");
            }
            else if (__dummyScrutVar14.equals(0x01))
            {
                //b->t
                _writer.writeAttributeString("filter", "wipe(u)");
            }
            else if (__dummyScrutVar14.equals(0x02))
            {
                //l->r
                _writer.writeAttributeString("filter", "wipe(r)");
            }
            else if (__dummyScrutVar14.equals(0x03))
            {
                //t->b
                _writer.writeAttributeString("filter", "wipe(d)");
            }
                
        }
        else if (__dummyScrutVar9.equals(0x0b))
        {
            //Zoom (box)
            if (animinfo.animEffectDirection == 0x00)
            {
                _writer.writeAttributeString("filter", "box(out)");
            }
            else
            {
                _writer.writeAttributeString("filter", "box(in)");
            } 
        }
        else if (__dummyScrutVar9.equals(0x0c))
        {
            //Fly
            byte __dummyScrutVar15 = animinfo.animEffectDirection;
            if (__dummyScrutVar15.equals(0x00))
            {
                //from left
                _writer.writeAttributeString("filter", "slide(fromLeft)");
            }
            else if (__dummyScrutVar15.equals(0x01))
            {
                //from top
                _writer.writeAttributeString("filter", "slide(fromTop)");
            }
            else if (__dummyScrutVar15.equals(0x02))
            {
                //from right
                _writer.writeAttributeString("filter", "slide(fromRight)");
            }
            else if (__dummyScrutVar15.equals(0x03))
            {
                //from bottom
                _writer.writeAttributeString("filter", "slide(fromBottom)");
            }
            else //from top left
            //from top right
            //from bottom left
            //from bottom right
            //from left edge of shape / text
            //from bottom edge of shape / text
            //from right edge of shape / text
            //from top edge of shape / text
            //crawl from left
            //crawl from top
            //crawl from right
            //crawl from bottom
            //zoom 0 -> 1
            //zoom 0.5 -> 1
            //zoom 4 -> 1
            //zoom 1.5 -> 1
            //zoom 0 -> 1; screen center -> actual center
            //zoom 4 -> 1; bottom -> actual position
            //stretch center -> l & r
            //stretch l -> r
            //stretch t -> b
            //stretch r -> l
            //stretch b -> t
            //rotate around vertical axis that passes through its center
            if (__dummyScrutVar15.equals(0x04) || __dummyScrutVar15.equals(0x05) || __dummyScrutVar15.equals(0x06) || __dummyScrutVar15.equals(0x07) || __dummyScrutVar15.equals(0x08) || __dummyScrutVar15.equals(0x09) || __dummyScrutVar15.equals(0x0a) || __dummyScrutVar15.equals(0x0b) || __dummyScrutVar15.equals(0x0c) || __dummyScrutVar15.equals(0x0d) || __dummyScrutVar15.equals(0x0e) || __dummyScrutVar15.equals(0x0f) || __dummyScrutVar15.equals(0x10) || __dummyScrutVar15.equals(0x11) || __dummyScrutVar15.equals(0x12) || __dummyScrutVar15.equals(0x13) || __dummyScrutVar15.equals(0x14) || __dummyScrutVar15.equals(0x15) || __dummyScrutVar15.equals(0x16) || __dummyScrutVar15.equals(0x17) || __dummyScrutVar15.equals(0x18) || __dummyScrutVar15.equals(0x19) || __dummyScrutVar15.equals(0x1a) || __dummyScrutVar15.equals(0x1b) || __dummyScrutVar15.equals(0x1c))
            {
                //flies in a spiral
                _writer.writeAttributeString("filter", "slide(fromBottom)");
            }
                 
        }
        else if (__dummyScrutVar9.equals(0x0d))
        {
            //Split
            byte __dummyScrutVar16 = animinfo.animEffectDirection;
            if (__dummyScrutVar16.equals(0x00))
            {
                //horz m -> tb
                _writer.writeAttributeString("filter", "split(outHorizontal)");
            }
            else if (__dummyScrutVar16.equals(0x01))
            {
                //horz tb -> m
                _writer.writeAttributeString("filter", "split(inHorizontal)");
            }
            else if (__dummyScrutVar16.equals(0x02))
            {
                //vert m -> lr
                _writer.writeAttributeString("filter", "split(outVertical)");
            }
            else if (__dummyScrutVar16.equals(0x03))
            {
                //vert
                _writer.writeAttributeString("filter", "split(inVertical)");
            }
                
        }
        else if (__dummyScrutVar9.equals(0x0e))
        {
            //Flash
            byte __dummyScrutVar17 = animinfo.animEffectDirection;
            //after short time
            //after medium time
            if (__dummyScrutVar17.equals(0x00) || __dummyScrutVar17.equals(0x01) || __dummyScrutVar17.equals(0x02))
            {
            }
             
        }
        else //after long time
        if (__dummyScrutVar9.equals(0x0f) || __dummyScrutVar9.equals(0x11))
        {
            //Diamond
            _writer.writeAttributeString("filter", "diamond(out)");
        }
        else if (__dummyScrutVar9.equals(0x12))
        {
            //Plus
            _writer.writeAttributeString("filter", "plus");
        }
        else if (__dummyScrutVar9.equals(0x13))
        {
            //Wedge
            _writer.writeAttributeString("filter", "wedge");
        }
        else if (__dummyScrutVar9.equals(0x14) || __dummyScrutVar9.equals(0x15) || __dummyScrutVar9.equals(0x16) || __dummyScrutVar9.equals(0x17) || __dummyScrutVar9.equals(0x18) || __dummyScrutVar9.equals(0x19) || __dummyScrutVar9.equals(0x1a))
        {
            //Wheel
            byte __dummyScrutVar18 = animinfo.animEffectDirection;
            if (__dummyScrutVar18.equals(0x01))
            {
                //1 spoke
                _writer.writeAttributeString("filter", "wheel(1)");
            }
            else if (__dummyScrutVar18.equals(0x02))
            {
                //2 spokes
                _writer.writeAttributeString("filter", "wheel(2)");
            }
            else if (__dummyScrutVar18.equals(0x03))
            {
                //3 spokes
                _writer.writeAttributeString("filter", "wheel(3)");
            }
            else if (__dummyScrutVar18.equals(0x04))
            {
                //4 spokes
                _writer.writeAttributeString("filter", "wheel(4)");
            }
            else if (__dummyScrutVar18.equals(0x08))
            {
                //8 spokes
                _writer.writeAttributeString("filter", "wheel(8)");
            }
                 
        }
        else if (__dummyScrutVar9.equals(0x1b))
        {
            //Circle
            _writer.writeAttributeString("filter", "circle");
        }
        else
        {
            _writer.writeAttributeString("filter", "blinds(horizontal)");
        }                    
        _writer.WriteStartElement("p", "cBhvr", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "cTn", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("id", String.valueOf((++lastID)));
        _writer.writeAttributeString("dur", "500");
        _writer.writeEndElement();
        //cTn
        _writer.WriteStartElement("p", "tgtEl", OpenXmlNamespaces.PresentationML);
        _writer.WriteStartElement("p", "spTgt", OpenXmlNamespaces.PresentationML);
        _writer.writeAttributeString("spid", ShapeID);
        _writer.writeEndElement();
        //spTgt
        _writer.writeEndElement();
        //tgtEl
        _writer.writeEndElement();
        //cBhvr
        _writer.writeEndElement();
    }

}


//animEffect