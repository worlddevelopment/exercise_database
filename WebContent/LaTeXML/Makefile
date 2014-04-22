# This Makefile is for the LaTeXML extension to perl.
#
# It was generated automatically by MakeMaker version
# 6.57_05 (Revision: 65705) from the contents of
# Makefile.PL. Don't edit this file, edit Makefile.PL instead.
#
#       ANY CHANGES MADE HERE WILL BE LOST!
#
#   MakeMaker ARGV: ()
#

#   MakeMaker Parameters:

#     ABSTRACT => q[transforms TeX and LaTeX into XML]
#     AUTHOR => [q[Bruce Miller <bruce.miller@nist.gov>]]
#     BUILD_REQUIRES => {  }
#     EXE_FILES => [q[bin/latexml], q[bin/latexmlpost], q[bin/latexmlfind], q[bin/latexmlmath], q[bin/latexmlc], q[bin/latexmls]]
#     NAME => q[LaTeXML]
#     PREREQ_PM => { XML::LibXSLT=>q[1.58], URI=>q[0], IO::Socket::SSL=>q[0], IO::String=>q[0], Archive::Zip=>q[0], DB_File=>q[0], File::Which=>q[0], Test::Simple=>q[0], Image::Magick=>q[0], JSON::XS=>q[0], Parse::RecDescent=>q[0], IO::Socket=>q[0], XML::LibXML=>q[1.61], LWP::Simple=>q[0], LWP=>q[0] }
#     VERSION_FROM => q[lib/LaTeXML/Version.in]
#     macro => { OLD_REVISION=>q[`$(PERLRUN) -ne 'chomp;if(s/.*?REVISION\s*=\s*\"// && s/\".*//){print;}' < $(REVISION_FILE)`], REVISION=>q[`svnversion $(REVISION_BASE)`], REVISION_BASE=>q[/home/administrator/Desktop/aufgaben_db_eclipse/LaTeXML], INST_TEXMFDIR=>q[$(INST_LIB)/LaTeXML/texmf], RECORD_REVISION=>q[$(PERLRUN) -pe "s@__REVISION__@$(REVISION)@" ], REVISION_FILE=>q[$(INST_LIBDIR)/LaTeXML/Version.pm], INSTALLTEXMFDIR=>q[/usr/local/share/texmf/tex/latex/latexml], DESTINSTALLTEXMFDIR=>q[$(DESTDIR)$(INSTALLTEXMFDIR)] }

# --- MakeMaker post_initialize section:


# --- MakeMaker const_config section:

# These definitions are from config.sh (via /usr/lib/perl/5.14/Config.pm).
# They may have been overridden via Makefile.PL or on the command line.
AR = ar
CC = cc
CCCDLFLAGS = -fPIC
CCDLFLAGS = -Wl,-E
DLEXT = so
DLSRC = dl_dlopen.xs
EXE_EXT = 
FULL_AR = /usr/bin/ar
LD = cc
LDDLFLAGS = -shared -O2 -g -L/usr/local/lib -fstack-protector
LDFLAGS =  -fstack-protector -L/usr/local/lib
LIBC = 
LIB_EXT = .a
OBJ_EXT = .o
OSNAME = linux
OSVERS = 2.6.24-31-server
RANLIB = :
SITELIBEXP = /usr/local/share/perl/5.14.2
SITEARCHEXP = /usr/local/lib/perl/5.14.2
SO = so
VENDORARCHEXP = /usr/lib/perl5
VENDORLIBEXP = /usr/share/perl5


# --- MakeMaker constants section:
AR_STATIC_ARGS = cr
DIRFILESEP = /
DFSEP = $(DIRFILESEP)
NAME = LaTeXML
NAME_SYM = LaTeXML
VERSION = 0.7.9alpha
VERSION_MACRO = VERSION
VERSION_SYM = 0_7_9alpha
DEFINE_VERSION = -D$(VERSION_MACRO)=\"$(VERSION)\"
XS_VERSION = 0.7.9alpha
XS_VERSION_MACRO = XS_VERSION
XS_DEFINE_VERSION = -D$(XS_VERSION_MACRO)=\"$(XS_VERSION)\"
INST_ARCHLIB = blib/arch
INST_SCRIPT = blib/script
INST_BIN = blib/bin
INST_LIB = blib/lib
INST_MAN1DIR = blib/man1
INST_MAN3DIR = blib/man3
MAN1EXT = 1p
MAN3EXT = 3pm
INSTALLDIRS = site
DESTDIR = 
PREFIX = /usr
PERLPREFIX = $(PREFIX)
SITEPREFIX = $(PREFIX)/local
VENDORPREFIX = $(PREFIX)
INSTALLPRIVLIB = $(PERLPREFIX)/share/perl/5.14
DESTINSTALLPRIVLIB = $(DESTDIR)$(INSTALLPRIVLIB)
INSTALLSITELIB = $(SITEPREFIX)/share/perl/5.14.2
DESTINSTALLSITELIB = $(DESTDIR)$(INSTALLSITELIB)
INSTALLVENDORLIB = $(VENDORPREFIX)/share/perl5
DESTINSTALLVENDORLIB = $(DESTDIR)$(INSTALLVENDORLIB)
INSTALLARCHLIB = $(PERLPREFIX)/lib/perl/5.14
DESTINSTALLARCHLIB = $(DESTDIR)$(INSTALLARCHLIB)
INSTALLSITEARCH = $(SITEPREFIX)/lib/perl/5.14.2
DESTINSTALLSITEARCH = $(DESTDIR)$(INSTALLSITEARCH)
INSTALLVENDORARCH = $(VENDORPREFIX)/lib/perl5
DESTINSTALLVENDORARCH = $(DESTDIR)$(INSTALLVENDORARCH)
INSTALLBIN = $(PERLPREFIX)/bin
DESTINSTALLBIN = $(DESTDIR)$(INSTALLBIN)
INSTALLSITEBIN = $(SITEPREFIX)/bin
DESTINSTALLSITEBIN = $(DESTDIR)$(INSTALLSITEBIN)
INSTALLVENDORBIN = $(VENDORPREFIX)/bin
DESTINSTALLVENDORBIN = $(DESTDIR)$(INSTALLVENDORBIN)
INSTALLSCRIPT = $(PERLPREFIX)/bin
DESTINSTALLSCRIPT = $(DESTDIR)$(INSTALLSCRIPT)
INSTALLSITESCRIPT = $(SITEPREFIX)/bin
DESTINSTALLSITESCRIPT = $(DESTDIR)$(INSTALLSITESCRIPT)
INSTALLVENDORSCRIPT = $(VENDORPREFIX)/bin
DESTINSTALLVENDORSCRIPT = $(DESTDIR)$(INSTALLVENDORSCRIPT)
INSTALLMAN1DIR = $(PERLPREFIX)/share/man/man1
DESTINSTALLMAN1DIR = $(DESTDIR)$(INSTALLMAN1DIR)
INSTALLSITEMAN1DIR = $(SITEPREFIX)/man/man1
DESTINSTALLSITEMAN1DIR = $(DESTDIR)$(INSTALLSITEMAN1DIR)
INSTALLVENDORMAN1DIR = $(VENDORPREFIX)/share/man/man1
DESTINSTALLVENDORMAN1DIR = $(DESTDIR)$(INSTALLVENDORMAN1DIR)
INSTALLMAN3DIR = $(PERLPREFIX)/share/man/man3
DESTINSTALLMAN3DIR = $(DESTDIR)$(INSTALLMAN3DIR)
INSTALLSITEMAN3DIR = $(SITEPREFIX)/man/man3
DESTINSTALLSITEMAN3DIR = $(DESTDIR)$(INSTALLSITEMAN3DIR)
INSTALLVENDORMAN3DIR = $(VENDORPREFIX)/share/man/man3
DESTINSTALLVENDORMAN3DIR = $(DESTDIR)$(INSTALLVENDORMAN3DIR)
PERL_LIB = /usr/share/perl/5.14
PERL_ARCHLIB = /usr/lib/perl/5.14
LIBPERL_A = libperl.a
FIRST_MAKEFILE = Makefile
MAKEFILE_OLD = Makefile.old
MAKE_APERL_FILE = Makefile.aperl
PERLMAINCC = $(CC)
PERL_INC = /usr/lib/perl/5.14/CORE
PERL = /usr/bin/perl
FULLPERL = /usr/bin/perl
ABSPERL = $(PERL)
PERLRUN = $(PERL)
FULLPERLRUN = $(FULLPERL)
ABSPERLRUN = $(ABSPERL)
PERLRUNINST = $(PERLRUN) "-I$(INST_ARCHLIB)" "-I$(INST_LIB)"
FULLPERLRUNINST = $(FULLPERLRUN) "-I$(INST_ARCHLIB)" "-I$(INST_LIB)"
ABSPERLRUNINST = $(ABSPERLRUN) "-I$(INST_ARCHLIB)" "-I$(INST_LIB)"
PERL_CORE = 0
PERM_DIR = 755
PERM_RW = 644
PERM_RWX = 755

MAKEMAKER   = /usr/share/perl/5.14/ExtUtils/MakeMaker.pm
MM_VERSION  = 6.57_05
MM_REVISION = 65705

# FULLEXT = Pathname for extension directory (eg Foo/Bar/Oracle).
# BASEEXT = Basename part of FULLEXT. May be just equal FULLEXT. (eg Oracle)
# PARENT_NAME = NAME without BASEEXT and no trailing :: (eg Foo::Bar)
# DLBASE  = Basename part of dynamic library. May be just equal BASEEXT.
MAKE = make
FULLEXT = LaTeXML
BASEEXT = LaTeXML
PARENT_NAME = 
DLBASE = $(BASEEXT)
VERSION_FROM = lib/LaTeXML/Version.in
OBJECT = 
LDFROM = $(OBJECT)
LINKTYPE = dynamic
BOOTDEP = 

# Handy lists of source code files:
XS_FILES = 
C_FILES  = 
O_FILES  = 
H_FILES  = 
MAN1PODS = bin/latexml \
	bin/latexmlc \
	bin/latexmlfind \
	bin/latexmlmath \
	bin/latexmlpost \
	bin/latexmls
MAN3PODS = lib/LaTeXML.pm \
	lib/LaTeXML/Array.pm \
	lib/LaTeXML/Bib.pm \
	lib/LaTeXML/Box.pm \
	lib/LaTeXML/Converter.pm \
	lib/LaTeXML/Definition.pm \
	lib/LaTeXML/Document.pm \
	lib/LaTeXML/Error.pm \
	lib/LaTeXML/Font.pm \
	lib/LaTeXML/Global.pm \
	lib/LaTeXML/Gullet.pm \
	lib/LaTeXML/MathParser.pm \
	lib/LaTeXML/Model.pm \
	lib/LaTeXML/Mouth.pm \
	lib/LaTeXML/Number.pm \
	lib/LaTeXML/Object.pm \
	lib/LaTeXML/Package.pm \
	lib/LaTeXML/Parameters.pm \
	lib/LaTeXML/Post.pm \
	lib/LaTeXML/Post/MathML.pm \
	lib/LaTeXML/Rewrite.pm \
	lib/LaTeXML/State.pm \
	lib/LaTeXML/Stomach.pm \
	lib/LaTeXML/Token.pm \
	lib/LaTeXML/Util/Config.pm \
	lib/LaTeXML/Util/KeyVal.pm \
	lib/LaTeXML/Util/Pathname.pm

# Where is the Config information that we are using/depend on
CONFIGDEP = $(PERL_ARCHLIB)$(DFSEP)Config.pm $(PERL_INC)$(DFSEP)config.h

# Where to build things
INST_LIBDIR      = $(INST_LIB)
INST_ARCHLIBDIR  = $(INST_ARCHLIB)

INST_AUTODIR     = $(INST_LIB)/auto/$(FULLEXT)
INST_ARCHAUTODIR = $(INST_ARCHLIB)/auto/$(FULLEXT)

INST_STATIC      = 
INST_DYNAMIC     = 
INST_BOOT        = 

# Extra linker info
EXPORT_LIST        = 
PERL_ARCHIVE       = 
PERL_ARCHIVE_AFTER = 


TO_INST_PM = lib/LaTeXML.pm \
	lib/LaTeXML/Array.pm \
	lib/LaTeXML/Bib.pm \
	lib/LaTeXML/Box.pm \
	lib/LaTeXML/Color.pm \
	lib/LaTeXML/Common/XML.pm \
	lib/LaTeXML/Converter.pm \
	lib/LaTeXML/Definition.pm \
	lib/LaTeXML/Document.pm \
	lib/LaTeXML/Error.pm \
	lib/LaTeXML/Font.pm \
	lib/LaTeXML/Global.pm \
	lib/LaTeXML/Gullet.pm \
	lib/LaTeXML/LaTeXML.catalog \
	lib/LaTeXML/MathParser.pm \
	lib/LaTeXML/Model.pm \
	lib/LaTeXML/Model/DTD.pm \
	lib/LaTeXML/Model/RelaxNG.pm \
	lib/LaTeXML/Mouth.pm \
	lib/LaTeXML/Number.pm \
	lib/LaTeXML/Object.pm \
	lib/LaTeXML/Package.pm \
	lib/LaTeXML/Package/AmSTeX.pool.ltxml \
	lib/LaTeXML/Package/BibTeX.pool.ltxml \
	lib/LaTeXML/Package/JHEP.cls.ltxml \
	lib/LaTeXML/Package/JHEP2.cls.ltxml \
	lib/LaTeXML/Package/JHEP3.cls.ltxml \
	lib/LaTeXML/Package/LaTeX.pool.ltxml \
	lib/LaTeXML/Package/TeX.pool.ltxml \
	lib/LaTeXML/Package/a4.sty.ltxml \
	lib/LaTeXML/Package/a4wide.sty.ltxml \
	lib/LaTeXML/Package/aa.cls.ltxml \
	lib/LaTeXML/Package/aa_support.sty.ltxml \
	lib/LaTeXML/Package/aas_support.sty.ltxml \
	lib/LaTeXML/Package/aasms.sty.ltxml \
	lib/LaTeXML/Package/aaspp.sty.ltxml \
	lib/LaTeXML/Package/aastex.cls.ltxml \
	lib/LaTeXML/Package/aastex.sty.ltxml \
	lib/LaTeXML/Package/acronym.sty.ltxml \
	lib/LaTeXML/Package/ae.sty.ltxml \
	lib/LaTeXML/Package/afterpage.sty.ltxml \
	lib/LaTeXML/Package/alltt.sty.ltxml \
	lib/LaTeXML/Package/ams_core.cls.ltxml \
	lib/LaTeXML/Package/ams_support.sty.ltxml \
	lib/LaTeXML/Package/amsa.fontmap.ltxml \
	lib/LaTeXML/Package/amsart.cls.ltxml \
	lib/LaTeXML/Package/amsb.fontmap.ltxml \
	lib/LaTeXML/Package/amsbook.cls.ltxml \
	lib/LaTeXML/Package/amsbsy.sty.ltxml \
	lib/LaTeXML/Package/amscd.sty.ltxml \
	lib/LaTeXML/Package/amsfonts.sty.ltxml \
	lib/LaTeXML/Package/amsmath.sty.ltxml \
	lib/LaTeXML/Package/amsopn.sty.ltxml \
	lib/LaTeXML/Package/amsppt.sty.ltxml \
	lib/LaTeXML/Package/amsproc.cls.ltxml \
	lib/LaTeXML/Package/amsrefs.sty.ltxml \
	lib/LaTeXML/Package/amssymb.sty.ltxml \
	lib/LaTeXML/Package/amstex.sty.ltxml \
	lib/LaTeXML/Package/amstex.tex.ltxml \
	lib/LaTeXML/Package/amstext.sty.ltxml \
	lib/LaTeXML/Package/amsthm.sty.ltxml \
	lib/LaTeXML/Package/amsxtra.sty.ltxml \
	lib/LaTeXML/Package/applemac.def.ltxml \
	lib/LaTeXML/Package/array.sty.ltxml \
	lib/LaTeXML/Package/article.cls.ltxml \
	lib/LaTeXML/Package/avant.sty.ltxml \
	lib/LaTeXML/Package/babel.def.ltxml \
	lib/LaTeXML/Package/babel.sty.ltxml \
	lib/LaTeXML/Package/bbm.sty.ltxml \
	lib/LaTeXML/Package/bbold.sty.ltxml \
	lib/LaTeXML/Package/beton.sty.ltxml \
	lib/LaTeXML/Package/bm.sty.ltxml \
	lib/LaTeXML/Package/book.cls.ltxml \
	lib/LaTeXML/Package/bookman.sty.ltxml \
	lib/LaTeXML/Package/cancel.sty.ltxml \
	lib/LaTeXML/Package/caption.sty.ltxml \
	lib/LaTeXML/Package/ccfonts.sty.ltxml \
	lib/LaTeXML/Package/chancery.sty.ltxml \
	lib/LaTeXML/Package/charter.sty.ltxml \
	lib/LaTeXML/Package/cite.sty.ltxml \
	lib/LaTeXML/Package/citesort.sty.ltxml \
	lib/LaTeXML/Package/cmbright.sty.ltxml \
	lib/LaTeXML/Package/color.sty.ltxml \
	lib/LaTeXML/Package/colortbl.sty.ltxml \
	lib/LaTeXML/Package/comment.sty.ltxml \
	lib/LaTeXML/Package/concmath.sty.ltxml \
	lib/LaTeXML/Package/courier.sty.ltxml \
	lib/LaTeXML/Package/cp852.def.ltxml \
	lib/LaTeXML/Package/cropmark.sty.ltxml \
	lib/LaTeXML/Package/dcolumn.sty.ltxml \
	lib/LaTeXML/Package/doublespace.sty.ltxml \
	lib/LaTeXML/Package/eTeX.pool.ltxml \
	lib/LaTeXML/Package/elsart.cls.ltxml \
	lib/LaTeXML/Package/elsart.sty.ltxml \
	lib/LaTeXML/Package/elsart_support.sty.ltxml \
	lib/LaTeXML/Package/elsarticle.cls.ltxml \
	lib/LaTeXML/Package/emulateapj.cls.ltxml \
	lib/LaTeXML/Package/emulateapj.sty.ltxml \
	lib/LaTeXML/Package/enumerate.sty.ltxml \
	lib/LaTeXML/Package/epigraph.sty.ltxml \
	lib/LaTeXML/Package/epsf.sty.ltxml \
	lib/LaTeXML/Package/epsfig.sty.ltxml \
	lib/LaTeXML/Package/epstopdf.sty.ltxml \
	lib/LaTeXML/Package/eucal.sty.ltxml \
	lib/LaTeXML/Package/eufrak.sty.ltxml \
	lib/LaTeXML/Package/euler.sty.ltxml \
	lib/LaTeXML/Package/eulervm.sty.ltxml \
	lib/LaTeXML/Package/eurosym.sty.ltxml \
	lib/LaTeXML/Package/euscript.sty.ltxml \
	lib/LaTeXML/Package/exscale.sty.ltxml \
	lib/LaTeXML/Package/fixltx2e.sty.ltxml \
	lib/LaTeXML/Package/fleqn.sty.ltxml \
	lib/LaTeXML/Package/float.sty.ltxml \
	lib/LaTeXML/Package/floatfig.sty.ltxml \
	lib/LaTeXML/Package/floatflt.sty.ltxml \
	lib/LaTeXML/Package/fontenc.sty.ltxml \
	lib/LaTeXML/Package/fontspec.sty.ltxml \
	lib/LaTeXML/Package/footmisc.sty.ltxml \
	lib/LaTeXML/Package/fourier.sty.ltxml \
	lib/LaTeXML/Package/framed.sty.ltxml \
	lib/LaTeXML/Package/frenchb.ldf.ltxml \
	lib/LaTeXML/Package/fullpage.sty.ltxml \
	lib/LaTeXML/Package/gen-j-l.cls.ltxml \
	lib/LaTeXML/Package/gen-m-l.cls.ltxml \
	lib/LaTeXML/Package/gen-p-l.cls.ltxml \
	lib/LaTeXML/Package/german.sty.ltxml \
	lib/LaTeXML/Package/graphics.sty.ltxml \
	lib/LaTeXML/Package/graphicx.sty.ltxml \
	lib/LaTeXML/Package/helvet.sty.ltxml \
	lib/LaTeXML/Package/here.sty.ltxml \
	lib/LaTeXML/Package/hhline.sty.ltxml \
	lib/LaTeXML/Package/html.sty.ltxml \
	lib/LaTeXML/Package/hyperref.sty.ltxml \
	lib/LaTeXML/Package/hyperxmp.sty.ltxml \
	lib/LaTeXML/Package/ifpdf.sty.ltxml \
	lib/LaTeXML/Package/indentfirst.sty.ltxml \
	lib/LaTeXML/Package/inputenc.sty.ltxml \
	lib/LaTeXML/Package/inst_support.sty.ltxml \
	lib/LaTeXML/Package/iopams.sty.ltxml \
	lib/LaTeXML/Package/iopart.cls.ltxml \
	lib/LaTeXML/Package/iopart_support.sty.ltxml \
	lib/LaTeXML/Package/keyval.sty.ltxml \
	lib/LaTeXML/Package/latexml.sty.ltxml \
	lib/LaTeXML/Package/latexsym.sty.ltxml \
	lib/LaTeXML/Package/latin10.def.ltxml \
	lib/LaTeXML/Package/lgr.fontmap.ltxml \
	lib/LaTeXML/Package/listings.sty.ltxml \
	lib/LaTeXML/Package/llncs.cls.ltxml \
	lib/LaTeXML/Package/longtable.sty.ltxml \
	lib/LaTeXML/Package/lscape.sty.ltxml \
	lib/LaTeXML/Package/luximono.sty.ltxml \
	lib/LaTeXML/Package/lxRDFa.sty.ltxml \
	lib/LaTeXML/Package/ly1.fontmap.ltxml \
	lib/LaTeXML/Package/makeidx.sty.ltxml \
	lib/LaTeXML/Package/mathpazo.sty.ltxml \
	lib/LaTeXML/Package/mathpple.sty.ltxml \
	lib/LaTeXML/Package/mathptm.sty.ltxml \
	lib/LaTeXML/Package/mathptmx.sty.ltxml \
	lib/LaTeXML/Package/mathrsfs.sty.ltxml \
	lib/LaTeXML/Package/mn.cls.ltxml \
	lib/LaTeXML/Package/mn2e.cls.ltxml \
	lib/LaTeXML/Package/mn2e_support.sty.ltxml \
	lib/LaTeXML/Package/multicol.sty.ltxml \
	lib/LaTeXML/Package/multido.sty.ltxml \
	lib/LaTeXML/Package/multirow.sty.ltxml \
	lib/LaTeXML/Package/natbib.sty.ltxml \
	lib/LaTeXML/Package/newcent.sty.ltxml \
	lib/LaTeXML/Package/ngerman.sty.ltxml \
	lib/LaTeXML/Package/ntheorem.sty.ltxml \
	lib/LaTeXML/Package/numprint.sty.ltxml \
	lib/LaTeXML/Package/ot4.fontmap.ltxml \
	lib/LaTeXML/Package/palatino.sty.ltxml \
	lib/LaTeXML/Package/paralist.sty.ltxml \
	lib/LaTeXML/Package/pdfTeX.pool.ltxml \
	lib/LaTeXML/Package/pifont.sty.ltxml \
	lib/LaTeXML/Package/placeins.sty.ltxml \
	lib/LaTeXML/Package/psfig.sty.ltxml \
	lib/LaTeXML/Package/pspicture.sty.ltxml \
	lib/LaTeXML/Package/pst-grad.sty.ltxml \
	lib/LaTeXML/Package/pst-node.sty.ltxml \
	lib/LaTeXML/Package/pstricks.sty.ltxml \
	lib/LaTeXML/Package/pxfonts.sty.ltxml \
	lib/LaTeXML/Package/report.cls.ltxml \
	lib/LaTeXML/Package/revsymb.sty.ltxml \
	lib/LaTeXML/Package/revtex.cls.ltxml \
	lib/LaTeXML/Package/revtex.sty.ltxml \
	lib/LaTeXML/Package/revtex3_support.sty.ltxml \
	lib/LaTeXML/Package/revtex4.cls.ltxml \
	lib/LaTeXML/Package/revtex4.sty.ltxml \
	lib/LaTeXML/Package/revtex4_support.sty.ltxml \
	lib/LaTeXML/Package/rsfs.sty.ltxml \
	lib/LaTeXML/Package/scalefnt.sty.ltxml \
	lib/LaTeXML/Package/setspace.sty.ltxml \
	lib/LaTeXML/Package/slides.cls.ltxml \
	lib/LaTeXML/Package/subfigure.sty.ltxml \
	lib/LaTeXML/Package/supertabular.sty.ltxml \
	lib/LaTeXML/Package/sv_support.sty.ltxml \
	lib/LaTeXML/Package/svjour.cls.ltxml \
	lib/LaTeXML/Package/svmult.cls.ltxml \
	lib/LaTeXML/Package/t1.fontmap.ltxml \
	lib/LaTeXML/Package/t1enc.def.ltxml \
	lib/LaTeXML/Package/t2a.fontmap.ltxml \
	lib/LaTeXML/Package/t2b.fontmap.ltxml \
	lib/LaTeXML/Package/t2c.fontmap.ltxml \
	lib/LaTeXML/Package/tabularx.sty.ltxml \
	lib/LaTeXML/Package/textcomp.sty.ltxml \
	lib/LaTeXML/Package/texvc.sty.ltxml \
	lib/LaTeXML/Package/theorem.sty.ltxml \
	lib/LaTeXML/Package/times.sty.ltxml \
	lib/LaTeXML/Package/tocbibind.sty.ltxml \
	lib/LaTeXML/Package/ts1.fontmap.ltxml \
	lib/LaTeXML/Package/txfonts.sty.ltxml \
	lib/LaTeXML/Package/upgreek.sty.ltxml \
	lib/LaTeXML/Package/upref.sty.ltxml \
	lib/LaTeXML/Package/url.sty.ltxml \
	lib/LaTeXML/Package/utf8.def.ltxml \
	lib/LaTeXML/Package/utopia.sty.ltxml \
	lib/LaTeXML/Package/wrapfig.sty.ltxml \
	lib/LaTeXML/Package/xcolor.sty.ltxml \
	lib/LaTeXML/Package/xspace.sty.ltxml \
	lib/LaTeXML/Package/xunicode.sty.ltxml \
	lib/LaTeXML/Package/yfonts.sty.ltxml \
	lib/LaTeXML/Parameters.pm \
	lib/LaTeXML/Post.pm \
	lib/LaTeXML/Post/Collector.pm \
	lib/LaTeXML/Post/CrossRef.pm \
	lib/LaTeXML/Post/Graphics.pm \
	lib/LaTeXML/Post/LaTeXImages.pm \
	lib/LaTeXML/Post/MakeBibliography.pm \
	lib/LaTeXML/Post/MakeIndex.pm \
	lib/LaTeXML/Post/MathImages.pm \
	lib/LaTeXML/Post/MathML.pm \
	lib/LaTeXML/Post/OpenMath.pm \
	lib/LaTeXML/Post/PictureImages.pm \
	lib/LaTeXML/Post/SVG.pm \
	lib/LaTeXML/Post/Scan.pm \
	lib/LaTeXML/Post/Split.pm \
	lib/LaTeXML/Post/Writer.pm \
	lib/LaTeXML/Post/XMath.pm \
	lib/LaTeXML/Post/XSLT.pm \
	lib/LaTeXML/Rewrite.pm \
	lib/LaTeXML/State.pm \
	lib/LaTeXML/Stomach.pm \
	lib/LaTeXML/Token.pm \
	lib/LaTeXML/Util/Alignment.pm \
	lib/LaTeXML/Util/Config.pm \
	lib/LaTeXML/Util/Geometry.pm \
	lib/LaTeXML/Util/KeyVal.pm \
	lib/LaTeXML/Util/MathMLLinebreaker.pm \
	lib/LaTeXML/Util/ObjectDB.pm \
	lib/LaTeXML/Util/Pathname.pm \
	lib/LaTeXML/Util/Test.pm \
	lib/LaTeXML/Util/Transform.pm \
	lib/LaTeXML/resources/CSS/LaTeXML-blue.css \
	lib/LaTeXML/resources/CSS/LaTeXML-marginpar.css \
	lib/LaTeXML/resources/CSS/LaTeXML-navbar-left.css \
	lib/LaTeXML/resources/CSS/LaTeXML-navbar-right.css \
	lib/LaTeXML/resources/CSS/LaTeXML.css \
	lib/LaTeXML/resources/CSS/ltx-amsart.css \
	lib/LaTeXML/resources/CSS/ltx-apj.css \
	lib/LaTeXML/resources/CSS/ltx-article.css \
	lib/LaTeXML/resources/CSS/ltx-book.css \
	lib/LaTeXML/resources/CSS/ltx-report.css \
	lib/LaTeXML/resources/DTD/LaTeXML.dtd \
	lib/LaTeXML/resources/Profiles/fragment-html.opt \
	lib/LaTeXML/resources/Profiles/fragment-xhtml.opt \
	lib/LaTeXML/resources/Profiles/fragment.opt \
	lib/LaTeXML/resources/Profiles/math.opt \
	lib/LaTeXML/resources/Profiles/modern.opt \
	lib/LaTeXML/resources/Profiles/standard.opt \
	lib/LaTeXML/resources/Profiles/stex-migration.opt \
	lib/LaTeXML/resources/Profiles/stex-module.opt \
	lib/LaTeXML/resources/Profiles/stex.opt \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.model \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-font.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-font.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-image.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-image.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-qname.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-script.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-script.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-style.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-style.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-text.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-text.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-view.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-view.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg11-basic.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg11-tiny.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg11.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg11.rng \
	lib/LaTeXML/resources/XSLT/LaTeXML-all-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-bib-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-block-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-common.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-html.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-html5.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-inline-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-math-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-meta-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-misc-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-para-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-picture-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-structure-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-tabular-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-webpage-xhtml.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-xhtml.xsl \
	lib/LaTeXML/resources/javascript/LaTeXML-maybeMathjax.js \
	lib/LaTeXML/texmf/latexml.sty \
	lib/LaTeXML/texmf/lxRDFa.sty

PM_TO_BLIB = lib/LaTeXML/Package/pst-grad.sty.ltxml \
	blib/lib/LaTeXML/Package/pst-grad.sty.ltxml \
	lib/LaTeXML/Package/xspace.sty.ltxml \
	blib/lib/LaTeXML/Package/xspace.sty.ltxml \
	lib/LaTeXML/Package/natbib.sty.ltxml \
	blib/lib/LaTeXML/Package/natbib.sty.ltxml \
	lib/LaTeXML/resources/Profiles/stex-module.opt \
	blib/lib/LaTeXML/resources/Profiles/stex-module.opt \
	lib/LaTeXML/Package/multido.sty.ltxml \
	blib/lib/LaTeXML/Package/multido.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rng \
	lib/LaTeXML/Model/DTD.pm \
	blib/lib/LaTeXML/Model/DTD.pm \
	lib/LaTeXML/Package/alltt.sty.ltxml \
	blib/lib/LaTeXML/Package/alltt.sty.ltxml \
	lib/LaTeXML/Util/Config.pm \
	blib/lib/LaTeXML/Util/Config.pm \
	lib/LaTeXML/Package/charter.sty.ltxml \
	blib/lib/LaTeXML/Package/charter.sty.ltxml \
	lib/LaTeXML/Package/afterpage.sty.ltxml \
	blib/lib/LaTeXML/Package/afterpage.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rnc \
	lib/LaTeXML/Definition.pm \
	blib/lib/LaTeXML/Definition.pm \
	lib/LaTeXML/Package/aas_support.sty.ltxml \
	blib/lib/LaTeXML/Package/aas_support.sty.ltxml \
	lib/LaTeXML/State.pm \
	blib/lib/LaTeXML/State.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-image.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-image.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rng \
	lib/LaTeXML/resources/CSS/LaTeXML-marginpar.css \
	blib/lib/LaTeXML/resources/CSS/LaTeXML-marginpar.css \
	lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rng \
	lib/LaTeXML/Post/MakeBibliography.pm \
	blib/lib/LaTeXML/Post/MakeBibliography.pm \
	lib/LaTeXML/Package/amsmath.sty.ltxml \
	blib/lib/LaTeXML/Package/amsmath.sty.ltxml \
	lib/LaTeXML/Package/upgreek.sty.ltxml \
	blib/lib/LaTeXML/Package/upgreek.sty.ltxml \
	lib/LaTeXML/Package/lscape.sty.ltxml \
	blib/lib/LaTeXML/Package/lscape.sty.ltxml \
	lib/LaTeXML/resources/Profiles/stex-migration.opt \
	blib/lib/LaTeXML/resources/Profiles/stex-migration.opt \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rnc \
	lib/LaTeXML/Post/OpenMath.pm \
	blib/lib/LaTeXML/Post/OpenMath.pm \
	lib/LaTeXML/Package/amscd.sty.ltxml \
	blib/lib/LaTeXML/Package/amscd.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-meta-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-meta-xhtml.xsl \
	lib/LaTeXML/Package/mathrsfs.sty.ltxml \
	blib/lib/LaTeXML/Package/mathrsfs.sty.ltxml \
	lib/LaTeXML/Package/dcolumn.sty.ltxml \
	blib/lib/LaTeXML/Package/dcolumn.sty.ltxml \
	lib/LaTeXML/Package/TeX.pool.ltxml \
	blib/lib/LaTeXML/Package/TeX.pool.ltxml \
	lib/LaTeXML/Package/iopams.sty.ltxml \
	blib/lib/LaTeXML/Package/iopams.sty.ltxml \
	lib/LaTeXML/Package/psfig.sty.ltxml \
	blib/lib/LaTeXML/Package/psfig.sty.ltxml \
	lib/LaTeXML/resources/Profiles/math.opt \
	blib/lib/LaTeXML/resources/Profiles/math.opt \
	lib/LaTeXML/Rewrite.pm \
	blib/lib/LaTeXML/Rewrite.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg11.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg11.rnc \
	lib/LaTeXML/Package/eurosym.sty.ltxml \
	blib/lib/LaTeXML/Package/eurosym.sty.ltxml \
	lib/LaTeXML/Util/MathMLLinebreaker.pm \
	blib/lib/LaTeXML/Util/MathMLLinebreaker.pm \
	lib/LaTeXML/Package/float.sty.ltxml \
	blib/lib/LaTeXML/Package/float.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rnc \
	lib/LaTeXML/Package/amsb.fontmap.ltxml \
	blib/lib/LaTeXML/Package/amsb.fontmap.ltxml \
	lib/LaTeXML/Package/footmisc.sty.ltxml \
	blib/lib/LaTeXML/Package/footmisc.sty.ltxml \
	lib/LaTeXML/resources/CSS/LaTeXML.css \
	blib/lib/LaTeXML/resources/CSS/LaTeXML.css \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-view.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-view.rnc \
	lib/LaTeXML/Package/tocbibind.sty.ltxml \
	blib/lib/LaTeXML/Package/tocbibind.sty.ltxml \
	lib/LaTeXML/Package/fullpage.sty.ltxml \
	blib/lib/LaTeXML/Package/fullpage.sty.ltxml \
	lib/LaTeXML/Package/fixltx2e.sty.ltxml \
	blib/lib/LaTeXML/Package/fixltx2e.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rnc \
	lib/LaTeXML/Util/Transform.pm \
	blib/lib/LaTeXML/Util/Transform.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rng \
	lib/LaTeXML/Package/amsxtra.sty.ltxml \
	blib/lib/LaTeXML/Package/amsxtra.sty.ltxml \
	lib/LaTeXML/Package/hyperxmp.sty.ltxml \
	blib/lib/LaTeXML/Package/hyperxmp.sty.ltxml \
	lib/LaTeXML/Package/lgr.fontmap.ltxml \
	blib/lib/LaTeXML/Package/lgr.fontmap.ltxml \
	lib/LaTeXML/Package/lxRDFa.sty.ltxml \
	blib/lib/LaTeXML/Package/lxRDFa.sty.ltxml \
	lib/LaTeXML/Package/amsfonts.sty.ltxml \
	blib/lib/LaTeXML/Package/amsfonts.sty.ltxml \
	lib/LaTeXML/Box.pm \
	blib/lib/LaTeXML/Box.pm \
	lib/LaTeXML/Package/setspace.sty.ltxml \
	blib/lib/LaTeXML/Package/setspace.sty.ltxml \
	lib/LaTeXML/Package/revtex4_support.sty.ltxml \
	blib/lib/LaTeXML/Package/revtex4_support.sty.ltxml \
	lib/LaTeXML/Package/elsart.sty.ltxml \
	blib/lib/LaTeXML/Package/elsart.sty.ltxml \
	lib/LaTeXML/resources/Profiles/standard.opt \
	blib/lib/LaTeXML/resources/Profiles/standard.opt \
	lib/LaTeXML/resources/XSLT/LaTeXML-html5.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-html5.xsl \
	lib/LaTeXML/Package/enumerate.sty.ltxml \
	blib/lib/LaTeXML/Package/enumerate.sty.ltxml \
	lib/LaTeXML/Package/gen-j-l.cls.ltxml \
	blib/lib/LaTeXML/Package/gen-j-l.cls.ltxml \
	lib/LaTeXML/Package/yfonts.sty.ltxml \
	blib/lib/LaTeXML/Package/yfonts.sty.ltxml \
	lib/LaTeXML/Package/svmult.cls.ltxml \
	blib/lib/LaTeXML/Package/svmult.cls.ltxml \
	lib/LaTeXML/Package/txfonts.sty.ltxml \
	blib/lib/LaTeXML/Package/txfonts.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rnc \
	lib/LaTeXML/Package/paralist.sty.ltxml \
	blib/lib/LaTeXML/Package/paralist.sty.ltxml \
	lib/LaTeXML/Package/t2a.fontmap.ltxml \
	blib/lib/LaTeXML/Package/t2a.fontmap.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-font.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-font.rnc \
	lib/LaTeXML/Package.pm \
	blib/lib/LaTeXML/Package.pm \
	lib/LaTeXML/Package/listings.sty.ltxml \
	blib/lib/LaTeXML/Package/listings.sty.ltxml \
	lib/LaTeXML/Global.pm \
	blib/lib/LaTeXML/Global.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rng \
	lib/LaTeXML/Package/longtable.sty.ltxml \
	blib/lib/LaTeXML/Package/longtable.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rng \
	lib/LaTeXML/Package/inputenc.sty.ltxml \
	blib/lib/LaTeXML/Package/inputenc.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rng \
	lib/LaTeXML/Package/bbold.sty.ltxml \
	blib/lib/LaTeXML/Package/bbold.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-style.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-style.rng \
	lib/LaTeXML/Package/here.sty.ltxml \
	blib/lib/LaTeXML/Package/here.sty.ltxml \
	lib/LaTeXML/Package/indentfirst.sty.ltxml \
	blib/lib/LaTeXML/Package/indentfirst.sty.ltxml \
	lib/LaTeXML/resources/CSS/LaTeXML-navbar-left.css \
	blib/lib/LaTeXML/resources/CSS/LaTeXML-navbar-left.css \
	lib/LaTeXML/Package/ntheorem.sty.ltxml \
	blib/lib/LaTeXML/Package/ntheorem.sty.ltxml \
	lib/LaTeXML/Package/xcolor.sty.ltxml \
	blib/lib/LaTeXML/Package/xcolor.sty.ltxml \
	lib/LaTeXML/Package/textcomp.sty.ltxml \
	blib/lib/LaTeXML/Package/textcomp.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rnc \
	lib/LaTeXML/texmf/latexml.sty \
	blib/lib/LaTeXML/texmf/latexml.sty \
	lib/LaTeXML/resources/Profiles/fragment-xhtml.opt \
	blib/lib/LaTeXML/resources/Profiles/fragment-xhtml.opt \
	lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rng \
	lib/LaTeXML/Package/amsbook.cls.ltxml \
	blib/lib/LaTeXML/Package/amsbook.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rnc \
	lib/LaTeXML/Package/t2c.fontmap.ltxml \
	blib/lib/LaTeXML/Package/t2c.fontmap.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-picture-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-picture-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rng \
	lib/LaTeXML/resources/Profiles/fragment-html.opt \
	blib/lib/LaTeXML/resources/Profiles/fragment-html.opt \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rnc \
	lib/LaTeXML/Array.pm \
	blib/lib/LaTeXML/Array.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-qname.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-qname.rng \
	lib/LaTeXML/Package/keyval.sty.ltxml \
	blib/lib/LaTeXML/Package/keyval.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-bib-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-bib-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rng \
	lib/LaTeXML/Post/XMath.pm \
	blib/lib/LaTeXML/Post/XMath.pm \
	lib/LaTeXML/Package/utf8.def.ltxml \
	blib/lib/LaTeXML/Package/utf8.def.ltxml \
	lib/LaTeXML/Package/texvc.sty.ltxml \
	blib/lib/LaTeXML/Package/texvc.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rnc \
	lib/LaTeXML/Package/colortbl.sty.ltxml \
	blib/lib/LaTeXML/Package/colortbl.sty.ltxml \
	lib/LaTeXML/Package/acronym.sty.ltxml \
	blib/lib/LaTeXML/Package/acronym.sty.ltxml \
	lib/LaTeXML/Package/amsopn.sty.ltxml \
	blib/lib/LaTeXML/Package/amsopn.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-font.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-font.rng \
	lib/LaTeXML/Package/caption.sty.ltxml \
	blib/lib/LaTeXML/Package/caption.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rng \
	lib/LaTeXML/Package/latin10.def.ltxml \
	blib/lib/LaTeXML/Package/latin10.def.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rnc \
	lib/LaTeXML/Token.pm \
	blib/lib/LaTeXML/Token.pm \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rng \
	lib/LaTeXML/Package/AmSTeX.pool.ltxml \
	blib/lib/LaTeXML/Package/AmSTeX.pool.ltxml \
	lib/LaTeXML/Package/ot4.fontmap.ltxml \
	blib/lib/LaTeXML/Package/ot4.fontmap.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rng \
	lib/LaTeXML/Package/ccfonts.sty.ltxml \
	blib/lib/LaTeXML/Package/ccfonts.sty.ltxml \
	lib/LaTeXML/Package/amstext.sty.ltxml \
	blib/lib/LaTeXML/Package/amstext.sty.ltxml \
	lib/LaTeXML/Package/citesort.sty.ltxml \
	blib/lib/LaTeXML/Package/citesort.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rnc \
	lib/LaTeXML/Package/frenchb.ldf.ltxml \
	blib/lib/LaTeXML/Package/frenchb.ldf.ltxml \
	lib/LaTeXML/Package/gen-m-l.cls.ltxml \
	blib/lib/LaTeXML/Package/gen-m-l.cls.ltxml \
	lib/LaTeXML/Package/courier.sty.ltxml \
	blib/lib/LaTeXML/Package/courier.sty.ltxml \
	lib/LaTeXML/Package/aastex.cls.ltxml \
	blib/lib/LaTeXML/Package/aastex.cls.ltxml \
	lib/LaTeXML/Post/MakeIndex.pm \
	blib/lib/LaTeXML/Post/MakeIndex.pm \
	lib/LaTeXML/Package/revsymb.sty.ltxml \
	blib/lib/LaTeXML/Package/revsymb.sty.ltxml \
	lib/LaTeXML/Package/sv_support.sty.ltxml \
	blib/lib/LaTeXML/Package/sv_support.sty.ltxml \
	lib/LaTeXML/Package/doublespace.sty.ltxml \
	blib/lib/LaTeXML/Package/doublespace.sty.ltxml \
	lib/LaTeXML/Post/XSLT.pm \
	blib/lib/LaTeXML/Post/XSLT.pm \
	lib/LaTeXML/resources/XSLT/LaTeXML-para-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-para-xhtml.xsl \
	lib/LaTeXML/Post/Scan.pm \
	blib/lib/LaTeXML/Post/Scan.pm \
	lib/LaTeXML/Package/article.cls.ltxml \
	blib/lib/LaTeXML/Package/article.cls.ltxml \
	lib/LaTeXML/Package/cropmark.sty.ltxml \
	blib/lib/LaTeXML/Package/cropmark.sty.ltxml \
	lib/LaTeXML/Common/XML.pm \
	blib/lib/LaTeXML/Common/XML.pm \
	lib/LaTeXML/Package/scalefnt.sty.ltxml \
	blib/lib/LaTeXML/Package/scalefnt.sty.ltxml \
	lib/LaTeXML/resources/CSS/ltx-article.css \
	blib/lib/LaTeXML/resources/CSS/ltx-article.css \
	lib/LaTeXML/Package/eucal.sty.ltxml \
	blib/lib/LaTeXML/Package/eucal.sty.ltxml \
	lib/LaTeXML/Package/pstricks.sty.ltxml \
	blib/lib/LaTeXML/Package/pstricks.sty.ltxml \
	lib/LaTeXML/Package/times.sty.ltxml \
	blib/lib/LaTeXML/Package/times.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rnc \
	lib/LaTeXML/Package/bookman.sty.ltxml \
	blib/lib/LaTeXML/Package/bookman.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-style.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-style.rnc \
	lib/LaTeXML/Package/BibTeX.pool.ltxml \
	blib/lib/LaTeXML/Package/BibTeX.pool.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rnc \
	lib/LaTeXML/Package/report.cls.ltxml \
	blib/lib/LaTeXML/Package/report.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rnc \
	lib/LaTeXML/Util/KeyVal.pm \
	blib/lib/LaTeXML/Util/KeyVal.pm \
	lib/LaTeXML/resources/DTD/LaTeXML.dtd \
	blib/lib/LaTeXML/resources/DTD/LaTeXML.dtd \
	lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rng \
	lib/LaTeXML/Package/luximono.sty.ltxml \
	blib/lib/LaTeXML/Package/luximono.sty.ltxml \
	lib/LaTeXML/Package/amsppt.sty.ltxml \
	blib/lib/LaTeXML/Package/amsppt.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-all-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-all-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg-view.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-view.rng \
	lib/LaTeXML/Package/mathpple.sty.ltxml \
	blib/lib/LaTeXML/Package/mathpple.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rng \
	lib/LaTeXML/Package/revtex.sty.ltxml \
	blib/lib/LaTeXML/Package/revtex.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rnc \
	lib/LaTeXML/Package/babel.def.ltxml \
	blib/lib/LaTeXML/Package/babel.def.ltxml \
	lib/LaTeXML/Package/latexml.sty.ltxml \
	blib/lib/LaTeXML/Package/latexml.sty.ltxml \
	lib/LaTeXML/Package/placeins.sty.ltxml \
	blib/lib/LaTeXML/Package/placeins.sty.ltxml \
	lib/LaTeXML/Package/gen-p-l.cls.ltxml \
	blib/lib/LaTeXML/Package/gen-p-l.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rng \
	lib/LaTeXML/Package/multirow.sty.ltxml \
	blib/lib/LaTeXML/Package/multirow.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg11-basic.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg11-basic.rng \
	lib/LaTeXML/Package/JHEP2.cls.ltxml \
	blib/lib/LaTeXML/Package/JHEP2.cls.ltxml \
	lib/LaTeXML/Package/babel.sty.ltxml \
	blib/lib/LaTeXML/Package/babel.sty.ltxml \
	lib/LaTeXML/Package/iopart_support.sty.ltxml \
	blib/lib/LaTeXML/Package/iopart_support.sty.ltxml \
	lib/LaTeXML/Package/mathpazo.sty.ltxml \
	blib/lib/LaTeXML/Package/mathpazo.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-text.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-text.rnc \
	lib/LaTeXML/Package/graphicx.sty.ltxml \
	blib/lib/LaTeXML/Package/graphicx.sty.ltxml \
	lib/LaTeXML/Parameters.pm \
	blib/lib/LaTeXML/Parameters.pm \
	lib/LaTeXML/Package/bbm.sty.ltxml \
	blib/lib/LaTeXML/Package/bbm.sty.ltxml \
	lib/LaTeXML/Package/hyperref.sty.ltxml \
	blib/lib/LaTeXML/Package/hyperref.sty.ltxml \
	lib/LaTeXML/Package/aastex.sty.ltxml \
	blib/lib/LaTeXML/Package/aastex.sty.ltxml \
	lib/LaTeXML/Stomach.pm \
	blib/lib/LaTeXML/Stomach.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rng \
	lib/LaTeXML/Package/amsbsy.sty.ltxml \
	blib/lib/LaTeXML/Package/amsbsy.sty.ltxml \
	lib/LaTeXML/LaTeXML.catalog \
	blib/lib/LaTeXML/LaTeXML.catalog \
	lib/LaTeXML/Number.pm \
	blib/lib/LaTeXML/Number.pm \
	lib/LaTeXML/Package/fontenc.sty.ltxml \
	blib/lib/LaTeXML/Package/fontenc.sty.ltxml \
	lib/LaTeXML/resources/CSS/ltx-book.css \
	blib/lib/LaTeXML/resources/CSS/ltx-book.css \
	lib/LaTeXML/Package/numprint.sty.ltxml \
	blib/lib/LaTeXML/Package/numprint.sty.ltxml \
	lib/LaTeXML/Post/Graphics.pm \
	blib/lib/LaTeXML/Post/Graphics.pm \
	lib/LaTeXML/Package/chancery.sty.ltxml \
	blib/lib/LaTeXML/Package/chancery.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-webpage-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-webpage-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rnc \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rnc \
	lib/LaTeXML/Package/mn2e.cls.ltxml \
	blib/lib/LaTeXML/Package/mn2e.cls.ltxml \
	lib/LaTeXML/Package/utopia.sty.ltxml \
	blib/lib/LaTeXML/Package/utopia.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rnc \
	lib/LaTeXML/Package/ifpdf.sty.ltxml \
	blib/lib/LaTeXML/Package/ifpdf.sty.ltxml \
	lib/LaTeXML/Package/theorem.sty.ltxml \
	blib/lib/LaTeXML/Package/theorem.sty.ltxml \
	lib/LaTeXML/resources/CSS/LaTeXML-blue.css \
	blib/lib/LaTeXML/resources/CSS/LaTeXML-blue.css \
	lib/LaTeXML/Package/mn.cls.ltxml \
	blib/lib/LaTeXML/Package/mn.cls.ltxml \
	lib/LaTeXML/Model.pm \
	blib/lib/LaTeXML/Model.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rnc \
	lib/LaTeXML/Package/ts1.fontmap.ltxml \
	blib/lib/LaTeXML/Package/ts1.fontmap.ltxml \
	lib/LaTeXML/Package/ae.sty.ltxml \
	blib/lib/LaTeXML/Package/ae.sty.ltxml \
	lib/LaTeXML/Package/amssymb.sty.ltxml \
	blib/lib/LaTeXML/Package/amssymb.sty.ltxml \
	lib/LaTeXML/Package/aa_support.sty.ltxml \
	blib/lib/LaTeXML/Package/aa_support.sty.ltxml \
	lib/LaTeXML/Package/pdfTeX.pool.ltxml \
	blib/lib/LaTeXML/Package/pdfTeX.pool.ltxml \
	lib/LaTeXML/Package/a4.sty.ltxml \
	blib/lib/LaTeXML/Package/a4.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.rng \
	lib/LaTeXML/Package/LaTeX.pool.ltxml \
	blib/lib/LaTeXML/Package/LaTeX.pool.ltxml \
	lib/LaTeXML/Package/emulateapj.cls.ltxml \
	blib/lib/LaTeXML/Package/emulateapj.cls.ltxml \
	lib/LaTeXML/Package/bm.sty.ltxml \
	blib/lib/LaTeXML/Package/bm.sty.ltxml \
	lib/LaTeXML/Package/exscale.sty.ltxml \
	blib/lib/LaTeXML/Package/exscale.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-tabular-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-tabular-xhtml.xsl \
	lib/LaTeXML/Package/epsfig.sty.ltxml \
	blib/lib/LaTeXML/Package/epsfig.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rng \
	lib/LaTeXML/Post/PictureImages.pm \
	blib/lib/LaTeXML/Post/PictureImages.pm \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rnc \
	lib/LaTeXML/Package/epsf.sty.ltxml \
	blib/lib/LaTeXML/Package/epsf.sty.ltxml \
	lib/LaTeXML/Package/amstex.tex.ltxml \
	blib/lib/LaTeXML/Package/amstex.tex.ltxml \
	lib/LaTeXML/Model/RelaxNG.pm \
	blib/lib/LaTeXML/Model/RelaxNG.pm \
	lib/LaTeXML/Package/iopart.cls.ltxml \
	blib/lib/LaTeXML/Package/iopart.cls.ltxml \
	lib/LaTeXML/Post/Split.pm \
	blib/lib/LaTeXML/Post/Split.pm \
	lib/LaTeXML/Package/mathptmx.sty.ltxml \
	blib/lib/LaTeXML/Package/mathptmx.sty.ltxml \
	lib/LaTeXML/resources/CSS/LaTeXML-navbar-right.css \
	blib/lib/LaTeXML/resources/CSS/LaTeXML-navbar-right.css \
	lib/LaTeXML/Package/rsfs.sty.ltxml \
	blib/lib/LaTeXML/Package/rsfs.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rnc \
	lib/LaTeXML/Package/multicol.sty.ltxml \
	blib/lib/LaTeXML/Package/multicol.sty.ltxml \
	lib/LaTeXML/Package/emulateapj.sty.ltxml \
	blib/lib/LaTeXML/Package/emulateapj.sty.ltxml \
	lib/LaTeXML/Package/svjour.cls.ltxml \
	blib/lib/LaTeXML/Package/svjour.cls.ltxml \
	lib/LaTeXML/Package/pxfonts.sty.ltxml \
	blib/lib/LaTeXML/Package/pxfonts.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rnc \
	lib/LaTeXML/Package/eufrak.sty.ltxml \
	blib/lib/LaTeXML/Package/eufrak.sty.ltxml \
	lib/LaTeXML/Package/pst-node.sty.ltxml \
	blib/lib/LaTeXML/Package/pst-node.sty.ltxml \
	lib/LaTeXML/Package/fourier.sty.ltxml \
	blib/lib/LaTeXML/Package/fourier.sty.ltxml \
	lib/LaTeXML/Package/tabularx.sty.ltxml \
	blib/lib/LaTeXML/Package/tabularx.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rng \
	lib/LaTeXML/Package/subfigure.sty.ltxml \
	blib/lib/LaTeXML/Package/subfigure.sty.ltxml \
	lib/LaTeXML/texmf/lxRDFa.sty \
	blib/lib/LaTeXML/texmf/lxRDFa.sty \
	lib/LaTeXML/Package/revtex4.cls.ltxml \
	blib/lib/LaTeXML/Package/revtex4.cls.ltxml \
	lib/LaTeXML/Package/hhline.sty.ltxml \
	blib/lib/LaTeXML/Package/hhline.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rng \
	lib/LaTeXML/Converter.pm \
	blib/lib/LaTeXML/Converter.pm \
	lib/LaTeXML/Package/color.sty.ltxml \
	blib/lib/LaTeXML/Package/color.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rnc \
	lib/LaTeXML/Post.pm \
	blib/lib/LaTeXML/Post.pm \
	lib/LaTeXML/Package/concmath.sty.ltxml \
	blib/lib/LaTeXML/Package/concmath.sty.ltxml \
	lib/LaTeXML/Package/aaspp.sty.ltxml \
	blib/lib/LaTeXML/Package/aaspp.sty.ltxml \
	lib/LaTeXML/Package/upref.sty.ltxml \
	blib/lib/LaTeXML/Package/upref.sty.ltxml \
	lib/LaTeXML/Post/LaTeXImages.pm \
	blib/lib/LaTeXML/Post/LaTeXImages.pm \
	lib/LaTeXML/Package/newcent.sty.ltxml \
	blib/lib/LaTeXML/Package/newcent.sty.ltxml \
	lib/LaTeXML/resources/Profiles/fragment.opt \
	blib/lib/LaTeXML/resources/Profiles/fragment.opt \
	lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rng \
	lib/LaTeXML/Package/inst_support.sty.ltxml \
	blib/lib/LaTeXML/Package/inst_support.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rng \
	lib/LaTeXML/Post/Writer.pm \
	blib/lib/LaTeXML/Post/Writer.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rng \
	lib/LaTeXML/Package/euscript.sty.ltxml \
	blib/lib/LaTeXML/Package/euscript.sty.ltxml \
	lib/LaTeXML/resources/CSS/ltx-amsart.css \
	blib/lib/LaTeXML/resources/CSS/ltx-amsart.css \
	lib/LaTeXML/Package/llncs.cls.ltxml \
	blib/lib/LaTeXML/Package/llncs.cls.ltxml \
	lib/LaTeXML/Package/epigraph.sty.ltxml \
	blib/lib/LaTeXML/Package/epigraph.sty.ltxml \
	lib/LaTeXML/Package/amsproc.cls.ltxml \
	blib/lib/LaTeXML/Package/amsproc.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rnc \
	lib/LaTeXML/Package/t1enc.def.ltxml \
	blib/lib/LaTeXML/Package/t1enc.def.ltxml \
	lib/LaTeXML/Package/mathptm.sty.ltxml \
	blib/lib/LaTeXML/Package/mathptm.sty.ltxml \
	lib/LaTeXML/Package/floatfig.sty.ltxml \
	blib/lib/LaTeXML/Package/floatfig.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rng \
	lib/LaTeXML/Package/aa.cls.ltxml \
	blib/lib/LaTeXML/Package/aa.cls.ltxml \
	lib/LaTeXML/Package/palatino.sty.ltxml \
	blib/lib/LaTeXML/Package/palatino.sty.ltxml \
	lib/LaTeXML/Package/ngerman.sty.ltxml \
	blib/lib/LaTeXML/Package/ngerman.sty.ltxml \
	lib/LaTeXML/Package/html.sty.ltxml \
	blib/lib/LaTeXML/Package/html.sty.ltxml \
	lib/LaTeXML/Package/supertabular.sty.ltxml \
	blib/lib/LaTeXML/Package/supertabular.sty.ltxml \
	lib/LaTeXML/Util/Pathname.pm \
	blib/lib/LaTeXML/Util/Pathname.pm \
	lib/LaTeXML/Object.pm \
	blib/lib/LaTeXML/Object.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML.model \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.model \
	lib/LaTeXML/Package/wrapfig.sty.ltxml \
	blib/lib/LaTeXML/Package/wrapfig.sty.ltxml \
	lib/LaTeXML/Package/eulervm.sty.ltxml \
	blib/lib/LaTeXML/Package/eulervm.sty.ltxml \
	lib/LaTeXML/Package/book.cls.ltxml \
	blib/lib/LaTeXML/Package/book.cls.ltxml \
	lib/LaTeXML/Package/german.sty.ltxml \
	blib/lib/LaTeXML/Package/german.sty.ltxml \
	lib/LaTeXML/Package/elsart_support.sty.ltxml \
	blib/lib/LaTeXML/Package/elsart_support.sty.ltxml \
	lib/LaTeXML/Package/cmbright.sty.ltxml \
	blib/lib/LaTeXML/Package/cmbright.sty.ltxml \
	lib/LaTeXML/Package/slides.cls.ltxml \
	blib/lib/LaTeXML/Package/slides.cls.ltxml \
	lib/LaTeXML/Package/mn2e_support.sty.ltxml \
	blib/lib/LaTeXML/Package/mn2e_support.sty.ltxml \
	lib/LaTeXML/Package/aasms.sty.ltxml \
	blib/lib/LaTeXML/Package/aasms.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rnc \
	lib/LaTeXML/resources/XSLT/LaTeXML-misc-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-misc-xhtml.xsl \
	lib/LaTeXML/Package/cp852.def.ltxml \
	blib/lib/LaTeXML/Package/cp852.def.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-structure-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-structure-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rnc \
	lib/LaTeXML/Package/xunicode.sty.ltxml \
	blib/lib/LaTeXML/Package/xunicode.sty.ltxml \
	lib/LaTeXML/Package/fleqn.sty.ltxml \
	blib/lib/LaTeXML/Package/fleqn.sty.ltxml \
	lib/LaTeXML/resources/CSS/ltx-report.css \
	blib/lib/LaTeXML/resources/CSS/ltx-report.css \
	lib/LaTeXML/Package/euler.sty.ltxml \
	blib/lib/LaTeXML/Package/euler.sty.ltxml \
	lib/LaTeXML/resources/Profiles/stex.opt \
	blib/lib/LaTeXML/resources/Profiles/stex.opt \
	lib/LaTeXML/Color.pm \
	blib/lib/LaTeXML/Color.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rnc \
	lib/LaTeXML/Package/array.sty.ltxml \
	blib/lib/LaTeXML/Package/array.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rng \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rnc \
	lib/LaTeXML/Util/ObjectDB.pm \
	blib/lib/LaTeXML/Util/ObjectDB.pm \
	lib/LaTeXML/Package/amsrefs.sty.ltxml \
	blib/lib/LaTeXML/Package/amsrefs.sty.ltxml \
	lib/LaTeXML/Package/t2b.fontmap.ltxml \
	blib/lib/LaTeXML/Package/t2b.fontmap.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rnc \
	lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rnc \
	lib/LaTeXML/Package/fontspec.sty.ltxml \
	blib/lib/LaTeXML/Package/fontspec.sty.ltxml \
	lib/LaTeXML/Package/pspicture.sty.ltxml \
	blib/lib/LaTeXML/Package/pspicture.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-block-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-block-xhtml.xsl \
	lib/LaTeXML/Package/helvet.sty.ltxml \
	blib/lib/LaTeXML/Package/helvet.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-image.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-image.rnc \
	lib/LaTeXML/Package/elsart.cls.ltxml \
	blib/lib/LaTeXML/Package/elsart.cls.ltxml \
	lib/LaTeXML/Package/revtex4.sty.ltxml \
	blib/lib/LaTeXML/Package/revtex4.sty.ltxml \
	lib/LaTeXML/Post/MathImages.pm \
	blib/lib/LaTeXML/Post/MathImages.pm \
	lib/LaTeXML/Package/comment.sty.ltxml \
	blib/lib/LaTeXML/Package/comment.sty.ltxml \
	lib/LaTeXML/Package/ams_core.cls.ltxml \
	blib/lib/LaTeXML/Package/ams_core.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rnc \
	lib/LaTeXML/resources/javascript/LaTeXML-maybeMathjax.js \
	blib/lib/LaTeXML/resources/javascript/LaTeXML-maybeMathjax.js \
	lib/LaTeXML/Package/ly1.fontmap.ltxml \
	blib/lib/LaTeXML/Package/ly1.fontmap.ltxml \
	lib/LaTeXML/Util/Alignment.pm \
	blib/lib/LaTeXML/Util/Alignment.pm \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rng \
	lib/LaTeXML/Package/floatflt.sty.ltxml \
	blib/lib/LaTeXML/Package/floatflt.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-math-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-math-xhtml.xsl \
	lib/LaTeXML/Bib.pm \
	blib/lib/LaTeXML/Bib.pm \
	lib/LaTeXML/Post/MathML.pm \
	blib/lib/LaTeXML/Post/MathML.pm \
	lib/LaTeXML/Package/cite.sty.ltxml \
	blib/lib/LaTeXML/Package/cite.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rnc \
	lib/LaTeXML/Package/revtex3_support.sty.ltxml \
	blib/lib/LaTeXML/Package/revtex3_support.sty.ltxml \
	lib/LaTeXML/Package/amsthm.sty.ltxml \
	blib/lib/LaTeXML/Package/amsthm.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rnc \
	lib/LaTeXML/Package/t1.fontmap.ltxml \
	blib/lib/LaTeXML/Package/t1.fontmap.ltxml \
	lib/LaTeXML/Font.pm \
	blib/lib/LaTeXML/Font.pm \
	lib/LaTeXML/Package/amsart.cls.ltxml \
	blib/lib/LaTeXML/Package/amsart.cls.ltxml \
	lib/LaTeXML/Package/pifont.sty.ltxml \
	blib/lib/LaTeXML/Package/pifont.sty.ltxml \
	lib/LaTeXML/resources/XSLT/LaTeXML-common.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-common.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg11-tiny.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg11-tiny.rng \
	lib/LaTeXML/Gullet.pm \
	blib/lib/LaTeXML/Gullet.pm \
	lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rnc \
	lib/LaTeXML/Error.pm \
	blib/lib/LaTeXML/Error.pm \
	lib/LaTeXML/Package/a4wide.sty.ltxml \
	blib/lib/LaTeXML/Package/a4wide.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rnc \
	lib/LaTeXML/Package/eTeX.pool.ltxml \
	blib/lib/LaTeXML/Package/eTeX.pool.ltxml \
	lib/LaTeXML/Package/ams_support.sty.ltxml \
	blib/lib/LaTeXML/Package/ams_support.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rnc \
	lib/LaTeXML/Util/Test.pm \
	blib/lib/LaTeXML/Util/Test.pm \
	lib/LaTeXML/Package/JHEP.cls.ltxml \
	blib/lib/LaTeXML/Package/JHEP.cls.ltxml \
	lib/LaTeXML/Post/SVG.pm \
	blib/lib/LaTeXML/Post/SVG.pm \
	lib/LaTeXML/Document.pm \
	blib/lib/LaTeXML/Document.pm \
	lib/LaTeXML/Package/url.sty.ltxml \
	blib/lib/LaTeXML/Package/url.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rnc \
	lib/LaTeXML/Util/Geometry.pm \
	blib/lib/LaTeXML/Util/Geometry.pm \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg11.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg11.rng \
	lib/LaTeXML/Package/makeidx.sty.ltxml \
	blib/lib/LaTeXML/Package/makeidx.sty.ltxml \
	lib/LaTeXML/resources/CSS/ltx-apj.css \
	blib/lib/LaTeXML/resources/CSS/ltx-apj.css \
	lib/LaTeXML/Package/framed.sty.ltxml \
	blib/lib/LaTeXML/Package/framed.sty.ltxml \
	lib/LaTeXML/MathParser.pm \
	blib/lib/LaTeXML/MathParser.pm \
	lib/LaTeXML/Package/epstopdf.sty.ltxml \
	blib/lib/LaTeXML/Package/epstopdf.sty.ltxml \
	lib/LaTeXML/Package/avant.sty.ltxml \
	blib/lib/LaTeXML/Package/avant.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rnc \
	lib/LaTeXML/Mouth.pm \
	blib/lib/LaTeXML/Mouth.pm \
	lib/LaTeXML/Post/CrossRef.pm \
	blib/lib/LaTeXML/Post/CrossRef.pm \
	lib/LaTeXML/Package/revtex.cls.ltxml \
	blib/lib/LaTeXML/Package/revtex.cls.ltxml \
	lib/LaTeXML/Package/graphics.sty.ltxml \
	blib/lib/LaTeXML/Package/graphics.sty.ltxml \
	lib/LaTeXML/Package/latexsym.sty.ltxml \
	blib/lib/LaTeXML/Package/latexsym.sty.ltxml \
	lib/LaTeXML/Package/elsarticle.cls.ltxml \
	blib/lib/LaTeXML/Package/elsarticle.cls.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rng \
	lib/LaTeXML/Package/JHEP3.cls.ltxml \
	blib/lib/LaTeXML/Package/JHEP3.cls.ltxml \
	lib/LaTeXML/Package/amsa.fontmap.ltxml \
	blib/lib/LaTeXML/Package/amsa.fontmap.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rng \
	lib/LaTeXML/resources/Profiles/modern.opt \
	blib/lib/LaTeXML/resources/Profiles/modern.opt \
	lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rnc \
	lib/LaTeXML/Package/beton.sty.ltxml \
	blib/lib/LaTeXML/Package/beton.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-script.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-script.rng \
	lib/LaTeXML/Package/amstex.sty.ltxml \
	blib/lib/LaTeXML/Package/amstex.sty.ltxml \
	lib/LaTeXML/Post/Collector.pm \
	blib/lib/LaTeXML/Post/Collector.pm \
	lib/LaTeXML/Package/applemac.def.ltxml \
	blib/lib/LaTeXML/Package/applemac.def.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rnc \
	lib/LaTeXML/resources/XSLT/LaTeXML-inline-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-inline-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg-text.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-text.rng \
	lib/LaTeXML/resources/XSLT/LaTeXML-html.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-html.xsl \
	lib/LaTeXML/resources/XSLT/LaTeXML-xhtml.xsl \
	blib/lib/LaTeXML/resources/XSLT/LaTeXML-xhtml.xsl \
	lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rng \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rng \
	lib/LaTeXML/resources/RelaxNG/svg/svg-script.rnc \
	blib/lib/LaTeXML/resources/RelaxNG/svg/svg-script.rnc \
	lib/LaTeXML.pm \
	blib/lib/LaTeXML.pm \
	lib/LaTeXML/Package/cancel.sty.ltxml \
	blib/lib/LaTeXML/Package/cancel.sty.ltxml \
	lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rng \
	blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rng


# --- MakeMaker platform_constants section:
MM_Unix_VERSION = 6.57_05
PERL_MALLOC_DEF = -DPERL_EXTMALLOC_DEF -Dmalloc=Perl_malloc -Dfree=Perl_mfree -Drealloc=Perl_realloc -Dcalloc=Perl_calloc


# --- MakeMaker tool_autosplit section:
# Usage: $(AUTOSPLITFILE) FileToSplit AutoDirToSplitInto
AUTOSPLITFILE = $(ABSPERLRUN)  -e 'use AutoSplit;  autosplit($$ARGV[0], $$ARGV[1], 0, 1, 1)' --



# --- MakeMaker tool_xsubpp section:


# --- MakeMaker tools_other section:
SHELL = /bin/sh
CHMOD = chmod
CP = cp
MV = mv
NOOP = $(TRUE)
NOECHO = @
RM_F = rm -f
RM_RF = rm -rf
TEST_F = test -f
TOUCH = touch
UMASK_NULL = umask 0
DEV_NULL = > /dev/null 2>&1
MKPATH = $(ABSPERLRUN) -MExtUtils::Command -e 'mkpath' --
EQUALIZE_TIMESTAMP = $(ABSPERLRUN) -MExtUtils::Command -e 'eqtime' --
FALSE = false
TRUE = true
ECHO = echo
ECHO_N = echo -n
UNINST = 0
VERBINST = 0
MOD_INSTALL = $(ABSPERLRUN) -MExtUtils::Install -e 'install([ from_to => {@ARGV}, verbose => '\''$(VERBINST)'\'', uninstall_shadows => '\''$(UNINST)'\'', dir_mode => '\''$(PERM_DIR)'\'' ]);' --
DOC_INSTALL = $(ABSPERLRUN) -MExtUtils::Command::MM -e 'perllocal_install' --
UNINSTALL = $(ABSPERLRUN) -MExtUtils::Command::MM -e 'uninstall' --
WARN_IF_OLD_PACKLIST = $(ABSPERLRUN) -MExtUtils::Command::MM -e 'warn_if_old_packlist' --
MACROSTART = 
MACROEND = 
USEMAKEFILE = -f
FIXIN = $(ABSPERLRUN) -MExtUtils::MY -e 'MY->fixin(shift)' --


# --- MakeMaker makemakerdflt section:
makemakerdflt : all
	$(NOECHO) $(NOOP)


# --- MakeMaker dist section:
TAR = tar
TARFLAGS = cvf
ZIP = zip
ZIPFLAGS = -r
COMPRESS = gzip --best
SUFFIX = .gz
SHAR = shar
PREOP = $(NOECHO) $(NOOP)
POSTOP = $(NOECHO) $(NOOP)
TO_UNIX = $(NOECHO) $(NOOP)
CI = ci -u
RCS_LABEL = rcs -Nv$(VERSION_SYM): -q
DIST_CP = best
DIST_DEFAULT = tardist
DISTNAME = LaTeXML
DISTVNAME = LaTeXML-0.7.9alpha


# --- MakeMaker macro section:
OLD_REVISION = `$(PERLRUN) -ne 'chomp;if(s/.*?REVISION\s*=\s*\"// && s/\".*//){print;}' < $(REVISION_FILE)`
REVISION = `svnversion $(REVISION_BASE)`
REVISION_BASE = /home/administrator/Desktop/aufgaben_db_eclipse/LaTeXML
INST_TEXMFDIR = $(INST_LIB)/LaTeXML/texmf
RECORD_REVISION = $(PERLRUN) -pe "s@__REVISION__@$(REVISION)@" 
REVISION_FILE = $(INST_LIBDIR)/LaTeXML/Version.pm
INSTALLTEXMFDIR = /usr/local/share/texmf/tex/latex/latexml
DESTINSTALLTEXMFDIR = $(DESTDIR)$(INSTALLTEXMFDIR)


# --- MakeMaker depend section:


# --- MakeMaker cflags section:


# --- MakeMaker const_loadlibs section:


# --- MakeMaker const_cccmd section:


# --- MakeMaker post_constants section:


# --- MakeMaker pasthru section:

PASTHRU = LIBPERL_A="$(LIBPERL_A)"\
	LINKTYPE="$(LINKTYPE)"\
	PREFIX="$(PREFIX)"


# --- MakeMaker special_targets section:
.SUFFIXES : .xs .c .C .cpp .i .s .cxx .cc $(OBJ_EXT)

.PHONY: all config static dynamic test linkext manifest blibdirs clean realclean disttest distdir



# --- MakeMaker c_o section:


# --- MakeMaker xs_c section:


# --- MakeMaker xs_o section:


# --- MakeMaker top_targets section:
all :: pure_all manifypods
	$(NOECHO) $(NOOP)


pure_all :: config pm_to_blib subdirs linkext
	$(NOECHO) $(NOOP)

subdirs :: $(MYEXTLIB)
	$(NOECHO) $(NOOP)

config :: $(FIRST_MAKEFILE) blibdirs
	$(NOECHO) $(NOOP)

help :
	perldoc ExtUtils::MakeMaker


# --- MakeMaker blibdirs section:
blibdirs : $(INST_LIBDIR)$(DFSEP).exists $(INST_ARCHLIB)$(DFSEP).exists $(INST_AUTODIR)$(DFSEP).exists $(INST_ARCHAUTODIR)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists $(INST_SCRIPT)$(DFSEP).exists $(INST_MAN1DIR)$(DFSEP).exists $(INST_MAN3DIR)$(DFSEP).exists
	$(NOECHO) $(NOOP)

# Backwards compat with 6.18 through 6.25
blibdirs.ts : blibdirs
	$(NOECHO) $(NOOP)

$(INST_LIBDIR)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_LIBDIR)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_LIBDIR)
	$(NOECHO) $(TOUCH) $(INST_LIBDIR)$(DFSEP).exists

$(INST_ARCHLIB)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_ARCHLIB)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_ARCHLIB)
	$(NOECHO) $(TOUCH) $(INST_ARCHLIB)$(DFSEP).exists

$(INST_AUTODIR)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_AUTODIR)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_AUTODIR)
	$(NOECHO) $(TOUCH) $(INST_AUTODIR)$(DFSEP).exists

$(INST_ARCHAUTODIR)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_ARCHAUTODIR)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_ARCHAUTODIR)
	$(NOECHO) $(TOUCH) $(INST_ARCHAUTODIR)$(DFSEP).exists

$(INST_BIN)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_BIN)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_BIN)
	$(NOECHO) $(TOUCH) $(INST_BIN)$(DFSEP).exists

$(INST_SCRIPT)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_SCRIPT)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_SCRIPT)
	$(NOECHO) $(TOUCH) $(INST_SCRIPT)$(DFSEP).exists

$(INST_MAN1DIR)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_MAN1DIR)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_MAN1DIR)
	$(NOECHO) $(TOUCH) $(INST_MAN1DIR)$(DFSEP).exists

$(INST_MAN3DIR)$(DFSEP).exists :: Makefile.PL
	$(NOECHO) $(MKPATH) $(INST_MAN3DIR)
	$(NOECHO) $(CHMOD) $(PERM_DIR) $(INST_MAN3DIR)
	$(NOECHO) $(TOUCH) $(INST_MAN3DIR)$(DFSEP).exists



# --- MakeMaker linkext section:

linkext :: $(LINKTYPE)
	$(NOECHO) $(NOOP)


# --- MakeMaker dlsyms section:


# --- MakeMaker dynamic section:

dynamic :: $(FIRST_MAKEFILE) $(INST_DYNAMIC) $(INST_BOOT)
	$(NOECHO) $(NOOP)


# --- MakeMaker dynamic_bs section:

BOOTSTRAP =


# --- MakeMaker dynamic_lib section:


# --- MakeMaker static section:

## $(INST_PM) has been moved to the all: target.
## It remains here for awhile to allow for old usage: "make static"
static :: $(FIRST_MAKEFILE) $(INST_STATIC)
	$(NOECHO) $(NOOP)


# --- MakeMaker static_lib section:


# --- MakeMaker manifypods section:

POD2MAN_EXE = $(PERLRUN) "-MExtUtils::Command::MM" -e pod2man "--"
POD2MAN = $(POD2MAN_EXE)


manifypods : pure_all  \
	bin/latexmlpost \
	bin/latexmls \
	bin/latexmlmath \
	bin/latexmlc \
	bin/latexmlfind \
	bin/latexml \
	lib/LaTeXML/Object.pm \
	lib/LaTeXML/Document.pm \
	lib/LaTeXML/Array.pm \
	lib/LaTeXML/Util/KeyVal.pm \
	lib/LaTeXML/Font.pm \
	lib/LaTeXML/Model.pm \
	lib/LaTeXML/Util/Config.pm \
	lib/LaTeXML/Rewrite.pm \
	lib/LaTeXML/MathParser.pm \
	lib/LaTeXML/Definition.pm \
	lib/LaTeXML/Parameters.pm \
	lib/LaTeXML/Mouth.pm \
	lib/LaTeXML/State.pm \
	lib/LaTeXML/Stomach.pm \
	lib/LaTeXML/Gullet.pm \
	lib/LaTeXML/Token.pm \
	lib/LaTeXML/Error.pm \
	lib/LaTeXML/Number.pm \
	lib/LaTeXML/Package.pm \
	lib/LaTeXML/Global.pm \
	lib/LaTeXML/Bib.pm \
	lib/LaTeXML/Util/Pathname.pm \
	lib/LaTeXML/Post/MathML.pm \
	lib/LaTeXML.pm \
	lib/LaTeXML/Converter.pm \
	lib/LaTeXML/Box.pm \
	lib/LaTeXML/Post.pm
	$(NOECHO) $(POD2MAN) --section=$(MAN1EXT) --perm_rw=$(PERM_RW) \
	  bin/latexmlpost $(INST_MAN1DIR)/latexmlpost.$(MAN1EXT) \
	  bin/latexmls $(INST_MAN1DIR)/latexmls.$(MAN1EXT) \
	  bin/latexmlmath $(INST_MAN1DIR)/latexmlmath.$(MAN1EXT) \
	  bin/latexmlc $(INST_MAN1DIR)/latexmlc.$(MAN1EXT) \
	  bin/latexmlfind $(INST_MAN1DIR)/latexmlfind.$(MAN1EXT) \
	  bin/latexml $(INST_MAN1DIR)/latexml.$(MAN1EXT) 
	$(NOECHO) $(POD2MAN) --section=$(MAN3EXT) --perm_rw=$(PERM_RW) \
	  lib/LaTeXML/Object.pm $(INST_MAN3DIR)/LaTeXML::Object.$(MAN3EXT) \
	  lib/LaTeXML/Document.pm $(INST_MAN3DIR)/LaTeXML::Document.$(MAN3EXT) \
	  lib/LaTeXML/Array.pm $(INST_MAN3DIR)/LaTeXML::Array.$(MAN3EXT) \
	  lib/LaTeXML/Util/KeyVal.pm $(INST_MAN3DIR)/LaTeXML::Util::KeyVal.$(MAN3EXT) \
	  lib/LaTeXML/Font.pm $(INST_MAN3DIR)/LaTeXML::Font.$(MAN3EXT) \
	  lib/LaTeXML/Model.pm $(INST_MAN3DIR)/LaTeXML::Model.$(MAN3EXT) \
	  lib/LaTeXML/Util/Config.pm $(INST_MAN3DIR)/LaTeXML::Util::Config.$(MAN3EXT) \
	  lib/LaTeXML/Rewrite.pm $(INST_MAN3DIR)/LaTeXML::Rewrite.$(MAN3EXT) \
	  lib/LaTeXML/MathParser.pm $(INST_MAN3DIR)/LaTeXML::MathParser.$(MAN3EXT) \
	  lib/LaTeXML/Definition.pm $(INST_MAN3DIR)/LaTeXML::Definition.$(MAN3EXT) \
	  lib/LaTeXML/Parameters.pm $(INST_MAN3DIR)/LaTeXML::Parameters.$(MAN3EXT) \
	  lib/LaTeXML/Mouth.pm $(INST_MAN3DIR)/LaTeXML::Mouth.$(MAN3EXT) \
	  lib/LaTeXML/State.pm $(INST_MAN3DIR)/LaTeXML::State.$(MAN3EXT) \
	  lib/LaTeXML/Stomach.pm $(INST_MAN3DIR)/LaTeXML::Stomach.$(MAN3EXT) \
	  lib/LaTeXML/Gullet.pm $(INST_MAN3DIR)/LaTeXML::Gullet.$(MAN3EXT) \
	  lib/LaTeXML/Token.pm $(INST_MAN3DIR)/LaTeXML::Token.$(MAN3EXT) \
	  lib/LaTeXML/Error.pm $(INST_MAN3DIR)/LaTeXML::Error.$(MAN3EXT) \
	  lib/LaTeXML/Number.pm $(INST_MAN3DIR)/LaTeXML::Number.$(MAN3EXT) \
	  lib/LaTeXML/Package.pm $(INST_MAN3DIR)/LaTeXML::Package.$(MAN3EXT) \
	  lib/LaTeXML/Global.pm $(INST_MAN3DIR)/LaTeXML::Global.$(MAN3EXT) \
	  lib/LaTeXML/Bib.pm $(INST_MAN3DIR)/LaTeXML::Bib.$(MAN3EXT) \
	  lib/LaTeXML/Util/Pathname.pm $(INST_MAN3DIR)/LaTeXML::Util::Pathname.$(MAN3EXT) \
	  lib/LaTeXML/Post/MathML.pm $(INST_MAN3DIR)/LaTeXML::Post::MathML.$(MAN3EXT) \
	  lib/LaTeXML.pm $(INST_MAN3DIR)/LaTeXML.$(MAN3EXT) \
	  lib/LaTeXML/Converter.pm $(INST_MAN3DIR)/LaTeXML::Converter.$(MAN3EXT) \
	  lib/LaTeXML/Box.pm $(INST_MAN3DIR)/LaTeXML::Box.$(MAN3EXT) \
	  lib/LaTeXML/Post.pm $(INST_MAN3DIR)/LaTeXML::Post.$(MAN3EXT) 




# --- MakeMaker processPL section:


# --- MakeMaker installbin section:

EXE_FILES = bin/latexml bin/latexmlpost bin/latexmlfind bin/latexmlmath bin/latexmlc bin/latexmls

pure_all :: $(INST_SCRIPT)/latexmlpost $(INST_SCRIPT)/latexmls $(INST_SCRIPT)/latexmlmath $(INST_SCRIPT)/latexmlc $(INST_SCRIPT)/latexmlfind $(INST_SCRIPT)/latexml
	$(NOECHO) $(NOOP)

realclean ::
	$(RM_F) \
	  $(INST_SCRIPT)/latexmlpost $(INST_SCRIPT)/latexmls \
	  $(INST_SCRIPT)/latexmlmath $(INST_SCRIPT)/latexmlc \
	  $(INST_SCRIPT)/latexmlfind $(INST_SCRIPT)/latexml 

$(INST_SCRIPT)/latexmlpost : bin/latexmlpost $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexmlpost
	$(CP) bin/latexmlpost $(INST_SCRIPT)/latexmlpost
	$(FIXIN) $(INST_SCRIPT)/latexmlpost
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexmlpost

$(INST_SCRIPT)/latexmls : bin/latexmls $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexmls
	$(CP) bin/latexmls $(INST_SCRIPT)/latexmls
	$(FIXIN) $(INST_SCRIPT)/latexmls
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexmls

$(INST_SCRIPT)/latexmlmath : bin/latexmlmath $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexmlmath
	$(CP) bin/latexmlmath $(INST_SCRIPT)/latexmlmath
	$(FIXIN) $(INST_SCRIPT)/latexmlmath
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexmlmath

$(INST_SCRIPT)/latexmlc : bin/latexmlc $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexmlc
	$(CP) bin/latexmlc $(INST_SCRIPT)/latexmlc
	$(FIXIN) $(INST_SCRIPT)/latexmlc
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexmlc

$(INST_SCRIPT)/latexmlfind : bin/latexmlfind $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexmlfind
	$(CP) bin/latexmlfind $(INST_SCRIPT)/latexmlfind
	$(FIXIN) $(INST_SCRIPT)/latexmlfind
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexmlfind

$(INST_SCRIPT)/latexml : bin/latexml $(FIRST_MAKEFILE) $(INST_SCRIPT)$(DFSEP).exists $(INST_BIN)$(DFSEP).exists
	$(NOECHO) $(RM_F) $(INST_SCRIPT)/latexml
	$(CP) bin/latexml $(INST_SCRIPT)/latexml
	$(FIXIN) $(INST_SCRIPT)/latexml
	-$(NOECHO) $(CHMOD) $(PERM_RWX) $(INST_SCRIPT)/latexml



# --- MakeMaker subdirs section:

# none

# --- MakeMaker clean_subdirs section:
clean_subdirs :
	$(NOECHO) $(NOOP)


# --- MakeMaker clean section:

# Delete temporary files but do not touch installed files. We don't delete
# the Makefile here so a later make realclean still has a makefile to use.

clean :: clean_subdirs
	- $(RM_F) \
	  *$(LIB_EXT) core \
	  core.[0-9] $(INST_ARCHAUTODIR)/extralibs.all \
	  core.[0-9][0-9] $(BASEEXT).bso \
	  pm_to_blib.ts core.[0-9][0-9][0-9][0-9] \
	  MYMETA.yml $(BASEEXT).x \
	  $(BOOTSTRAP) perl$(EXE_EXT) \
	  tmon.out *$(OBJ_EXT) \
	  pm_to_blib $(INST_ARCHAUTODIR)/extralibs.ld \
	  blibdirs.ts core.[0-9][0-9][0-9][0-9][0-9] \
	  *perl.core core.*perl.*.? \
	  $(MAKE_APERL_FILE) perl \
	  $(BASEEXT).def core.[0-9][0-9][0-9] \
	  mon.out lib$(BASEEXT).def \
	  perlmain.c perl.exe \
	  so_locations $(BASEEXT).exp 
	- $(RM_RF) \
	  blib 
	- $(MV) $(FIRST_MAKEFILE) $(MAKEFILE_OLD) $(DEV_NULL)


# --- MakeMaker realclean_subdirs section:
realclean_subdirs :
	$(NOECHO) $(NOOP)


# --- MakeMaker realclean section:
# Delete temporary files (via clean) and also delete dist files
realclean purge ::  clean realclean_subdirs
	- $(RM_F) \
	  $(MAKEFILE_OLD) $(FIRST_MAKEFILE) 
	- $(RM_RF) \
	  $(DISTVNAME) 


# --- MakeMaker metafile section:
metafile : create_distdir
	$(NOECHO) $(ECHO) Generating META.yml
	$(NOECHO) $(ECHO) '--- #YAML:1.0' > META_new.yml
	$(NOECHO) $(ECHO) 'name:               LaTeXML' >> META_new.yml
	$(NOECHO) $(ECHO) 'version:            0.7.9alpha' >> META_new.yml
	$(NOECHO) $(ECHO) 'abstract:           transforms TeX and LaTeX into XML' >> META_new.yml
	$(NOECHO) $(ECHO) 'author:' >> META_new.yml
	$(NOECHO) $(ECHO) '    - Bruce Miller <bruce.miller@nist.gov>' >> META_new.yml
	$(NOECHO) $(ECHO) 'license:            unknown' >> META_new.yml
	$(NOECHO) $(ECHO) 'distribution_type:  module' >> META_new.yml
	$(NOECHO) $(ECHO) 'configure_requires:' >> META_new.yml
	$(NOECHO) $(ECHO) '    ExtUtils::MakeMaker:  0' >> META_new.yml
	$(NOECHO) $(ECHO) 'build_requires:' >> META_new.yml
	$(NOECHO) $(ECHO) '    ExtUtils::MakeMaker:  0' >> META_new.yml
	$(NOECHO) $(ECHO) 'requires:' >> META_new.yml
	$(NOECHO) $(ECHO) '    Archive::Zip:       0' >> META_new.yml
	$(NOECHO) $(ECHO) '    DB_File:            0' >> META_new.yml
	$(NOECHO) $(ECHO) '    File::Which:        0' >> META_new.yml
	$(NOECHO) $(ECHO) '    Image::Magick:      0' >> META_new.yml
	$(NOECHO) $(ECHO) '    IO::Socket:         0' >> META_new.yml
	$(NOECHO) $(ECHO) '    IO::Socket::SSL:    0' >> META_new.yml
	$(NOECHO) $(ECHO) '    IO::String:         0' >> META_new.yml
	$(NOECHO) $(ECHO) '    JSON::XS:           0' >> META_new.yml
	$(NOECHO) $(ECHO) '    LWP:                0' >> META_new.yml
	$(NOECHO) $(ECHO) '    LWP::Simple:        0' >> META_new.yml
	$(NOECHO) $(ECHO) '    Parse::RecDescent:  0' >> META_new.yml
	$(NOECHO) $(ECHO) '    Test::Simple:       0' >> META_new.yml
	$(NOECHO) $(ECHO) '    URI:                0' >> META_new.yml
	$(NOECHO) $(ECHO) '    XML::LibXML:        1.61' >> META_new.yml
	$(NOECHO) $(ECHO) '    XML::LibXSLT:       1.58' >> META_new.yml
	$(NOECHO) $(ECHO) 'no_index:' >> META_new.yml
	$(NOECHO) $(ECHO) '    directory:' >> META_new.yml
	$(NOECHO) $(ECHO) '        - t' >> META_new.yml
	$(NOECHO) $(ECHO) '        - inc' >> META_new.yml
	$(NOECHO) $(ECHO) 'generated_by:       ExtUtils::MakeMaker version 6.57_05' >> META_new.yml
	$(NOECHO) $(ECHO) 'meta-spec:' >> META_new.yml
	$(NOECHO) $(ECHO) '    url:      http://module-build.sourceforge.net/META-spec-v1.4.html' >> META_new.yml
	$(NOECHO) $(ECHO) '    version:  1.4' >> META_new.yml
	-$(NOECHO) $(MV) META_new.yml $(DISTVNAME)/META.yml


# --- MakeMaker signature section:
signature :
	cpansign -s


# --- MakeMaker dist_basics section:
distclean :: realclean distcheck
	$(NOECHO) $(NOOP)

distcheck :
	$(PERLRUN) "-MExtUtils::Manifest=fullcheck" -e fullcheck

skipcheck :
	$(PERLRUN) "-MExtUtils::Manifest=skipcheck" -e skipcheck

manifest :
	$(PERLRUN) "-MExtUtils::Manifest=mkmanifest" -e mkmanifest

veryclean : realclean
	$(RM_F) *~ */*~ *.orig */*.orig *.bak */*.bak *.old */*.old 



# --- MakeMaker dist_core section:

dist : $(DIST_DEFAULT) $(FIRST_MAKEFILE)
	$(NOECHO) $(ABSPERLRUN) -l -e 'print '\''Warning: Makefile possibly out of date with $(VERSION_FROM)'\''' \
	  -e '    if -e '\''$(VERSION_FROM)'\'' and -M '\''$(VERSION_FROM)'\'' < -M '\''$(FIRST_MAKEFILE)'\'';' --

tardist : $(DISTVNAME).tar$(SUFFIX)
	$(NOECHO) $(NOOP)

uutardist : $(DISTVNAME).tar$(SUFFIX)
	uuencode $(DISTVNAME).tar$(SUFFIX) $(DISTVNAME).tar$(SUFFIX) > $(DISTVNAME).tar$(SUFFIX)_uu

$(DISTVNAME).tar$(SUFFIX) : distdir
	$(PREOP)
	$(TO_UNIX)
	$(TAR) $(TARFLAGS) $(DISTVNAME).tar $(DISTVNAME)
	$(RM_RF) $(DISTVNAME)
	$(COMPRESS) $(DISTVNAME).tar
	$(POSTOP)

zipdist : $(DISTVNAME).zip
	$(NOECHO) $(NOOP)

$(DISTVNAME).zip : distdir
	$(PREOP)
	$(ZIP) $(ZIPFLAGS) $(DISTVNAME).zip $(DISTVNAME)
	$(RM_RF) $(DISTVNAME)
	$(POSTOP)

shdist : distdir
	$(PREOP)
	$(SHAR) $(DISTVNAME) > $(DISTVNAME).shar
	$(RM_RF) $(DISTVNAME)
	$(POSTOP)


# --- MakeMaker distdir section:
create_distdir :
	$(RM_RF) $(DISTVNAME)
	$(PERLRUN) "-MExtUtils::Manifest=manicopy,maniread" \
		-e "manicopy(maniread(),'$(DISTVNAME)', '$(DIST_CP)');"

distdir : create_distdir distmeta 
	$(NOECHO) $(NOOP)



# --- MakeMaker dist_test section:
disttest : distdir
	cd $(DISTVNAME) && $(ABSPERLRUN) Makefile.PL 
	cd $(DISTVNAME) && $(MAKE) $(PASTHRU)
	cd $(DISTVNAME) && $(MAKE) test $(PASTHRU)



# --- MakeMaker dist_ci section:

ci :
	$(PERLRUN) "-MExtUtils::Manifest=maniread" \
	  -e "@all = keys %{ maniread() };" \
	  -e "print(qq{Executing $(CI) @all\n}); system(qq{$(CI) @all});" \
	  -e "print(qq{Executing $(RCS_LABEL) ...\n}); system(qq{$(RCS_LABEL) @all});"


# --- MakeMaker distmeta section:
distmeta : create_distdir metafile
	$(NOECHO) cd $(DISTVNAME) && $(ABSPERLRUN) -MExtUtils::Manifest=maniadd -e 'eval { maniadd({q{META.yml} => q{Module meta-data (added by MakeMaker)}}) } ' \
	  -e '    or print "Could not add META.yml to MANIFEST: $${'\''@'\''}\n"' --



# --- MakeMaker distsignature section:
distsignature : create_distdir
	$(NOECHO) cd $(DISTVNAME) && $(ABSPERLRUN) -MExtUtils::Manifest=maniadd -e 'eval { maniadd({q{SIGNATURE} => q{Public-key signature (added by MakeMaker)}}) } ' \
	  -e '    or print "Could not add SIGNATURE to MANIFEST: $${'\''@'\''}\n"' --
	$(NOECHO) cd $(DISTVNAME) && $(TOUCH) SIGNATURE
	cd $(DISTVNAME) && cpansign -s



# --- MakeMaker install section:

install :: pure_install doc_install
	$(NOECHO) $(NOOP)

install_perl :: pure_perl_install doc_perl_install
	$(NOECHO) $(NOOP)

install_site :: pure_site_install doc_site_install
	$(NOECHO) $(NOOP)

install_vendor :: pure_vendor_install doc_vendor_install
	$(NOECHO) $(NOOP)

pure_install :: pure_$(INSTALLDIRS)_install
	$(NOECHO) $(NOOP)

doc_install :: doc_$(INSTALLDIRS)_install
	$(NOECHO) $(NOOP)

pure__install : pure_site_install
	$(NOECHO) $(ECHO) INSTALLDIRS not defined, defaulting to INSTALLDIRS=site

doc__install : doc_site_install
	$(NOECHO) $(ECHO) INSTALLDIRS not defined, defaulting to INSTALLDIRS=site

pure_perl_install :: all
	$(NOECHO) umask 022; $(MOD_INSTALL) \
		$(INST_LIB) $(DESTINSTALLPRIVLIB) \
		$(INST_ARCHLIB) $(DESTINSTALLARCHLIB) \
		$(INST_BIN) $(DESTINSTALLBIN) \
		$(INST_SCRIPT) $(DESTINSTALLSCRIPT) \
		$(INST_MAN1DIR) $(DESTINSTALLMAN1DIR) \
		$(INST_MAN3DIR) $(DESTINSTALLMAN3DIR)
	$(NOECHO) $(WARN_IF_OLD_PACKLIST) \
		$(SITEARCHEXP)/auto/$(FULLEXT)


pure_site_install :: all
	$(NOECHO) umask 02; $(MOD_INSTALL) \
		read $(SITEARCHEXP)/auto/$(FULLEXT)/.packlist \
		write $(DESTINSTALLSITEARCH)/auto/$(FULLEXT)/.packlist \
		$(INST_LIB) $(DESTINSTALLSITELIB) \
		$(INST_ARCHLIB) $(DESTINSTALLSITEARCH) \
		$(INST_BIN) $(DESTINSTALLSITEBIN) \
		$(INST_SCRIPT) $(DESTINSTALLSITESCRIPT) \
		$(INST_MAN1DIR) $(DESTINSTALLSITEMAN1DIR) \
		$(INST_MAN3DIR) $(DESTINSTALLSITEMAN3DIR)
	$(NOECHO) $(WARN_IF_OLD_PACKLIST) \
		$(PERL_ARCHLIB)/auto/$(FULLEXT)

pure_vendor_install :: all
	$(NOECHO) umask 022; $(MOD_INSTALL) \
		$(INST_LIB) $(DESTINSTALLVENDORLIB) \
		$(INST_ARCHLIB) $(DESTINSTALLVENDORARCH) \
		$(INST_BIN) $(DESTINSTALLVENDORBIN) \
		$(INST_SCRIPT) $(DESTINSTALLVENDORSCRIPT) \
		$(INST_MAN1DIR) $(DESTINSTALLVENDORMAN1DIR) \
		$(INST_MAN3DIR) $(DESTINSTALLVENDORMAN3DIR)

doc_perl_install :: all

doc_site_install :: all
	$(NOECHO) $(ECHO) Appending installation info to $(DESTINSTALLSITEARCH)/perllocal.pod
	-$(NOECHO) umask 02; $(MKPATH) $(DESTINSTALLSITEARCH)
	-$(NOECHO) umask 02; $(DOC_INSTALL) \
		"Module" "$(NAME)" \
		"installed into" "$(INSTALLSITELIB)" \
		LINKTYPE "$(LINKTYPE)" \
		VERSION "$(VERSION)" \
		EXE_FILES "$(EXE_FILES)" \
		>> $(DESTINSTALLSITEARCH)/perllocal.pod

doc_vendor_install :: all


uninstall :: uninstall_from_$(INSTALLDIRS)dirs
	$(NOECHO) $(NOOP)

uninstall_from_perldirs ::

uninstall_from_sitedirs ::
	$(NOECHO) $(UNINSTALL) $(SITEARCHEXP)/auto/$(FULLEXT)/.packlist

uninstall_from_vendordirs ::



# --- MakeMaker force section:
# Phony target to force checking subdirectories.
FORCE :
	$(NOECHO) $(NOOP)


# --- MakeMaker perldepend section:


# --- MakeMaker makefile section:
# We take a very conservative approach here, but it's worth it.
# We move Makefile to Makefile.old here to avoid gnu make looping.
$(FIRST_MAKEFILE) : Makefile.PL $(CONFIGDEP)
	$(NOECHO) $(ECHO) "Makefile out-of-date with respect to $?"
	$(NOECHO) $(ECHO) "Cleaning current config before rebuilding Makefile..."
	-$(NOECHO) $(RM_F) $(MAKEFILE_OLD)
	-$(NOECHO) $(MV)   $(FIRST_MAKEFILE) $(MAKEFILE_OLD)
	- $(MAKE) $(USEMAKEFILE) $(MAKEFILE_OLD) clean $(DEV_NULL)
	$(PERLRUN) Makefile.PL 
	$(NOECHO) $(ECHO) "==> Your Makefile has been rebuilt. <=="
	$(NOECHO) $(ECHO) "==> Please rerun the $(MAKE) command.  <=="
	$(FALSE)



# --- MakeMaker staticmake section:

# --- MakeMaker makeaperl section ---
MAP_TARGET    = perl
FULLPERL      = /usr/bin/perl

$(MAP_TARGET) :: static $(MAKE_APERL_FILE)
	$(MAKE) $(USEMAKEFILE) $(MAKE_APERL_FILE) $@

$(MAKE_APERL_FILE) : $(FIRST_MAKEFILE) pm_to_blib
	$(NOECHO) $(ECHO) Writing \"$(MAKE_APERL_FILE)\" for this $(MAP_TARGET)
	$(NOECHO) $(PERLRUNINST) \
		Makefile.PL DIR= \
		MAKEFILE=$(MAKE_APERL_FILE) LINKTYPE=static \
		MAKEAPERL=1 NORECURS=1 CCCDLFLAGS=


# --- MakeMaker test section:

TEST_VERBOSE=0
TEST_TYPE=test_$(LINKTYPE)
TEST_FILE = test.pl
TEST_FILES = t/*.t
TESTDB_SW = -d

testdb :: testdb_$(LINKTYPE)

test :: $(TEST_TYPE) subdirs-test

subdirs-test ::
	$(NOECHO) $(NOOP)


test_dynamic :: pure_all
	PERL_DL_NONLAZY=1 $(FULLPERLRUN) "-MExtUtils::Command::MM" "-e" "test_harness($(TEST_VERBOSE), '$(INST_LIB)', '$(INST_ARCHLIB)')" $(TEST_FILES)

testdb_dynamic :: pure_all
	PERL_DL_NONLAZY=1 $(FULLPERLRUN) $(TESTDB_SW) "-I$(INST_LIB)" "-I$(INST_ARCHLIB)" $(TEST_FILE)

test_ : test_dynamic

test_static :: test_dynamic
testdb_static :: testdb_dynamic


# --- MakeMaker ppd section:
# Creates a PPD (Perl Package Description) for a binary distribution.
ppd :
	$(NOECHO) $(ECHO) '<SOFTPKG NAME="$(DISTNAME)" VERSION="0.7.9alpha">' > $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '    <ABSTRACT>transforms TeX and LaTeX into XML</ABSTRACT>' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '    <AUTHOR>Bruce Miller &lt;bruce.miller@nist.gov&gt;</AUTHOR>' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '    <IMPLEMENTATION>' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="Archive::Zip" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="DB_File::" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="File::Which" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="IO::Socket" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="IO::Socket::SSL" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="IO::String" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="Image::Magick" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="JSON::XS" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="LWP::" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="LWP::Simple" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="Parse::RecDescent" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="Test::Simple" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="URI::" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="XML::LibXML" VERSION="1.61" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <REQUIRE NAME="XML::LibXSLT" VERSION="1.58" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <ARCHITECTURE NAME="x86_64-linux-gnu-thread-multi-5.14" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '        <CODEBASE HREF="" />' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '    </IMPLEMENTATION>' >> $(DISTNAME).ppd
	$(NOECHO) $(ECHO) '</SOFTPKG>' >> $(DISTNAME).ppd


# --- MakeMaker pm_to_blib section:

pm_to_blib : $(FIRST_MAKEFILE) $(TO_INST_PM)
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/pst-grad.sty.ltxml blib/lib/LaTeXML/Package/pst-grad.sty.ltxml \
	  lib/LaTeXML/Package/xspace.sty.ltxml blib/lib/LaTeXML/Package/xspace.sty.ltxml \
	  lib/LaTeXML/Package/natbib.sty.ltxml blib/lib/LaTeXML/Package/natbib.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/stex-module.opt blib/lib/LaTeXML/resources/Profiles/stex-module.opt \
	  lib/LaTeXML/Package/multido.sty.ltxml blib/lib/LaTeXML/Package/multido.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rng \
	  lib/LaTeXML/Model/DTD.pm blib/lib/LaTeXML/Model/DTD.pm \
	  lib/LaTeXML/Package/alltt.sty.ltxml blib/lib/LaTeXML/Package/alltt.sty.ltxml \
	  lib/LaTeXML/Util/Config.pm blib/lib/LaTeXML/Util/Config.pm \
	  lib/LaTeXML/Package/charter.sty.ltxml blib/lib/LaTeXML/Package/charter.sty.ltxml \
	  lib/LaTeXML/Package/afterpage.sty.ltxml blib/lib/LaTeXML/Package/afterpage.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rnc \
	  lib/LaTeXML/Definition.pm blib/lib/LaTeXML/Definition.pm \
	  lib/LaTeXML/Package/aas_support.sty.ltxml blib/lib/LaTeXML/Package/aas_support.sty.ltxml \
	  lib/LaTeXML/State.pm blib/lib/LaTeXML/State.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-image.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-image.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rnc \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rng \
	  lib/LaTeXML/resources/CSS/LaTeXML-marginpar.css blib/lib/LaTeXML/resources/CSS/LaTeXML-marginpar.css \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rng \
	  lib/LaTeXML/Post/MakeBibliography.pm blib/lib/LaTeXML/Post/MakeBibliography.pm \
	  lib/LaTeXML/Package/amsmath.sty.ltxml blib/lib/LaTeXML/Package/amsmath.sty.ltxml \
	  lib/LaTeXML/Package/upgreek.sty.ltxml blib/lib/LaTeXML/Package/upgreek.sty.ltxml \
	  lib/LaTeXML/Package/lscape.sty.ltxml blib/lib/LaTeXML/Package/lscape.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/stex-migration.opt blib/lib/LaTeXML/resources/Profiles/stex-migration.opt \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rnc \
	  lib/LaTeXML/Post/OpenMath.pm blib/lib/LaTeXML/Post/OpenMath.pm \
	  lib/LaTeXML/Package/amscd.sty.ltxml blib/lib/LaTeXML/Package/amscd.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-meta-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-meta-xhtml.xsl \
	  lib/LaTeXML/Package/mathrsfs.sty.ltxml blib/lib/LaTeXML/Package/mathrsfs.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/dcolumn.sty.ltxml blib/lib/LaTeXML/Package/dcolumn.sty.ltxml \
	  lib/LaTeXML/Package/TeX.pool.ltxml blib/lib/LaTeXML/Package/TeX.pool.ltxml \
	  lib/LaTeXML/Package/iopams.sty.ltxml blib/lib/LaTeXML/Package/iopams.sty.ltxml \
	  lib/LaTeXML/Package/psfig.sty.ltxml blib/lib/LaTeXML/Package/psfig.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/math.opt blib/lib/LaTeXML/resources/Profiles/math.opt \
	  lib/LaTeXML/Rewrite.pm blib/lib/LaTeXML/Rewrite.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg11.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg11.rnc \
	  lib/LaTeXML/Package/eurosym.sty.ltxml blib/lib/LaTeXML/Package/eurosym.sty.ltxml \
	  lib/LaTeXML/Util/MathMLLinebreaker.pm blib/lib/LaTeXML/Util/MathMLLinebreaker.pm \
	  lib/LaTeXML/Package/float.sty.ltxml blib/lib/LaTeXML/Package/float.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-mask.rnc \
	  lib/LaTeXML/Package/amsb.fontmap.ltxml blib/lib/LaTeXML/Package/amsb.fontmap.ltxml \
	  lib/LaTeXML/Package/footmisc.sty.ltxml blib/lib/LaTeXML/Package/footmisc.sty.ltxml \
	  lib/LaTeXML/resources/CSS/LaTeXML.css blib/lib/LaTeXML/resources/CSS/LaTeXML.css \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-view.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-view.rnc \
	  lib/LaTeXML/Package/tocbibind.sty.ltxml blib/lib/LaTeXML/Package/tocbibind.sty.ltxml \
	  lib/LaTeXML/Package/fullpage.sty.ltxml blib/lib/LaTeXML/Package/fullpage.sty.ltxml \
	  lib/LaTeXML/Package/fixltx2e.sty.ltxml blib/lib/LaTeXML/Package/fixltx2e.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rnc \
	  lib/LaTeXML/Util/Transform.pm blib/lib/LaTeXML/Util/Transform.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rng \
	  lib/LaTeXML/Package/amsxtra.sty.ltxml blib/lib/LaTeXML/Package/amsxtra.sty.ltxml \
	  lib/LaTeXML/Package/hyperxmp.sty.ltxml blib/lib/LaTeXML/Package/hyperxmp.sty.ltxml \
	  lib/LaTeXML/Package/lgr.fontmap.ltxml blib/lib/LaTeXML/Package/lgr.fontmap.ltxml \
	  lib/LaTeXML/Package/lxRDFa.sty.ltxml blib/lib/LaTeXML/Package/lxRDFa.sty.ltxml \
	  lib/LaTeXML/Package/amsfonts.sty.ltxml blib/lib/LaTeXML/Package/amsfonts.sty.ltxml \
	  lib/LaTeXML/Box.pm blib/lib/LaTeXML/Box.pm \
	  lib/LaTeXML/Package/setspace.sty.ltxml blib/lib/LaTeXML/Package/setspace.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/revtex4_support.sty.ltxml blib/lib/LaTeXML/Package/revtex4_support.sty.ltxml \
	  lib/LaTeXML/Package/elsart.sty.ltxml blib/lib/LaTeXML/Package/elsart.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/standard.opt blib/lib/LaTeXML/resources/Profiles/standard.opt \
	  lib/LaTeXML/resources/XSLT/LaTeXML-html5.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-html5.xsl \
	  lib/LaTeXML/Package/enumerate.sty.ltxml blib/lib/LaTeXML/Package/enumerate.sty.ltxml \
	  lib/LaTeXML/Package/gen-j-l.cls.ltxml blib/lib/LaTeXML/Package/gen-j-l.cls.ltxml \
	  lib/LaTeXML/Package/yfonts.sty.ltxml blib/lib/LaTeXML/Package/yfonts.sty.ltxml \
	  lib/LaTeXML/Package/svmult.cls.ltxml blib/lib/LaTeXML/Package/svmult.cls.ltxml \
	  lib/LaTeXML/Package/txfonts.sty.ltxml blib/lib/LaTeXML/Package/txfonts.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rnc \
	  lib/LaTeXML/Package/paralist.sty.ltxml blib/lib/LaTeXML/Package/paralist.sty.ltxml \
	  lib/LaTeXML/Package/t2a.fontmap.ltxml blib/lib/LaTeXML/Package/t2a.fontmap.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rnc \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphevents-attrib.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-font.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-font.rnc \
	  lib/LaTeXML/Package.pm blib/lib/LaTeXML/Package.pm \
	  lib/LaTeXML/Package/listings.sty.ltxml blib/lib/LaTeXML/Package/listings.sty.ltxml \
	  lib/LaTeXML/Global.pm blib/lib/LaTeXML/Global.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rng \
	  lib/LaTeXML/Package/longtable.sty.ltxml blib/lib/LaTeXML/Package/longtable.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rng \
	  lib/LaTeXML/Package/inputenc.sty.ltxml blib/lib/LaTeXML/Package/inputenc.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-clip.rng \
	  lib/LaTeXML/Package/bbold.sty.ltxml blib/lib/LaTeXML/Package/bbold.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-opacity-attrib.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-style.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-style.rng \
	  lib/LaTeXML/Package/here.sty.ltxml blib/lib/LaTeXML/Package/here.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/indentfirst.sty.ltxml blib/lib/LaTeXML/Package/indentfirst.sty.ltxml \
	  lib/LaTeXML/resources/CSS/LaTeXML-navbar-left.css blib/lib/LaTeXML/resources/CSS/LaTeXML-navbar-left.css \
	  lib/LaTeXML/Package/ntheorem.sty.ltxml blib/lib/LaTeXML/Package/ntheorem.sty.ltxml \
	  lib/LaTeXML/Package/xcolor.sty.ltxml blib/lib/LaTeXML/Package/xcolor.sty.ltxml \
	  lib/LaTeXML/Package/textcomp.sty.ltxml blib/lib/LaTeXML/Package/textcomp.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rnc \
	  lib/LaTeXML/texmf/latexml.sty blib/lib/LaTeXML/texmf/latexml.sty \
	  lib/LaTeXML/resources/Profiles/fragment-xhtml.opt blib/lib/LaTeXML/resources/Profiles/fragment-xhtml.opt \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rng \
	  lib/LaTeXML/Package/amsbook.cls.ltxml blib/lib/LaTeXML/Package/amsbook.cls.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-profile.rnc \
	  lib/LaTeXML/Package/t2c.fontmap.ltxml blib/lib/LaTeXML/Package/t2c.fontmap.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-picture-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-picture-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-structure.rng \
	  lib/LaTeXML/resources/Profiles/fragment-html.opt blib/lib/LaTeXML/resources/Profiles/fragment-html.opt \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extensibility.rnc \
	  lib/LaTeXML/Array.pm blib/lib/LaTeXML/Array.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-qname.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-qname.rng \
	  lib/LaTeXML/Package/keyval.sty.ltxml blib/lib/LaTeXML/Package/keyval.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-bib-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-bib-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rng \
	  lib/LaTeXML/Post/XMath.pm blib/lib/LaTeXML/Post/XMath.pm \
	  lib/LaTeXML/Package/utf8.def.ltxml blib/lib/LaTeXML/Package/utf8.def.ltxml \
	  lib/LaTeXML/Package/texvc.sty.ltxml blib/lib/LaTeXML/Package/texvc.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rnc \
	  lib/LaTeXML/Package/colortbl.sty.ltxml blib/lib/LaTeXML/Package/colortbl.sty.ltxml \
	  lib/LaTeXML/Package/acronym.sty.ltxml blib/lib/LaTeXML/Package/acronym.sty.ltxml \
	  lib/LaTeXML/Package/amsopn.sty.ltxml blib/lib/LaTeXML/Package/amsopn.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-font.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-font.rng 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/caption.sty.ltxml blib/lib/LaTeXML/Package/caption.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-gradient.rng \
	  lib/LaTeXML/Package/latin10.def.ltxml blib/lib/LaTeXML/Package/latin10.def.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-extresources-attrib.rnc \
	  lib/LaTeXML/Token.pm blib/lib/LaTeXML/Token.pm \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rng \
	  lib/LaTeXML/Package/AmSTeX.pool.ltxml blib/lib/LaTeXML/Package/AmSTeX.pool.ltxml \
	  lib/LaTeXML/Package/ot4.fontmap.ltxml blib/lib/LaTeXML/Package/ot4.fontmap.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rng \
	  lib/LaTeXML/Package/ccfonts.sty.ltxml blib/lib/LaTeXML/Package/ccfonts.sty.ltxml \
	  lib/LaTeXML/Package/amstext.sty.ltxml blib/lib/LaTeXML/Package/amstext.sty.ltxml \
	  lib/LaTeXML/Package/citesort.sty.ltxml blib/lib/LaTeXML/Package/citesort.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.rnc \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rnc \
	  lib/LaTeXML/Package/frenchb.ldf.ltxml blib/lib/LaTeXML/Package/frenchb.ldf.ltxml \
	  lib/LaTeXML/Package/gen-m-l.cls.ltxml blib/lib/LaTeXML/Package/gen-m-l.cls.ltxml \
	  lib/LaTeXML/Package/courier.sty.ltxml blib/lib/LaTeXML/Package/courier.sty.ltxml \
	  lib/LaTeXML/Package/aastex.cls.ltxml blib/lib/LaTeXML/Package/aastex.cls.ltxml \
	  lib/LaTeXML/Post/MakeIndex.pm blib/lib/LaTeXML/Post/MakeIndex.pm \
	  lib/LaTeXML/Package/revsymb.sty.ltxml blib/lib/LaTeXML/Package/revsymb.sty.ltxml \
	  lib/LaTeXML/Package/sv_support.sty.ltxml blib/lib/LaTeXML/Package/sv_support.sty.ltxml \
	  lib/LaTeXML/Package/doublespace.sty.ltxml blib/lib/LaTeXML/Package/doublespace.sty.ltxml \
	  lib/LaTeXML/Post/XSLT.pm blib/lib/LaTeXML/Post/XSLT.pm \
	  lib/LaTeXML/resources/XSLT/LaTeXML-para-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-para-xhtml.xsl \
	  lib/LaTeXML/Post/Scan.pm blib/lib/LaTeXML/Post/Scan.pm \
	  lib/LaTeXML/Package/article.cls.ltxml blib/lib/LaTeXML/Package/article.cls.ltxml \
	  lib/LaTeXML/Package/cropmark.sty.ltxml blib/lib/LaTeXML/Package/cropmark.sty.ltxml \
	  lib/LaTeXML/Common/XML.pm blib/lib/LaTeXML/Common/XML.pm \
	  lib/LaTeXML/Package/scalefnt.sty.ltxml blib/lib/LaTeXML/Package/scalefnt.sty.ltxml \
	  lib/LaTeXML/resources/CSS/ltx-article.css blib/lib/LaTeXML/resources/CSS/ltx-article.css \
	  lib/LaTeXML/Package/eucal.sty.ltxml blib/lib/LaTeXML/Package/eucal.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/pstricks.sty.ltxml blib/lib/LaTeXML/Package/pstricks.sty.ltxml \
	  lib/LaTeXML/Package/times.sty.ltxml blib/lib/LaTeXML/Package/times.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rnc \
	  lib/LaTeXML/Package/bookman.sty.ltxml blib/lib/LaTeXML/Package/bookman.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-style.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-style.rnc \
	  lib/LaTeXML/Package/BibTeX.pool.ltxml blib/lib/LaTeXML/Package/BibTeX.pool.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-inline.rnc \
	  lib/LaTeXML/Package/report.cls.ltxml blib/lib/LaTeXML/Package/report.cls.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rnc \
	  lib/LaTeXML/Util/KeyVal.pm blib/lib/LaTeXML/Util/KeyVal.pm \
	  lib/LaTeXML/resources/DTD/LaTeXML.dtd blib/lib/LaTeXML/resources/DTD/LaTeXML.dtd \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rnc \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rng \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rng \
	  lib/LaTeXML/Package/luximono.sty.ltxml blib/lib/LaTeXML/Package/luximono.sty.ltxml \
	  lib/LaTeXML/Package/amsppt.sty.ltxml blib/lib/LaTeXML/Package/amsppt.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-all-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-all-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-view.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-view.rng \
	  lib/LaTeXML/Package/mathpple.sty.ltxml blib/lib/LaTeXML/Package/mathpple.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rng \
	  lib/LaTeXML/Package/revtex.sty.ltxml blib/lib/LaTeXML/Package/revtex.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animation.rnc \
	  lib/LaTeXML/Package/babel.def.ltxml blib/lib/LaTeXML/Package/babel.def.ltxml \
	  lib/LaTeXML/Package/latexml.sty.ltxml blib/lib/LaTeXML/Package/latexml.sty.ltxml \
	  lib/LaTeXML/Package/placeins.sty.ltxml blib/lib/LaTeXML/Package/placeins.sty.ltxml \
	  lib/LaTeXML/Package/gen-p-l.cls.ltxml blib/lib/LaTeXML/Package/gen-p-l.cls.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rng \
	  lib/LaTeXML/Package/multirow.sty.ltxml blib/lib/LaTeXML/Package/multirow.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg11-basic.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg11-basic.rng \
	  lib/LaTeXML/Package/JHEP2.cls.ltxml blib/lib/LaTeXML/Package/JHEP2.cls.ltxml \
	  lib/LaTeXML/Package/babel.sty.ltxml blib/lib/LaTeXML/Package/babel.sty.ltxml \
	  lib/LaTeXML/Package/iopart_support.sty.ltxml blib/lib/LaTeXML/Package/iopart_support.sty.ltxml \
	  lib/LaTeXML/Package/mathpazo.sty.ltxml blib/lib/LaTeXML/Package/mathpazo.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-text.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-text.rnc \
	  lib/LaTeXML/Package/graphicx.sty.ltxml blib/lib/LaTeXML/Package/graphicx.sty.ltxml \
	  lib/LaTeXML/Parameters.pm blib/lib/LaTeXML/Parameters.pm \
	  lib/LaTeXML/Package/bbm.sty.ltxml blib/lib/LaTeXML/Package/bbm.sty.ltxml \
	  lib/LaTeXML/Package/hyperref.sty.ltxml blib/lib/LaTeXML/Package/hyperref.sty.ltxml \
	  lib/LaTeXML/Package/aastex.sty.ltxml blib/lib/LaTeXML/Package/aastex.sty.ltxml \
	  lib/LaTeXML/Stomach.pm blib/lib/LaTeXML/Stomach.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-font.rng \
	  lib/LaTeXML/Package/amsbsy.sty.ltxml blib/lib/LaTeXML/Package/amsbsy.sty.ltxml \
	  lib/LaTeXML/LaTeXML.catalog blib/lib/LaTeXML/LaTeXML.catalog \
	  lib/LaTeXML/Number.pm blib/lib/LaTeXML/Number.pm \
	  lib/LaTeXML/Package/fontenc.sty.ltxml blib/lib/LaTeXML/Package/fontenc.sty.ltxml \
	  lib/LaTeXML/resources/CSS/ltx-book.css blib/lib/LaTeXML/resources/CSS/ltx-book.css \
	  lib/LaTeXML/Package/numprint.sty.ltxml blib/lib/LaTeXML/Package/numprint.sty.ltxml \
	  lib/LaTeXML/Post/Graphics.pm blib/lib/LaTeXML/Post/Graphics.pm \
	  lib/LaTeXML/Package/chancery.sty.ltxml blib/lib/LaTeXML/Package/chancery.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-webpage-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-webpage-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-structure.rnc \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-bib.rnc \
	  lib/LaTeXML/Package/mn2e.cls.ltxml blib/lib/LaTeXML/Package/mn2e.cls.ltxml \
	  lib/LaTeXML/Package/utopia.sty.ltxml blib/lib/LaTeXML/Package/utopia.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rnc \
	  lib/LaTeXML/Package/ifpdf.sty.ltxml blib/lib/LaTeXML/Package/ifpdf.sty.ltxml \
	  lib/LaTeXML/Package/theorem.sty.ltxml blib/lib/LaTeXML/Package/theorem.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/CSS/LaTeXML-blue.css blib/lib/LaTeXML/resources/CSS/LaTeXML-blue.css \
	  lib/LaTeXML/Package/mn.cls.ltxml blib/lib/LaTeXML/Package/mn.cls.ltxml \
	  lib/LaTeXML/Model.pm blib/lib/LaTeXML/Model.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-shape.rnc \
	  lib/LaTeXML/Package/ts1.fontmap.ltxml blib/lib/LaTeXML/Package/ts1.fontmap.ltxml \
	  lib/LaTeXML/Package/ae.sty.ltxml blib/lib/LaTeXML/Package/ae.sty.ltxml \
	  lib/LaTeXML/Package/amssymb.sty.ltxml blib/lib/LaTeXML/Package/amssymb.sty.ltxml \
	  lib/LaTeXML/Package/aa_support.sty.ltxml blib/lib/LaTeXML/Package/aa_support.sty.ltxml \
	  lib/LaTeXML/Package/pdfTeX.pool.ltxml blib/lib/LaTeXML/Package/pdfTeX.pool.ltxml \
	  lib/LaTeXML/Package/a4.sty.ltxml blib/lib/LaTeXML/Package/a4.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.rng \
	  lib/LaTeXML/Package/LaTeX.pool.ltxml blib/lib/LaTeXML/Package/LaTeX.pool.ltxml \
	  lib/LaTeXML/Package/emulateapj.cls.ltxml blib/lib/LaTeXML/Package/emulateapj.cls.ltxml \
	  lib/LaTeXML/Package/bm.sty.ltxml blib/lib/LaTeXML/Package/bm.sty.ltxml \
	  lib/LaTeXML/Package/exscale.sty.ltxml blib/lib/LaTeXML/Package/exscale.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-tabular-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-tabular-xhtml.xsl \
	  lib/LaTeXML/Package/epsfig.sty.ltxml blib/lib/LaTeXML/Package/epsfig.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rng \
	  lib/LaTeXML/Post/PictureImages.pm blib/lib/LaTeXML/Post/PictureImages.pm \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-math.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rnc \
	  lib/LaTeXML/Package/epsf.sty.ltxml blib/lib/LaTeXML/Package/epsf.sty.ltxml \
	  lib/LaTeXML/Package/amstex.tex.ltxml blib/lib/LaTeXML/Package/amstex.tex.ltxml \
	  lib/LaTeXML/Model/RelaxNG.pm blib/lib/LaTeXML/Model/RelaxNG.pm \
	  lib/LaTeXML/Package/iopart.cls.ltxml blib/lib/LaTeXML/Package/iopart.cls.ltxml \
	  lib/LaTeXML/Post/Split.pm blib/lib/LaTeXML/Post/Split.pm \
	  lib/LaTeXML/Package/mathptmx.sty.ltxml blib/lib/LaTeXML/Package/mathptmx.sty.ltxml \
	  lib/LaTeXML/resources/CSS/LaTeXML-navbar-right.css blib/lib/LaTeXML/resources/CSS/LaTeXML-navbar-right.css \
	  lib/LaTeXML/Package/rsfs.sty.ltxml blib/lib/LaTeXML/Package/rsfs.sty.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rnc \
	  lib/LaTeXML/Package/multicol.sty.ltxml blib/lib/LaTeXML/Package/multicol.sty.ltxml \
	  lib/LaTeXML/Package/emulateapj.sty.ltxml blib/lib/LaTeXML/Package/emulateapj.sty.ltxml \
	  lib/LaTeXML/Package/svjour.cls.ltxml blib/lib/LaTeXML/Package/svjour.cls.ltxml \
	  lib/LaTeXML/Package/pxfonts.sty.ltxml blib/lib/LaTeXML/Package/pxfonts.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rnc \
	  lib/LaTeXML/Package/eufrak.sty.ltxml blib/lib/LaTeXML/Package/eufrak.sty.ltxml \
	  lib/LaTeXML/Package/pst-node.sty.ltxml blib/lib/LaTeXML/Package/pst-node.sty.ltxml \
	  lib/LaTeXML/Package/fourier.sty.ltxml blib/lib/LaTeXML/Package/fourier.sty.ltxml \
	  lib/LaTeXML/Package/tabularx.sty.ltxml blib/lib/LaTeXML/Package/tabularx.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-misc.rng \
	  lib/LaTeXML/Package/subfigure.sty.ltxml blib/lib/LaTeXML/Package/subfigure.sty.ltxml \
	  lib/LaTeXML/texmf/lxRDFa.sty blib/lib/LaTeXML/texmf/lxRDFa.sty \
	  lib/LaTeXML/Package/revtex4.cls.ltxml blib/lib/LaTeXML/Package/revtex4.cls.ltxml \
	  lib/LaTeXML/Package/hhline.sty.ltxml blib/lib/LaTeXML/Package/hhline.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rng \
	  lib/LaTeXML/Converter.pm blib/lib/LaTeXML/Converter.pm \
	  lib/LaTeXML/Package/color.sty.ltxml blib/lib/LaTeXML/Package/color.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-picture.rnc \
	  lib/LaTeXML/Post.pm blib/lib/LaTeXML/Post.pm \
	  lib/LaTeXML/Package/concmath.sty.ltxml blib/lib/LaTeXML/Package/concmath.sty.ltxml \
	  lib/LaTeXML/Package/aaspp.sty.ltxml blib/lib/LaTeXML/Package/aaspp.sty.ltxml \
	  lib/LaTeXML/Package/upref.sty.ltxml blib/lib/LaTeXML/Package/upref.sty.ltxml \
	  lib/LaTeXML/Post/LaTeXImages.pm blib/lib/LaTeXML/Post/LaTeXImages.pm \
	  lib/LaTeXML/Package/newcent.sty.ltxml blib/lib/LaTeXML/Package/newcent.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/fragment.opt blib/lib/LaTeXML/resources/Profiles/fragment.opt \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-viewport-attrib.rng \
	  lib/LaTeXML/Package/inst_support.sty.ltxml blib/lib/LaTeXML/Package/inst_support.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rng \
	  lib/LaTeXML/Post/Writer.pm blib/lib/LaTeXML/Post/Writer.pm 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-graphics-attrib.rng \
	  lib/LaTeXML/Package/euscript.sty.ltxml blib/lib/LaTeXML/Package/euscript.sty.ltxml \
	  lib/LaTeXML/resources/CSS/ltx-amsart.css blib/lib/LaTeXML/resources/CSS/ltx-amsart.css \
	  lib/LaTeXML/Package/llncs.cls.ltxml blib/lib/LaTeXML/Package/llncs.cls.ltxml \
	  lib/LaTeXML/Package/epigraph.sty.ltxml blib/lib/LaTeXML/Package/epigraph.sty.ltxml \
	  lib/LaTeXML/Package/amsproc.cls.ltxml blib/lib/LaTeXML/Package/amsproc.cls.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-structure.rnc \
	  lib/LaTeXML/Package/t1enc.def.ltxml blib/lib/LaTeXML/Package/t1enc.def.ltxml \
	  lib/LaTeXML/Package/mathptm.sty.ltxml blib/lib/LaTeXML/Package/mathptm.sty.ltxml \
	  lib/LaTeXML/Package/floatfig.sty.ltxml blib/lib/LaTeXML/Package/floatfig.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-datatypes.rng \
	  lib/LaTeXML/Package/aa.cls.ltxml blib/lib/LaTeXML/Package/aa.cls.ltxml \
	  lib/LaTeXML/Package/palatino.sty.ltxml blib/lib/LaTeXML/Package/palatino.sty.ltxml \
	  lib/LaTeXML/Package/ngerman.sty.ltxml blib/lib/LaTeXML/Package/ngerman.sty.ltxml \
	  lib/LaTeXML/Package/html.sty.ltxml blib/lib/LaTeXML/Package/html.sty.ltxml \
	  lib/LaTeXML/Package/supertabular.sty.ltxml blib/lib/LaTeXML/Package/supertabular.sty.ltxml \
	  lib/LaTeXML/Util/Pathname.pm blib/lib/LaTeXML/Util/Pathname.pm \
	  lib/LaTeXML/Object.pm blib/lib/LaTeXML/Object.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-docevents-attrib.rng \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML.model blib/lib/LaTeXML/resources/RelaxNG/LaTeXML.model \
	  lib/LaTeXML/Package/wrapfig.sty.ltxml blib/lib/LaTeXML/Package/wrapfig.sty.ltxml \
	  lib/LaTeXML/Package/eulervm.sty.ltxml blib/lib/LaTeXML/Package/eulervm.sty.ltxml \
	  lib/LaTeXML/Package/book.cls.ltxml blib/lib/LaTeXML/Package/book.cls.ltxml \
	  lib/LaTeXML/Package/german.sty.ltxml blib/lib/LaTeXML/Package/german.sty.ltxml \
	  lib/LaTeXML/Package/elsart_support.sty.ltxml blib/lib/LaTeXML/Package/elsart_support.sty.ltxml \
	  lib/LaTeXML/Package/cmbright.sty.ltxml blib/lib/LaTeXML/Package/cmbright.sty.ltxml \
	  lib/LaTeXML/Package/slides.cls.ltxml blib/lib/LaTeXML/Package/slides.cls.ltxml \
	  lib/LaTeXML/Package/mn2e_support.sty.ltxml blib/lib/LaTeXML/Package/mn2e_support.sty.ltxml \
	  lib/LaTeXML/Package/aasms.sty.ltxml blib/lib/LaTeXML/Package/aasms.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-paint-attrib.rnc 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/XSLT/LaTeXML-misc-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-misc-xhtml.xsl \
	  lib/LaTeXML/Package/cp852.def.ltxml blib/lib/LaTeXML/Package/cp852.def.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-structure-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-structure-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-hyperlink.rnc \
	  lib/LaTeXML/Package/xunicode.sty.ltxml blib/lib/LaTeXML/Package/xunicode.sty.ltxml \
	  lib/LaTeXML/Package/fleqn.sty.ltxml blib/lib/LaTeXML/Package/fleqn.sty.ltxml \
	  lib/LaTeXML/resources/CSS/ltx-report.css blib/lib/LaTeXML/resources/CSS/ltx-report.css \
	  lib/LaTeXML/Package/euler.sty.ltxml blib/lib/LaTeXML/Package/euler.sty.ltxml \
	  lib/LaTeXML/resources/Profiles/stex.opt blib/lib/LaTeXML/resources/Profiles/stex.opt \
	  lib/LaTeXML/Color.pm blib/lib/LaTeXML/Color.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-filter.rnc \
	  lib/LaTeXML/Package/array.sty.ltxml blib/lib/LaTeXML/Package/array.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-tabular.rng \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-meta.rnc \
	  lib/LaTeXML/Util/ObjectDB.pm blib/lib/LaTeXML/Util/ObjectDB.pm \
	  lib/LaTeXML/Package/amsrefs.sty.ltxml blib/lib/LaTeXML/Package/amsrefs.sty.ltxml \
	  lib/LaTeXML/Package/t2b.fontmap.ltxml blib/lib/LaTeXML/Package/t2b.fontmap.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-basic-text.rnc \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-marker.rnc \
	  lib/LaTeXML/Package/fontspec.sty.ltxml blib/lib/LaTeXML/Package/fontspec.sty.ltxml \
	  lib/LaTeXML/Package/pspicture.sty.ltxml blib/lib/LaTeXML/Package/pspicture.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-block-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-block-xhtml.xsl \
	  lib/LaTeXML/Package/helvet.sty.ltxml blib/lib/LaTeXML/Package/helvet.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-image.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-image.rnc \
	  lib/LaTeXML/Package/elsart.cls.ltxml blib/lib/LaTeXML/Package/elsart.cls.ltxml \
	  lib/LaTeXML/Package/revtex4.sty.ltxml blib/lib/LaTeXML/Package/revtex4.sty.ltxml \
	  lib/LaTeXML/Post/MathImages.pm blib/lib/LaTeXML/Post/MathImages.pm \
	  lib/LaTeXML/Package/comment.sty.ltxml blib/lib/LaTeXML/Package/comment.sty.ltxml \
	  lib/LaTeXML/Package/ams_core.cls.ltxml blib/lib/LaTeXML/Package/ams_core.cls.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rnc \
	  lib/LaTeXML/resources/javascript/LaTeXML-maybeMathjax.js blib/lib/LaTeXML/resources/javascript/LaTeXML-maybeMathjax.js \
	  lib/LaTeXML/Package/ly1.fontmap.ltxml blib/lib/LaTeXML/Package/ly1.fontmap.ltxml \
	  lib/LaTeXML/Util/Alignment.pm blib/lib/LaTeXML/Util/Alignment.pm \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-block.rng \
	  lib/LaTeXML/Package/floatflt.sty.ltxml blib/lib/LaTeXML/Package/floatflt.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-math-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-math-xhtml.xsl \
	  lib/LaTeXML/Bib.pm blib/lib/LaTeXML/Bib.pm \
	  lib/LaTeXML/Post/MathML.pm blib/lib/LaTeXML/Post/MathML.pm \
	  lib/LaTeXML/Package/cite.sty.ltxml blib/lib/LaTeXML/Package/cite.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rnc \
	  lib/LaTeXML/Package/revtex3_support.sty.ltxml blib/lib/LaTeXML/Package/revtex3_support.sty.ltxml \
	  lib/LaTeXML/Package/amsthm.sty.ltxml blib/lib/LaTeXML/Package/amsthm.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-xlink-attrib.rnc \
	  lib/LaTeXML/Package/t1.fontmap.ltxml blib/lib/LaTeXML/Package/t1.fontmap.ltxml \
	  lib/LaTeXML/Font.pm blib/lib/LaTeXML/Font.pm \
	  lib/LaTeXML/Package/amsart.cls.ltxml blib/lib/LaTeXML/Package/amsart.cls.ltxml \
	  lib/LaTeXML/Package/pifont.sty.ltxml blib/lib/LaTeXML/Package/pifont.sty.ltxml \
	  lib/LaTeXML/resources/XSLT/LaTeXML-common.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-common.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg11-tiny.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg11-tiny.rng \
	  lib/LaTeXML/Gullet.pm blib/lib/LaTeXML/Gullet.pm \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-filter.rnc \
	  lib/LaTeXML/Error.pm blib/lib/LaTeXML/Error.pm \
	  lib/LaTeXML/Package/a4wide.sty.ltxml blib/lib/LaTeXML/Package/a4wide.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-clip.rnc \
	  lib/LaTeXML/Package/eTeX.pool.ltxml blib/lib/LaTeXML/Package/eTeX.pool.ltxml \
	  lib/LaTeXML/Package/ams_support.sty.ltxml blib/lib/LaTeXML/Package/ams_support.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-cursor.rnc \
	  lib/LaTeXML/Util/Test.pm blib/lib/LaTeXML/Util/Test.pm \
	  lib/LaTeXML/Package/JHEP.cls.ltxml blib/lib/LaTeXML/Package/JHEP.cls.ltxml \
	  lib/LaTeXML/Post/SVG.pm blib/lib/LaTeXML/Post/SVG.pm \
	  lib/LaTeXML/Document.pm blib/lib/LaTeXML/Document.pm 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/Package/url.sty.ltxml blib/lib/LaTeXML/Package/url.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rnc \
	  lib/LaTeXML/Util/Geometry.pm blib/lib/LaTeXML/Util/Geometry.pm \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-para.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg11.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg11.rng \
	  lib/LaTeXML/Package/makeidx.sty.ltxml blib/lib/LaTeXML/Package/makeidx.sty.ltxml \
	  lib/LaTeXML/resources/CSS/ltx-apj.css blib/lib/LaTeXML/resources/CSS/ltx-apj.css \
	  lib/LaTeXML/Package/framed.sty.ltxml blib/lib/LaTeXML/Package/framed.sty.ltxml \
	  lib/LaTeXML/MathParser.pm blib/lib/LaTeXML/MathParser.pm \
	  lib/LaTeXML/Package/epstopdf.sty.ltxml blib/lib/LaTeXML/Package/epstopdf.sty.ltxml \
	  lib/LaTeXML/Package/avant.sty.ltxml blib/lib/LaTeXML/Package/avant.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-conditional.rnc \
	  lib/LaTeXML/Mouth.pm blib/lib/LaTeXML/Mouth.pm \
	  lib/LaTeXML/Post/CrossRef.pm blib/lib/LaTeXML/Post/CrossRef.pm \
	  lib/LaTeXML/Package/revtex.cls.ltxml blib/lib/LaTeXML/Package/revtex.cls.ltxml \
	  lib/LaTeXML/Package/graphics.sty.ltxml blib/lib/LaTeXML/Package/graphics.sty.ltxml \
	  lib/LaTeXML/Package/latexsym.sty.ltxml blib/lib/LaTeXML/Package/latexsym.sty.ltxml \
	  lib/LaTeXML/Package/elsarticle.cls.ltxml blib/lib/LaTeXML/Package/elsarticle.cls.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-container-attrib.rng \
	  lib/LaTeXML/Package/JHEP3.cls.ltxml blib/lib/LaTeXML/Package/JHEP3.cls.ltxml \
	  lib/LaTeXML/Package/amsa.fontmap.ltxml blib/lib/LaTeXML/Package/amsa.fontmap.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-graphics-attrib.rng \
	  lib/LaTeXML/resources/Profiles/modern.opt blib/lib/LaTeXML/resources/Profiles/modern.opt \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-animevents-attrib.rnc \
	  lib/LaTeXML/Package/beton.sty.ltxml blib/lib/LaTeXML/Package/beton.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-pattern.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-script.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-script.rng \
	  lib/LaTeXML/Package/amstex.sty.ltxml blib/lib/LaTeXML/Package/amstex.sty.ltxml \
	  lib/LaTeXML/Post/Collector.pm blib/lib/LaTeXML/Post/Collector.pm \
	  lib/LaTeXML/Package/applemac.def.ltxml blib/lib/LaTeXML/Package/applemac.def.ltxml 
	$(NOECHO) $(ABSPERLRUN) -MExtUtils::Install -e 'pm_to_blib({@ARGV}, '\''$(INST_LIB)/auto'\'', q[$(PM_FILTER)], '\''$(PERM_DIR)'\'')' -- \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rnc blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rnc \
	  lib/LaTeXML/resources/XSLT/LaTeXML-inline-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-inline-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-text.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-text.rng \
	  lib/LaTeXML/resources/XSLT/LaTeXML-html.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-html.xsl \
	  lib/LaTeXML/resources/XSLT/LaTeXML-xhtml.xsl blib/lib/LaTeXML/resources/XSLT/LaTeXML-xhtml.xsl \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rng blib/lib/LaTeXML/resources/RelaxNG/svg/svg-core-attrib.rng \
	  lib/LaTeXML/resources/RelaxNG/svg/svg-script.rnc blib/lib/LaTeXML/resources/RelaxNG/svg/svg-script.rnc \
	  lib/LaTeXML.pm blib/lib/LaTeXML.pm \
	  lib/LaTeXML/Package/cancel.sty.ltxml blib/lib/LaTeXML/Package/cancel.sty.ltxml \
	  lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rng blib/lib/LaTeXML/resources/RelaxNG/LaTeXML-common.rng 
	$(NOECHO) $(TOUCH) pm_to_blib


# --- MakeMaker selfdocument section:


# --- MakeMaker postamble section:

# Record the svn revision in the Version module, for more informative diagnostics
pure_all :: $(REVISION_FILE) update_revision

# Always set version if version module template is newer
$(REVISION_FILE): lib/LaTeXML/Version.in
	$(RECORD_REVISION) lib/LaTeXML/Version.in > $(REVISION_FILE)

# update version if stored revision if not current
update_revision:
	- $(NOECHO) test $(REVISION) = $(OLD_REVISION) \
	|| $(RECORD_REVISION) lib/LaTeXML/Version.in > $(REVISION_FILE)


# Precompile the (Recursive Descent) MathGrammar
pure_all :: $(INST_LIBDIR)/LaTeXML/MathGrammar.pm

$(INST_LIBDIR)/LaTeXML/MathGrammar.pm: lib/LaTeXML/MathGrammar
	$(PERLRUN) -MParse::RecDescent - lib/LaTeXML/MathGrammar LaTeXML::MathGrammar
	$(NOECHO) $(MKPATH) $(INST_LIBDIR)/LaTeXML
	$(MV) MathGrammar.pm blib/lib/LaTeXML/MathGrammar.pm


# Install LaTeXML's LaTeX style files into the standard texmf location
pure_install ::
	$(NOECHO) $(MKPATH) "$(DESTINSTALLTEXMFDIR)"
	$(NOECHO) $(MOD_INSTALL) \
		read "$(INSTALLTEXMFDIR)/.packlist" \
		write "$(DESTINSTALLTEXMFDIR)/.packlist" \
		"$(INST_TEXMFDIR)" "$(DESTINSTALLTEXMFDIR)"
	- $(PERLRUN) -e "exit(1) unless -w shift;" "$(INSTALLTEXMFDIR)" && mktexlsr

uninstall ::
	$(NOECHO) $(UNINSTALL) "$(INSTALLTEXMFDIR)/.packlist"
	- $(PERLRUN) -e "exit(1) unless -w shift;" "$(INSTALLTEXMFDIR)" && mktexlsr


EXTRA_TEST_FILES = t/*.tt

fulltest : test extratest

extratest ::
	PERL_DL_NONLAZY=1 $(FULLPERLRUN) "-MExtUtils::Command::MM" "-e" "test_harness($(TEST_VERBOSE), '$(INST_LIB)', '$(INST_ARCHLIB)')" $(EXTRA_TEST_FILES)



# End.
