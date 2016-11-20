(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdOrderItem', SdOrderItem);

    SdOrderItem.$inject = ['$resource', 'DateUtils'];

    function SdOrderItem ($resource, DateUtils) {
        var resourceUrl =  'api/sd-order-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.itemNo = DateUtils.convertLocalDateFromServer(data.itemNo);
                        data.consignDate = DateUtils.convertLocalDateFromServer(data.consignDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.itemNo = DateUtils.convertLocalDateToServer(copy.itemNo);
                    copy.consignDate = DateUtils.convertLocalDateToServer(copy.consignDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.itemNo = DateUtils.convertLocalDateToServer(copy.itemNo);
                    copy.consignDate = DateUtils.convertLocalDateToServer(copy.consignDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
