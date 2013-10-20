
<%@page import="swp.*"%>
<!-- Tooltip -->
<script src="jquery/toolTipPreview.js" type="text/javascript"></script>
<!-- Treeview -->
<link rel="stylesheet" href="jquery.treeview/jquery.treeview.css" />
<script src="jquery.treeview/jquery.treeview.js" type="text/javascript"></script>
<!-- <script type="text/javascript" src="jquery.treeview/demo/demo.js"></script>-->
<script src="jquery.treeview/lib/jquery.cookie.js" type="text/javascript"></script>

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
function ajax_get_listing_sheetdrafts(filelink, semester,course,lecturer,type, ajaxlink_num) {
	//alert(typeof(par1));
	//alert(par1);
	$(document).ready(function(){
		
		$("#ajaxlink" + ajaxlink_num).click(function(){
			//alert(par1+par2+par3);
			$.ajax({
			type: "GET",
			url: "table.ajax.jsp",
			data: "filelink=" + filelink
				+ "&semester=" + semester +"&course="+ course
				+ "&lecturer=" + lecturer + "&type=" + type
				+ "&ajaxlink_num=" + ajaxlink_num,
			success: function(data){
			    $("#ajaxcontent").html(data);
			
			}
			});
			
		});
	
	
	});
};

/**
 * Loads a table containing all exercises of the lecturer.
 */
function ajax_get_listing_exercises(sheetdraft_filelink, lecturer /*par1,par2,par3,par4,par5*/, ajaxlink_num) {
	/* Lecturer is required because this saves us one join operation in table.ajax.jsp. */
	$(document).ready(function(){
		$("#ajaxlink0" + ajaxlink_num).click(function(){
		$.ajax({
		type: "GET",
		url: "table.ajax.jsp",
		//data: "semester="+par1+"&course="+ par2 + "&lecturer=" + par3 + "&type=" + par4 + "&filelink=" + par5,
		data: "filelink=" + sheetdraft_filelink + "&lecturer=" + lecturer
			+ "&session_user=<%=session.getAttribute("user")%>"/*for exercise table.ajax.jsp!*/
			+ "&exercise_listing"
			+ "&ajaxlink_num=" + ajaxlink_num,
		success: function(data){
			  $("#ajaxcontent").html(data);
		}
		});
	
	});


});
};
//-->
</script>
<!-- rewritten May 2013
     Reason: old approach was not working for attribute value.
             The value attribute was required because the inner html
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
	$(document).ready(function(){
		$.ajax({
			type: "GET",
			url: "tree.ajax.jsp",
			//data: "ansicht="+ this_obj.options[this_obj.selectedIndex].value,/*text();*/
			data: "ansicht="+ $("ansichtWrapper option:selected").val(),/*text();*/
			success: function(data) {
				//alert("get_ansicht: ajax success!!!!!");
				$("#ac").html(data);
			}
		});
	});
	
	//showHideStaticLists(this_obj);TODO: only if static non-ajax solution
};
//-->
</script>


<div id="ansichtWrapper">
<label for="ansicht">Ansicht:</label><!-- Attention: time is important! Wait for ajax!! -->
<select id="ansicht" onchange="reloadView();/*revertTree()*/return false;">
	<option value="semester" selected="selected">Semester</option>
	<option value="lecturer">Dozent</option>
	<option value="course">Veranstaltung</option>
</select>
<p>&nbsp;</p>
</div>
<div id="treecontrol">
	<a title="Den ganzen Baum minimieren" href="#"><img src="jquery.treeview/images/minus.gif" /></a>
	<a title="Den ganzen Baum ausklappen" href="#"><img src="jquery.treeview/images/plus.gif" /></a>
</div>



<!-- Baum -->

<div id="ac" class="">
    <jsp:include page="tree.ajax.jsp" />
   
</div>


<!--  
<ul id='ac' class="treeview">
	<li>Item1
		<ul>
			<li>I1.1
				<ul>
					<li>Item1.1.1</li>
				</ul>
			</li>
			<li>I1.2</li>
		</ul>
	</li>
	
	<li>Item2
		<ul>


			<li>I2.1</li>
		</ul>
	</li>
</ul>
-->


<script type="text/javascript">
//<!-- rewritten May 2013 -- it's ensured that if javascript is deactivated
//there will be shown all lists - if statically loaded -->
//<!--
reloadView = function(){
	var target = /*(Object HtmlSelectElement)*/($("#ansicht"));
	
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
	$.ajax({
		type: "GET",
		url: "tree.ajax.jsp",
		//data: "ansicht=" + target.options[target.selectedIndex].value,
		data: "ansicht=" + $("#ansichtWrapper option:selected").val(),
		success: function(data){
			//alert("ajax success!!!!!");
			$("#ac").html(data);
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

