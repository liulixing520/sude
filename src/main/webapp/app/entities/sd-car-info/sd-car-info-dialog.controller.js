(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCarInfoDialogController', SdCarInfoDialogController);

    SdCarInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdCarInfo'];

    function SdCarInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdCarInfo) {
        var vm = this;

        vm.sdCarInfo = entity;
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
            if (vm.sdCarInfo.id !== null) {
                SdCarInfo.update(vm.sdCarInfo, onSaveSuccess, onSaveError);
            } else {
                SdCarInfo.save(vm.sdCarInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdCarInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.buyDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
