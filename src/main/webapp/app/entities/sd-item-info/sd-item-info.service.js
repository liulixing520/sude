(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdItemInfo', SdItemInfo).factory('SdItemInfos', SdItemInfos);

    SdItemInfo.$inject = ['$resource'];
    SdItemInfos.$inject = ['$resource'];

    function SdItemInfo ($resource) {
        var resourceUrl =  'api/sd-item-infos/:id';

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
    
    function SdItemInfos ($resource) {
    	var resourceUrl =  'api/getItemInfoByOrderNo/:id';
    	
    	return $resource(resourceUrl, {}, {
    		'query': { method: 'GET', isArray: true}
    	});
    }
})();
