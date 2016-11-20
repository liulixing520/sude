(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdDriverDialogController', SdDriverDialogController);

    SdDriverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdDriver'];

    function SdDriverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdDriver) {
        var vm = this;

        vm.sdDriver = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sdDriver.id !== null) {
                SdDriver.update(vm.sdDriver, onSaveSuccess, onSaveError);
            } else {
                SdDriver.save(vm.sdDriver, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdDriverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
