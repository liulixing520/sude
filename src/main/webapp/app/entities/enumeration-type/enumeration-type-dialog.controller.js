(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationTypeDialogController', EnumerationTypeDialogController);

    EnumerationTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EnumerationType'];

    function EnumerationTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EnumerationType) {
        var vm = this;

        vm.enumerationType = entity;
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
            if (vm.enumerationType.id !== null) {
                EnumerationType.update(vm.enumerationType, onSaveSuccess, onSaveError);
            } else {
                EnumerationType.save(vm.enumerationType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:enumerationTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
