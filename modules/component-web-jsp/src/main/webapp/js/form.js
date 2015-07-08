/**
	Vormiga seotud toimingud 
*/

/* lehe filtri seisu hoidla  */
var epmPageFormDataHolder = {};

/* angular */

app.directive('elementEvent', function() {
	return function(scope, element, attrs) {
		angularCheckElements(scope, element, attrs);
		};
	});

function angularCheckElements(scope, element, attrs) {
	var el = $(element);
	restrictNumericFieldInput(el, attrs);
	
	if (attrs.useAjaxValidation == "true") {
		changeValidate(el);
	}
}

/* //angular */

$(function() {
	addDateFieldMask($(document));
	addElementEnterHandling($(document));
	reFillFormDataFields();
});

if (typeof(Storage) !== "undefined") {
	loadPageFormData();
}

/* filter */
function reFillFormDataFields(){
	
	var path = location.pathname;
	var checkPath = contextPath + "/" + activeLang;
	
	if(path == checkPath || (document.referrer && document.referrer.indexOf(contextPath)<=0)){
		// tühistame varasema seisu, kui tegu on uue sisselogimisega või kui päring tuli sisse mõnelt teiselt lehelt (CAS), mis tähendab, et lehe rajaks on /contextPath/activeLang
		sessionStorage.epmPageFormData = JSON.stringify({});
		loadPageFormData();
	}
	
	var data = epmPageFormDataHolder[path.hashCode()];
	if(data){
		var lastEl = false;
		$.each(data, function(name, value) {
			var el = $(document.getElementsByName(name)[0]);
			el.val(value);
			lastEl = el;
		});
		
		if(lastEl){
			invokeEnterEvent(lastEl);	
		}
	}
}

function addFormDataToHolder(data){
	epmPageFormDataHolder[location.pathname.hashCode()] = data;
	sessionStorage.epmPageFormData = JSON.stringify(epmPageFormDataHolder);
}

function loadPageFormData(){
	if(typeof(sessionStorage.epmPageFormData) === "undefined"){
		sessionStorage.epmPageFormData = JSON.stringify({});
	}
	
	epmPageFormDataHolder = jQuery.parseJSON(sessionStorage.epmPageFormData);	
}
/* //filter */


function addElementEnterHandling(mainElement){
	mainElement.find("input").bind("keypress.enter", function(event) {
		if (event.which == 13) {
			// kui on tegu enter klahvi vajutusega ning komponendi vorm eksisteerib siis postitame vormi sisu, kasutades selleks submit nupu funktsionaalsust
			var el = $(event.target);
			
			if(el.hasClass("autocomplete")){
				return;
			}
			return invokeEnterEvent(el);
		}
	});
}


function invokeEnterEvent(el){
	var form = el.closest("form");
	if(form.length > 0){
		// otsime kas tabeli real eksisteerib submit nupp, kui eksisteerib, siis käivitame selle, tegemist on filtri reaga
		var submitEl = el.closest("tr").find("button[value='Submit']");
		if(submitEl.length > 0){
			el.attr("restrictViolation", true);
			submitEl.trigger("click");
			return false;
		} else {
			var curTabindex = parseInt(el.attr("tabindex"));
			if(curTabindex==0){
				return false;
			}
			
			var modalPopUp = el.closest(".modalpopup");
			
			var nextElement = modalPopUp.find('[tabindex=' + (curTabindex + 1) + ']');
			if (nextElement.length) {
				nextElement.focus();
			} else {
				// liigume tagasi esimesele väljale
				nextElement = modalPopUp.find('[tabindex]');
				if (nextElement.length) {
					modalPopUp.find('[tabindex=' + nextElement.attr('tabindex') + ']').focus();
				}
			}
			return false;
		}
	}
}

function changeValidate(el){
	el.bind("change.validate", function() {
		
		if(el.attr("restrictViolation")){
			return;
		}
		
		var params = {};
		injectAjaxEventParams(params, 'validate', el.attr('id'), "");
		var elValue;
		
		if(el.attr("data-element-type")=="boolean"){
			elValue = el.is(':checked');
		}else {
			elValue = el.val();
		}
		
		validateElementValue(el, elValue);
	});
}

function validateElementValue(el, elValue){
	
	// kui väga suur, siis ei valideeri, las POST telegeb sellega
	if(elValue && !$.isArray(elValue) && elValue.length > 10000000){
		return;
	}
	
	var params = {};
	var id = el.attr('data-element-event');

	if(!id){
		id = el.attr('id');
	}
	injectAjaxEventParams(params, 'validate', id, "");
	params[el.attr('name')] = elValue;
	if($.isArray(elValue)){
		params = paramsToGetParams(params);
	}
	sendAjaxRequest(params,
			function(response) {
				setRemoveFieldError(el);
				showMessages(response);
			});
}

/**
 * Lisame elementidele millel on küljes klass .date juurde maski, mis lubab sisestada ainult numbreid
 */
function addDateFieldMask(el){
	var elements = el.find(".date:not(input[type='hidden'])");
	elements.mask("99.99.9999");
	elements.bind("blur.datechanged",
		function() {
			dateTimeChangeEvent(this.id);	
		});
}


function restrictNumericFieldInput(el, attrs){
	var min = attrs.min;
	var max = attrs.max;
	
	if(attrs.elementType != "long" && attrs.elementType != "integer" && attrs.elementType != "double"){
		return;
	}
	
	if(attrs.elementType == "double"){
		el.numeric({ decimal : "," });
		min = parseFloat(min);
		max = parseFloat(max);
	}else {
		el.numeric({decimal : false});
		min = parseInt(min);
		max = parseInt(max);
	}
	
	el.change(function() {
		el.removeAttr("restrictViolation");
		var val = el.val().replace(",", ".");
		if(val==""){
			return;
		}
		if(val > max){
			setRemoveFieldError(el, getEpmLangText("global.error.field.value-is-to-high"));
			el.attr("restrictViolation", true);
			el.val(max);
		}
		if(val < min){
			setRemoveFieldError(el, getEpmLangText("global.error.field.value-is-to-low"));
			el.attr("restrictViolation", true);
			el.val(min);
		}
	});
}

/**
 * Funkstioon on kasutusel kuupäeva elemendi juures. Tegemist on abistava funktsiooniga, 
 * mis täidab vastavalt kasutaja valikutele ära varjatud välja.
 * @param id
 * @returns {Boolean}
 */
function dateTimeChangeEvent(id){
	id = escapeId(id.replace("-date", ""));
	var el = $('#' + id);
	var date = $('#' + id + "-date");
	var hour = $('#' + id + "-hour");
	var minute = $('#' + id + "-minute");
	
	var result = "";
	if(date.length > 0){
		result = date.val();
	}
	
	if(hour.length > 0 && minute.length > 0){ 
		if(date.length > 0){
			result += " "; 
		}		
		result += hour.val() + ':' + minute.val();
	}
	
	el.val(result);
	el.trigger('change');
	
	return true;
}