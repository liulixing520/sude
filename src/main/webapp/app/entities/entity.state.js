(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('entity', {
            abstract: true,
            parent: 'app'
        });
        $stateProvider.state('baseInfo', {
        	abstract: true,
        	parent: 'app'
        });
        $stateProvider.state('userManager', {
        	abstract: true,
        	parent: 'app'
        });
        $stateProvider.state('custManager', {
        	abstract: true,
        	parent: 'app'
        });
        $stateProvider.state('carManager', {
        	abstract: true,
        	parent: 'app'
        });
        $stateProvider.state('orderManager', {
        	abstract: true,
        	parent: 'app'
        });
    }
})();
