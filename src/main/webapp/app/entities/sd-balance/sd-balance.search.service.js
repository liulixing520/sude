(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdBalanceSearch', SdBalanceSearch);

    SdBalanceSearch.$inject = ['$resource'];

    function SdBalanceSearch($resource) {
        var resourceUrl =  'api/_search/sd-balances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
