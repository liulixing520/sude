(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdMaintenanceRecord', SdMaintenanceRecord);

    SdMaintenanceRecord.$inject = ['$resource', 'DateUtils'];

    function SdMaintenanceRecord ($resource, DateUtils) {
        var resourceUrl =  'api/sd-maintenance-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.maintainDate = DateUtils.convertLocalDateFromServer(data.maintainDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.maintainDate = DateUtils.convertLocalDateToServer(copy.maintainDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.maintainDate = DateUtils.convertLocalDateToServer(copy.maintainDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
