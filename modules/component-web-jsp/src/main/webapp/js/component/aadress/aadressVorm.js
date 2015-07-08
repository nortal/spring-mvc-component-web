/*
 * Aadressi otsing 
 * 
 */

function toggleAutocomplete(el) {
	el = $(el);
	var search = el.closest('.form').find(".address-autocomplete");
	if (el.val() == 'EE') {
		search.autocomplete("enable");
	} else {
		search.autocomplete("disable");
	}
};
				
function toggleValitudAadress(el) {
	el = $(el); 
	var aadressDisplay = el.closest('.form').find(".address-display").parent().parent();
	if (el.val() == 'EE') {
		aadressDisplay.show();
	} else {
		aadressDisplay.hide();
	}
};
				
function removeCountryFromAddress(address) {
	if (!address) 
		return;
	var strs = address.split(",");
	var result = "";
	for (i = 0, l = strs.length; i < l - 1; i++) {
		result += strs[i];
		if (i < l - 2) {
			result += ", ";
		}
	}
	return result;
};

function initializeAadressElement() {
	var jgc_URL = getEpmSysParamValue("REGIO_JGC_URL");
	$(".address-autocomplete").autocomplete({
		source: function(req, callback) {
			$.ajax({
				dataType: "jsonp",
				url: jgc_URL,
				data: {q: req.term},
				success: function(data) {
					var suggestions = [];
					//Process results
 					$.each(data, function(key, val) {
 						if (val.placemark) {
 							$.each(val.placemark, function(key, val) {
 								suggestions.push({
 									label: removeCountryFromAddress(val.address),
 									item: val,
 									id: val
 								});
 							});
 						}
 					});
 					callback(suggestions);
				},
				error: function(xhr, ajaxOptions, thrownError) {
					showMessage('msg-error', xhr.responseText);
				}
			});
		},
		select: function(event, ui) {
			var params = {};
			var form = $(event.target).closest('.form');
			params["epm_evt_aadress_json"] = JSON.stringify(ui.item.item);
			injectAjaxEventParams(params, "submitAadress", form.attr("id"), "");
			sendAjaxRequest(params, function(response) {
				form.find(".address-display").html(response.data.aadress);
				form.find(".zip-code-display").find("input:first").val(response.data.postiindeks);
			});
		}
	});
}

$(function() {
	initializeAadressElement();
	
	var riikEl = $(".riik-display").find("select:first");
	toggleAutocomplete(riikEl);
	toggleValitudAadress(riikEl);
});