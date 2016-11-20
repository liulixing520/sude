(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdDriver', SdDriver);

    SdDriver.$inject = ['$resource'];

    function SdDriver ($resource) {
        var resourceUrl =  'api/sd-drivers/:id';

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
