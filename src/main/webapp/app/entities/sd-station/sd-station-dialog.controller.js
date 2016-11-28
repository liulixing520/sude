(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdStationDialogController', SdStationDialogController);

    SdStationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SdStation'];

    function SdStationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SdStation) {
        var vm = this;

        vm.sdStation = entity;
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
            if (vm.sdStation.id !== null) {
                SdStation.update(vm.sdStation, onSaveSuccess, onSaveError);
            } else {
                SdStation.save(vm.sdStation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdStationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
