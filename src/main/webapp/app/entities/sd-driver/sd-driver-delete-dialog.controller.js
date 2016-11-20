(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdDriverDeleteController',SdDriverDeleteController);

    SdDriverDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdDriver'];

    function SdDriverDeleteController($uibModalInstance, entity, SdDriver) {
        var vm = this;

        vm.sdDriver = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdDriver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
