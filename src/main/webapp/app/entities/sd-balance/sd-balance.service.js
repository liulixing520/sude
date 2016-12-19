(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdBalance', SdBalance);

    SdBalance.$inject = ['$resource'];

    function SdBalance ($resource) {
        var resourceUrl =  'api/sd-balances/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
