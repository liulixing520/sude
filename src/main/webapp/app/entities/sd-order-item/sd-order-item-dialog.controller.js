(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemDialogController', SdOrderItemDialogController);

    SdOrderItemDialogController.$inject = ['$timeout', '$scope', '$http','$filter','$stateParams', '$uibModalInstance', 'entity', 'SdOrderItem','SequenceValue'];

    function SdOrderItemDialogController ($timeout, $scope,$http,$filter, $stateParams, $uibModalInstance, entity, SdOrderItem,SequenceValue) {
        var vm = this;

        vm.sdOrderItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        if(entity.id==null){
        	vm.sdOrderItem.consignDate = $filter("date")(new Date(), "yyyy-MM-dd");
        }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sdOrderItem.id !== null) {
                SdOrderItem.update(vm.sdOrderItem, onSaveSuccess, onSaveError);
            } else {
                SdOrderItem.save(vm.sdOrderItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdOrderItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        // 从后台搜索获取数据
        $scope.getCustomer = function(val) {
          return $http.get('/api/_search/searchSdCustomers', {
            params: {
              query: val,
              sensor: false
            }
          }).then(function(response){
            return response.data;
          });
        };
        //给发货人补充信息
        $scope.setconsignerDetail = function ($item, $model) { 
        	vm.sdOrderItem.consignerName = $item.customerName;
        	vm.sdOrderItem.consignerId = $item.id;
        	vm.sdOrderItem.consignerAddress = $item.address;
        	vm.sdOrderItem.consignerMbPhone = $item.mobilePhone;
        	vm.sdOrderItem.consignerPhone = $item.phone;
        };
        //给收货人补充信息
        $scope.setconsigneeDetail = function ($item, $model) { 
        	vm.sdOrderItem.consigneeName = $item.customerName;
        	vm.sdOrderItem.consigneeId = $item.id;
        	vm.sdOrderItem.consigneeAddress = $item.address;
        	vm.sdOrderItem.consigneeMbPhone = $item.mobilePhone;
        	vm.sdOrderItem.consigneePhone = $item.phone;
        };
    }
})();
