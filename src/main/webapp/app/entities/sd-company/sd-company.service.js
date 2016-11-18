(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdCompany', SdCompany);

    SdCompany.$inject = ['$resource'];

    function SdCompany ($resource) {
        var resourceUrl =  'api/sd-companies/:id';

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
