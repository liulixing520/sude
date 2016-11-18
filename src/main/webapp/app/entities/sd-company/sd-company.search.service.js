(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdCompanySearch', SdCompanySearch);

    SdCompanySearch.$inject = ['$resource'];

    function SdCompanySearch($resource) {
        var resourceUrl =  'api/_search/sd-companies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
