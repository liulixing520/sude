(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('EnumerationType', EnumerationType);

    EnumerationType.$inject = ['$resource'];

    function EnumerationType ($resource) {
        var resourceUrl =  'api/enumeration-types/:id';

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
