///////////////////////////////////

angular.module('test-app', [])
.factory('$ItemsAPIv1', function($httpIfc) {
	return new v1.ItemsAPI($httpIfc);
})
.factory('$ItemsAPIv2', function($httpIfc) {
	return new v2.ItemsAPI($httpIfc);
})
.factory('$ItemsAPIv3', function($httpIfc) {
	return new v3.ItemsAPI($httpIfc);
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
.controller("MainCtrl", function($scope, $ItemsAPIv1, $ItemsAPIv2, $ItemsAPIv3) {
	$scope.name = "newItem"
	$scope.count = "1"
	$scope.desc = ""
	var f = function(data) {
		$scope.result = data
	}
	$scope.v1 = {
		availableItems: function() {
			$ItemsAPIv1.availableItems().success(f).error(f)
		},
		saveItem: function() {
			$ItemsAPIv1.saveItem($scope.name, $scope.count).success(f).error(f)
		}
	}
	$scope.v2 = {
		availableItems: function() {
			$ItemsAPIv2.availableItems().success(f).error(f)
		},
		saveItem: function() {
			$ItemsAPIv2.saveItem($scope.name, $scope.count, $scope.desc).success(f).error(f)			
		}
	}
	$scope.v3 = {
		availableItems: function() {
			$ItemsAPIv3.availableItems().success(f).error(f)
		},
		saveItem: function() {
			$ItemsAPIv3.saveItem($scope.name, $scope.count, $scope.desc).success(f).error(f)
		}
	}
})
