/*
 * Url preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 
this.screenshotPreview = function() {
	/* CONFIG */

	xOffset = 10;
	yOffset = 30;

	// these 2 variable determine popup's distance from the cursor
	// you might want to adjust to get the right result

	/* END CONFIG */
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
	$("a.screenshot")
			.hover(
					function(e) {
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
					}
					,
					function() {
						this.setAttribute('title', this.t);
						$targetWrapper.hide();//.remove();
					}
			);
	$("a.screenshot").mousemove(function(e) {
		$("#screenshot")
		// .css("top",(e.pageY - xOffset) + "px")
		.css("left", (e.pageX + yOffset) + "px");
	});
	$("#screenshot").hover/*mousemove*/(function(e) { $("#screenshot").hide(); /*$("#screenshot").src='';*/ });
};

// starting the script on page load
$(document).ready(function() {
	screenshotPreview();
});