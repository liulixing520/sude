(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdItemInfoDialogController', SdItemInfoDialogController);

    SdItemInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdItemInfo'];

    function SdItemInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdItemInfo) {
        var vm = this;

        vm.sdItemInfo = entity;
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
            if (vm.sdItemInfo.id !== null) {
                SdItemInfo.update(vm.sdItemInfo, onSaveSuccess, onSaveError);
            } else {
                SdItemInfo.save(vm.sdItemInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdItemInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
