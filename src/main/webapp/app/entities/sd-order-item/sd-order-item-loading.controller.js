(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemLoadingController', SdOrderItemLoadingController);

    SdOrderItemLoadingController.$inject = ['$scope', '$state', 'SdOrderItem', 'SdOrderItemSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants','SdOrderItemLoading'];

    function SdOrderItemLoadingController ($scope, $state, SdOrderItem, SdOrderItemSearch, ParseLinks, AlertService, pagingParams, paginationConstants,SdOrderItemLoading) {
        var vm = this;
        
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = 100;
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.list = [];
        vm.selecteAll = false;
        vm.ids = [];
        
        $scope.selectedOne = function($event, id){
	         var checkbox = $event.target;
	         if(checkbox.checked){
	        	 vm.ids.push(id);
	         }else{
	        	 var idx = vm.ids.indexOf(id);
	        	 vm.ids.splice(idx,1);
	         }
	     }
        $scope.selectedAll = function ($event){
        	var checkbox = $event.target;
        	if(checkbox.checked){
                angular.forEach(vm.list, function (i) {
                    vm.ids.push(i);
                })
                vm.selectedOne=true;
            }else {
                angular.forEach(vm.list, function (i) {
                    vm.ids = [];
                })
                vm.selectedOne=false;
            }
        }
        
        loadAll();

        function loadAll () {
        	SdOrderItemLoading.query({
            	orderStat: 'orderStat_1',//
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.sdOrderItems = data;
                vm.page = pagingParams.page;
                
                angular.forEach(data , function (i) {
                    vm.list.push(i.id);
                })
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear () {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }
    }
})();
