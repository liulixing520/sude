(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCustomerDeleteController',SdCustomerDeleteController);

    SdCustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdCustomer'];

    function SdCustomerDeleteController($uibModalInstance, entity, SdCustomer) {
        var vm = this;

        vm.sdCustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdCustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
