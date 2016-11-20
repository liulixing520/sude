(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationTypeDeleteController',EnumerationTypeDeleteController);

    EnumerationTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EnumerationType'];

    function EnumerationTypeDeleteController($uibModalInstance, entity, EnumerationType) {
        var vm = this;

        vm.enumerationType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EnumerationType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
