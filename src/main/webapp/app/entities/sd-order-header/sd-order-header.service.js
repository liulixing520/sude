(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdOrderHeader', SdOrderHeader).factory('SdOrderHeaders', SdOrderHeaders);

    SdOrderHeader.$inject = ['$resource', 'DateUtils'];
    SdOrderHeaders.$inject = ['$resource', 'DateUtils'];

    function SdOrderHeader ($resource, DateUtils) {
        var resourceUrl =  'api/sd-order-headers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.departureTime = DateUtils.convertDateTimeFromServer(data.departureTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    
    function SdOrderHeaders ($resource, DateUtils) {
    	var resourceUrl =  'api/sdOrderHeaders';
    	
    	return $resource(resourceUrl, {}, {
    		'get': { method: 'GET'}
    	});
    }
})();
