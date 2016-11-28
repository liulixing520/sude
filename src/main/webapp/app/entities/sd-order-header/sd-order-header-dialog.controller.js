(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderHeaderDialogController', SdOrderHeaderDialogController);

    SdOrderHeaderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdOrderHeader'];

    function SdOrderHeaderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdOrderHeader) {
        var vm = this;

        vm.sdOrderHeader = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ids = $stateParams.ids;
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
    }
})();
