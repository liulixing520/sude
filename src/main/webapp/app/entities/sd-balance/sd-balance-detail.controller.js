(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdBalanceDetailController', SdBalanceDetailController);

    SdBalanceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdBalance', 'Enumeration'];

    function SdBalanceDetailController($scope, $rootScope, $stateParams, previousState, entity, SdBalance, Enumeration) {
        var vm = this;

        vm.sdBalance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdBalanceUpdate', function(event, result) {
            vm.sdBalance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
