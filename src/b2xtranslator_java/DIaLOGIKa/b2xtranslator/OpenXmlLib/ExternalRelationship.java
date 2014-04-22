//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:47 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib;

import java.net.URI;

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
public class ExternalRelationship   
{
    protected String _id;
    protected String _relationshipType;
    protected String _target;
    public ExternalRelationship(String id, String relationshipType, URI targetUri) throws Exception {
        _id = id;
        _relationshipType = relationshipType;
        _target = targetUri.toString();
    }

    public ExternalRelationship(String id, String relationshipType, String target) throws Exception {
        _id = id;
        _relationshipType = relationshipType;
        _target = target;
    }

    public String getId() throws Exception {
        return _id;
    }

    public void setId(String value) throws Exception {
        _id = value;
    }

    public String getRelationshipType() throws Exception {
        return _relationshipType;
    }

    public void setRelationshipType(String value) throws Exception {
        _relationshipType = value;
    }

    public String getTarget() throws Exception {
        return _target;
    }

    public void setTarget(String value) throws Exception {
        _target = value;
    }

    public URI getTargetUri() throws Exception {
        return new URI(_target, UriKind.RelativeOrAbsolute);
    }

}


