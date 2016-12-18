(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-customer', {
            parent: 'custManager',
            url: '/sd-customer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCustomer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-customer/sd-customers.html',
                    controller: 'SdCustomerController',
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
                    $translatePartialLoader.addPart('sdCustomer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-customer-detail', {
            parent: 'custManager',
            url: '/sd-customer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCustomer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-customer/sd-customer-detail.html',
                    controller: 'SdCustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdCustomer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdCustomer', function($stateParams, SdCustomer) {
                    return SdCustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-customer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-customer-detail.edit', {
            parent: 'sd-customer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-customer/sd-customer-dialog.html',
                    controller: 'SdCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCustomer', function(SdCustomer) {
                            return SdCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-customer.new', {
            parent: 'sd-customer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-customer/sd-customer-dialog.html',
                    controller: 'SdCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                customerName: null,
                                customerNM: null,
                                sex: null,
                                mobilePhone: null,
                                idCard: null,
                                address: null,
                                bank: null,
                                bankNo: null,
                                bankOpenName: null,
                                company: null,
                                custType: null,
                                remark: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-customer', null, { reload: 'sd-customer' });
                }, function() {
                    $state.go('sd-customer');
                });
            }]
        })
        .state('sd-customer.edit', {
            parent: 'sd-customer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-customer/sd-customer-dialog.html',
                    controller: 'SdCustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCustomer', function(SdCustomer) {
                            return SdCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-customer', null, { reload: 'sd-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-customer.delete', {
            parent: 'sd-customer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-customer/sd-customer-delete-dialog.html',
                    controller: 'SdCustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdCustomer', function(SdCustomer) {
                            return SdCustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-customer', null, { reload: 'sd-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
