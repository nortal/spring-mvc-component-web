/*
 * Kiirotsing 
 * 
 */

$(function() {
	var input = $("#search").find(".s-field");
	input.bind("keypress.enter", function(event) {
		if (event.which == 13) {
			kiirotsing();
		}
	});
});

function kiirotsing() {
	var input = $("#search").find(".s-field");
	var url = input.attr("data-url");
	var value = input.val();
	
	var form = $("<form>").attr("method", "post").attr("action",  contextPath + "/" + activeLang + url); 
	form.append($("<input>").attr("value", value).attr("name", "keyword").attr("type", "hidden"));
	
	$("body").append(form);
	form.submit();
}