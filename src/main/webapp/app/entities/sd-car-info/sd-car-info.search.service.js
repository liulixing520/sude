(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdCarInfoSearch', SdCarInfoSearch).factory('SdCarInfoSearchByCarNo', SdCarInfoSearchByCarNo);

    SdCarInfoSearch.$inject = ['$resource'];
    SdCarInfoSearchByCarNo.$inject = ['$resource'];

    function SdCarInfoSearch($resource) {
        var resourceUrl =  'api/_search/sd-car-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
    function SdCarInfoSearchByCarNo($resource) {
    	var resourceUrl =  'api/_search/searchSdCarInfoByCarNo/:carNo';
    	
    	return $resource(resourceUrl, {}, {
    		'query': { method: 'GET', isArray: true}
    	});
    }
    
})();
