(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCarInfoDeleteController',SdCarInfoDeleteController);

    SdCarInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdCarInfo'];

    function SdCarInfoDeleteController($uibModalInstance, entity, SdCarInfo) {
        var vm = this;

        vm.sdCarInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdCarInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
