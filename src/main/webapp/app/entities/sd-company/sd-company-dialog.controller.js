(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCompanyDialogController', SdCompanyDialogController);

    SdCompanyDialogController.$inject = ['$timeout', '$scope', '$stateParams',  'entity', 'SdCompany'];

    function SdCompanyDialogController ($timeout, $scope, $stateParams,  entity, SdCompany) {
        var vm = this;

        vm.sdCompany = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
        }

        function save () {
            vm.isSaving = true;
            if (vm.sdCompany.id !== null) {
                SdCompany.update(vm.sdCompany, onSaveSuccess, onSaveError);
            } else {
                SdCompany.save(vm.sdCompany, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sudeApp:sdCompanyUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
