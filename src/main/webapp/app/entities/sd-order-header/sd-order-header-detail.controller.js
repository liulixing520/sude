(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdOrderHeaderDetailController', SdOrderHeaderDetailController);

    SdOrderHeaderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdOrderHeader'];

    function SdOrderHeaderDetailController($scope, $rootScope, $stateParams, previousState, entity, SdOrderHeader) {
        var vm = this;

        vm.sdOrderHeader = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdOrderHeaderUpdate', function(event, result) {
            vm.sdOrderHeader = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
