(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-order-item', {
            parent: 'orderManager',
            url: '/sd-order-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdOrderItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-order-item/sd-order-items.html',
                    controller: 'SdOrderItemController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdOrderItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-order-item-detail', {
            parent: 'orderManager',
            url: '/sd-order-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdOrderItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-order-item/sd-order-item-detail.html',
                    controller: 'SdOrderItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdOrderItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdOrderItem', function($stateParams, SdOrderItem) {
                    return SdOrderItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-order-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-order-item-detail.edit', {
            parent: 'sd-order-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-item/sd-order-item-dialog.html',
                    controller: 'SdOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdOrderItem', function(SdOrderItem) {
                            return SdOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-order-item.new', {
            parent: 'sd-order-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal','SequenceValue', function($stateParams, $state, $uibModal,SequenceValue) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-item/sd-order-item-dialog.html',
                    controller: 'SdOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                        	var orderNo = SequenceValue.getNextSeqId({id:"SdOrderItem"}).$promise;
                            return {
                                orderNo: orderNo,
                                orderHeaderNo: null,
                                itemNo: null,
                                consignDate: null,
                                fromStation: null,
                                toStation: null,
                                middleStation: null,
                                consignerId: null,
                                consignerName: null,
                                consignerAddress: null,
                                consignerPhone: null,
                                consignerMbPhone: null,
                                consigneeId: null,
                                consigneeName: null,
                                consigneePhone: null,
                                consigneeMbPhone: null,
                                consigneeAddress: null,
                                bankNo: null,
                                bankName: null,
                                openName: null,
                                idCard: null,
                                payType: null,
                                cashPay: null,
                                fetchPay: null,
                                receiptPay: null,
                                monthPay: null,
                                chargePay: null,
                                transportType: null,
                                backRequire: null,
                                handOverType: null,
                                otherPay: null,
                                payExplain: null,
                                remark: null,
                                kickBack: null,
                                cashOwe: null,
                                requireItem: null,
                                tagged: null,
                                envelopes: null,
                                salesMan: null,
                                operator: null,
                                orderStat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-order-item', null, { reload: 'sd-order-item' });
                }, function() {
                    $state.go('sd-order-item');
                });
            }]
        })
        .state('sd-order-item.edit', {
            parent: 'sd-order-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-item/sd-order-item-dialog.html',
                    controller: 'SdOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdOrderItem', function(SdOrderItem) {
                            return SdOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-order-item', null, { reload: 'sd-order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-order-item.delete', {
            parent: 'sd-order-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-item/sd-order-item-delete-dialog.html',
                    controller: 'SdOrderItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdOrderItem', function(SdOrderItem) {
                            return SdOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-order-item', null, { reload: 'sd-order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
