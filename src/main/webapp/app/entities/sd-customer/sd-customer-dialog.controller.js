(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCustomerDialogController', SdCustomerDialogController);

    SdCustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdCustomer'];

    function SdCustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdCustomer) {
        var vm = this;

        vm.sdCustomer = entity;
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
            if (vm.sdCustomer.id !== null) {
                SdCustomer.update(vm.sdCustomer, onSaveSuccess, onSaveError);
            } else {
                SdCustomer.save(vm.sdCustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdCustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
