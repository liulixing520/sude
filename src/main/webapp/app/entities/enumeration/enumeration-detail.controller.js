(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationDetailController', EnumerationDetailController);

    EnumerationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enumeration'];

    function EnumerationDetailController($scope, $rootScope, $stateParams, previousState, entity, Enumeration) {
        var vm = this;

        vm.enumeration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:enumerationUpdate', function(event, result) {
            vm.enumeration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
