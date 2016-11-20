(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdCarInfo', SdCarInfo);

    SdCarInfo.$inject = ['$resource', 'DateUtils'];

    function SdCarInfo ($resource, DateUtils) {
        var resourceUrl =  'api/sd-car-infos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.buyDate = DateUtils.convertLocalDateFromServer(data.buyDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.buyDate = DateUtils.convertLocalDateToServer(copy.buyDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.buyDate = DateUtils.convertLocalDateToServer(copy.buyDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
