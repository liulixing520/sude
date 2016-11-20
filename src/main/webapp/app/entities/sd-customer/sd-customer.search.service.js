(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdCustomerSearch', SdCustomerSearch);

    SdCustomerSearch.$inject = ['$resource'];

    function SdCustomerSearch($resource) {
        var resourceUrl =  'api/_search/sd-customers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
