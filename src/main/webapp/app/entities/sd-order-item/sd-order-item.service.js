(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdOrderItem', SdOrderItem).factory('SdOrderItemLoading', SdOrderItemLoading)
        .factory('SdOrderItemUpdate', SdOrderItemUpdate)
        .factory('SdOrderItemQuery', SdOrderItemQuery)
        ;

    SdOrderItem.$inject = ['$resource', 'DateUtils'];
    SdOrderItemLoading.$inject = ['$resource', 'DateUtils'];
    SdOrderItemUpdate.$inject = ['$resource'];
    SdOrderItemQuery.$inject = ['$q', '$http'];

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
    
    function SdOrderItemQuery ($q, $http) {
    	
    	var dataPromise;

        var service = {
        		getByIds : getByIds,
        		getByOrderNo : getByOrderNo
        };

        return service;

        function getByIds(ids) {
            dataPromise = $http.get('api/sd-order-items-query/',{params: ids}).then(function(result) {
                if (result.data) {
                    var response = {};
                    response.data = result.data;
                    return response;
                }
            });
            return dataPromise;
        }
        
        function getByOrderNo(orderHeaderNo) {
        	dataPromise = $http.get('api/queryItemsByOrderNo/',{params: orderHeaderNo}).then(function(result) {
                if (result.data) {
                    var response = {};
                    response.data = result.data;
                    return response;
                }
            });
            return dataPromise;
        }
        
//    	var resourceUrl =  'api/sd-order-items-query/:orderHeaderNo';
//    	
//    	return $resource(resourceUrl, {}, {
//    		'query': { method: 'GET', isArray: true},
//    		'get': { method: 'GET', isArray: true}
//    	});
    }
    
    function SdOrderItemUpdate ($resource, DateUtils) {
    	var resourceUrl =  'api/sd-order-items-update';
    	return $resource(resourceUrl, {}, {
    		'update': {method: 'GET'}
    	});
    }
    

})();
