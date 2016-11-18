(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCompanyDeleteController',SdCompanyDeleteController);

    SdCompanyDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdCompany'];

    function SdCompanyDeleteController($uibModalInstance, entity, SdCompany) {
        var vm = this;

        vm.sdCompany = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdCompany.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
