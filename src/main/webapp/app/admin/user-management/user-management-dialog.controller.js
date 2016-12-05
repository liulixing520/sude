(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams','$scope', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService','SdStation'];

    function UserManagementDialogController ($stateParams,$scope, $uibModalInstance, entity, User, JhiLanguageService,SdStation) {
        var vm = this;

        vm.authorities = [{name:'ROLE_ADMIN',description:"管理员"},{name:'ROLE_FINANCE',description:"财务"},{name:'ROLE_USER',description:"普通人员"}];
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
        
        //选中或不选中
        $scope.updateSelection = function($event, id){
	         var checkbox = $event.target;
	         if(checkbox.checked && vm.user.authorities.indexOf(id) == -1 ){
	        	 vm.user.authorities.push(id);
	         }
	         if(!checkbox.checked && vm.user.authorities.indexOf(id) != -1 ){
	        	 var idx = vm.user.authorities.indexOf(id);
	        	 vm.user.authorities.splice(idx,1);
	         }
	    }
    }
})();
