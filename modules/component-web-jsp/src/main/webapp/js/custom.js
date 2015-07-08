/* =Modal
-------------------------------------------------- */

/*
 * Antud faili ei tohi enam üks-ühele prototüübist üle kirjutada vaid üle tuleb tõsta vajalikud muudatused.
 */

openModalContent = null;

$(function(){
	var modalContentMaxHeight = $(window).height() - 200;
	$(".modalpopup-content").css('max-height',modalContentMaxHeight + 'px');

	$(window).resize(function(){
		modalContentMaxHeight = $(window).height() - 200;
		$(".modalpopup-content").css('max-height',modalContentMaxHeight + 'px');
	});
});


showModal= function(target, size) {
	if ( $("#overlay").length == 0 ) {
		$("body").append("<div id='overlay'></div>");
	}
	if ( openModalContent != null ) {
		$("#" + openModalContent).hide();
	}
	openModalContent = target;

	$("#overlay").css("display","block");
	$("#" + target).css("display","block");
	$("#modalwrap").fadeIn(150);

	$("#modalwrap").removeClass("modalpopup-large modalpopup-huge");
	if ( size == 'large' ) {
		$("#modalwrap").addClass("modalpopup-large");
	} else if ( size == 'huge' ){
		$("#modalwrap").addClass("modalpopup-huge");
	}

	$("#modalwrap").css("top",50 + 'px');


	escape();
	initializeAadressElement();
	return false;
};

hideModal=function() {
	if ( openModalContent != null ) {
		$("#" + openModalContent).fadeOut(150);
	}
	$("#modalwrap").fadeOut(150, function() {
		$("#overlay").css("display","none");
	});
	$("#" + openModalContent).removeClass("modalpopup-large");

	return false;
};

function escape() {
	document.onkeyup = function(e) {
		var code;
		if ( !e ) var e = window.event;
		if ( e.keyCode ) code = e.keyCode;
		else if ( e.which ) code = e.which;
		if ( code == 27 ) {
		  $('#overlayCloseLink').click();
		}
	};
}

/* autofocus */

function setAutofocus() {
	var autofocus = "autofocus" in document.createElement("input");
	if ( !autofocus && $(":input[autofocus]").length ) {
		$(":input[autofocus]:visible:eq(0)").focus();
	}
}

/* data tables */

function markRows() {
	$("table.mark").find("tbody tr").dblclick(function(){
		$(this).toggleClass("marked");
	});
};

/* radio, checkbox */

function checkAll(){
	$("a.check-all").click(function(){
		var a = $(this);
		a.parent().nextAll("table.data:eq(0)").find("td input:checkbox").prop('checked', true).trigger("change");
		a.parents("tr:eq(0)").find("input:checkbox").prop('checked', true).trigger("change");
		return false;
	});
};
function checkNone(){
	$("a.check-none").click(function(){
		var a = $(this);
		a.parent().nextAll("table.data:eq(0)").find("td input:checkbox").removeAttr("checked").trigger("change");
		a.parents("tr:eq(0)").find("input:checkbox").removeAttr("checked").trigger("change");
		return false;
	});
};


/* help layer */

function toggleHelp() {
	$(".help-toggle").click(function(){
		var link = $(this),
			target = link.next("div.help"),
			linkOffset = link.offset(),
			scroll = $(document).scrollLeft();

		if(target.is(":visible")){
			target.css("display","none");
		}
		else {
			target
				.css({
					display: "block",
					top: linkOffset.top + "px",
					left: $(this).width() + linkOffset.left + "px",
					right: "auto"
				})
				.removeClass("top");

			/* fix position */

			var targetOffset = target.offset();
			target.css({
					top: parseInt(target.css('top').replace('px','')) - (targetOffset.top - linkOffset.top) - 10 + 'px',
					left: parseInt(target.css('left').replace('px','')) - (targetOffset.left - linkOffset.left) + 17 + "px"
				});

			if( linkOffset.left - scroll + target.width() > $(window).width() ){
				targetOffset = target.offset();
				target
					.addClass("top")
					.css({
						top: parseInt(target.css('top').replace('px','')) - (targetOffset.top - linkOffset.top) + 20 + 'px',
						left: parseInt(target.css('left').replace('px','')) - ((targetOffset.left + target.outerWidth()) - ($(window).scrollLeft() + $(window).width()))
					});
			}

			$("div.help").not(target).css("display","none");
		}
		return false;
	});
}
function closeHelp() {
	$("div.help p.close a").click(function(){
		$("div.help:visible").css("display","none");
		return false;
	});
}

/* toggle */

var toggleH2boxes = {
	init : function(){
		$("a.help.toggle").click(this.clickIt);
	},
	clickIt : function(){
		var target = $(this);

		target.toggleClass("toggle-open");
		target.parents("div.title:eq(0)").nextAll("div.toggle-wrap:first").slideToggle("fast");
		closeHelp();

		return false;
	}
};
var toggleMore = {
	init : function(){
		$(".toggle-open-more").click(this.clickIt);
	},
	clickIt : function(){
		var more = $(this);
		var wrap = more.parents("p:eq(0)").next();
		more.toggleClass("open");

		if(more.hasClass("open")) {
			wrap.addClass("toggle-wrap-open");
			more.text( more.attr("rel") );
		}
		else {
			wrap.removeClass("toggle-wrap-open");
			more.text(more.attr("rev") );
		}
		return false;

		heading.toggleClass("toggle-open");
		heading.nextAll("div.toggle-wrap:first").slideToggle("fast");

		return false;
	}
};

var toggleLayer = {
	init : function(){
		$(".action-target").children("a").click(this.clickIt);
	},
	clickIt : function(){
		var target = $(this);
		var list = $(".action-list:visible");

		target.toggleClass("open");
		target.parents(".action-target:eq(0)").nextAll(".action-list:first").slideToggle("fast");
		return false;
	}
};

$(document).bind('click', function(e) {  

	var $clicked = $(e.target);
	if (!$clicked.parents().hasClass("action-list")) {
		$(".action-list").hide();
	}
});

/* document ready */

$(function(){

	setAutofocus();
	$("input[placeholder], textarea[placeholder]").placeholder();

	markRows();

	toggleHelp();
	closeHelp();


	toggleLayer.init();

	toggleH2boxes.init();
	toggleMore.init();

	checkAll();
	checkNone();

	if($(".multiselect").length){
		$(".multiselect")
			.multiselect({
				noneSelectedText: getEpmText('multiselect.select'),
				selectedList:15,
				checkAllText: getEpmText('multiselect.selectAll'),
				uncheckAllText: getEpmText('multiselect.unselect')
			});
	}

	if($(".combobox").length){
		$(".combobox").combobox();
	}
	setDatepicks($(document));
	
});

/* combobox dropdown fix */

$(window).scroll(function(){
    if ($('.modalpopup:visible').length) {
        if ($('.ui-multiselect-menu:visible').length && $('.ui-multiselect.ui-state-active:visible').length ) {
            var input = $('.ui-multiselect.ui-state-active:visible'),
                dropdown = $('.ui-multiselect-menu:visible');
 
            dropdown.css('top', input.offset().top + input.outerHeight());
        }
    }
});
$('.modalpopup-content').scroll(function(){
    var content = $(this),
        input, dropdown;
 
    if (content.find('.ui-multiselect.ui-state-active:visible').length && $('.ui-multiselect-menu:visible').length) {
        input = content.find('.ui-multiselect.ui-state-active:visible');
        dropdown = $('.ui-multiselect-menu:visible');          
 
        dropdown.css('top', input.offset().top + input.outerHeight());
    }
});

function setDatepicks(el){
	var elements = el.find("input.date");
	if(elements.length){
		elements.datepick({
			showSpeed: 100,
			dateFormat: 'dd.mm.yyyy',
			firstDay: 1,
			onChangeMonthYear : function(){
				$('.datepick').find('.datepick-highlight').removeClass('datepick-highlight');
			},
		 	onSelect: function (dateText, inst){
		 		dateTimeChangeEvent(this.id);
		 	}
		});
	}
}

/* http://keith-wood.name/datepick.html
   Estonian localisation for jQuery Datepicker.
   Written by Mart Sõmermaa (mrts.pydev at gmail com). */ 

(function($){
	$.datepick.regional['userSpecific'] = {
		//monthNames: [ 'Jaanuar','Veebruar','Märts','Aprill','Mai','Juuni','Juuli','August','September','Oktoober','November','Detsember'],
		//dayNamesMin: ['P','E','T','K','N','R','L'],
		monthNames: [ getEpmText('calendar.month.january'), getEpmText('calendar.month.february'), getEpmText('calendar.month.march'), 
		              getEpmText('calendar.month.april'), getEpmText('calendar.month.may'), getEpmText('calendar.month.june'), 
		              getEpmText('calendar.month.july'), getEpmText('calendar.month.august'), getEpmText('calendar.month.september'), 
		              getEpmText('calendar.month.october'), getEpmText('calendar.month.november'), getEpmText('calendar.month.december')],
		dayNamesMin: [ getEpmText('calendar.day.sunday.min'), getEpmText('calendar.day.monday.min'), getEpmText('calendar.day.tuesday.min'),
		               getEpmText('calendar.day.wednesday.min'), getEpmText('calendar.day.thursday.min'), getEpmText('calendar.day.friday.min'),
		               getEpmText('calendar.day.saturday.min')],
		dateFormat: 'dd.mm.yyyy', firstDay: 1,
		renderer: $.datepick.defaultRenderer,
		//prevText: 'Eelnev', prevStatus: '',
		prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
		//nextText: 'Järgnev', nextStatus: '',
		nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
		//currentText: 'Täna', currentStatus: '',
		//todayText: 'Täna', todayStatus: '',
		clearText: '', clearStatus: '',
		closeText: 'Sulge', closeStatus: '',
		yearStatus: '', monthStatus: '',
		//weekText: 'Sm', weekStatus: '',
		dayStatus: '', defaultStatus: '',
		yearRange: 'c-100:c+10',
		isRTL: false
	};
	$.datepick.setDefaults($.datepick.regional['userSpecific']);
})(jQuery);
