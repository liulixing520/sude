(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-company', {
            parent: 'baseInfo',
            url: '/sd-company?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCompany.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-company/sd-companies.html',
                    controller: 'SdCompanyController',
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
                    $translatePartialLoader.addPart('sdCompany');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-company-detail', {
            parent: 'baseInfo',
            url: '/sd-company/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCompany.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-company/sd-company-detail.html',
                    controller: 'SdCompanyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdCompany');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdCompany', function($stateParams, SdCompany) {
                    return SdCompany.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-company',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-company-detail.edit', {
            parent: 'sd-company-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-company/sd-company-dialog.html',
                    controller: 'SdCompanyDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdCompany');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdCompany', function($stateParams, SdCompany) {
                    return SdCompany.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-company',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-company/sd-company-dialog.html',
                    controller: 'SdCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCompany', function(SdCompany) {
                            return SdCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-company.new', {
            parent: 'sd-company',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-company/sd-company-dialog.html',
                    controller: 'SdCompanyDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            	entity: ['SdCompany', function(SdCompany) {
                    return SdCompany.get({id:'1'});
                }]
            }
        })
        .state('sd-company.edit', {
            parent: 'sd-company',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-company/sd-company-dialog.html',
                    controller: 'SdCompanyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCompany', function(SdCompany) {
                            return SdCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-company', null, { reload: 'sd-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-company.delete', {
            parent: 'sd-company',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-company/sd-company-delete-dialog.html',
                    controller: 'SdCompanyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdCompany', function(SdCompany) {
                            return SdCompany.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-company', null, { reload: 'sd-company' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
