(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('LoginService', LoginService);

    LoginService.$inject = ['$uibModal','$state'];

    function LoginService ($uibModal,$state) {
        var service = {
            open: open
        };

        var modalInstance = null;
        var resetModal = function () {
            modalInstance = null;
        };

        return service;

        function open () {
        	$state.go('login');
//            if (modalInstance !== null) return;
//            modalInstance = $uibModal.open({
//                animation: true,
//                templateUrl: 'app/components/login/login.html',
//                controller: 'LoginController',
//                controllerAs: 'vm',
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('login');
//                        return $translate.refresh();
//                    }]
//                }
//            });
//            modalInstance.result.then(
//                resetModal,
//                resetModal
//            );
        }
    }
})();
