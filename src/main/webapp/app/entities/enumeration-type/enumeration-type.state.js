(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enumeration-type', {
            parent: 'entity',
            url: '/enumeration-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.enumerationType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enumeration-type/enumeration-types.html',
                    controller: 'EnumerationTypeController',
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
                    $translatePartialLoader.addPart('enumerationType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('enumeration-type-detail', {
            parent: 'entity',
            url: '/enumeration-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.enumerationType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enumeration-type/enumeration-type-detail.html',
                    controller: 'EnumerationTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('enumerationType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EnumerationType', function($stateParams, EnumerationType) {
                    return EnumerationType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enumeration-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enumeration-type-detail.edit', {
            parent: 'enumeration-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration-type/enumeration-type-dialog.html',
                    controller: 'EnumerationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EnumerationType', function(EnumerationType) {
                            return EnumerationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enumeration-type.new', {
            parent: 'enumeration-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration-type/enumeration-type-dialog.html',
                    controller: 'EnumerationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                isDelete: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enumeration-type', null, { reload: 'enumeration-type' });
                }, function() {
                    $state.go('enumeration-type');
                });
            }]
        })
        .state('enumeration-type.edit', {
            parent: 'enumeration-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration-type/enumeration-type-dialog.html',
                    controller: 'EnumerationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EnumerationType', function(EnumerationType) {
                            return EnumerationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enumeration-type', null, { reload: 'enumeration-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enumeration-type.delete', {
            parent: 'enumeration-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration-type/enumeration-type-delete-dialog.html',
                    controller: 'EnumerationTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EnumerationType', function(EnumerationType) {
                            return EnumerationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enumeration-type', null, { reload: 'enumeration-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
