var cacheEpmMessages = true;
var cacheEpmSysParams = true;

var epmMessages = [];
var epmSysParams = [];

// Adds new message
function addEpmMessage(message) {
	epmMessages.push(message);
}

function addEpmSysParam(sysParam) {
	epmSysParams.push(sysParam);
}

// Loads message by code from server for supported locales
// Also if Web Storage is enabled then store it to avoid loading on every request
function loadEpmMsg(code) {
	var params = {};
	params["code"] = code;
	sendAjaxRequest(params, loadMessageAndAdd, true, contextPath + "/" + activeLang + "/resource/message/translate");
}

function loadEpmSysParam(code) {
	var params = {};
	params["code"] = code;
	sendAjaxRequest(params, function(response) {
		addEpmSysParam(response.data.sys_param);
	}, true, contextPath + "/" + activeLang + "/resource/sysparam/query");
	
	if (typeof(Storage) !== "undefined" && cacheEpmSysParams) {
		sessionStorage.epmSysParams = JSON.stringify(epmSysParams);
	}
}

// Returns message object for lang and code
function getEpmLangMsg(code) {
	var result = false;
	$.each(epmMessages, function(idx, message) {
		if (message.langCode.toLowerCase() == activeLang.toLowerCase() && message.code === code) {
			result = message;
		}
	});
	
	if (result == false){
		try {
			loadEpmMsg(code);
			result = getEpmLangMsg(code);
		} catch(e2) {
			throw "No message found for code=" + code;
		}		
	}

	return result;
}

function getEpmSysParam(code) {
	var result = false;
	$.each(epmSysParams, function(idx, sp) {
		if (sp.code === code) {
			result = sp;
		}
	});
	
	if (result == false){
		try {
			loadEpmSysParam(code);
			result = getEpmSysParam(code);
		} catch(e2) {
			throw "No sys param found for code=" + code;
		}		
	}

	return result;
}

function getEpmSysParamValue(code) {
	var param = getEpmSysParam(code);
	return param.value;
}

// Translated text for lang and code
function getEpmLangText(code) {
	var msg = getEpmLangMsg(code);
	if(msg){
		return msg.message;
	}
	return code;
}

// Returns text for active lang
function getEpmText(code) {
	return getEpmLangText(code);
}

// If Web Storage is enabled then serialize cached messages from storage
if (typeof(Storage) !== "undefined" && cacheEpmMessages) {
	if (sessionStorage.epmMessages) {
		epmMessages = jQuery.parseJSON(sessionStorage.epmMessages);
	}else {
		// laeme serverist tõlgete paketi
		var params = {};
		sendAjaxRequest(params, loadMessageAndAdd, true, contextPath + "/" + activeLang + "/resource/message/global-messages");
	}
	
	if (sessionStorage.epmSysParams) {
		epmSysParams = jQuery.parseJSON(sessionStorage.epmSysParams);		
	}else {
		sessionStorage.epmSysParams = JSON.stringify([]);
	}
}


function loadMessageAndAdd(response){
	$.each(response.messages, function(mapIdx, msgMap) {
		$.each(msgMap, function(idx, msg) {
			addEpmMessage(msg);
		});
	});
	
	if (typeof(Storage) !== "undefined" && cacheEpmMessages) {
		sessionStorage.epmMessages = JSON.stringify(epmMessages);
	}
}