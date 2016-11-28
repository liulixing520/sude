(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdStationDeleteController',SdStationDeleteController);

    SdStationDeleteController.$inject = ['$uibModalInstance', 'entity', 'SdStation'];

    function SdStationDeleteController($uibModalInstance, entity, SdStation) {
        var vm = this;

        vm.sdStation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SdStation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
