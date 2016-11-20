(function() {
    'use strict';

    angular
        .module('sudeApp')
        .factory('SdMaintenanceRecordSearch', SdMaintenanceRecordSearch);

    SdMaintenanceRecordSearch.$inject = ['$resource'];

    function SdMaintenanceRecordSearch($resource) {
        var resourceUrl =  'api/_search/sd-maintenance-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
