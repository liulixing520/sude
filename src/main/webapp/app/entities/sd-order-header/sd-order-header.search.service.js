(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdOrderHeaderSearch', SdOrderHeaderSearch);

    SdOrderHeaderSearch.$inject = ['$resource'];

    function SdOrderHeaderSearch($resource) {
        var resourceUrl =  'api/_search/sd-order-headers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
