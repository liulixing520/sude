(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('EnumerationTypeDetailController', EnumerationTypeDetailController);

    EnumerationTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EnumerationType'];

    function EnumerationTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, EnumerationType) {
        var vm = this;

        vm.enumerationType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:enumerationTypeUpdate', function(event, result) {
            vm.enumerationType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
