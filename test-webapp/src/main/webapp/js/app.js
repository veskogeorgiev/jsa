///////////////////////////////////

angular.module('test-app', [])
.factory('$ItemsAPI', function($httpIfc) {
	return new ns.ItemsAPI($httpIfc);
})
.factory('$httpIfc', function($http) {
	return {
		send: function(req) {
			return $http({
				method: req.method,
				headers: req.headers,
				url: req.url,
				data: req.data
			})
		}
	}
})
.controller("MainCtrl", function($scope, $ItemsAPI) {
	$scope.getItems = function() {
		$ItemsAPI.getItems().success(function(data) {
			$scope.result = data
		});		
	}
	$scope.getItemResult = function() {
		$ItemsAPI.getItemResult().success(function(data) {
			$scope.result = data
		});		
	}
	$scope.getMapResult = function() {
		$ItemsAPI.getMapResult().success(function(data) {
			$scope.result = data
		});		
	}
})
