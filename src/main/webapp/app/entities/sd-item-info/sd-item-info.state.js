(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-item-info', {
            parent: 'entity',
            url: '/sd-item-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdItemInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-item-info/sd-item-infos.html',
                    controller: 'SdItemInfoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'createdDate,desc',
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
                    $translatePartialLoader.addPart('sdItemInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-item-info-detail', {
            parent: 'entity',
            url: '/sd-item-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdItemInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-item-info/sd-item-info-detail.html',
                    controller: 'SdItemInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdItemInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdItemInfo', function($stateParams, SdItemInfo) {
                    return SdItemInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-item-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-item-info-detail.edit', {
            parent: 'sd-item-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-item-info/sd-item-info-dialog.html',
                    controller: 'SdItemInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdItemInfo', function(SdItemInfo) {
                            return SdItemInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-item-info.new', {
            parent: 'sd-item-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-item-info/sd-item-info-dialog.html',
                    controller: 'SdItemInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderNo: null,
                                tradName: null,
                                itemNum: null,
                                weight: null,
                                volume: null,
                                itemUnit: null,
                                freight: null,
                                kickBack: null,
                                cod: null,
                                deliveryExpense: null,
                                claimingValue: null,
                                premium: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-item-info', null, { reload: 'sd-item-info' });
                }, function() {
                    $state.go('sd-item-info');
                });
            }]
        })
        .state('sd-item-info.edit', {
            parent: 'sd-item-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-item-info/sd-item-info-dialog.html',
                    controller: 'SdItemInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdItemInfo', function(SdItemInfo) {
                            return SdItemInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-item-info', null, { reload: 'sd-item-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-item-info.delete', {
            parent: 'sd-item-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-item-info/sd-item-info-delete-dialog.html',
                    controller: 'SdItemInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdItemInfo', function(SdItemInfo) {
                            return SdItemInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-item-info', null, { reload: 'sd-item-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
