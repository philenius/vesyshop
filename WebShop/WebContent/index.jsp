<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome to the Cake Shop</title>
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
			<div class="col-lg-12">
				<div class="page-header">
					<h1>
						Welcome to the Cake Shop <small>BRAND NEW <span
							class="glyphicon glyphicon-star" aria-hidden="true"></span> <span
							class="glyphicon glyphicon-star" aria-hidden="true"></span> <span
							class="glyphicon glyphicon-star" aria-hidden="true"></span>
						</small>
					</h1>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<h2>See all our great tasty cakes!</h2>
				<table class="table">
					<thead>
						<tr>
							<th>Cake</th>
							<th>Picture</th>
							<th>Price</th>
							<th>Ingredients</th>
							<th>Buy</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Cheese Cake</td>
							<td><a href="#" class="thumbnail"> <img
									src="${pageContext.request.contextPath}/resources/img/cake1.png"
									alt="pic of cake">
							</a></td>
							<td>12,50 €</td>
							<td>Yoghurt, Beer</td>
							<td>
								<form action="addToCart" method="POST">
									<div class="form-group">
										<input type="text" value="0" name="id"
											class="form-control hidden">
										<button class="btn btn-success">Add to cart</button>
									</div>
								</form>
							</td>
						</tr>
						<tr>
							<td>Wedding Cake</td>
							<td><a href="#" class="thumbnail"> <img
									src="${pageContext.request.contextPath}/resources/img/cake2.png"
									alt="pic of cake">
							</a></td>
							<td>56 €</td>
							<td>Parmesan, Nutella</td>
							<td>
								<form action="addToCart" method="POST">
									<div class="form-group">
										<input type="text" value="1" name="id"
											class="form-control hidden">
										<button class="btn btn-success">Add to cart</button>
									</div>
								</form>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- end row -->
	</div>
	<footer class="footer">
		<div class="container">
			<p class="text-muted">
				Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a>
				from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a>
				is licensed by <a href="http://creativecommons.org/licenses/by/3.0/"
					title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a>
			</p>
		</div>
	</footer>
	<!-- end container -->
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>