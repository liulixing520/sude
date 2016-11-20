(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdOrderItemSearch', SdOrderItemSearch);

    SdOrderItemSearch.$inject = ['$resource'];

    function SdOrderItemSearch($resource) {
        var resourceUrl =  'api/_search/sd-order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
