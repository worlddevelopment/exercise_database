	/*Excerpt from dragontale's fairytale.js, one of my private projects.*/
	// ADD EVENT
	function addEvent(el, event, func, mode) {
	    event = (typeof(event) != 'string') ? event+'' : event;  // prep
	    mode  = mode || false;                                   //allowTriggerUntrusted=false
	    //
	    if (typeof(el) != 'object') {
	        el = document.getElementById(el);
	    }
	    //
	    if (el.addEventListener) {                             // Firefox, Iceweasel,
	        el.addEventListener(event, func, mode);            // Google Chrome, Netscape, 
	    }                        
	    else if (el.attachEvent) {          
	        el.attachEvent('on'+event, func);                  // IE
	    }
	    else alert ('Your browser doesn\'t support important features of this page.\n'
	                  +'Please try the newest Firefox, Opera, Safari or Google Chrome version'
	                  +' or any equal browser ...');
	    //
	    return el;
	}
	// RM EVENT
	function rmEvent(el, event, func, mode) {
	    mode  = mode || false;                                 //allowTriggerUntrusted=false
	    //
	    if (typeof(el) != 'object') {
	        el = document.getElementById(el);
	    }
	    //
	    if (el.removeEventListener) {                          // Firefox, Iceweasel,
	        el.removeEventListener(event, func, mode);         // Google Chrome, Netscape, 
	    }                        
	    else if (el.detachEvent) {          
	        el.detachEvent('on'+event, func);                  // IE
	    }
	    //
	    return el;
	}
	// REMOVE EVENT
	function removeEvent(el, e, f, mode) {
	    return rmEvent(el, e, f, mode);
	}