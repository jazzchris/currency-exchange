var stompClient = null;
var name = window.location.pathname;

function connect() {
    var socket = new SockJS(name + "currency");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/price', function (stocks) {
    		var json = JSON.parse(stocks.body);
    		$("#publicationDate").text(json.publicationDate);
    		var items = json.items;
    		var currentRow;
    		for(var i in items) {
    			currentRow = $("#buy-row-"+i);
    			if (currentRow.length) {
    				currentRow.find(".sell").val(items[i].sellPrice);
    			}
    			currentRow = $("#sell-row-"+i);
    			if (currentRow.length) {
    				currentRow.find(".buy").val(items[i].purchasePrice);
    				count(currentRow);
    			}
    		}
    	});
    });
}

function clearForm(modal) {
	modal.hide();
	modal.find(".transaction-units").val("");
	modal.find(".to-count").val("");
}

function count(currentRow) {
	var x = currentRow.find(".amount").val();
	var y = currentRow.find(".buy").val();
	var z = x*y;
	currentRow.find(".total").val(z.toFixed(2));
}

$(function () {
    $(".not-change").keypress(function() { return false; });

	$(window).click(function() {
		var target = $(event.target);
		if (target.is(".modal")) {
			clearForm(target);
		}
	});
	
	$(".load").click(function() {
		var parent = $(this).closest("tr");
		console.log("parent: " + parent.attr('id'));
		var modalParent;
		if (parent.attr('id').startsWith('buy')) {
			modalParent = $("#buy-modal");
			modalParent.find(".unit-price").val(parent.find(".sell").val());
		}
		else if (parent.attr('id').startsWith('sell')) {
			modalParent = $("#sell-modal");
			modalParent.find(".unit-price").val(parent.find(".buy").val());
			modalParent.find(".available-units").val(parent.find(".amount").val());	
		}
		modalParent.find(".currency").val(parent.find(".currency").val());
		modalParent.find(".unit").text(parent.find(".unit").val());
		modalParent.show();
	});
	
	$(".closeFunction").click(function() {
		var modal = $(this).closest(".modal");
		clearForm(modal);
	});
	
	$(".submit-transaction").click(function() {
		if (!(confirm('Confirm transaction')))
			return false
	});
	
	$(".transaction-units").keyup(function() {
		var parent = $(this).closest("table");
		if ($(this).val() < 1 || $(this).val() % 1 !== 0) {
			parent.find(".submit-transaction").prop("disabled", true);
			parent.find(".to-count").val("");
			return;
		}
		var x = parent.find(".unit-price").val();
		var y = $(this).val();
		var z = x*y;
		parent.find(".to-count").val(z.toFixed(2));
		parent.find(".submit-transaction").prop("disabled", false);
	});
});