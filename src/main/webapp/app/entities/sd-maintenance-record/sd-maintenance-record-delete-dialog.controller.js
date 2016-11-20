(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdMaintenanceRecordDeleteController',SdMaintenanceRecordDeleteController);

    SdMaintenanceRecordDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdMaintenanceRecord'];

    function SdMaintenanceRecordDeleteController($uibModalInstance, entity, SdMaintenanceRecord) {
        var vm = this;

        vm.sdMaintenanceRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdMaintenanceRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
