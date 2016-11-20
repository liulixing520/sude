(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationDeleteController',EnumerationDeleteController);

    EnumerationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Enumeration'];

    function EnumerationDeleteController($uibModalInstance, entity, Enumeration) {
        var vm = this;

        vm.enumeration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Enumeration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
