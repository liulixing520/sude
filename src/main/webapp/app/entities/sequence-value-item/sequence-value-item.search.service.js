(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SequenceValueItemSearch', SequenceValueItemSearch);

    SequenceValueItemSearch.$inject = ['$resource'];

    function SequenceValueItemSearch($resource) {
        var resourceUrl =  'api/_search/sequence-value-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
