var ANGULAR_MODULE_NAME = "epmModule";

var app = angular.module(ANGULAR_MODULE_NAME, []);
var epmPageScrollKey = "epm_page_scroll";
var epmSessionCheckKey = "epm_session_check";

var IEVersion = (!! window.ActiveXObject && +(/msie\s(\d+)/i.exec(navigator.userAgent)[1])) || NaN;

app.directive('bindHtmlUnsafe', function( $parse, $compile ) {
	// Funktsioon tegeleb erineva HTML sisu uuendamisega, sidudes uuesti ära ka erinevad JS sündmused
    return function( $scope, $element, $attrs ) {
        var compile = function( newHTML ) {
            newHTML = $compile(newHTML)($scope);
            $element.html('').append(newHTML);
            
            // lisame JS funktsioonid
            addDateFieldMask($element);
			addElementEnterHandling($element);
			setDatepicks($element);
        };
        
        var htmlName = $attrs.bindHtmlUnsafe;
        
        $scope.$watch(htmlName, function( newHTML ) {
            if(!newHTML){
            	return;
            }
            compile(newHTML);
        });
    };
});

/* juba välja kuvatud teadete hoidla */
var messagesHolder = [];

var param_epm_evt = "epm_evt";
var param_epm_evt_target = "epm_evt_target";
var param_epm_evt_param = "epm_evt_param";
var param_epm_evt_ajax = "epm_evt_ajax";
var param_epm_evt_region = "epm_evt_region";

$(function() {
	if(errorMessagesNotExist()) {
		moveScrollToLastLocation();
	}
});

function errorMessagesNotExist() {
	return $('#messages .msg-error, #messages .msg-warning').length == 0;
}

function myCustomOnChangeHandler(inst) {
    alert("Some one modified something");
    alert("The HTML is now:" + inst.getBody().innerHTML);
}

tinyMCE.init({ 
	mode : "specific_textareas",
	editor_selector : /(mceRichText)/,
	relative_urls : false,
	convert_urls: false,
	menubar: false,
    statusbar: false,
    language: activeLang,
    plugins : 'autoresize link',
    setup: function(editor) {
        editor.on('change', function(e) {
        	validateElementValue($('#' + escapeId($(e.target).attr('id'))), e.level.content);
        });
    }
});


function moveScrollToLastLocation(){
	var lastLocation = localStorage.getItem(epmPageScrollKey);
	if(lastLocation){
		lastLocation = $.parseJSON(lastLocation);
		if(lastLocation.location == window.location.href){
			$(document).scrollTop(lastLocation.scrollTop);
		}
	}
}

/**
 * Välja veateadete eemaldamine või lisamine
 * @param element
 * @param error
 */
function setRemoveFieldError(element, error){
	element.removeClass("error");
	   
	var label = getJQueryElementById(element.attr('id') + "_label");
	if(label.length>0){
	   label.removeClass("error");
	   label.parent().removeClass("error");
	}
	
	var msg = getJQueryElementById(element.attr('id') + "_error");
	if(msg.length>0){
	   msg.remove();
	}
	
	if(error){
		if(label.length>0){
			 label.addClass("error");
			 label.parent().addClass("error");
		}
		
		element.addClass("error");
		
		var p = $("<p>").attr("id", element.attr('id') + "_error").html(error);
		p.addClass("error");
		element.parent().append(p);
	}
}

/**
 * Varjatud välja lisamine.
 * @param name
 * @param value
 * @param form
 */
function addHiddenField(name, value, form){
	var id = convertPathToDisplayId(name);
	var dataHolder = form ? form : getModelComp();
	
	dataHolder.find("#"+escapeId(id)).remove();	
	
	var input = $("<input>").attr("type", "hidden").attr("name", name).attr("id", id).val(value);	
	
	if($(document).find(dataHolder).length == 0){
		$("body").append(dataHolder);
	}
	
	dataHolder.append(input);
}

function getModelComp(){
	return $("#"+modelCompName);
}

/**
 * Submit päringu koos event parameetritega käivitamine. Lisaks jäetakse meelde kasutaja asukoht ekraanil
 * Parameeter caller sisaldab endas sündmuse välja kutsunud elementi. Hetkel funkstiooni sees antud parameetrit ei kasutata, 
 * kuid lähitulevikus võib selleks tekkida vajadus 
 * @param caller
 * @param name
 * @param targetComp
 * @param parameter
 * @param form 
 * @returns {Boolean}
 */
function epmEventSubmit(caller, name, targetComp, parameter, form) {
	
	startLoader();
	
	var sendFormDataOnEvent = true;
	if($(caller).length){
		var attr = $(caller).attr("data-send-form-data-on-event");
		sendFormDataOnEvent = attr == undefined || attr == 'true';
	}
	
	localStorage.setItem(epmPageScrollKey, '{"location" : "' + window.location.href + '", "scrollTop" : "' + $(document).scrollTop() + '"}');
	
	var sendForm;
	
	if(sendFormDataOnEvent){		
		sendForm = form ? form : getModelComp(); 
		addHiddenField(param_epm_evt, name, sendForm);
		addHiddenField(param_epm_evt_target, targetComp, sendForm);
		addHiddenField(param_epm_evt_param, parameter, sendForm);
	}else {
		sendForm = form ? form : $("<form>").attr("method", "post"); 
		addHiddenField(param_epm_evt, name, sendForm);
		addHiddenField(param_epm_evt_target, targetComp, sendForm);
		addHiddenField(param_epm_evt_param, parameter, sendForm);
	}
	
	if($(document).find(sendForm).length == 0){
		$("body").append(sendForm);
	}
		
	
	downloadTimer = window.setInterval( function() {
		var token = $.cookie("downloadToken");
		if(token == 'true') {
			window.clearInterval(downloadTimer);
			$.removeCookie('downloadToken');
			closeLoader();
		}
	}, 1000 );
	sendForm.submit();
	return false;
}

function escapeId(selector) {
    return selector.replace(/(:|_)/g, function($1, $2) {
    	return "\\" + $2;
    });
} 

/**
 * Konverteerime elemendi ID springi jaoks vajalikule kujule
 * @param displayId
 * @returns
 */
function convertDisplayIdToPath(displayId) {
	if (displayId == null){
		return null;
	}
	return displayId.replace(/(---([^.]+?)---)/g, "['$2']").replace(/(--([^.]+?)--)/g, "[$2]").replace(/[_]/g, ".");
}

function convertPathToDisplayId(path){
	if (path == null){
		return null;
	}
	return path.replace(/(\[')|('])/g, "---").replace(/[\[\]]/g, "--").replace(/[\.]/g, "_");
}

/**
 * Funktsioon kutsutakse välja modaal dialoog aknas kinnitamisel
 * @param caller
 * @param targetCompd
 * @returns
 */
function modalDialogSubmit(caller,targetCompd){
	return epmEventSubmit(caller, "submitModalDialogAccept",targetCompd);	
}

function modalEventSubmit(caller, targetComponent, event) {
	return epmEventSubmit(caller, event, targetComponent);
}

/**
 * Funktsioon kutsutakse välja igasugustel submit sündmuste puhul
 * @param caller
 * @param targetCompd
 * @returns
 */
function sendOnClickEvent(caller,targetCompd){
	return epmEventSubmit(caller, "submitOnClick",targetCompd);
}

/**
 * Funktsioon kutsutakse välja onChange eventi puhul
 * @param caller
 * @param targetCompd
 * @returns
 */
function sendOnChangeEvent(caller,targetCompd){
	return epmEventSubmit(caller, "submitOnChange",targetCompd);
}

/**
 * Funktsioon kutustakse välja modaal akna sulgemisel
 * @param caller
 * @param targetCompd
 * @returns
 */
function modalDialogClose(caller,targetCompd){
	return epmEventSubmit(caller, "submitModalClose",targetCompd);
}

/**
 * Funktsioon kutustakse välja modaal akna katkestamisel
 * @param caller
 * @param targetCompd
 * @returns
 */
function modalDialogCancel(caller,targetCompd){
	return epmEventSubmit(caller, "submitModalCancel",targetCompd);
}

function changeKlient(id) {
	addHiddenField("changeKlientId", id);
	return epmEventSubmit(null, "changeKlientEvent");	
}

function toggleVisibility(targetElement, targetElement2) {
	$('#' + targetElement).toggle();
	if ($('#' + targetElement + 'Link').is(':hidden')) {
		$('#' + targetElement).removeClass('active');
	} else {
		$('#' + targetElement).addClass('active');
	}
	$('#' + targetElement2).toggle();
	if ($('#' + targetElement2 + 'Link').is(':hidden')) {
		$('#' + targetElement2).removeClass('active');
	} else {
		$('#' + targetElement2).addClass('active');
	}
}

/**
 * Meetod leiab rekursiivselt üles kindla elemendi kindla IDga
 * @param elements
 * @param id
 * @returns
 */
function findChildById(elements, id){
	for ( var nr in elements) {
		var element = elements[nr];
		if(element.nodeType != 1){
			continue;
		}
		
		if(element.id == id){
			return element;
		}else if(element.hasChildNodes()){
			var find = findChildById(element.childNodes, id);
			if(find!=undefined){
				return find;
			}
		}	
	}
}

/**
 * Meetod leiab rekursiivselt üles kindlad elemendid kindla klassiga
 * @param elements
 * @param elClass
 * @param childs
 * @returns
 */
function findChildsByClass(elements, elClass, childs){
	
	for ( var nr in elements) {
		var element = elements[nr];
		if(element.nodeType != 1){
			continue;
		}
		
		for ( var attrNr in element.attributes) {
			var attr = element.attributes[attrNr];
			if(attr == undefined){
				continue;
			}
			if(attr.nodeName == 'class' && attr.nodeValue == elClass){
				childs.push(element);	
			}
		}
		
		if(element.hasChildNodes()){
			var find = findChildsByClass(element.childNodes, elClass, childs);
			if(find!=undefined){
				return find;
			}
		}	
	}
}

/**
 * Modaal dialoog akna näitamine
 * @param properties
 */
function showConfirmationModalDialog(properties){
	
	$('#' + escapeId(properties.displayId)).remove();
	
	var divMain = $("<div>").attr("id", properties.id).attr("class", "modaldialog");
	var h1 = $("<h1>").html(getEpmText(properties.caption));
	divMain.append(h1);
	var p;
	
	if(properties.content){
		p = $("<p>").html(getEpmText(properties.content));
		divMain.append(p);
	}
	
	var divAction = $("<div>").attr("class", "action clear");
	
	if(properties.modalButtons.buttons.length>0){
		p = $("<p>").attr("class", "main");
		
		$.each(properties.modalButtons.buttons, function(index, btn) {
			  var button = $("<button>").attr("class", "button").html(getEpmText(btn.element.label));
			  button.attr("onclick", "modalEventSubmit(this, '" + btn.element.parentDisplayId +"', '" + btn.element.eventName + "');hideModal();return false;");
			  p.append(button);
		});
		
		divAction.append(p);
	}
	
	p = $("<p>").attr("class", "alt");
	
	var a = $("<a>").attr("class", "cancel").attr("href", "#");
	a.attr("onclick", "return modalDialogCancel(this, '" + properties.displayId +"',  '" + properties.componentName +"');hideModal();");
	a.html(getEpmText("modal.dialog.cancel"));
	p.append(a);
	
	divAction.append(p);
	
	divMain.append(divAction);
	$("#modalwrap").append(divMain);
	
	showModal(properties.id, properties.sizeStyleClass);
}

function showMessages(response){
	
	if(response.fieldErrors){
		for ( var errorKey in response.fieldErrors) {
			setRemoveFieldError(getJQueryElementById(errorKey), response.fieldErrors[errorKey]);
		}	
	}
		
	var messages = response.messages;
	
	if(!messages){
		return;
	}
	for ( var type in messages) {
		var messagesByType = messages[type];
		for ( var message in messagesByType) {
			
			var msg =  messagesByType[message];
			if(!msg.activeLanguageMessage){
				continue;
			}
			
			switch (type) {
			case 'ERROR':
				showMessage('msg-error', msg);
				break;
			case 'OK':
				showMessage('msg-ok', msg);
				break;
			case 'WARNING':
				showMessage('msg-warning', msg);
				break;
			case 'INFO':
				showMessage('msg-info', msg);
				break;
			case 'LABEL':
				showMessage('msg-label', msg);
				break;
			case 'UNKNOWN':
				showMessage('msg-unknown', msg);
				break;

			default:
				break;
			}
		}
	}
}

function showMessage(cssClass, msg){
	
	if(jQuery.inArray(msg.message + msg.messageBody, messagesHolder)>-1){
		return;
	}
	
	var divMsg = $("#messages");

	var div = $("<div>").addClass("message").addClass(cssClass);
	var h2 = $("<h2>").html(msg.message);
	div.append(h2);
	if(msg.messageBody!=null){
		var p = $("<p>").html(msg.messageBody);
		div.append(p);
	}
	
	divMsg.append(div);
	
	messagesHolder.push(msg.message + msg.messageBody);
}

/**
 * Ajax POST päringu saatmine
 */
function sendAjaxRequest(params, callback, synchronous, url){

	var async = !synchronous;
	
	checkSessionCallRequest(function(){
		$.ajax({
			  async: async,
			  type: "POST",
			  url: (url ? url : window.location.href),
			  data: params,
			  dataType: "json",
			  success: function(responseData) {
				  callback(responseData);
			  },
			  error:function(responseData, status){
				  if(responseData && responseData.messages){
					  showMessages(responseData);
				  } else if(responseData && responseData.responseJSON){
						  showMessages(responseData.responseJSON);
				  } else if(status != 'error'){// ei tea kust selline viga tuleb, kuid paistab et jQuery kõhust, tahame siin ikka näha HTTP statuse numbrit, seega ignoreerime
					  alert("Esines viga! Staatus: " + status);	
				  }
			  }
			});
	});
}

function paramsToGetParams(params){
	var sParams = "";
	
	$.each(params, function(idx1,val1) {
		
		if($.isArray(val1)){
			$.each(val1, function(idx2,val2) {
				if(val2){
					sParams+= idx1 + "=" + (val2==null ? '' : val2) + "&";
				}
			});
		}else {
			sParams+= idx1 + "=" + (val1==null ? '' : val1) + "&";
		}
	});

	return sParams;
}

function injectEventParams(params, evt, target, param){
	params[param_epm_evt] = evt;
	params[param_epm_evt_target] = target;
	params[param_epm_evt_param] = param;
}

function injectAjaxEventParams(params, evt, target, param){
	params[param_epm_evt_ajax] = true;
	injectEventParams(params, evt, target, param);
}

/**
 * Abistab elemendi tema ID alusel leidmise. jQueryle otse ei meeldi meie ID kuju
 * @param displayId
 * @returns
 */
function getJQueryElementById(displayId){
	return $(window.document.getElementById(displayId));
}

String.prototype.hashCode = function(){
    var hash = 0;
    if (this.length == 0) return hash;
    for (var i = 0; i < this.length; i++) {
        var character = this.charCodeAt(i);
        hash = ((hash<<5)-hash)+character;
        hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
};


function openPopUp(url, name, width, height){
	var left = (screen.width/2)-(width/2);
	var top = (screen.height/2)-(height/2);
	var win = window.open(url, name, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no, width='+width+', height='+height+', top='+top+', left='+left);
	win.focus();
	return win;
}

function openManual(type, anchor){
	return openPopUp(contextPath + '/static/'+type+'/'+activeLang+'/userguide/index.html#'+anchor, 'manual', 1024, 600);
}

function startLoader(){
	$.loader({
		className:"blue-with-image-2",
		content:''
	});
}

function closeLoader(){
	$.loader('close');
}

/**
 * Ennem argumendiks oleva funktsiooni käivitamist kontrollirakse, kas sessioon on aktiivne, kui on siis käivitatakse funktsioon, 
 * vastasel juhul uuendatakse ära leht ning kasutaja suunatakse kas sisselogimis- või kliendivaliku lehele. 
 * Sessiooni kehtivust kontrollitakse uuesti, kui viimase päringu teostamise aeg on hilisem kui 1 minut 
 * @param callFunction käivitatav funktsioon
 */
function checkSessionCallRequest(callFunction){
	
	// kontrollime kas on vaja sessiooni kehtivust kontrollida
	var needToCheck = true;
	var lastCheckTime = sessionStorage.getItem(epmSessionCheckKey);
	if(lastCheckTime){
		if((parseInt(lastCheckTime) + (1000) * 60)  > new Date().getTime()){
			needToCheck = false;
		}
	}
	
	if(needToCheck){
		// peame kontrollima sessiooni kehtivust
		// kõigepealt käivitme sessiooni kehtivuse kontrolli, kui see õnnestub, käivitame soovitud funktsionaalsuse, vastasel juhul uuendame lehe
		sessionStorage.setItem(epmSessionCheckKey, new Date().getTime());
		$.ajax({
				dataType: 'json',
				type: 'POST',
				url: contextPath + '/common/session-check/is-active',
				success: function(resp){
					if(resp.success === true){
						callFunction();  
					}else {
						window.location.reload(true);
					}
				},
			});
	}else{
		callFunction();
	} 
}