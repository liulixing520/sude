(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemDeleteController',SdOrderItemDeleteController);

    SdOrderItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdOrderItem'];

    function SdOrderItemDeleteController($uibModalInstance, entity, SdOrderItem) {
        var vm = this;

        vm.sdOrderItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdOrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
