(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdDriverSearch', SdDriverSearch);

    SdDriverSearch.$inject = ['$resource'];

    function SdDriverSearch($resource) {
        var resourceUrl =  'api/_search/sd-drivers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
