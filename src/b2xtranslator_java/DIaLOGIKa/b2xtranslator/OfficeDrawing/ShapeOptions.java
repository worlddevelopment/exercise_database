//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:33 AM
//

package DIaLOGIKa.b2xtranslator.OfficeDrawing;

import DIaLOGIKa.b2xtranslator.OfficeDrawing.OfficeRecordAttribute;
import DIaLOGIKa.b2xtranslator.Tools.Utils;
import java.util.HashMap;

public class ShapeOptions  extends DIaLOGIKa.b2xtranslator.OfficeDrawing.Record 
{
    public enum PropertyId
    {
        //Transform
        left,
        top,
        right,
        bottom,
        rotation,
        gvPage,
        fChangePage,
        fFlipV,
        fFlipH,
        //Protection
        fLockAgainstUngrouping,
        fLockRotation,
        fLockAspectRatio,
        fLockPosition,
        fLockAgainstSelect,
        fLockCropping,
        fLockVertices,
        fLockText,
        fLockAdjustHandles,
        protectionBooleans,
        //Text
        lTxid,
        dxTextLeft,
        dyTextTop,
        dxTextRight,
        dyTextBottom,
        WrapText,
        scaleText,
        anchorText,
        txflTextFlow,
        cdirFont,
        hspNext,
        txdir,
        ccol,
        dzColMargin,
        fSelectText,
        fAutoTextMargin,
        fRotateText,
        fFitShapeToText,
        TextBooleanProperties,
        //GeoText
        gtextUNICODE,
        gtextRTF,
        gtextAlign,
        gtextSize,
        gtextSpacing,
        gtextFont,
        gtextCSSFont,
        gtextFReverseRows,
        fGtext,
        gtextFVertical,
        gtextFKern,
        gtextFTight,
        gtextFStretch,
        gtextFShrinkFit,
        gtextFBestFit,
        gtextFNormalize,
        gtextFDxMeasure,
        gtextFBold,
        gtextFItalic,
        gtextFUnderline,
        gtextFShadow,
        gtextFSmallcaps,
        GeometryTextBooleanProperties,
        //BLIP
        cropFromTop,
        cropFromBottom,
        cropFromLeft,
        cropFromRight,
        Pib,
        pibName,
        pibFlags,
        pictureTransparent,
        pictureContrast,
        pictureBrightness,
        pictureGamma,
        pictureId,
        pictureDblCrMod,
        pictureFillCrMod,
        pictureLineCrMod,
        pibPrint,
        pibPrintName,
        pibPrintFlags,
        movie,
        pictureRecolor,
        picturePreserveGrays,
        fRewind,
        fLooping,
        pictureGray,
        pictureBiLevel,
        BlipBooleanProperties,
        //Geometry
        geoLeft,
        geoTop,
        geoRight,
        geoBottom,
        shapePath,
        pVertices,
        pSegmentInfo,
        adjustValue,
        adjust2Value,
        adjust3Value,
        adjust4Value,
        adjust5Value,
        adjust6Value,
        adjust7Value,
        adjust8Value,
        adjust9Value,
        adjust10Value,
        pConnectionSites,
        pConnectionSitesDir,
        xLimo,
        yLimo,
        pAdjustHandles,
        pGuides,
        pInscribe,
        cxk,
        pFragments,
        fColumnLineOK,
        fShadowOK,
        f3DOK,
        fLineOK,
        fGtextOK,
        fFillShadeShapeOK,
        geometryBooleans,
        //Fill Style
        fillType,
        fillColor,
        fillOpacity,
        fillBackColor,
        fillBackOpacity,
        fillCrMod,
        fillBlip,
        fillBlipName,
        fillBlipFlags,
        fillWidth,
        fillHeight,
        fillAngle,
        fillFocus,
        fillToLeft,
        fillToTop,
        fillToRight,
        fillToBottom,
        fillRectLeft,
        fillRectTop,
        fillRectRight,
        fillRectBottom,
        fillDztype,
        fillShadePreset,
        fillShadeColors,
        fillOriginX,
        fillOriginY,
        fillShapeOriginX,
        fillShapeOriginY,
        fillShadeType,
        fillColorExt,
        fillColorExtMod,
        fillBackColorExt,
        fillBackColorExtMod,
        fRecolorFillAsPicture,
        fUseShapeAnchor,
        fFilled,
        fHitTestFill,
        fillShape,
        fillUseRect,
        FillStyleBooleanProperties,
        //Line Style
        lineColor,
        lineOpacity,
        lineBackColor,
        lineCrMod,
        lineType,
        lineFillBlip,
        lineFillBlipName,
        lineFillBlipFlags,
        lineFillWidth,
        lineFillHeight,
        lineFillDztype,
        lineWidth,
        lineMiterLimit,
        lineStyle,
        lineDashing,
        lineDashStyle,
        lineStartArrowhead,
        lineEndArrowhead,
        lineStartArrowWidth,
        lineStartArrowLength,
        lineEndArrowWidth,
        lineEndArrowLength,
        lineJoinStyle,
        lineEndCapStyle,
        fInsetPen,
        fInsetPenOK,
        fArrowheadsOK,
        fLine,
        fHitTestLine,
        lineFillShape,
        lineStyleBooleans,
        //Shadow Style
        shadowType,
        shadowColor,
        shadowHighlight,
        shadowCrMod,
        shadowOpacity,
        shadowOffsetX,
        shadowOffsetY,
        shadowSecondOffsetX,
        shadowSecondOffsetY,
        shadowScaleXToX,
        shadowScaleYToX,
        shadowScaleXToY,
        shadowScaleYToY,
        shadowPerspectiveX,
        shadowPerspectiveY,
        shadowWeight,
        shadowOriginX,
        shadowOriginY,
        fShadow,
        ShadowStyleBooleanProperties,
        //Perspective Style
        perspectiveType,
        perspectiveOffsetX,
        perspectiveOffsetY,
        perspectiveScaleXToX,
        perspectiveScaleYToX,
        perspectiveScaleXToY,
        perspectiveScaleYToY,
        perspectivePerspectiveX,
        perspectivePerspectiveY,
        perspectiveWeight,
        perspectiveOriginX,
        perspectiveOriginY,
        PerspectiveStyleBooleanProperties,
        //3D Object
        c3DSpecularAmt,
        c3DDiffuseAmt,
        c3DShininess,
        c3DEdgeThickness,
        C3DExtrudeForward,
        c3DExtrudeBackward,
        c3DExtrudePlane,
        c3DExtrusionColor,
        c3DCrMod,
        f3D,
        fc3DMetallic,
        fc3DUseExtrusionColor,
        ThreeDObjectBooleanProperties,
        //3D Style
        c3DYRotationAngle,
        c3DXRotationAngle,
        c3DRotationAxisX,
        c3DRotationAxisY,
        c3DRotationAxisZ,
        c3DRotationAngle,
        c3DRotationCenterX,
        c3DRotationCenterY,
        c3DRotationCenterZ,
        c3DRenderMode,
        c3DTolerance,
        c3DXViewpoint,
        c3DYViewpoint,
        c3DZViewpoint,
        c3DOriginX,
        c3DOriginY,
        c3DSkewAngle,
        c3DSkewAmount,
        c3DAmbientIntensity,
        c3DKeyX,
        c3DKeyY,
        c3DKeyZ,
        c3DKeyIntensity,
        c3DFillX,
        c3DFillY,
        c3DFillZ,
        c3DFillIntensity,
        fc3DConstrainRotation,
        fc3DRotationCenterAuto,
        fc3DParallel,
        fc3DKeyHarsh,
        ThreeDStyleBooleanProperties,
        //Shape
        hspMaster,
        cxstyle,
        bWMode,
        bWModePureBW,
        bWModeBW,
        idDiscussAnchor,
        dgmLayout,
        dgmNodeKind,
        dgmLayoutMRU,
        wzEquationXML,
        fPolicyLabel,
        fPolicyBarcode,
        fFlipHQFE5152,
        fFlipVQFE5152,
        fPreferRelativeResize,
        fLockShapeType,
        fInitiator,
        fDeleteAttachedObject,
        shapeBooleans,
        //Callout
        spcot,
        dxyCalloutGap,
        spcoa,
        spcod,
        dxyCalloutDropSpecified,
        dxyCalloutLengthSpecified,
        fCallout,
        fCalloutAccentBar,
        fCalloutTextBorder,
        fCalloutMinusX,
        fCalloutMinusY,
        fCalloutDropAuto,
        fCalloutLengthSpecified,
        //Groupe Shape
        wzName,
        wzDescription,
        pihlShape,
        pWrapPolygonVertices,
        dxWrapDistLeft,
        dyWrapDistTop,
        dxWrapDistRight,
        dyWrapDistBottom,
        lidRegroup,
        groupLeft,
        groupTop,
        groupRight,
        groupBottom,
        wzTooltip,
        wzScript,
        posh,
        posrelh,
        posv,
        posrelv,
        pctHR,
        alignHR,
        dxHeightHR,
        dxWidthHR,
        wzScriptExtAttr,
        scriptLang,
        wzScriptIdAttr,
        wzScriptLangAttr,
        borderTopColor,
        borderLeftColor,
        borderBottomColor,
        borderRightColor,
        tableProperties,
        tableRowProperties,
        scriptHtmlLocation,
        wzApplet,
        wzFrameTrgtUnused,
        wzWebBot,
        wzAppletArg,
        wzAccessBlob,
        metroBlob,
        dhgt,
        fLayoutInCell,
        fIsBullet,
        fStandardHR,
        fNoshadeHR,
        fHorizRule,
        fUserDrawn,
        fAllowOverlap,
        fReallyHidden,
        fScriptAnchor,
        groupShapeBooleans,
        relRotation,
        //Unknown HTML
        wzLineId,
        wzFillId,
        wzPictureId,
        wzPathId,
        wzShadowId,
        wzPerspectiveId,
        wzGtextId,
        wzFormulaeId,
        wzHandlesId,
        wzCalloutId,
        wzLockId,
        wzTextId,
        wzThreeDId,
        FakeShapeType,
        fFakeMaster,
        //Diagramm
        dgmt,
        dgmStyle,
        pRelationTbl,
        dgmScaleX,
        dgmScaleY,
        dgmDefaultFontSize,
        dgmConstrainBounds,
        dgmBaseTextScale,
        fBorderlessCanvas,
        fNonStickyInkCanvas,
        fDoFormat,
        fReverse,
        fDoLayout,
        diagramBooleans,
        //Web Component
        webComponentWzHtml,
        webComponentWzName,
        webComponentWzUrl,
        webComponentWzProperties,
        fIsWebComponent,
        //Clip
        pVerticesClip,
        pSegmentInfoClip,
        shapePathClip,
        fClipToWrap,
        fClippedOK,
        //Ink
        pInkData,
        fInkAnnotation,
        fHitTestInk,
        fRenderShape,
        fRenderInk,
        //Signature
        wzSigSetupId,
        wzSigSetupProvId,
        wzSigSetupSuggSigner,
        wzSigSetupSuggSigner2,
        wzSigSetupSuggSignerEmail,
        wzSigSetupSignInst,
        wzSigSetupAddlXml,
        wzSigSetupProvUrl,
        fSigSetupShowSignDate,
        fSigSetupAllowComments,
        fSigSetupSignInstSet,
        fIsSignatureLine,
        //Groupe Shape 2
        pctHoriz,
        pctVert,
        pctHorizPos,
        pctVertPos,
        sizerelh,
        sizerelv,
        colStart,
        colSpan
    }
    public enum PositionHorizontal
    {
        __dummyEnum__0,
        msophAbs,
        msophLeft,
        msophCenter,
        msophRight,
        msophInside,
        msophOutside
    }
    public enum PositionHorizontalRelative
    {
        msoprhMargin,
        msoprhPage,
        msoprhText,
        msoprhChar
    }
    public enum PositionVertical
    {
        __dummyEnum__0,
        msopvAbs,
        msopvTop,
        msopvCenter,
        msopvBottom,
        msopvInside,
        msopvOutside
    }
    public enum PositionVerticalRelative
    {
        msoprvMargin,
        msoprvPage,
        msoprvText,
        msoprvLine
    }
    public enum LineEnd
    {
        NoEnd,
        ArrowEnd,
        ArrowStealthEnd,
        ArrowDiamondEnd,
        ArrowOvalEnd,
        ArrowOpenEnd,
        ArrowChevronEnd,
        ArrowDoubleChevronEnd
    }
    public enum LineDashing
    {
        Solid,
        DashSys,
        DotSys,
        DashDotSys,
        DashDotDotSys,
        DotGEL,
        DashGEL,
        LongDashGEL,
        DashDotGEL,
        LongDashDotGEL,
        LongDashDotDotGEL
    }
    public static class OptionEntry   
    {
        public OptionEntry() {
        }

        public PropertyId pid = PropertyId.left;
        public boolean fBid;
        public boolean fComplex;
        public long op;
        public byte[] opComplex;
    }

    public DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry[] Options;
    public HashMap<PropertyId,DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry> OptionsByID = new HashMap<PropertyId,DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry>();
    public ShapeOptions() throws Exception {
        super();
        this.Options = new DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry[0];
    }

    public ShapeOptions(BinaryReader _reader, uint size, uint typeCode, uint version, uint instance) throws Exception {
        super(_reader, size, typeCode, version, instance);
        long pos = this.Reader.BaseStream.Position;
        //instance is the count of properties stored in this record
        this.Options = new DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry[instance];
        for (int i = 0;i < instance;i++)
        {
            //parse the flags and the simple values
            DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry entry = new DIaLOGIKa.b2xtranslator.OfficeDrawing.ShapeOptions.OptionEntry();
            UInt16 flag = this.Reader.ReadUInt16();
            entry.pid = (PropertyId)Utils.BitmaskToInt(flag, 0x3FFF);
            entry.fBid = Utils.BitmaskToBool(flag, 0x4000);
            entry.fComplex = Utils.BitmaskToBool(flag, 0x8000);
            entry.op = this.Reader.ReadUInt32();
            this.Options[i] = entry;
        }
        for (int i = 0;i < instance;i++)
        {
            //parse the complex values
            //these values are stored directly at the end
            //of the OptionEntry arry, sorted by pid
            if (this.Options[i].fComplex)
            {
                if (this.Options[i].pid == PropertyId.pVertices)
                {
                    this.Options[i].opComplex = this.Reader.ReadBytes((int)this.Options[i].op + 6);
                }
                else
                {
                    this.Options[i].opComplex = this.Reader.ReadBytes((int)this.Options[i].op);
                } 
            }
             
            if (this.OptionsByID.containsKey(this.Options[i].pid))
            {
                OptionsByID.put(this.Options[i].pid, this.Options[i]);
            }
            else
            {
                OptionsByID.put(this.Options[i].pid, this.Options[i]);
            } 
        }
        this.Reader.BaseStream.Seek(pos + size, SeekOrigin.Begin);
    }

}


