///////////////////////////////////

angular.module('test-app', [])
.factory('$ItemsAPI', function($httpIfc) {
	return new v1.ItemsAPI($httpIfc);
})
.factory('$v2ItemsAPI', function($httpIfc) {
	return new v2.ItemsAPI($httpIfc);
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
.controller("MainCtrl", function($scope, $ItemsAPI, $v2ItemsAPI) {
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
	$scope.save = function() {
		var item1 = {
			"count": 1,
			"id": "id1",
			"name": "Test Item 1"
		}
		var item2 = {
			"count": 2,
			"id": "id2",
			"name": "Test Item 2"
		}
		$v2ItemsAPI.save(item1).success(function(data) {
			$scope.result = data
		}).error(function(data) {
			alert(data);
		});		

//		$v2ItemsAPI.saveBoth(item1, item2).success(function(data) {
//			$scope.result = data
//		});		
	}
})
