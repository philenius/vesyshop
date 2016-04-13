var app = angular.module('shop', ['ngRoute']);
app.constant('baseURL', 'http://localhost:8080/WebShop');

app.config(function ($routeProvider) {
	$routeProvider
	.when('/',
	{
		controller: 'displayAllCakes',
		templateUrl: 'partials/allCakes.html'
	})
	.when('/register',
	{
		controller: 'RegisterController',
		templateUrl: 'partials/register.html'
	})
	.when('/cart',
	{
		controller: 'CartController',
		templateUrl: 'partials/cart.html'
	})
	.when('/checkout',
	{
		controller: 'CheckoutController',
		templateUrl: 'partials/checkout.html'
	})
	.otherwise({ redirectTo: '/' });
});

app.controller('displayStatusBar', function ($scope, $http, $rootScope, baseURL) {
	$scope.statusBar = new HandleStatusBar($scope, $http, $rootScope, baseURL);
});

app.controller('displayAllCakes', function ($scope, $http, $rootScope, baseURL) {
	$scope.cakeVM = new HandleAllCakes($http, $rootScope, baseURL);
});

app.controller('CartController', function ($scope, $http, $rootScope, baseURL) {
	$scope.cartVM = new HandleCart($http, $rootScope, baseURL);
});

app.controller('RegisterController', function ($scope, $http, baseURL) {
	$scope.registerVM = new HandleRegistration($scope, $http, baseURL);
});

app.controller('CheckoutController', function ($scope, $http, baseURL) {
	$scope.checkoutVM = new HandleCheckout($scope, $http, baseURL);
});

function HandleStatusBar ($scope, $http, $rootScope, baseURL) {
	var that = this;

	this.messageError = null;
	this.loggedIn = false;
	this.cartItems = 0;

	this.user = null;
	this.password = null;
	this.badLogin = false;

	this.logIn = function () {
		if (that.user && that.password) {			
			var params = {
				user: that.user,
				password: that.password
			};

			$http.post(baseURL + '/api/login', params)
			.then( function (result) {
				that.loggedIn = true;
				$rootScope.$broadcast('shop.loggedIn');
			}).catch( function (reason) {
				that.badLogin = true;
				that.messageError = 'Your login was not successful!';
			});
		}
	}

	this.logOut = function () {
		that.loggedIn = false;
		that.user = null;
		that.password = null;

		$http.post(baseURL + '/api/logout')
		.then( function (result) {
			that.loggedIn = false;
			$rootScope.$broadcast('shop.loggedOut');
			window.location = baseURL;
		}).catch( function (reason) {
			that.badLogin = false;
			that.messageError = 'Your logout was not successful!';
		});

	}

	var updateCartItems = function() {
		$http
		.get(baseURL + '/api/cart')
		.then(function (result) {
			that.cartItems = result.data.count;
		}).catch(function (result) {
			that.messageError = 'Error: ' + result.status
			+ ' ' + result.statusText;
		});
	};

	updateCartItems();

	$scope.$on('shop.addedCartItem', function(event) {
		updateCartItems();
	});
}

function HandleAllCakes ($http, $rootScope, baseURL) {
	var that = this;

	var messageError = null;
	var messageInfo = null;
	this.imageBaseURL = baseURL + '/resources/img/';

	$http
	.get(baseURL + '/api/cakes')
	.then(function (result) {
		that.cakes = result.data;
	}).catch(function (result) {
		that.messageError = 'Error: ' + result.status
		+ ' ' + result.statusText;
	});

	this.addToCart = function(cake) {
		var params = { cake: cake.name };
		$http.post(baseURL + '/api/cart', params)
		.then( function (result) {
			$rootScope.$broadcast('shop.addedCartItem');
			that.messageInfo = 'Added cake to shopping cart!';
		}).catch( function (reason) {
			that.messageError = 'The cake could not be added to the shopping cart!';
		});	
	}
}

function HandleCart ($http, $rootScope, baseURL) {
	var that = this;

	var messageError = null;
	var messageInfo = null;
	this.imageBaseURL = baseURL + '/resources/img/';
	this.cart = {};
	this.cartItems = 0;

	var updateCart = function () {
		$http
		.get(baseURL + '/api/cart')
		.then(function (result) {
			that.cart = result.data;
			that.cartItems = result.data.count;
		}).catch(function (result) {
			that.messageError = 'Error: ' + result.status
			+ ' ' + result.statusText;
		});		
	}
	
	updateCart();

	this.removeItem = function (cake) {
		var params = { cake: cake.name };

		$http.delete(baseURL + '/api/cart', {data: params })
		.then( function (result) {
			updateCart();
			$rootScope.$broadcast('shop.addedCartItem');
			that.messageInfo = 'Deleted cake from shopping cart!';
		}).catch( function (reason) {
			that.messageError = 'The cake could not be deleted from the shopping cart!';
		});	
	};
}

function HandleRegistration ($scope, $http, baseURL) {
	var that = this;

	this.user = null;
	this.password = null;
	this.loggedIn = false;
	this.successfulRegistration = false;

	this.messageInfo = null;
	that.messageError = null;

	this.register = function () {
		if (that.user && that.password) {			
			var params = {
				user: that.user,
				password: that.password
			};

			$http.post(baseURL + '/api/register', params)
			.then( function (result) {
				that.messageInfo = 'You\'re registration was successful. You may now login!';
			}).catch( function (reason) {
				that.messageError = 'The registration was not successful!';
			});	
		}
	}

	$scope.$on('shop.loggedIn', function(event) {
		that.loggedIn = true;
	});

	$scope.$on('shop.loggedOut', function(event) {
		that.loggedIn = false;
	});
}

function HandleCheckout($scope, $http, baseURL) {
	var that = this;
	this.checkedOut = true;
}