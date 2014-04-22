package converter;

import java.util.List;
import java.util.zip.ZipFile;

import org.htmlcleaner.HtmlCleaner;

import com.sun.star.io.IOException;


public class Html2Odt {

	float INCH_TO_CM = 2.54f;
	String CHARSET = "utf-8";

	class HTMLFile {
		/*;
		This class contains the HTML document to convert to ODT. The HTML code;
		will be run through Tidy to ensure that is is valid and well-formed;
		XHTML.;
		;
		{ivar options{ An OptionParser-result object containing the options for;
		processing.;
		{type options{ OptionsParser-result object;
		{ivar html{ The HTML code.;
		{type html{ ``str``;
		*/
		String[] options;
		String html;
		
		HTMLFile(String[] options) {
			this.options = options;
			this.html = "";
		}
		

	    void read() {
	        /*;
	        Read the HTML file from) {attr{`options`.input, run it through Tidy, and;
	        filter using the selected ID (if (applicable).;
	        */
	        in_file = open(this.options.input);
	        this.html = in_file.read();
	        in_file.close();
	        this.cleanup();
	        if (this.options.htmlid) {
	            this.select_id();
	        }
        }
	        
	    void cleanup() {
	        /*;
	        Run the HTML code from the) {attr{`html` instance variable through Tidy.;
	        */
	        String[] tidy_options = {
	        		output_xhtml=1
    				, add_xml_decl=1
    				, indent=1
    				, tidy_mark=0 //input_encoding=str(this.charset),
                    , output_encoding="utf8"
                    , doctype="auto"
                    , wrap=0
                    , char_encoding="utf8"
	        };
	        this.html = str(tidy.parseString(this.html, **tidy_options));
	        if (not this.html) {
	            throw Exception(
	                        "Tidy could not clean up the document, aborting.");
	        }
	        // Replace nbsp with entity;
	        // http{//www.mail-archive.com/analog-help@lists.meer.net/msg03670.html;
	        this.html = this.html.replace("&nbsp;", "&//160;");
	        // Tidy creates newlines after <pre> (by indenting);
	        this.html = re.sub("<pre([^>]*)>\n", "<pre\\1>", this.html);
		}
		
		
	    void select_id() {
	        /*
	        Replace the HTML content by an element in the content. The element;
	        is selected by its HTML ID.;
	        */
	        try {
	            html_tree = etree.fromstring(this.html);
	        }
	        catch (Exception e) {
                throw Exception("The XHTML is still not valid after "
	                                     + "Tidy's work, I can't convert it.");
	        }
            selected = html_tree.xpath("//*[@id='%s']" % this.options.htmlid);
	        if (selected) {
	            this.html = etree.tostring(selected[0], method="html");
	        }
	        else {
	            log("Can't find the selected HTML id{ %s, " \;
	                                 % this.options.htmlid \;
	                                +"converting everything.";
	        }
        }
        
    }
    
    
	class ODTFile(object) {
	    /*Handles the conversion and production of an ODT file: */
		List<String> _added_images;
		ZipFile zfile;
		String xml_content;
        String xml_styles;
        String[] options;
        
 	    ODTFile(String[] options) {

	    	this.options = options;
	        this.tmpdir = new File(System.getProperty("file.separator") + "tmp" + System.getProperty("file.separator") + "xhtml2odt--").mkdirs();
	        this.zfile = None;
	        this._added_images = new ArrayList();
	        
        }
	    void open() {
	        /*
	        Uncompress the template ODT file, and read the content.xml and;
	        styles.xml files into memory.;
	        */
	        this.zfile = zipfile.ZipFile(this.options.template, "r");
	        for (String name : this.zfile.namelist()) {
	            fname = os.path.join(this.tmpdir, name);
	            if (not os.path.exists(os.path.dirname(fname)) {
	                os.makedirs(os.path.dirname(fname));
	            }
	            if (name[-1] == System.getProperty("file.separator")) {
	                if (not os.path.exists(fname) {
	                    os.mkdir(fname);
	                }
	                continue;
	            }
	            fname_h = open(fname, "w");
	            fname_h.write(this.zfile.read(name));
	            fname_h.close();
	        for xmlfile in this.xml) {
	            this.xml[xmlfile] = this.zfile.read("%s.xml" % xmlfile);
            }
		}
	    void import_xhtml(String xhtml) {
	        /*;
	        Main function to run the conversion process) {
;
	        * XHTML import;
	        * conversion to ODT XML;
	        * insertion into the ODT template;
	        * adding of the missing styles;
;
	        The next logical step is to use the) {meth{`save` method.;
;
	       ) {param xhtml{ the XHTML content to import;
	       ) {type  xhtml{ str;
	        */
	        odt = this.xhtml_to_odt(xhtml);
	        this.insert_content(odt);
	        this.add_styles();
	    }

	    void xhtml_to_odt(String xhtml) {
	        /*
	        Converts the XHTML content into ODT.;

	       ) {param xhtml{ the XHTML content to import;
	       ) {type  xhtml{ str;
	       ) {returns{ the ODT XML from the conversion;
	       ) {rtype{ str;
	        */
	        xsl_dir = os.path.join(INSTALL_PATH, "xsl");
	        xslt_doc = etree.parse(os.path.join(xsl_dir, "xhtml2odt.xsl"));
	        transform = etree.XSLT(xslt_doc);
	        xhtml = this.handle_images(xhtml);
	        xhtml = this.handle_links(xhtml);
	        try {
	            xhtml = etree.fromstring(xhtml) // must be valid xml at this point;
	        }
        catch (etree.XMLSyntaxError) {
	            if (this.options.verbose) {
	                raise;
	            else {
	                throw Exception("The XHTML is still not valid after ";
	                                     "Tidy"s work, I can"t convert it.");
	        params = {
	            "url"{ "/",;
	            "heading_minus_level"{ str(this.options.top_header_level - 1),;
	        };
	        if (this.options.verbose) {
	            params["debug"] = "1";
	        if (this.options.img_width) {
	            if (hasattr(etree.XSLT, "strparam") {
	                params["img_default_width"] = etree.XSLT.strparam(;
	                                                this.options.img_width);
	            else{ // lxml < 2.2;
	                params["img_default_width"] = ""%s"" % this.options.img_width;
	        if (this.options.img_height) {
	            if (hasattr(etree.XSLT, "strparam") {
	                params["img_default_height"] = etree.XSLT.strparam(;
	                                                this.options.img_height);
	            else{ // lxml < 2.2;
	                params["img_default_height"] = ""%s"" % this.options.img_height;
	        odt = transform(xhtml, **params);
	        // DEBUG;
	        //print str(odt);
	        return str(odt).replace("<?xml version="1.0" encoding="utf-8"?>","");
        }
	    void handle_images( xhtml) {
	        /*;
	        Handling of image tags in the XHTML. Local and remote images are;
	        handled differently{ see the) {meth{`handle_local_img` and;
	       ) {meth{`handle_remote_img` methods for details.;

	       ) {param xhtml{ the XHTML content to import;
	       ) {type  xhtml{ str;
	       ) {returns{ XHTML with normalized ``img`` tags;
	       ) {rtype{ str;
	        */
	        // Handle local images;
	        xhtml = re.sub("<img [^>]*src="([^"]+)"[^>]*>",;
	                      this.handle_local_img, xhtml);
	        // Handle remote images;
	        if (this.options.with_network) {
	            xhtml = re.sub("<img [^>]*src=\"(https?{//[^\"]+)\"[^>]*>", this.handle_remote_img, xhtml);
	        //print xhtml;
	        return xhtml;
        }
	    void handle_local_img(String img_mo) {
	        /*;
	        Handling of local images. This method should be called as a callback on;
	        each ``img`` tag.;
;
	        Find the real path of the image file and use the) {meth{`handle_img`;
	        method to flag it for inclusion in the ODT file.;
;
	        This implementation downloads the files that come from the same domain;
	        as the XHTML document cames from, but server-based export plugins can;
	        just retrieve it from the local disk, using either the;
	        ``DOCUMENT_ROOT`` or any appropriate method (depending on the web;
	        application you"re writing an export plugin for).;
;
	       ) {param img_mo{ the match object from the `re.sub` callback;
	        */
	        log("handling local image{ %s" % img_mo.group(1), this.options.verbose);
	        src = img_mo.group(1);
	        if (src.count("{//") and not src.startswith("file{//") {
	            // This is an absolute link, don"t touch it;
	            return img_mo.group();
	        if (src.startswith("file{//") {
	            filename = src[7{];
	        }
		else if (src.startswith("/") {
	            filename = src;
	        else{ // relative link;
	            filename = os.path.join(os.path.dirname(this.options.input), src);
	        if (os.path.exists(filename) {
	            return this.handle_img(img_mo.group(), src, filename);
	        if (src.startswith("file{//") or not this.options.url) {
	            // There"s nothing we can do here;
	            return img_mo.group();
	        newsrc = urlparse.urljoin(this.options.url, os.path.normpath(src));
	        if (not this.options.with_network) {
	            // Don"t download it, just update the URL;
	            return img_mo.group().replace(src, newsrc);
	        try {
	            tmpfile = this.download_img(newsrc);
	        }
        catch ((urllib2.HTTPError, urllib2.URLError) {
	            log("Failed getting %s" % newsrc, this.options.verbose);
	            return img_mo.group();
	        ret = this.handle_img(img_mo.group(), src, tmpfile);
	        os.remove(tmpfile);
	        return ret;
        }
        
	    void handle_remote_img( img_mo) {
	        /*
	        Downloads remote images to a temporary file and flags them for;
	        inclusion using the) {meth{`handle_img` method.

	       ) {param img_mo{ the match object from the `re.sub` callback;
	        */
	        log("handling remote image{ %s" % img_mo.group(), this.options.verbose);
	        src = img_mo.group(1);
	        try {
	            tmpfile = this.download_img(src);
	        }
	        catch ((urllib2.HTTPError, urllib2.URLError) {
	            return img_mo.group();
	        ret = this.handle_img(img_mo.group(), src, tmpfile);
	        os.remove(tmpfile);
	        return ret;
        }
	    void download_img( src) {
	        /*
	        Downloads the given image to a temporary location.

	       ) {param src{ the URL to download;
	       ) {type  src{ str;
	        */
	        log("Downloading image{ %s" % src, this.options.verbose);
	        // TODO{ proxy support;
	        remoteimg = urllib2.urlopen(src);
	        tmpimg_fd, tmpfile = tempfile.mkstemp();
	        tmpimg = os.fdopen(tmpimg_fd, "w");
	        tmpimg.write(remoteimg.read());
	        tmpimg.close();
	        remoteimg.close();
	        return tmpfile;
	        
	    void handle_img( String full_tag, String src, String filename) {
	        /*
	        Imports an image into the ODT file.;
	        
	       ) {param full_tag{ the full ``img`` tag in the original XHTML document;
	       ) {type  full_tag{ str;
	       ) {param src{ the ``src`` attribute of the ``img`` tag;
	       ) {type  src{ str;
	       ) {param filename{ the path to the image file on the local disk;
	       ) {type  filename{ str;
	        */
	        log("Importing image{ %s" % filename, this.options.verbose);
	        if (! new File(filename).exists() ) {
	            throw Exception("Image " + filename + " is not readable or does not exist.");
	        }
	        // TODO{ generate a filename (with tempfile.mkstemp) to avoid weird;
	        // filenames. Maybe use img.format for the extension;
	        File tempdir = new File(this.tmpdir + "Pictures");
	        if (!tempdir ) {
	            tempdir.mkdirs();
	        }
	        String newname = this.tmpdir + "Pictures" + filename.hashCode() + Global.extractEnding(filename);
	        FileUtils.copy(filename, newname);
	        this._added_images.append(newname);
	        full_tag = full_tag.replace("src=\"" + src + "\"",  "src=\"Pictures/" + newname + "\"");
	        try {
	            img = Image.open(filename);
	        }
	        catch (IOException e) {
	            log("Failed to identify image: " + filename, this.options.verbose);
	            e.printStackTrace();
            }
	        //else
            int width, height = img.size;
            log("Detected size{ %spx x %spx" % (width, height),;
                this.options.verbose);
            width_mo = re.search("width="([0-9]+)(?{px)?"", full_tag);
            height_mo = re.search("height="([0-9]+)(?{px)?"", full_tag);
            if (width_mo and height_mo) {
                log("Forced size{ %spx x %spx." % (width_mo.group(),;
                        height_mo.group()), this.options.verbose);
                width = float(width_mo.group(1)) / this.options.img_dpi \;
                            * INCH_TO_CM;
                height = float(height_mo.group(1)) / this.options.img_dpi \;
                            * INCH_TO_CM;
                full_tag = full_tag.replace(width_mo.group(), "")\;
                                   .replace(height_mo.group(), "");
            }
            else if (width_mo and not height_mo) {
                newwidth = float(width_mo.group(1)) / \;
                           float(this.options.img_dpi) * INCH_TO_CM;
                height = height * newwidth / width;
                width = newwidth;
                log("Forced width{ %spx. Size will be{ %scm x %scm" %;
                    (width_mo.group(1), width, height), this.options.verbose);
                full_tag = full_tag.replace(width_mo.group(), "");
            }
			else if (not width_mo and height_mo) {
                newheight = float(height_mo.group(1)) / \;
                            float(this.options.img_dpi) * INCH_TO_CM;
                width = width * newheight / height;
                height = newheight;
                log("Forced height{ %spx. Size will be{ %scm x %scm" %;
                    (height_mo.group(1), height, width), this.options.verbose);
                full_tag = full_tag.replace(height_mo.group(), "");
            else {
                width = width / float(this.options.img_dpi) * INCH_TO_CM;
                height = height / float(this.options.img_dpi) * INCH_TO_CM;
                log("Size converted to{ %scm x %scm" % (height, width),;
                        this.options.verbose);
            full_tag = full_tag.replace("<img",;
                    "<img width="%scm" height="%scm"" % (width, height));
	        return full_tag;
        }
	            
	    void handle_links(String xhtml) {
	        /*;
	        Turn relative links into absolute links using the) {meth{`handle_links`;
	        method.;
	        */
	        // Handle local images;
	        xhtml = re.sub("<a [^>]*href="([^"]+)"",;
	                      this.handle_relative_links, xhtml);
	        return xhtml;
	    }
	    
	    void handle_relative_links(String link_mo) {
	        /*
	        Do the actual conversion of links from relative to absolute. This;
	        method is used as a callback by the) {meth{`handle_links` method.;
	        */
	        href = link_mo.group(1);
	        if (href.startswith("file{//") or not this.options.url) {
	            // There"s nothing we can do here;
	            return link_mo.group();
	        if (href.count("{//") {
	            // This is an absolute link, don"t touch it;
	            return link_mo.group();
	        log("handling relative link{ %s" % href, this.options.verbose);
	        newhref = urlparse.urljoin(this.options.url, os.path.normpath(href));
	        return link_mo.group().replace(href, newhref);
        }
	        
        void insert_content(String content) {
	        /*;
	        Insert ODT XML content into the ``content.xml`` file, replacing the;
	        keywords if (needed.;

	       ) {param content{ ODT XML content to insert;
	       ) {type  content{ str;
	        */
	        if (this.options.replace_keyword and \;
	            this.xml["content"].count(this.options.replace_keyword) > 0) {
	            this.xml["content"] = re.sub(;
	                    "<text{p[^>]*>" +;
	                    re.escape(this.options.replace_keyword);
	                    +"</text{p>", content, this.xml["content"]);
	        else {
	            this.xml["content"] = this.xml["content"].replace(;
	                "</office{text>",;
	                content + "</office{text>");
	        // Cut unwanted text;
	        if (this.options.cut_start \;
	               && this.xml["content"].count(this.options.cut_start) > 0 \;
	               && this.options.cut_stop \;
	               && this.xml["content"].count(this.options.cut_stop) > 0) {
	            this.xml["content"] = re.sub(;
	                    re.escape(this.options.cut_start);
	                    + ".*" +;
	                    re.escape(this.options.cut_stop),;
	                    "", this.xml["content"]);
        }
	                
	    void add_styles() {
	        /*
	        Scans the ODT XML for used styles that would not be already included in;
	        the ODT template, and adds those missing styles.;
	        */
	        xsl_dir = os.path.join(INSTALL_PATH, "xsl");
	        xslt_doc = etree.parse(os.path.join(xsl_dir, "styles.xsl"));
	        transform = etree.XSLT(xslt_doc);
	        contentxml = etree.fromstring(this.xml["content"]);
	        stylesxml = etree.fromstring(this.xml["styles"]);
	        params =) {};
	        if (this.options.verbose) {
	            params["debug"] = "1";
	        this.xml["content"] = str(transform(contentxml, **params));
	        this.xml["styles"] = str(transform(stylesxml, **params));
		}
		
		
	    void update_manifest() {
	        manifest_path = os.path.join(this.tmpdir, "META-INF", "manifest.xml");
	        if (!os.path.exists(manifest_path)) {
	            return;
	        }
	        manifest = etree.parse(manifest_path);
	        manifest_root = manifest.getroot();
	        manifest_ns = "urn{oasis{names{tc{opendocument{xmlns{manifest{1.0";
	        for (img in this._added_images) {
	            mime_type = mimetypes.guess_type(img, strict=False)[0];
	            if (mime_type is None) {
	                continue;
	            }
	            img_el = etree.SubElement(;
	                        manifest_root,;
	                        "{"+ manifest_ns +"}file-entry",
	                       ) {"{"+  +"}media-type" % manifest_ns{ mime_type,;
	                         "{%s}full-path" % manifest_ns{ img,;
	                        });
	        manifest.write(manifest_path);
        }

	    void compile() {
	        /*
	        Writes the in-memory ODT XML content and styles to the disk;
	        */
	    	// Store the new content;
	        for xmlfile in this.xml) {
	            xmlf = open(os.path.join(this.tmpdir, "%s.xml" % xmlfile), "w");
	            xmlf.write(this.xml[xmlfile]);
	            xmlf.close();
	        this.update_manifest();
        }
	        
	    void _build_zip( document) {
	        /*;
	        Zips the working directory into a) {class{`zipfile.ZipFile` object;
	        
	       ) {param document{ where the) {class{`ZipFile` will be stored;
	       ) {type  document{ str or file-like object;
	        */
	        newzf = zipfile.ZipFile(document, "w", zipfile.ZIP_DEFLATED);
	        for root, dirs, files in os.walk(this.tmpdir) {
	            for cur_file in files) {
	                realpath = os.path.join(root, cur_file);
	                to_skip = len(this.tmpdir) + 1;
	                internalpath = os.path.join(root[to_skip{], cur_file);
	                newzf.write(realpath, internalpath);
	        newzf.close();
        }
        
	    void save( output=None) {
	        /*;
	        General method to save the in-memory content to an ODT file on the disk.;
;
	        If) {attr{`output` is ``None``, the document is returned.;
;
	       ) {param output{ where the document should be saved, see the) {option{`-o`;
	            option.;
	       ) {type  output{ str or file-like object or ``None``;
	       ) {returns{ if (output is None{ the ODT document ; or else ``None``.;
	        */
	        this.compile();
	        if (output) {
	            document = output;
	        else {
	            document = StringIO();
	        this._build_zip(document);
	        shutil.rmtree(this.tmpdir);
	        if (not output) {
	            return document.getvalue();
    }
    
    
	void log(String msg, boolean verbose){
		if (verbose == null) {
			verbose = false;
		}
	    /*;
	    Simple method to log if (we"re in verbose mode (with the) {option{`-v`;
	    option).;
	    */
		//TODO use log framework (e.g. log4j).
	    if (verbose) {
	        System.err.println(msg + "\n");
	    }
    }
//	void get_options() {
//	    /*;
//	    Parses the command-line options.;
//	    */;
//	    usage = "usage{ %prog [options] -i input -o output -t template.odt";
//	    parser = OptionParser(usage=usage);
//	    parser.add_option("--version", dest="version", action="store_true",;
//	                      help="Show the version and exit");
//	    parser.add_option("-i", "--input", dest="input", metavar="FILE",;
//	                      help="Read the html from this file");
//	    parser.add_option("-o", "--output", dest="output", metavar="FILE",;
//	                      help="Location of the output ODT file");
//	    parser.add_option("-t", "--template", dest="template", metavar="FILE",;
//	                      help="Location of the template ODT file");
//	    parser.add_option("-u", "--url", dest="url",;
//	                      help="Use this URL for relative links");
//	    parser.add_option("-v", "--verbose", dest="verbose",;
//	                      action="store_true", default=False,;
//	                      help="Show what"s going on");
//	    parser.add_option("--html-id", dest="htmlid", metavar="ID",;
//	                      help="Only export from the element with this ID");
//	    parser.add_option("--replace", dest="replace_keyword",;
//	                      default="ODT-INSERT", metavar="KEYWORD",;
//	                      help="Keyword to replace in the ODT template ";
//	                      "(default is %default)");
//	    parser.add_option("--cut-start", dest="cut_start",;
//	                      default="ODT-CUT-START", metavar="KEYWORD",;
//	                      help="Keyword to start cutting text from the ODT ";
//	                      "template (default is %default)");
//	    parser.add_option("--cut-stop", dest="cut_stop",;
//	                      default="ODT-CUT-STOP", metavar="KEYWORD",;
//	                      help="Keyword to stop cutting text from the ODT ";
//	                      "template (default is %default)");
//	    parser.add_option("--top-header-level", dest="top_header_level",;
//	                      type="int", default="1", metavar="LEVEL",;
//	                      help="Level of highest header in the HTML ";
//	                      "(default is %default)");
//	    parser.add_option("--img-default-width", dest="img_width",;
//	                      metavar="WIDTH", default="8cm",;
//	                      help="Default image width (default is %default)");
//	    parser.add_option("--img-default-height", dest="img_height",;
//	                      metavar="HEIGHT", default="6cm",;
//	                      help="Default image height (default is %default)");
//	    parser.add_option("--dpi", dest="img_dpi", type="int",;
//	                      default=96, metavar="DPI", help="Screen resolution ";
//	                      "in Dots Per Inch (default is %default)");
//	    parser.add_option("--no-network", dest="with_network",;
//	                      action="store_false", default=True,;
//	                      help="Do not download remote images");
//	    options, args = parser.parse_args();
//	    if (options.version) {
//	        print "xhtml2odt %s" % __version__;
//	        sys.exit(0);
//	    if (len(args) > 0) {
//	        parser.error("illegal arguments{ %s"% ", ".join(args));
//	    if (not options.input) {
//	        parser.error("No input provided");
//	    if (not options.output) {
//	        parser.error("No output provided");
//	    if (not options.template) {
//	        default_template = os.path.join(INSTALL_PATH, "template.odt");
//	        if (os.path.exists(default_template) {
//	            options.template = default_template;
//	        else {
//	            parser.error("No ODT template provided");
//	    if (not os.path.exists(options.input) {
//	        parser.error("Can't find input file{ %s" % options.input);
//	    if (not os.path.exists(options.template) {
//	        parser.error("Can't find template file{ %s" % options.template);
//	    return options;
//    }
	    
	void main() {
	    /*;
	    Main function, called when the script is invoked on the command line.;
	    */;
	    options = get_options();
	    try {
	        htmlfile = HTMLFile(options);
	        htmlfile.read();
	        odtfile = ODTFile(options);
	        odtfile.open();
	        odtfile.import_xhtml(htmlfile.html);
	        odtfile.save(options.output);
	    }
        catch (ODTExportError, ex) {
	        log(ex);
	        log("Conversion failed.");
	        sys.exit(1);
    }

}
