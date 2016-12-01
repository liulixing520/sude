(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdOrderItem', SdOrderItem).factory('SdOrderItemLoading', SdOrderItemLoading)
        .factory('SdOrderItemUpdate', SdOrderItemUpdate)
        ;

    SdOrderItem.$inject = ['$resource', 'DateUtils'];
    SdOrderItemLoading.$inject = ['$resource', 'DateUtils'];
    SdOrderItemUpdate.$inject = ['$resource'];

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
    
    function SdOrderItemLoading ($resource, DateUtils) {
    	var resourceUrl =  'api/sd-order-items-loading';
    	
    	return $resource(resourceUrl, {}, {
    		'query': { method: 'GET', isArray: true},
    	});
    }
    
    function SdOrderItemUpdate ($resource, DateUtils) {
    	var resourceUrl =  'api/sd-order-items-update';
    	return $resource(resourceUrl, {}, {
    		'update': {method: 'PUT'}
    	});
    }
})();
