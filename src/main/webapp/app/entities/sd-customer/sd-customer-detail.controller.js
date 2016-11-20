(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCustomerDetailController', SdCustomerDetailController);

    SdCustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdCustomer'];

    function SdCustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, SdCustomer) {
        var vm = this;

        vm.sdCustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdCustomerUpdate', function(event, result) {
            vm.sdCustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
