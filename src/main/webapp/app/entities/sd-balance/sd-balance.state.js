(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-balance', {
            parent: 'financialManager',
            url: '/sd-balance?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdBalance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-balance/sd-balances.html',
                    controller: 'SdBalanceController',
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
                    $translatePartialLoader.addPart('sdBalance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-balance-detail', {
            parent: 'financialManager',
            url: '/sd-balance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdBalance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-balance/sd-balance-detail.html',
                    controller: 'SdBalanceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdBalance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdBalance', function($stateParams, SdBalance) {
                    return SdBalance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-balance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-balance-detail.edit', {
            parent: 'sd-balance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-balance/sd-balance-dialog.html',
                    controller: 'SdBalanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdBalance', function(SdBalance) {
                            return SdBalance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-balance.new', {
            parent: 'sd-balance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-balance/sd-balance-dialog.html',
                    controller: 'SdBalanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                orderNo: null,
                                summary: null,
                                money: null,
                                remark: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-balance', null, { reload: 'sd-balance' });
                }, function() {
                    $state.go('sd-balance');
                });
            }]
        })
        .state('sd-balance.edit', {
            parent: 'sd-balance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-balance/sd-balance-dialog.html',
                    controller: 'SdBalanceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdBalance', function(SdBalance) {
                            return SdBalance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-balance', null, { reload: 'sd-balance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-balance.delete', {
            parent: 'sd-balance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-balance/sd-balance-delete-dialog.html',
                    controller: 'SdBalanceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdBalance', function(SdBalance) {
                            return SdBalance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-balance', null, { reload: 'sd-balance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
