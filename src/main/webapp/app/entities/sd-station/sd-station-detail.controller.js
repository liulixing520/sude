(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdStationDetailController', SdStationDetailController);

    SdStationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdStation'];

    function SdStationDetailController($scope, $rootScope, $stateParams, previousState, entity, SdStation) {
        var vm = this;

        vm.sdStation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdStationUpdate', function(event, result) {
            vm.sdStation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
