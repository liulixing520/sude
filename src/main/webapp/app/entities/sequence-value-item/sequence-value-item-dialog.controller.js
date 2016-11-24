(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SequenceValueItemDialogController', SequenceValueItemDialogController);

    SequenceValueItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SequenceValueItem'];

    function SequenceValueItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SequenceValueItem) {
        var vm = this;

        vm.sequenceValueItem = entity;
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
            if (vm.sequenceValueItem.id !== null) {
                SequenceValueItem.update(vm.sequenceValueItem, onSaveSuccess, onSaveError);
            } else {
                SequenceValueItem.save(vm.sequenceValueItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sequenceValueItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
