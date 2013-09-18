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
	$("a.screenshot")
			.hover(
					function(e) {
						this.t = this.title;
						this.title = "";

						var c = (this.t != "") ? "<br/>" + this.t : "";
						$("body").append(
								"<p id='screenshot'><img name = 'bild' src='"
										+ this.rel + "' alt='url preview' />"
										+ c + "</p>");
						var Bildhoehe;
						// var z=Bildhoehe-((window).innerHeight-e.pageY);
						var z = 0;
						Bildhoehe = document.images.bild.height;

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
						$("#screenshot").css("top", z + "px").css("left",
								(e.pageX + yOffset) + "px").fadeIn("fast");
					}, function() {
						this.title = this.t;
						$("#screenshot").remove();
					});
	$("a.screenshot").mousemove(function(e) {
		$("#screenshot")
		// .css("top",(e.pageY - xOffset) + "px")
		.css("left", (e.pageX + yOffset) + "px");
	});
};

// starting the script on page load
$(document).ready(function() {
	screenshotPreview();
});