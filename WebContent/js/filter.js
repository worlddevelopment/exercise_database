var filterInput;



var filterInputFields_ids = new Array;
filterInputFields_ids[0] = "search_string";
filterInputFields_ids[1] = "filter";
//var isEventAttached = new Object;

var filter = function(filterInput_) {

	//alert("reached filter .. filterInput_:" + filterInput.id)
	if (filterInput_) {
		filterInput = filterInput_;
		/*no event has to be attached because if this is reached it already is attached*/
	}
	if (!filterInput || filterInput.value == '' || filterInput.value == filterInput.placeholder) {
		filterInput = document.getElementById("search_string");
	}
	if (!filterInput || filterInput.value == '' || filterInput.value == filterInput.placeholder) {
		filterInput = document.getElementById("filter");
	}
	//still no filterInput object?
	if (!filterInput) {
		return false;
	}

	var elementsToSteerParent = document.getElementById('becometreeviewnull');
	if (!elementsToSteerParent) {
		elementsToSteerParent = document.getElementById('exercise-listing')/*table*/.children[1];//.lastChild/*tbody*/;
	}
	if (!elementsToSteerParent) {
		elementsToSteerParent = document.getElementById('exercise-listing')/*table*/;
	}
	/*if (!elementsToSteerParent) {
		elementsToSteerParent = document.getElementById('ac');
	}
	if (!elementsToSteerParent) {
		elementsToSteerParent = document;
	}<-- too big an impact as always the highest level is being collapsed.*/
	if (!elementsToSteerParent.textContent) {
		//var elementsToSteer = elementsToSteerParent.getElementsByTagName('li');
	}
	//elementsToSteer = $('#ac > li');// #becometreeviewnull');
	//alert(elementsToSteer + " length: " + elementsToSteerParent.childNodes.length);
	//for (element in elementsToSteerParent.children) {
	//for (var i = 0; i < elementsToSteer.length; i++) {
	for (var i = 0; i < elementsToSteerParent.children.length; i++) {//we use the filtered set children now instead of unfilter childNodes.
		//element = elementsToSteer[i];
		element = elementsToSteerParent.childNodes[i];
		if (typeof(element) != 'object') {
			//alert('skipped: ' + typeof(element) + " ["+element+"]");
			continue;
		}
		//alert(elementsToSteer/*Parent*/ + " node:" + element);
		var textOnly;
		textOnly = element.innerHTML;
		if (!textOnly) {
			textOnly = element.textContent;
		}
		/*if (!textOnly) {
			textOnly = element.value;
		}
		if (!textOnly) {
			textOnly = element.nodeValue;
		}*/
		/*ABOUT INNERHTML and TEXTCONTENT. INNERHTML IS ONLY A SHORTCUT.
		This is how innerHtml can be replaced without innerHTML:
		var abc = document.getElementById('abc');
		while(abc.firstChild) abc.removeChild(abc.firstChild);
		abc.appendChild(document.createTextNode("good morning"));
		//http://stackoverflow.com/questions/19586797/textnode-or-innerhtml
		*/
		if (!textOnly) {
			alert(textOnly + ' attribute of element: ' + element);
		}


		/*------- CONDITION COMBINATIONS AND VARIANTS -------*/
		var isConditionMet = false;
		//options/settings:
		var filter_settings = { case_sensitive: false };

		//OR .  /*do this first because AND has higher precedence/importance*/
		var ors = filterInput.value.split(' . ');
		//space . space because otherwise numbers' commas or endings may be met.

		var ors_i = -1;
		while (++ors_i < ors.length) {// && !isConditionMet/*!isResultEstablished*/) {
			//alert(ors_i + ' < ' + ors.length + ' : ' + ors[ors_i]);
			//AND ,
			var ands = ors[ors_i].trim().split(' , ');

			var ands_index = -1;
			var isContainingAllAndedFilterCriteria = true; // assumption all ands are true.
			//Examine if any of the AND criterias is NOT met:
			while (++ands_index < ands.length) {
				//alert(ands_index + ' < ' + ors.length + ' : ' + ands[ands_index]);

				// Does not contain the search criteria in any variant (case sensitive, insensitive, ..)?

				/* TODO if this is being reassembled this line will change the outcome to something
				   unexpected (without spaces).
				*/
				ands[ands_index] = ands[ands_index].trim();
				var versions = [
						ands[ands_index]
						, ands[ands_index].replace(' ', '_')
						, ands[ands_index].replace('_', ' ')

				];

				if (!filter_settings.case_sensitive) {
					versions[versions.length] = ands[ands_index].toLowerCase();
					versions[versions.length] = ands[ands_index].toUpperCase();
				}
				var versions_i = -1;
				var hasAtLeastOneVariantMet = false; //=> it's an OR condition.
				while (++versions_i < versions.length) {

					if ( textOnly.indexOf(versions[versions_i]) != -1 ) { //<--indexOf instead of contains for cross-browser-compatibilityy.
						hasAtLeastOneVariantMet = true;
					}

				}
				// Has no variant been contained?
				if (!hasAtLeastOneVariantMet) {
					//Then the content of this and criteria is not contained in this xml subtree!
					isContainingAllAndedFilterCriteria = false;
					break;//continue with next or, yet another chance that at least the next criteria is fulfilled.
				}
				//the first that is successful will prevent the following being examined too.
			}

			//Has this OR's collection of AND criteria already been entirely fulfilled?
			if (isContainingAllAndedFilterCriteria) {
				//okay then it's already fulfilled. no need to continue.
				isConditionMet = true;//we found all and criteria's content. => found.
				break;
			}
			//else next round / next OR's collection of AND criteria.

		}


		//if (!textOnly.contains(filterInput.value)) {
		if (!isConditionMet) {
			//alert('display:none;');
			if (element.style) {
				element.style.display = 'none';
			}
			else {
				element.setAttribute('style', 'display:none;');
			}
		}
		else {
			//alert('display:inline');
			if (element.style) {
				element.style.display = '';//'inline';
			}
			else {
				element.setAttribute('style', ''/*display:inline;'*/);
			}
		}

	}
};

(function() {

	for (var i = 0; i < filterInputFields_ids.length; i++) {

		filterInputField_id = filterInputFields_ids[i];
		filterInput = document.getElementById(filterInputField_id);
		//alert('reached anon id:' + filterInputField_id + ' filterInput: ' + filterInput);

		if (filterInput) {
			addEvent(filterInput
					, 'keyup'
					, (function() {
						//alert("reached fairytales filterInput: " + filterInput.id);
						//with this fairytale magic, we now can give parameters too:
						filter(filterInput);

					})
					, false
			);
			//isEventAttached[filterInput/*.id + 'keyup'*/] = true;
		}

	}
})();
