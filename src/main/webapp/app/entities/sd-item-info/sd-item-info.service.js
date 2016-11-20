(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdItemInfo', SdItemInfo);

    SdItemInfo.$inject = ['$resource'];

    function SdItemInfo ($resource) {
        var resourceUrl =  'api/sd-item-infos/:id';

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
