(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('SequenceValueItemDetailController', SequenceValueItemDetailController);

    SequenceValueItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SequenceValueItem'];

    function SequenceValueItemDetailController($scope, $rootScope, $stateParams, previousState, entity, SequenceValueItem) {
        var vm = this;

        vm.sequenceValueItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sudeApp:sequenceValueItemUpdate', function(event, result) {
            vm.sequenceValueItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
