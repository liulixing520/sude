(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdBalanceDeleteController',SdBalanceDeleteController);

    SdBalanceDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdBalance'];

    function SdBalanceDeleteController($uibModalInstance, entity, SdBalance) {
        var vm = this;

        vm.sdBalance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdBalance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
