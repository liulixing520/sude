(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdCarInfoSearch', SdCarInfoSearch);

    SdCarInfoSearch.$inject = ['$resource'];

    function SdCarInfoSearch($resource) {
        var resourceUrl =  'api/_search/sd-car-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
