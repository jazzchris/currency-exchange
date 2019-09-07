<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!doctype html>
<html lang="en">
<head>
	<title>Register New User Form</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet"
		 href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div>
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<div class="panel-title">Register New User</div>
				</div>
				<div style="padding-top: 30px" class="panel-body">
	<form:form action="${pageContext.request.contextPath}/register/form" modelAttribute="userRegistration" class="form-horizontal">
		<c:if test="${registrationError != null}">
			<div class="alert alert-danger col-xs-offset-1 col-xs-10">
				${registrationError}</div>
		</c:if>
	<form:input path="username" placeholder="username(*)" class="form-control" />
	<form:errors path="username" class="text-danger"/>
	<br>
	<form:password path="password" placeholder="password(*)" class="form-control" />
	<form:errors path="password" class="text-danger"/>
	<br>	
	<form:password path="checkPassword" placeholder="repeat password(*)" class="form-control" />
	<form:errors path="checkPassword" class="text-danger"/>
	<br>
	<form:input path="firstName" placeholder="first name(*)" class="form-control" />
	<form:errors path="firstName" class="text-danger"/>
	<br>
	<form:input path="lastName" placeholder="last name(*)" class="form-control" />
	<form:errors path="lastName" class="text-danger"/>
	<br>	
	<form:input path="email" placeholder="email(*)" class="form-control" />
	<form:errors path="email" class="text-danger"/>
		<br>PLN<form:input path="pln" type="number" placeholder="0" class="form-control" /><form:errors path="pln" class="text-danger"/>
		<br>USD<form:input path="usd" type="number" placeholder="0" class="form-control" /><form:errors path="usd" class="text-danger"/>		
		<br>EUR<form:input path="eur" type="number" placeholder="0" class="form-control" /><form:errors path="eur" class="text-danger"/>		
		<br>CHF<form:input path="chf" type="number" placeholder="0" class="form-control" /><form:errors path="chf" class="text-danger"/>
		<br>RUB<form:input path="rub" type="number" placeholder="0" class="form-control" /><form:errors path="rub" class="text-danger"/>
		<br>CZK<form:input path="czk" type="number" placeholder="0" class="form-control" /><form:errors path="czk" class="text-danger"/>
		<br>GBP<form:input path="gbp" type="number" placeholder="0" class="form-control" /><form:errors path="gbp" class="text-danger"/>
	<br>	
		<button type="submit" class="btn btn-primary">Register</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>