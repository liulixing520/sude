(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdDriverDetailController', SdDriverDetailController);

    SdDriverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdDriver'];

    function SdDriverDetailController($scope, $rootScope, $stateParams, previousState, entity, SdDriver) {
        var vm = this;

        vm.sdDriver = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdDriverUpdate', function(event, result) {
            vm.sdDriver = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
