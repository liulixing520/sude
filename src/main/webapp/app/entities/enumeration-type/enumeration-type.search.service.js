(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('EnumerationTypeSearch', EnumerationTypeSearch);

    EnumerationTypeSearch.$inject = ['$resource'];

    function EnumerationTypeSearch($resource) {
        var resourceUrl =  'api/_search/enumeration-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
