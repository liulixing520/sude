(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdItemInfoSearch', SdItemInfoSearch);

    SdItemInfoSearch.$inject = ['$resource'];

    function SdItemInfoSearch($resource) {
        var resourceUrl =  'api/_search/sd-item-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
