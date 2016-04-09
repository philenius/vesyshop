var app = angular.module("shop", []);
app.constant("baseURL", "http://localhost:8080/WebShop");

app.controller("displayAllCakes", function ($scope, $http, baseURL) {
	$scope.vm = new ListAllCakes($http, baseURL);
});

function ListAllCakes($http, baseURL) {
	var that = this;

	var messageError = null;
	var messageInfo = null;
	this.imageBaseURL = baseURL + "/resources/img/";

    $http
		.get(baseURL + "/api/cakes")
		.then(function (result) {
			that.cakes = result.data;
		}).catch(function (result) {
			that.messageError = "Error: " + result.status
						+ " " + result.statusText;
		});

    this.addToCart = function(cake) {
    	console.log(cake.id);

    	var params = { cake_id: cake.id };

    	$http.post(baseURL + "/api/cart", params)
    	.then( function (result) {
			that.messageInfo = "Added cake to shopping cart!";
			console.log(result.status);
		}).catch( function (reason) {
			that.messageError = "The cake could not be added to the shopping cart!";
		});	
    }
}