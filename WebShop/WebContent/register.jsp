<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cake Shop | Register</title>
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
				<li class="active"><a href="${pageContext.request.contextPath}/register">Register</a></li>
				<li><a href="${pageContext.request.contextPath}/cart"> Shopping cart <span class="badge badge-primary">4</span></a></li>
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
			<div class="col-lg-6 col-lg-offset-3">
				<form action="register" method="POST">
					<div class="form-group">
						<label for="username">Username</label> <input type="text"
							class="form-control" id="username" name="username" placeholder="User name"
							autofocus required>
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password"
							class="form-control" id="password" placeholder="Password" name="password" required>
					</div>
					<button class="btn btn-primary" type="submit">Register</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>