(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdStationSearch', SdStationSearch);

    SdStationSearch.$inject = ['$resource'];

    function SdStationSearch($resource) {
        var resourceUrl =  'api/_search/sd-stations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
