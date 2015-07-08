/**
 * Riigilõivude seadistuse kontrollimine 
 */
$(function() {

	var tableId = escapeId($("#list-component-display-id").text());
	var table = $("#" + tableId);

	function parseValuesFromJsonToDom(data) {
		var totalSum = 0;
		$.each(data, function(index, value) {
			var valueSum = parseFloat(value.sum);
			var row = $(table).find("tbody tr:nth-child(" + (index + 1) + ")");
			$(row).find("td:nth-child(3)").text(valueSum);
			totalSum += valueSum;
		});

		var totalField = $('#riigiloiv-total-field');
		var currency = totalField.text().split(" ")[1];
		totalField.text(totalSum + " " + currency);
	}

	function makeQuery(data, displayId) {
		var params = {};
		params["epm_evt_seadistus_json"] = data;

		injectAjaxEventParams(params, "calculateLoendigaSum", displayId, "");

		sendAjaxRequest(params, function(response) {
			showMessages(response);

			if (response.data != null) {
				parseValuesFromJsonToDom(response.data.results);
			}
		});
	}

	$(table).find("tbody input").change(function(data, event) {

		var rowId = $(this).attr("id");

		var componentId = $('#current-component-display-id').text();

		makeQuery(rowId, componentId);
	});
	
	// Eemaldame enteri käsitluse popupis
	$("#quantityHolder input").first().unbind("keypress.enter");
});

// ilma loendita riigilõivu kontrollimiseks
function quantityChange(element) {
	var params = {};

	params["epm_evt_seadistus_json"] = $(element).val();

	var form = $(element).closest('.form');
	injectAjaxEventParams(params, "calculateSum", $('#current-component-display-id').text(), "");

	sendAjaxRequest(params, function(response) {
		showMessages(response);
		var data = response.data;
		$(form).find('.data').text(data.sum);
	});
}