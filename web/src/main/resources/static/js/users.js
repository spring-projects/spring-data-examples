'use strict';

var controllers = angular
	.module('controllers', [])
	.controller('UsersCtrl', function($scope, $http, $routeParams) {	    
		$scope.foobar = 'xads';			
	    $scope.onServerSideItemsRequested = function(currentPage, pageItems, filterByFields, orderBy, orderByReverse) {
			var params = {
			    page: currentPage,
			    pageSize: pageItems,
			    orderBy: typeof orderBy === "undefined" ? null : orderBy,
			    orderByDirection: orderByReverse ? 'ASC' : 'DESC'
			};
			for (var prop in filterByFields) {
			    params[prop] = filterByFields[prop];		    
			}		
			$http.get('/users', {params: params}).success(function(data) {
			    $scope.page = data;
			});
	    };
	});

var pfmpro = angular
	.module('spring-data-web-example', ['ngRoute', 'trNgGrid', 'controllers'])
	.run();