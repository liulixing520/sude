(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('Enumeration', Enumeration);

    Enumeration.$inject = ['$resource'];

    function Enumeration ($resource) {
        var resourceUrl =  'api/enumerations/:id';

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
