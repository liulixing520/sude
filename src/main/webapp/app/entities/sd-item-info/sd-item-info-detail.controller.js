(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdItemInfoDetailController', SdItemInfoDetailController);

    SdItemInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdItemInfo'];

    function SdItemInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, SdItemInfo) {
        var vm = this;

        vm.sdItemInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdItemInfoUpdate', function(event, result) {
            vm.sdItemInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
