var stompClient = null;
var pathname = window.location.pathname;

function retrieveData() {
	$.get(pathname + "user", function(data) {
		$("#userFullName").html(data.fullName);
		$("#customer-pln").html(data.pln);
		for(var i in data.wallet)
			refreshCustomer(i, data.wallet[i]);
	});
}

function getCurrencies() {
	$.get(pathname + "currencySet", function(data) {
		data.foreign.forEach(print);
	});
}

function refreshAmounts(data) {
	$("#customer-pln").html(data.pln);
	for(var i in data.wallet) {
		var theRow = $("#sell-row2-"+i);
		theRow.find(".amount").text(data.wallet[i]);
		count(theRow);
		data.wallet[i]===0 ? theRow.hide() : theRow.show();
	}
}

function refreshCustomer(key, value) {
	var row = $('#sell-row2-'+key);
	row.find('.amount').text(value);
	value===0 ? row.hide() : row.show();
}

function print(value) {
	console.log(value);
	$('#buy-table > tbody:last').append('<tr id="buy-row2-'+value+'"><td class="currency">'+value+'</td><td class="unit"></td><td class="sell"></td><td><input type="button" value="Buy" class="load btn btn-sm btn-primary"/></td></tr>');
	$('#sell-table > tbody:last').append('<tr id="sell-row2-'+value+'"><td class="currency">'+value+'</td><td class="buy"></td><td class="amount"></td><td class="total"></td><td><input type="button" value="Sell" class="load btn btn-sm btn-primary"/></td></tr>');
}

function addSuccessInfo(data) {
	$('#success-info').text(data).show();
	$('#success-info').removeClass('alert-danger').addClass('alert-success');
}

function addFailInfo(data) {
	$('#success-info').text(data).show();
	$('#success-info').removeClass('alert-success').addClass('alert-danger');
}

function connect() {
	getCurrencies();
	retrieveData();
    var socket = new SockJS(pathname + "currency");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/price', function (stocks) {
    		var json = JSON.parse(stocks.body);
    		$("#publicationDate").text(json.PublicationDate);
    		var items = json.Items;
    		var id;
    		var currentRow;
    		for(var i in items) {
    			id = items[i].Code;
    			currentRow = $("#buy-row2-"+id);
    			if (currentRow.length) {
    				currentRow.find(".sell").text(items[i].SellPrice);
    				currentRow.find(".unit").text(items[i].Unit);
    			}
    			currentRow = $("#sell-row2-"+id);
    			if (currentRow.length) {
    				currentRow.find(".buy").text(items[i].PurchasePrice);
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
	var x = currentRow.find(".amount").text();
	var y = currentRow.find(".buy").text();
	var z = x*y;
	currentRow.find(".total").text(z.toFixed(2));
}

$(function () {
	$(".submit-transaction").click(function(event) {
		$(this).prop("disabled", true);
		if (!(confirm('Confirm transaction')))
			return false;
		var transactionType = $(this).val().toUpperCase();
		console.log(transactionType);
		var parent = $(this).closest("table");
		var data = {}
		data["unitPrice"] = Number(parent.find(".unit-price").val());
		data["currency"] = parent.find(".currency").val();
		data["transactionType"] = transactionType;
		data["transUnits"] = Number(parent.find(".transaction-units").val());
		console.log(data);
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		console.log(token + " " + header);
		$.ajaxSetup({
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			}
		});
		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "http://localhost:8080/currency-exchange/ajaxBuy",
			data: JSON.stringify(data),
//			success: function(data) {
//				console.log("SUCCESS " + data.userDto.fullName + " " + data.response);
//				refreshAmounts(data.userDto);
//				addInfo(data.response);
//			},
//			error: function(e) {
//				console.log("ERROR! " + e);
//			}
			statusCode: {
				200: function(data) {
					console.log("SUCCESS " + data.userDto.fullName + " " + data.response);
					refreshAmounts(data.userDto);
					addSuccessInfo(data.response);
				},
				400: function(data) {
					console.log("ERROR!");
					var response = data.responseJSON;
					addFailInfo(response.response);
				}
			}
		});
		clearForm(parent.closest('.modal'));
	});
	
	
    $(".not-change").keypress(function() { return false; });

	$(window).click(function() {
		var target = $(event.target);
		if (target.is(".modal")) {
			clearForm(target);
		}
	});
	
	$(document.body).on('click', '.load', function() {
		var parent = $(this).closest("tr");
		console.log("parent: " + parent.attr('id'));
		var modalParent;
		if (parent.attr('id').startsWith('buy')) {
			modalParent = $("#buy-modal");
			modalParent.find(".unit-price").val(parent.find(".sell").text());
		}
		else if (parent.attr('id').startsWith('sell')) {
			modalParent = $("#sell-modal");
			modalParent.find(".unit-price").val(parent.find(".buy").text());
			modalParent.find(".available-units").val(parent.find(".amount").text());	
		}
		modalParent.find(".currency").val(parent.find(".currency").text());
		modalParent.find(".unit").text(parent.find(".unit").text());
		modalParent.show();
	});
	
	$(".closeFunction").click(function() {
		var modal = $(this).closest(".modal");
		clearForm(modal);
	});
	
//	$(".submit-transaction").click(function() {
//		if (!(confirm('Confirm transaction')))
//			return false
//	});
	
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