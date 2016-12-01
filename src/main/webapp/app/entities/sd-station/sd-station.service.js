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
    	var resourceUrl =  'api/get-one-station/:id';
        return function(){
            var defer = $q.defer();
            $http.get(resourceUrl).then(function(d){
                defer.resolve(d);
            },function(err){
                defer.reject(err);
            });
            return defer.promise;
        }
    }
})();
