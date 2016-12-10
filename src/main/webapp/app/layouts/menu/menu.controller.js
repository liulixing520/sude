(function() {
    'use strict';

    angular
        .module('sudeApp')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['$state','$scope','$rootScope', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function MenuController ($state,$scope,$rootScope, Auth, Principal, ProfileService, LoginService) {
        var vm = this;
        vm.$state = $state;
    }
})();
