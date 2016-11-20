(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCarInfoDetailController', SdCarInfoDetailController);

    SdCarInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdCarInfo'];

    function SdCarInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, SdCarInfo) {
        var vm = this;

        vm.sdCarInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdCarInfoUpdate', function(event, result) {
            vm.sdCarInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
