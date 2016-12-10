(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-order-header', {
            parent: 'orderManager',
            url: '/sd-order-header?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdOrderHeader.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-order-header/sd-order-headers.html',
                    controller: 'SdOrderHeaderController',
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
                    $translatePartialLoader.addPart('sdOrderHeader');
                    $translatePartialLoader.addPart('sdOrderItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-order-header-detail', {
            parent: 'orderManager',
            url: '/sd-order-header/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdOrderHeader.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-order-header/sd-order-header-detail.html',
                    controller: 'SdOrderHeaderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdOrderHeader');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdOrderHeader', function($stateParams, SdOrderHeader) {
                    return SdOrderHeader.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-order-header',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-order-header-detail.edit', {
            parent: 'sd-order-header-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-header/sd-order-header-dialog.html',
                    controller: 'SdOrderHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdOrderHeader', function(SdOrderHeader) {
                            return SdOrderHeader.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-order-header.new', {
            parent: 'sd-order-item-loading',
            url: '/new?ids',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-header/sd-order-header-dialog.html',
                    controller: 'SdOrderHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderHeaderNo: null,
                                carNo: null,
                                driverName: null,
                                mobilePhone: null,
                                departBatch: null,
                                fromStation: null,
                                toStation: null,
                                unloadPlace: null,
                                cashPay: null,
                                driverCollection: null,
                                handlingCharges: null,
                                receiveShipment: null,
                                otherPay: null,
                                departureTime: null,
                                weight: null,
                                practical: null,
                                loadParter: null,
                                reply: null,
                                oilCardNo: null,
                                oilCardBlance: null,
                                freightSum: null,
                                arriveFreight: null,
                                arriveDriver: null,
                                orderHeadStat: 'ORDER_HEAD_STAT_1',
                                orderHeadStatName: '进行中',
                                id: null
                            };
                        },
//                        orderItems: ['SdOrderItemQuery',function(SdOrderItemQuery){
//                        	var list = SdOrderItemQuery.query({ids:$stateParams.ids});
//                        	return list;
//                        }],
                        sequence: ['SdOrderHeaders',function(SdOrderHeaders){
                        	return SdOrderHeaders.get().$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-order-header', null, { reload: 'sd-order-header' });
                }, function() {
                    $state.go('sd-order-item-loading');
                });
            }]
        })
        .state('sd-order-header.edit', {
            parent: 'sd-order-header',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-header/sd-order-header-dialog.html',
                    controller: 'SdOrderHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdOrderHeader', function(SdOrderHeader) {
                            return SdOrderHeader.get({id : $stateParams.id}).$promise;
                        }],
//                        orderItems: ['SdOrderItemQuery',function(SdOrderItemQuery){
//                        	var list = SdOrderItemQuery.get({orderHeaderNo:$stateParams.id});
//                        	return list;
//                        }],
                        sequence: ['SdOrderHeaders',function(SdOrderHeaders){
                        	return SdOrderHeaders.get().$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-order-header', null, { reload: 'sd-order-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-order-header.delete', {
            parent: 'sd-order-header',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-order-header/sd-order-header-delete-dialog.html',
                    controller: 'SdOrderHeaderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdOrderHeader', function(SdOrderHeader) {
                            return SdOrderHeader.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-order-header', null, { reload: 'sd-order-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
