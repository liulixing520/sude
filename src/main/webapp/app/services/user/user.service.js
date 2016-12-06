(function () {
    'use strict';

    angular
        .module('sudeApp')
        .factory('User', User)
        .factory('Authority', Authority);

    User.$inject = ['$resource'];
    Authority.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
    function Authority ($resource) {
    	var service = $resource('api/authority', {}, {
    		'query': {method: 'GET', isArray: true},
    	});
    	
    	return service;
    }
})();
