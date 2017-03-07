/*
 * Url preview script 
 * powered by jQuery (http://www.jquery.com)
 * based on work by Alen Grakalic
 * http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 * 
 * Completely rewritten by J.R.I.B.-Wein for
 *  - higher reliability,
 *  - multiple times in a row execution without breaking,
 *  - less jQuery dependence,
 *  - hiding,showing the preview instead of adding,removing html on each call
 *
 */
var aClassScreenshotMouseMove_callback = function(e) {
		$("#screenshot")
		// .css("top",(e.pageY - xOffset) + "px")
		.css("left", (e.pageX + yOffset) + "px");
		$("#screenshot").show();
};

var aClassScreenshotMouseOver_callback = function(e) {
	//J.R.I.B.-Wein: to not have to read from the DOM object's title? 
	this.t = this.title;
	this.title = "";
	$("#preview_image_label").html(this.t);
	
	var target = document.getElementById('screenshot_img');//<- since v31.13c
	$targetWrapper = $("#screenshot");
	if (!$targetWrapper) {
	}
	target.setAttribute('src', this.rel); 
	var Bildhoehe;
	// var z=Bildhoehe-((window).innerHeight-e.pageY);
	var z = 0;
	Bildhoehe = $("#screenshot_img").height() || document.images.bild.height;

	if (e.pageY <= Bildhoehe + scrollY) {
		// alert("1");
		z = scrollY;

	} else if (e.pageY + Bildhoehe + 10 > (window).innerHeight + scrollY) {

		z = (window).innerHeight - Bildhoehe - 30 + scrollY;
		// alert("2");
	} else {
		// alert("3");
		z = e.pageY - Bildhoehe / 2;
		// alert("hallo");
	}

	// scrollY + (e.pageY - Bildhoehe (window).innerHeight)
	// z<Bildhoehe) ? e.pageY-Bildhoehe/2+ "px" : e.pageY-z
	$targetWrapper.css("top", z + "px").css("left",	(e.pageX + yOffset) + "px")
			.fadeIn("fast");
	//$targetWrapper.show();//<-as of v31.13c
	
/*}
,
function() {*/
	this.setAttribute('title', this.t);
	//$("#screenshot").hide();//.remove();
};


/**
 * 
 */
var hideScreenshot = function(e) { 
	$("#screenshot").hide();
	/*$("#screenshot").src='';*/ 
};


var executedCount = 0;
var screenshotPreview = function() {
	/* CONFIG: popup distance from the cursor. */
	xOffset = 10;
	yOffset = 30;
	/* CONFIG -END */
	
	//if the if is omitted then this can be used as an indicator for how often this init function is called 
	if (!document.getElementById('screenshot')) { //all since v31.13c.
		var c = ( (this.t && this.t != "") ? this.t : "" );
		$("body").append( 
				"<div id='screenshot' style='z-index:1111;'>"
				+ "<img id='screenshot_img' name='bild' src='"
				/*+ this.rel*/ + "' alt='url preview' />"
				+ "<p id='preview_image_label'>" + c + "</p>"
				+ "</div>"
		);
	}
	
	
	// just for information:
	if (executedCount > 0) {
		//alert('Executed screenshotPreview ' + (executedCount + 1) + ' times');
	} 
	else if (executedCount < 1) {
		$("#screenshot").hover/*mousemove*/(function(e) { $("#screenshot").hide(); /*$("#screenshot").src='';*/ });
		//$(document).mousemove(setTimeout(hideScreenshot, 1000));
		addEvent("screenshot", 'mouseout', hideScreenshot);
	}
    ++executedCount;
    
    
	$("a.screenshot").each(function(i, el) {
		rmEvent(el, 'mouseover', aClassScreenshotMouseOver_callback);//<-- this way we never have multiple events attached.
		addEvent(el, 'mouseover', aClassScreenshotMouseOver_callback);
//	$("a.screenshot").hover( aClassScreenshotMouseOver_callback	);
	
		rmEvent(el, 'mouseout', hideScreenshot);
		addEvent(el, 'mouseout', hideScreenshot);
	
		rmEvent(el, 'mousemove', aClassScreenshotMouseMove_callback); //<-- this way we never have multiple events attached.
		addEvent(el, 'mousemove', aClassScreenshotMouseMove_callback);
		addEvent(el, 'mousemove', aClassScreenshotMouseMove_callback);
//	$("a.screenshot").mousemove(aClassScreenshotMouseMove_callback);
	} );
	
	
	
	
};

// starting the script on page load
$(document).ready(function() {
	screenshotPreview();
});
