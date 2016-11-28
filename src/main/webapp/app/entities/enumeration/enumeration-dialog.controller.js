(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationDialogController', EnumerationDialogController);

    EnumerationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enumeration','EnumerationTypes'];

    function EnumerationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enumeration,EnumerationTypes) {
        var vm = this;

        vm.enumeration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.enumerationTypes = EnumerationTypes.query();
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
//            if (vm.enumeration.id !== null) {
//                Enumeration.update(vm.enumeration, onSaveSuccess, onSaveError);
//            } else {
                Enumeration.save(vm.enumeration, onSaveSuccess, onSaveError);
//            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:enumerationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
