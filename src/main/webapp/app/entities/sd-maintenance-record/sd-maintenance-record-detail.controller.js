(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdMaintenanceRecordDetailController', SdMaintenanceRecordDetailController);

    SdMaintenanceRecordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdMaintenanceRecord'];

    function SdMaintenanceRecordDetailController($scope, $rootScope, $stateParams, previousState, entity, SdMaintenanceRecord) {
        var vm = this;

        vm.sdMaintenanceRecord = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdMaintenanceRecordUpdate', function(event, result) {
            vm.sdMaintenanceRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
