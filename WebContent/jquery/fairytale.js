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
	
	// http://stackoverflow.com/questions/5999118/add-or-update-query-string-parameter
	function UpdateQueryString(key, value, url) {
	    if (!url) url = window.location.href;
	    var re = new RegExp("([?&])" + key + "=.*?(&|#|$)(.*)", "gi");

	    if (re.test(url)) {
	        if (typeof value !== 'undefined' && value !== null)
	            return url.replace(re, '$1' + key + "=" + value + '$2$3');
	        else {
	            var hash = url.split('#');
	            url = hash[0].replace(re, '$1$3').replace(/(&|\?)$/, '');
	            if (typeof hash[1] !== 'undefined' && hash[1] !== null) 
	                url += '#' + hash[1];
	            return url;
	        }
	    }
	    else {
	        if (typeof value !== 'undefined' && value !== null) {
	            var separator = url.indexOf('?') !== -1 ? '&' : '?',
	                hash = url.split('#');
	            url = hash[0] + separator + key + '=' + value;
	            if (typeof hash[1] !== 'undefined' && hash[1] !== null) 
	                url += '#' + hash[1];
	            return url;
	        }
	        else
	            return url;
	    }
	}