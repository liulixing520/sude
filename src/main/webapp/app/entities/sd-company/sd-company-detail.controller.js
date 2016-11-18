(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SdCompanyDetailController', SdCompanyDetailController);

    SdCompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SdCompany'];

    function SdCompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, SdCompany) {
        var vm = this;

        vm.sdCompany = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sdCompanyUpdate', function(event, result) {
            vm.sdCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
