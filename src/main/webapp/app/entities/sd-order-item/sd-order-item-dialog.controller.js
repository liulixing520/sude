(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemDialogController', SdOrderItemDialogController);

    SdOrderItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdOrderItem'];

    function SdOrderItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdOrderItem) {
        var vm = this;

        vm.sdOrderItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

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

        vm.datePickerOpenStatus.itemNo = false;
        vm.datePickerOpenStatus.consignDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
