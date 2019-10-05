<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>Currency Exchange</title>
	<base href="${pageContext.request.contextPath}/" />
    <link href="webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <script src="webjars/jquery/jquery.min.js"></script>
    <script src="webjars/sockjs-client/sockjs.min.js"></script>
    <script src="webjars/stomp-websocket/stomp.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="js/app.js"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>


<body onload="connect();">

<nav class="navbar bg-dark navbar-dark">
	<div>
		<p class="navbar-brand">Currency Exchange</p>
	</div>
	<div style="float:center">
		<p class="navbar-item text-light">Logged in as <b><span id="userFullName"></span></b></p>
	</div>
	<div style="float:right">
		<p><form:form action="logout" method="POST">
				<button type="submit" class="btn btn-sm btn-outline-secondary text-light">
          		<span class="fa fa-sign-out"></span>Log out</button>
			</form:form></p>
	</div>
</nav>

<div id="main-content" class="container" style="padding-top:20px">

	<h5>Publication date: <small id="publicationDate"></small></h5>
	
	<div id="success-info" class="alert alert-success col-xs-offset-1"></div>
	
	
	
		<c:if test="${result != null}">
			<c:choose>
				<c:when test="${result.startsWith('Success')}">
					<p class="alert alert-success col-xs-offset-1">${result}</p>
				</c:when>
				<c:otherwise>
					<p class="alert alert-danger col-xs-offset-1">Failed! ${result}</p>
				</c:otherwise>
			</c:choose>
		</c:if>		
		
	<table id="buy-table" class="table table-bordered">
			<tr>
				<td colspan="4"><h4>Currencies</h4></td>
			</tr>
			<tr class="head-table">
				<td>Currency</td>
				<td>Unit</td>
				<td>Value</td>
				<td>Actions</td>
			</tr>	
	</table>
	
	<table id="sell-table" class="table table-bordered">
			<tr>
				<td colspan="5"><h4>My Wallet</h4></td>
			</tr>
			<tr class="head-table">
				<td>Currency</td>
				<td>Unit price</td>
				<td>Amount(units)</td>
				<td>Value</td>
				<td>Actions</td>
			</tr>
	 		<tbody id="tbody-sell">
			</tbody>
			<tfoot>
			<tr>
				<td colspan="5"><h5><small>Available PLN:</small><b>
				<span id="customer-pln"></span></b></h5></td>
			</tr>
			</tfoot>													
	</table>
	
</div>

<!-- BUY MODAL -->

<div id="buy-modal" class="modal">
  <div class="modal-content">
    <span class="close closeFunction">&times;</span>
    <form:form action="proceedBuy" modelAttribute="details" method="POST">
    	<form:hidden path="transactionType" value="BUY" />
		<table class="table">
			<tr>
				<td>Currency:</td>
				<td><form:input type="text" path="currency" class="currency hideborder not-change"/></td>
			</tr>
			<tr>
				<td>Unit:</td>
				<td><p class="unit"></p></td>
			</tr>
			<tr>
				<td>Unit price:</td>
				<td><form:input path="unitPrice" type="decimal" class="unit-price not-change" /></td>
			</tr>
			<tr>
				<td>Units to transaction:</td>
				<td><form:input path="transUnits" type="number" min="1" value="null" placeholder="0"
					class="transaction-units"/>
				<br><form:errors path="transUnits" class="text-danger" /></td>
			</tr>
			<tr>
				<td>Total:</td>
				<td><input type="text" class="to-count not-change"/></td>
			</tr>
			<tr>
				<td><input type="button" class="closeFunction btn btn-light" value="Back"/></td>
				<td><input type="button" value="Buy" id="process-buy" class="submit-transaction btn btn-primary" disabled="true"/></td>
			</tr>
		</table>
    </form:form>
  </div>
</div>

<!-- SELL MODAL -->

<div id="sell-modal" class="modal">
  <div class="modal-content">
    <span class="close closeFunction">&times;</span>
	<form:form action="proceedSell" modelAttribute="details" method="POST">
		<form:hidden path="transactionType" value="SELL" />
		<input type="hidden" value="${details.currency.unit}" class="unit"/>
		<table class="table">
			<tr>
				<td>Currency:</td>
				<td><form:input type="text" path="currency" class="currency not-change hideborder"/></td>
			</tr>
			<tr>
				<td>Unit available:</td>
				<td><input type="text" value="${availableUnits}" class="available-units not-change"/></td>
			</tr>
			<tr>
				<td>Unit price:</td>
				<td><form:input path="unitPrice" type="decimal"	class="unit-price not-change" /></td>
			</tr>
			<tr>
				<td>Units to transaction:</td>
				<td><form:input path="transUnits" type="number" min="1" value="null" placeholder="0" class="transaction-units"/>
				<br><form:errors path="transUnits" class="text-danger"/></td>
			</tr>
			<tr>
				<td>Total:</td>
				<td><input type="text" id="toCount" onkeypress="return false;" class="to-count"/></td>
			</tr>
			<tr>
				<td><input type="button" class="closeFunction btn btn-light" value="Back"/></td>
				<td><input type="button" value="Sell" id="process-sell" class="submit-transaction btn btn-primary" disabled="true"/></td>
			</tr>
		</table>
	</form:form>
	</div>
</div>
</body>

</html>