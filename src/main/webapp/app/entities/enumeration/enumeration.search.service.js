(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('EnumerationSearch', EnumerationSearch);

    EnumerationSearch.$inject = ['$resource'];

    function EnumerationSearch($resource) {
        var resourceUrl =  'api/_search/enumerations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
