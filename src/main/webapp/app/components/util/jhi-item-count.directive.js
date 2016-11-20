(function() {
    'use strict';
    
    var jhiItemCount = {
        template: '<div class="info pages ">' +
                    ' 共有{{$ctrl.queryCount}}条 ,显示{{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
                    '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
                    '条.' +
                '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('sudeApp')
        .component('jhiItemCount', jhiItemCount);
})();
