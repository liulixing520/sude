(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderHeaderDialogController', SdOrderHeaderDialogController);

    SdOrderHeaderDialogController.$inject = ['Principal','$timeout', '$scope','$http', '$stateParams', '$uibModalInstance', 'entity','SdStation', 'SdOrderHeader'];

    function SdOrderHeaderDialogController (Principal,$timeout, $scope,$http, $stateParams, $uibModalInstance, entity,SdStation, SdOrderHeader) {
        var vm = this;

        vm.sdOrderHeader = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ids = $stateParams.ids;
        vm.sdStations = SdStation.query({page: 0,size: 100,sort: null});
        
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sdOrderHeader.id !== null) {
                SdOrderHeader.update(vm.sdOrderHeader, onSaveSuccess, onSaveError);
            } else {
                SdOrderHeader.save(vm.sdOrderHeader, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdOrderHeaderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.departureTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        // 从后台搜索获取数据
        $scope.getStation = function(val) {
        	return $http.get('/api/_search/searchSdStation', {
        		params: {
        			query: val,
        			sensor: false
        		}
        	}).then(function(response){
        		return response.data;
        	});
        };
        //选中目的站
        $scope.setconStationDetail = function ($item, $model) { 
        	vm.sdOrderHeader.toStation = $item.id;
        	vm.sdOrderHeader.toStationName = $item.stationName;
        };
        //选中卸货地站
        $scope.setconStationUnload = function ($item, $model) { 
        	vm.sdOrderHeader.unloadPlace = $item.stationName;
        };
    }
})();
