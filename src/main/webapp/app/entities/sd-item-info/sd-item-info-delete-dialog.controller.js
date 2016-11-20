(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdItemInfoDeleteController',SdItemInfoDeleteController);

    SdItemInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdItemInfo'];

    function SdItemInfoDeleteController($uibModalInstance, entity, SdItemInfo) {
        var vm = this;

        vm.sdItemInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdItemInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
