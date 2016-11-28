(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('EnumerationType', EnumerationType).factory('EnumerationTypes', EnumerationTypes);

    EnumerationType.$inject = ['$resource'];
    EnumerationTypes.$inject = ['$resource'];

    function EnumerationType ($resource) {
        var resourceUrl =  'api/enumeration-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    function EnumerationTypes ($resource) {
    	var resourceUrl =  'api/getAllEnumerationType';
    	
    	return $resource(resourceUrl, {}, {
    		'query': { method: 'GET', isArray: true}
    	});
    }
})();
