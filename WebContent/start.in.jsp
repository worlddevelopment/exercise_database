
<%@page import="db.*, aufgaben_db.Global, java.net.URLEncoder"%>

<!-- Treeview -->
<link rel="stylesheet" href="jquery.treeview/jquery.treeview.css" />
<script src="jquery.treeview/jquery.treeview.js" type="text/javascript"></script>
<!-- <script type="text/javascript" src="jquery.treeview/demo/demo.js"></script>-->
<script src="jquery.treeview/lib/jquery.cookie.js" type="text/javascript"></script>


<!-- Tooltip -->
<script type="text/javascript" src="jquery/toolTipPreview.js"></script> 
<script type="text/javascript" src="js/validateSelectionDependingOnButton.js"></script>


<script type="text/javascript" >

//<!--

/**
 * Edit: Adds/loads a table of all files of the lecturer. 
 *
 *Transportiert ueber GET Mathode die Parameter aus dem Baum  an die Tabelle 
 *@param String:Semester_value
 *@param String:Veranstaltung_value
 *@param String:Dozenten_value
 *@param String:Typ_value
 *@param String:Link_id
 */
function ajax_showCacheOrLoad(/*filelink, semester,course,lecturer,type, ajaxlink_num,*/ target_idOrObj) {
	 alert('Don\'t call this function any more. getTargetIfLoadDesiredOtherwiseShowCache now is used because'
			 + ' the other (this) function could not properly invoke the dblclick event.');
}

function getTargetIfLoadDesiredOtherwiseShowCache(target_idOrObj, this_obj) {
	//alert(getTargetIfLoadDesiredOtherwiseShowCache);//<-- prints out the whole function!
	target_idOrObj = target_idOrObj || (this_obj.nextElementSibling ? this_obj.nextElementSibling.nextElementSibling 
			: (this_obj.nextSibling ? this_obj.nextSibling.nextSibling : undefined/*=> create target*/)
	);//"div#ajaxcontent"; // Only useful if each event has its own container and not only one like it has been before (the #ac).
	var target_obj = target_idOrObj;
	if (typeof target_idOrObj == 'string') {
		target_obj = document.getElementById(target_idOrObj);
	}// || $(target_idOrObj);
	//alert('id or obj: ' + target_idOrObj + '  object: ' + target_obj);
	
    /* if (!target_obj) {
    	target_obj = document.getElementById(target_idOrObj);
    } */
    //still no target object to attach the content to found?
    if (!target_obj) {
		var target_id_generated = "" + this_obj.id + "_listing"/*<-- could be both sheetdraft or exercise listing. */ 
		       //WORKING BUT PROBLEMATIC: + ("" + target_idOrObj).replace(" ", "_").replace("\[|\]", "-") + '_created_for_callee_' + ("" + this_obj).replace(" ", "_").replace("\[|\]", "-")
		;
    	/* alert('Target object was undefined! => Falling back to append the loaded content direclty after this element.'
    			+ '\r\nparentNode.lastChild.id: ' + this_obj.parentNode && this_obj.parentNode.lastChild.id); 
    	*/
    	//1. check if the last child of the parent node is already containing the generated id?
    	if (this_obj.parentNode.lastChild && this_obj.parentNode.lastChild.id == target_id_generated) {
    		//=> continue examination with this object then:
    		target_obj = this_obj.parentNode.lastChild;
    	}
    	else {
    		//=> create a new container then:
	    	var t = document.createElement('div');
	    	t.setAttribute('id', target_id_generated);
		   	//this.parentNode.insertAfter(this.nextSibling/*the ul list should stay above the target.*/, t);
		   	// OR
		   	this_obj.parentNode.appendChild(t);//<-- simply append at the end.
		   	//use this newly created element as the target:
	    	target_obj = t;
    	}
    	
    }
    
    // At this point there is an existing target object. 
    var text = target_obj.innerHTML;
    if (!text) {
        text = target_obj.textContent;
    }
    if (!text) {
    	text = target_obj.value;
    }
    //alert(target_obj + " => Content: " + text);
    //still no content found? 
    if (!text || text == "" || /*already shown?*/target_obj.style 
    		&& target_obj.style.display != 'none'/*then force re-loading*/) {
    	//=> load content per ajax request. 
    	return target_obj; /* Note: It is ensured that this target object exists (see above).*/
    	//this.dblclick();
    	//this.click();/*delay?*/ this.click();
    	//igniteEvent(this, 'dblclick');
    	//OR
    	//ajax_get_listing_sheetdrafts(filelink, semester,course,lecturer,type, ajaxlink_num, target_obj);
    }
    else {
    	/*Add button to hide the div: <-- Now the table column headers make hide the target container. See table.ajax.jsp .*/
    	//alert("Showing already existing once ago loaded content! target: " + target_obj);
    	//=> show the already existing once ago loaded content ('cache'). 
    	if (target_obj.style) {
    	    target_obj.style.display = 'inline';
    	}
    	else {
    		target_obj.setAttribute('style', 'display:inline;');
    	}
    }
    return undefined;/*indicates that the callee function should not continue its efforts (it needs a target object).*/
 }
/*
 as links seem not to be clickable via link.click(), here's an alternative:
 http://stackoverflow.com/questions/902713/how-do-i-automatically-click-a-link-with-javascript
 *///current no longer used:
function igniteEvent(obj, e) {
	//alert(obj + ' ignite event: ' + e);
	//which event shall be fired?
	e = e || 'click';
	var cancelled = false;
	if (document.createEvent) {
		var event = document.createEvent('MouseEvents');
		event.initMouseEvent(e/*e.g. click*/, true, true, window
			    , 0, 0, 0, 0, 0
			    , false, false, false, false
			    , 0, null
		);
		cancelled = !obj.dispatchEvent(event);
	}
	
	else if (obj.fireEvent) {
		cancelled = !obj.fireEvent('on' + e);
	}
	
	if (!cancelled && obj.href) {
		//window.location = obj.href;
		return true;/*Should have the same effect if the callee is a link?*/
	}
	
}
//This is called in the dblclick event (double click):
function ajax_get_listing_sheetdrafts(filelink, semester,course,lecturer,type, ajaxlink_num
		, target_idOrObj, source_obj) {
	//alert('ajax_get_listing_sheetdrafts');
	/*As of update v31.13 target_idOrObj and source_obj have been introduced. It's for
    1) caching, so that all link that may load ajax content has its own container nearby (and one global one).
    2) this also has the nice effect that the loaded content appears where it is expected and not at the end of the page.
    3) source_obj was required because this sometimes loses the connection to the HTML object and refers to the function itself instead.
    */
	// Ensure there is a target, create a container element for the ajax loaded content if not:
	var target_obj = getTargetIfLoadDesiredOtherwiseShowCache(target_idOrObj, source_obj);
	// If the result is still undefined then we should abort, thus:
	// Is the result undefined?
    if (!target_obj) {
    	return true;/* The cache of prior loaded content was shown by above function. */
    }
	
	//otherwise we involve ajax: 
    $("#loader").show();
    //$(document).ready(function(){
    	//Now as of v31.13 the ajax loaded table sheetdraft listing will be placed in somehow determined 
        target_idOrObj = target_idOrObj || (source_obj.nextElementSibling ? source_obj.nextElementSibling.nextElementSibling 
                : (source_obj.nextSibling ? source_obj.nextSibling.nextSibling : undefined/*=> create target*/)
        );    // "div#ajaxcontent"; 
        
    	/* If the commented ready functions and click event assignments are used then
    	  thise ajax get listing sheetdraft|exercise functions have to be called
    	  via function(//...)(); at the end of the site to assign the click event! */
        //$("#ajaxlink" + ajaxlink_num).click(function() {
            //alert(par1+par2+par3);
            $.ajax({
	            type: "GET",
	            url: "table.ajax.jsp",
	            data: "filelink=" + filelink
	                + "&semester=" + semester
	                + "&course="+ course
	                + "&lecturer=" + lecturer
	                + "&type=" + type
	                + "&ajaxlink_num=" + ajaxlink_num
	                + "&session_user=<%=Global.getUserURLEncoded(session)%>" /*for exercise table.ajax.jsp!*/
	                //+ "&sheetdraft_listing"//superfluous
	                ,
	            success: function(data) {
	                $(target_obj/*"div#ajaxcontent"*/).html(data);
	                $("#loader").hide();
	                screenshotPreview(); //<- as of v31.13c this may be called as often as desired.
	            }
            });
            
        //});
    
    
    //});
};

/**
 * Loads a table containing all exercises of the lecturer.
 */
function ajax_get_listing_exercises(sheetdraft_filelink, lecturer /*par1,par2,par3,par4,par5*/, ajaxlink_num
		, target_idOrObj, source_obj) {
	//alert('ajax_get_listing_exercises');
    /* Lecturer is required because this saves us one join operation in table.ajax.jsp.
       As of update v31.13 target_idOrObj and source_obj have been introduced. It's for
       1) caching, so that all link that may load ajax content has its own container nearby (and one global one).
       2) this also has the nice effect that the loaded content appears where it is expected and not at the end of the page.
       3) source_obj was required because this sometimes loses the connection to the HTML object and refers to the function itself instead.
       */
    
    // Ensure there is a target, create a container element for the ajax loaded content if not:
    var target_obj = getTargetIfLoadDesiredOtherwiseShowCache(target_idOrObj, source_obj);
    // If the result is still undefined then we should abort, thus:
    // Is the result undefined?
    if (!target_obj) {
        return true;/* The cache of prior loaded content was shown by above function. */
    }
    
    //otherwise we involve ajax: 
    $("#loader").show();
    //$(document).ready(function(){
        //Now as of v31.13 the ajax loaded table exercises listing will be placed in somehow determined 
        target_idOrObj = target_idOrObj || (source_obj.nextElementSibling ? source_obj.nextElementSibling.nextElementSibling 
                : (source_obj.nextSibling ? source_obj.nextSibling.nextSibling : undefined/*=> create target*/)
        );    // "div#ajaxcontent"; 
        
    	/* If the commented ready functions and click event assignments are used then
          thise ajax get listing sheetdraft|exercise functions have to be called
          via function(//...)(); at the end of the site to assign the click event! */
        //$("#ajaxlink0" + ajaxlink_num).click(function(){
        	//alert('ajax upcoming');
	        $.ajax({
		        type: "GET",
		        url: "table.ajax.jsp",
		        //data: "semester="+par1+"&course="+ par2 + "&lecturer=" + par3 + "&type=" + par4 + "&filelink=" + par5,
		        data: "filelink=" + sheetdraft_filelink
		            + "&lecturer=" + lecturer
		            + "&session_user=<%=Global.getUserURLEncoded(session)%>"/*for exercise table.ajax.jsp!*/
		            + "&exercise_listing"
		            + "&ajaxlink_num=" + ajaxlink_num,
		        success: function(data){
		        	$(target_obj/*"#ajaxcontent"*/).html(data);
		            $("#loader").hide();
		            screenshotPreview(); //<- as of v31.13c this may be called as often as desired. (attach tooltip event)
		        }
	        });
	    
        //});


    //});
};



// As of v31.14 :
function ajax_action(action/*equal to ?q=<action>*/
		, table, filelink/*primary key*/, field, obj_with_old_value_, obj_with_new_value
		, event_source_obj, feedback_target_obj) {
	
    //alert('ajax_update' + ' table:' + table + ' filelink:' + filelink + ' field:' + field + ' old_value:' + old_value + ' new_value' + new_value);

    if (feedback_target_obj === undefined) {
		feedback_target_obj = source_obj;
	}
	

    $("#loader").show();
    $.ajax({
        type: "GET",
        url: "action.ajax.jsp",
        data: "q=" + action
            + "&table=" + table
            + "&filelink=" + filelink
            + "&field=" + 
            + "&value_old="+ value_old
            + "&value_new=" + value_new
//            + "&ajaxlink_num=" + ajaxlink_num
            + "&session_user=<%=Global.getUserURLEncoded(session)%>" /*for keeping track of changes!*/
        ,
        success: function(data) {
        	if (feedback_target_obj) 
            $(target_obj/*"div#ajaxcontent"*/).html(data);
        	// All is fine, we can update the fields:
        	$(obj_with_old_value).val(new_value);
        	$(obj_with_new_value).val(new_value);
            $("#loader").hide();
            screenshotPreview(); //<- as of v31.13c this may be called as often as desired.
        }
    });
};

//-->
</script>
<!-- rewritten May 2013
     Reason: old approach was not working for attribute 'value=""'.
             The 'value'-attribute was required because the inner html
             text was already used for displaying the field in the proper 
             language. The db is in English, hence the value bears the BE.
     Change: Using native javascript instead of jquery and giving callee.
-->
<script type="text/javascript">
//<!-- rewritten May 2013 -- it's ensured that if javascript is deactivated
//there will be shown all lists - if statically loaded -->
/**
 * 
 */
function showHideStaticLists(target) {
    /*2nd way -- statically built lists != built per ajax request*/
    
    /*ensure that only one is visible*/
    //1) hide all
    for (var obj in document.getElementById('ac').childNodes) {
        //obj.setAttribute("style", "display:none;");
        obj.style='display:none;';
        obj.style.display = 'none';
    }
    //2) show the one selected per default in target list
    var listobj = document.getElementById("start_list_" + target.value);
    if (listobj == null) {
        alert("listobj to id = " + "start_list_" + target.value + " is " + listobj);
    }
    if (!listobj.style || listobj.style.display == 'none') {
        listobj.setAttribute('style', 'display:block;');
        listobj.style.display = 'block';
    }
    
    return target;
}
/**
 * No longer required, simply reused the on document ready executed function.
 */
function get_ansicht() {
    //alert("target = " + target);
    //alert("<br />\n\rSelected value = " + target.options[target.selectedIndex].value);
    $(document).ready(function(){ /* <-- if document is not ready yet and the answer comes too quickly 
    	we might have a problem. On the other hand we could save some time ... */
        $("#loader").show();
        $.ajax({
            type: "GET",
            url: "tree.ajax.jsp",
            //data: "ansicht="+ this_obj.options[this_obj.selectedIndex].value,/*text();*/
            data: "ansicht="+ $("ansichtWrapper option:selected").val(),/*text();*/
            success: function(data) {
                //alert("get_ansicht: ajax success!!!!!");
                $("#ac").html(data);
                $("#loader").hide();
            }
        })/*()*/;//<--remove () if document ready is re-activated.
        
    });
    
    //showHideStaticLists(this_obj);TODO: only if static non-ajax solution
};


function addStylesheet(id, css) {
	css = css || 'td label img, td label + hr { max-width: inherit; display: inline; }';
	//document.write('<style id=\"imagesIncreaseSizeStyle\" type=\"text/css\">
	//}</style>');
	var head = document.head || document.getElementsByTagName('head')[0];
	var style = document.createElement('style');
	style.id = id || 'imagesIncreaseSizeStyle';
    style.type = 'text/css';
    if (style.styleSheet) {
    	style.styleSheet.cssText = css;
    }
    else {
    	style.appendChild(document.createTextNode(css));
    }
    head.appendChild(style);
}


function removeNode(objOrId) {
	if (typeof objOrId == 'string') {
		objOrId = document.getElementById(objOrId);
	}
	if (!objOrId) return false && log('Node was empty.');
	return objOrId.parentNode.removeChild(objOrId);
}
//-->
</script>
<%
String requested = "list_all_exercises";
if (request.getParameter("ansicht") != null/* && session.getAttribute("ansicht") != null
        && !session.getAttribute("ansicht").toString().equals(request.getParameter("ansicht"))
        <--TODO 2 extra comparisons for only occasionally saving one write process?*/) {
	session.setAttribute("ansicht", request.getParameter("ansicht"));
}
if (session.getAttribute("ansicht") != null) {
	requested = "" + session.getAttribute("ansicht");
}
%>


<!-- ANSICHT WRAPPER -->
<div id="ansichtWrapper">
<label id="ansichtLabel" for="ansicht"><%=Global.display("view") %>:</label><!-- Attention: time is important! Wait for ajax!! -->
<select id="ansicht" onchange="document.getElementById('defaultAnsicht').value=$('#ansicht option:selected').val()/*this.selected;*/;reloadView();/*revertTree()this is now after successful completion of ajax*/return false;">
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" value="semester"<%=Global.getIfEqualSelectedAttribute(requested, "semester")%>><%=Global.display("semester") %></option>
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" value="lecturer"<%=Global.getIfEqualSelectedAttribute(requested, "lecturer")%>><%=Global.display("lecturer") %></option>
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" value="course"<%=Global.getIfEqualSelectedAttribute(requested, "course")%>><%=Global.display("course") %></option>
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" disabled="disabled">-------</option>
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" value="list_all_exercises"<%=Global.getIfEqual(requested, "list_all_exercises", " selected=\"selected\" ") %> >
        <%=Global.display("list_all_exercises") %></option>
    <option onclick="document.getElementById('defaultAnsicht').value=this.value" value="list_all_own_exercises"<%=Global.getIfEqual(requested, "list_all_own_exercises", " selected=\"selected\" ") %> >
        <%=Global.display("list_all_own") /*+ " " + Global.display("own")*/ + " " + Global.display("exercises") %></option>
</select>
<form method="post" action="" style="display:inline-block">
    <input id="defaultAnsicht" type="hidden" name="ansicht" value="" /><!-- value is set on select. -->
    <button class="btn btn-secondary">&larr; Set as default for this session.</button>
</form>
<form method="get" action="" class="filterForm" onsubmit="return false;">
    <input id="filter" type="text" name="search" value="" placeholder="filter" />
</form>
<p>&nbsp;</p>
</div>
<div id="treecontrol"><!-- ScreenshotPreview is required as document.ready() come BEFORE the elements have loaded. -->
    <a id="collapse_all" title="<%=Global.display("minimize the whole tree") %>" onclick="screenshotPreview(); return false;" href="#"><img src="jquery.treeview/images/minus.gif" /></a>
    <a id="expand_all" title="<%=Global.display("expand the whole tree") %>" onclick="screenshotPreview(); return false;" href="#"><img src="jquery.treeview/images/plus.gif" /></a>
</div>



<!-- Baum -->

<div id="ajax" class="">
   <div id="ac" class="">
        <jsp:include page="tree.ajax.jsp" />
   </div>
   <div id="ajaxcontent" style="text-align:right;">&nbsp;
   
	    <!-- toggle images button -->
		<div id="toggleImagesWrapper" style="background-color: rgba(255, 255, 255, 0.5); z-index:1; margin-left:500px;/*margin instead of width:700px;/ *right: 0px;float:right;*/ padding: 5px; position: fixed; bottom: 0px;">
		    <select style="height:28px; visibility:hidden;"><option></option></select>
		    
		    <div class="btn btn-primary" style="float:right;" 
		            onclick="if (document.getElementById('toggleImages').checked) { addStylesheet('imagesIncreaseSizeStyle', 'tr label img { max-width:inherit; display:inline;}'); } else { removeNode('imagesIncreaseSizeStyle'); }">
		        
		        <label for="toggleImages" style="font-size:inherit; margin-bottom:0;">Image Preview?</label> 
		        <input id="toggleImages" style="margin:0;" type="checkbox" default="false" />
		    </div>
		</div>
		
   </div>



</div>




<script type="text/javascript">
//<!-- rewritten May 2013 -- it's ensured that if javascript is deactivated
//there will be shown all lists - if statically loaded -->
//<!--
reloadView = function(target_idOrObj){
    var source = /*(Object HtmlSelectElement)*/($("#ansicht"));
    target_idOrObj = target_idOrObj || "#ac";
    /*1st way -- ajax request*/
    /* var selectedIndex = -1;
    for (var i = 0; i < target.options.length; i++) {
        if (target.options[i].selected == true) {
            selectedIndex = i;
            break; //we have what we want
        }
    }
    if (selectedIndex == -1) {
        alert("no selection! selectedIndex = " + selectedIndex
                + "<br />Aborting ...");
    } */
    //alert("target = " + target);
    //alert("<br />\n\rSelected value = " + target.options[selectedIndex].value);
    $("#loader").show();
    $.ajax({
        type: "GET",
        url: "tree.ajax.jsp",
        //data: "ansicht=" + target.options[target.selectedIndex].value,
        data: "ansicht=" + $("#ansicht option:selected").val() 
                + "&session_user=<%= URLEncoder.encode((String)session.getAttribute("user"), "utf-8") %>"
            		,
        success: function(data){
            //alert("ajax success!!!!!");
            $(target_idOrObj/*"#ac"*/).html(data);
            $("#loader").hide();
            revertTree();
            //screenshotPreview();//for tooltip preview it is necessary to update after loading
            /*if the javascript for tooltip screenshot previews is included in the *.ajax.jsp
              then this is not required here; probably has no effect anyway.*/
            //additional content per ajax.
        },
        error: function (xhr, status) {
              switch (status) {
                 case 404:
                     alert('File not found');
                     break;
                 case 500:
                     alert('Server error');
                     break;
                 case 0:
                     alert('Request aborted');
                     break;
                 default:
                     alert('Unknown error: ' + status);
             } 
         }
    
    });
    
    
    /*2nd way -- statically built lists != built per ajax request*/
    //showHideStaticLists(target);
    
};
$(document).ready(reloadView());

revertTree = function(){

    //$.each($(".treeview"), function(obj) {
    //$("#ac").treeview({
    $("#becometreeviewnull").treeview({
                animated: "fast",
                collapsed: true,
                control: "#treecontrol"
    });


};
$(document).ready(revertTree());
    

//-->
</script>

<!-- upload -->
<div id="uploadWrapper" style="display:none; width:90%; height:90%; margin-left:45%; margin-top:45%; top:50%;left:50%; position:fixed;"
        onclick="document.getElementById('uploadWrapper').style.display='none'">
    <jsp:include page="upload_sheet.in.jsp" />
</div>
