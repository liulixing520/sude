(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdStation', SdStation);

    SdStation.$inject = ['$resource'];

    function SdStation ($resource) {
        var resourceUrl =  'api/sd-stations/:id';

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
