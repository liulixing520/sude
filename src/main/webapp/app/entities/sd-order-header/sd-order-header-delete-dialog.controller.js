(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderHeaderDeleteController',SdOrderHeaderDeleteController);

    SdOrderHeaderDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdOrderHeader'];

    function SdOrderHeaderDeleteController($uibModalInstance, entity, SdOrderHeader) {
        var vm = this;

        vm.sdOrderHeader = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdOrderHeader.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
