//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:47 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPart;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlPartContainer;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlRelationshipTypes;

/*
 * Copyright (c) 2008, DIaLOGIKa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
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
public class ImagePart  extends OpenXmlPart 
{
    public enum ImageType
    {
        Bmp,
        Emf,
        Gif,
        Icon,
        Jpeg,
        //Pcx,
        Png,
        Tiff,
        Wmf
    }
    protected DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType _type = DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType.Bmp;
    public ImagePart(DIaLOGIKa.b2xtranslator.OpenXmlLib.ImagePart.ImageType type, OpenXmlPartContainer parent, int partIndex) throws Exception {
        super(parent, partIndex);
        _type = type;
    }

    public String getContentType() throws Exception {
        switch(_type)
        {
            case Bmp: 
                return "image/bmp";
            case Emf: 
                return "image/x-emf";
            case Gif: 
                return "image/gif";
            case Icon: 
                return "image/x-icon";
            case Jpeg: 
                return "image/jpeg";
            case Png: 
                return "image/png";
            case Tiff: 
                return "image/tiff";
            case Wmf: 
                return "image/x-wmf";
            default: 
                return "image/png";
        
        }
    }

    //case ImagePartType.Pcx:
    //    return "image/pcx";
    public String getRelationshipType() throws Exception {
        return OpenXmlRelationshipTypes.Image;
    }

    public String getTargetName() throws Exception {
        return "image" + this.getPartIndex();
    }

    //public override string TargetDirectory { get { return "media"; } }
    private String targetdirectory = "media";
    public String getTargetDirectory() throws Exception {
        return targetdirectory;
    }

    public void setTargetDirectory(String value) throws Exception {
        targetdirectory = value;
    }

    public String getTargetExt() throws Exception {
        switch(_type)
        {
            case Bmp: 
                return ".bmp";
            case Emf: 
                return ".emf";
            case Gif: 
                return ".gif";
            case Icon: 
                return ".ico";
            case Jpeg: 
                return ".jpg";
            case Png: 
                return ".png";
            case Tiff: 
                return ".tif";
            case Wmf: 
                return ".wmf";
            default: 
                return ".png";
        
        }
    }

}


//case ImagePartType.Pcx:
//    return ".pcx";