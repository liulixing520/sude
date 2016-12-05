(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService','SdStation'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, JhiLanguageService,SdStation) {
        var vm = this;

        vm.authorities = [{name:'ROLE_ADMIN',description:"管理员"},{name:'ROLE_FINANCE',description:"财务角色"},{name:'ROLE_USER',description:"普通角色"}];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.sdStations = SdStation.query({page: 0,size: 100,sort: null});

        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
