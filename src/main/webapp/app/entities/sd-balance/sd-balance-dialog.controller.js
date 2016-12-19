(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdBalanceDialogController', SdBalanceDialogController);

    SdBalanceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdBalance', 'Enumeration'];

    function SdBalanceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdBalance, Enumeration) {
        var vm = this;

        vm.sdBalance = entity;
        vm.clear = clear;
        vm.save = save;
        vm.enumerations = Enumeration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sdBalance.id !== null) {
                SdBalance.update(vm.sdBalance, onSaveSuccess, onSaveError);
            } else {
                SdBalance.save(vm.sdBalance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdBalanceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
