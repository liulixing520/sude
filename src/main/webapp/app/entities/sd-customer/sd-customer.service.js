(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdCustomer', SdCustomer).factory('SearchSdCustomers', SearchSdCustomers);

    SdCustomer.$inject = ['$resource'];
    SearchSdCustomers.$inject = ['$resource'];

    function SdCustomer ($resource) {
        var resourceUrl =  'api/sd-customers/:id';

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
    
    function SearchSdCustomers ($resource) {
    	var resourceUrl =  'api/_search/searchSdCustomers';
    	
    	return $resource(resourceUrl, {}, {
    		'query': { method: 'GET', isArray: true}
    	});
    }
})();
