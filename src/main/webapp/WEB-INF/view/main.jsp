<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>Currency Exchange</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/stomp-websocket/stomp.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>


<body onload="connect();">

<nav class="navbar bg-dark navbar-dark">
	<div>
		<p class="navbar-brand">Currency Exchange</p>
	</div>
	<div style="float:center">
		<p class="navbar-item text-light">Logged in as <b>${user.firstName} ${user.lastName}</b></p>
	</div>
	<div style="float:right">
		<p><form:form action="${pageContext.request.contextPath}/logout" method="POST">
				<button type="submit" class="btn btn-sm btn-outline-secondary text-light">
          		<span class="fa fa-sign-out"></span> Log out</button>
			</form:form></p>
	</div>
</nav>

<div id="main-content" class="container" style="padding-top:20px">

	<h5>Publication date: <small id="publicationDate"></small></h5>

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
	
			<c:forEach items="${curr}" var="entry">
				<c:set var="key" value="${entry.key}"/>
				<tr id="buy-row-${key}">
					<td><input type="text" value="${key}" class="currency not-change"/></td>
					<td><input type="text" value="${entry.value.unit}" class="unit not-change"/></td>
					<td><input type="text" class="sell not-change"/></td>
					<td><input type="button" value="Buy" class="load btn btn-sm btn-primary"/></td>
				</tr>
			</c:forEach>	
	</table>
	
	<table id="sell-table" class="table table-bordered">
			<tr>
				<td colspan="5"><h4>My Wallet</h4></td>
			</tr>
			<tr class="head-table">
				<td>Currency</td>
				<td>Unit price</td>
				<td>Amount(Units)</td>
				<td>Value</td>
				<td>Actions</td>
			</tr>
	 		
			<c:forEach items="${curr}" var="entry" >
				<c:set var="key" value="${entry.key}"/>
				<c:if test="${cash[key]>0}">
				
				<tr id="sell-row-${key}">
				
					<td><input type="hidden" value="${entry.value.unit}" class="unit" />
						<input type="text" value="${key}" class="currency not-change"/></td>
					<td><input type="text" class="buy not-change"/></td>
					<td><input type="text" value="${cash[key]}" class="amount not-change"/></td>
					<td><input type="text" class="total not-change"/></td>
					<td><input type="button" value="Sell" class="load btn btn-sm btn-primary"/></td>
				</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td colspan="5"><h5><small>Available PLN:</small><b> ${user.usersWallet.pln}</b></h5></td>
			</tr>													
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
				<td><input type="submit" value="Buy" class="submit-transaction btn btn-primary" disabled="true"/></td>
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
				<td><input type="submit" value="Sell" class="submit-transaction btn btn-primary" disabled="true"/></td>
			</tr>
		</table>
	</form:form>
	</div>
</div>

</body>

</html>