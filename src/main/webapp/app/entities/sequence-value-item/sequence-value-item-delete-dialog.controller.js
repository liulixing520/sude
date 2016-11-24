(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SequenceValueItemDeleteController',SequenceValueItemDeleteController);

    SequenceValueItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'SequenceValueItem'];

    function SequenceValueItemDeleteController($uibModalInstance, entity, SequenceValueItem) {
        var vm = this;

        vm.sequenceValueItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SequenceValueItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
