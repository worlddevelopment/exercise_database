//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:59 AM
//

package DIaLOGIKa.b2xtranslator.PptFileFormat;

import DIaLOGIKa.b2xtranslator.PptFileFormat.ParagraphMask;

public class ParagraphRun9   
{
    public ParagraphMask mask = ParagraphMask.None;
    public int bulletblipref;
    public int fBulletHasAutoNumber;
    public int bulletAutoNumberScheme = -1;
    public boolean getBulletBlipReferencePresent() throws Exception {
        return (this.mask & ParagraphMask.BulletBlip) != 0;
    }

}


