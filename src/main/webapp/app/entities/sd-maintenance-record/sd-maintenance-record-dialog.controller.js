(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdMaintenanceRecordDialogController', SdMaintenanceRecordDialogController);

    SdMaintenanceRecordDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdMaintenanceRecord'];

    function SdMaintenanceRecordDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdMaintenanceRecord) {
        var vm = this;

        vm.sdMaintenanceRecord = entity;
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
            if (vm.sdMaintenanceRecord.id !== null) {
                SdMaintenanceRecord.update(vm.sdMaintenanceRecord, onSaveSuccess, onSaveError);
            } else {
                SdMaintenanceRecord.save(vm.sdMaintenanceRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdMaintenanceRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.maintainDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
