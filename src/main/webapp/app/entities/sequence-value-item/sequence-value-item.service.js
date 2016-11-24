(function() {
    'use strict';
    angular
        .module('sudeApp')
        .factory('SequenceValueItem', SequenceValueItem).factory('SequenceValue', SequenceValue);

    SequenceValueItem.$inject = ['$resource'];
    SequenceValue.$inject = ['$resource'];

    function SequenceValueItem ($resource) {
        var resourceUrl =  'api/sequence-value-items/:id';

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
    function SequenceValue ($resource) {
    	var resourceUrl =  'api/getNextSeqIdLong/:id';
    	
    	return $resource(resourceUrl, {}, {
    		'getNextSeqId': { method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
    		}
    	});
    }
})();
