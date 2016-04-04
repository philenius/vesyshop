<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Cake Shop | Cart</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css"
	type="text/css">
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}">HOME
				</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/register">Register</a></li>
				<li class="active"><a href="${pageContext.request.contextPath}/cart"> Shopping cart <span class="badge badge-primary">4</span></a></li>
			</ul>
			<div class="navbar-right">
				<form class="navbar-form" role="search" action="login" method="POST">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="user"
							name="user" autofocus> <input type="password"
							class="form-control" placeholder="password" name="password">
					</div>
					<button type="submit" class="btn btn-default">Login</button>
				</form>
			</div>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-lg-12"></div>
		</div>
	</div>
</body>
</html>