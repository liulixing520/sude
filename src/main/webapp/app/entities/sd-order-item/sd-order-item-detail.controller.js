(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderItemDetailController', SdOrderItemDetailController);

    SdOrderItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdOrderItem'];

    function SdOrderItemDetailController($scope, $rootScope, $stateParams, previousState, entity, SdOrderItem) {
        var vm = this;

        vm.sdOrderItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdOrderItemUpdate', function(event, result) {
            vm.sdOrderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
