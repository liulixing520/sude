(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SdStation', SdStation).factory('OneSdStation', OneSdStation);

    SdStation.$inject = ['$resource'];
    OneSdStation.$inject = ['$resource','$q','$http'];

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
    
    function OneSdStation ($resource,$q,$http) {
    	var resourceUrl =  'api/get-one-station';
        return {
            query: function(id) {
                var defer = $q.defer();//声明延后执行
                $http({
                    method: 'GET',
                    url: resourceUrl,//获取json数据
                    params: {
                    	id: id
                    }
                }).success(function(data, status, headers, config) {
                    defer.resolve(data);//执行成功
                }).error(function(data, status, headers, config) {
                    defer.reject();//执行失败
                });
                return defer.promise;//返回获取的数据
            }
        }
    }
})();
