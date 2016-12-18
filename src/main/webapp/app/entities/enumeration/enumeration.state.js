(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('enumeration', {
            parent: 'baseInfo',
            url: '/enumeration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.enumeration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enumeration/enumerations.html',
                    controller: 'EnumerationController',
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
                    $translatePartialLoader.addPart('enumeration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('enumeration-detail', {
            parent: 'baseInfo',
            url: '/enumeration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.enumeration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/enumeration/enumeration-detail.html',
                    controller: 'EnumerationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('enumeration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Enumeration', function($stateParams, Enumeration) {
                    return Enumeration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'enumeration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('enumeration-detail.edit', {
            parent: 'enumeration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration/enumeration-dialog.html',
                    controller: 'EnumerationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enumeration', function(Enumeration) {
                            return Enumeration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enumeration.new', {
            parent: 'enumeration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration/enumeration-dialog.html',
                    controller: 'EnumerationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                isDelete: null,
                                description: null,
                                enumTypeId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('enumeration', null, { reload: 'enumeration' });
                }, function() {
                    $state.go('enumeration');
                });
            }]
        })
        .state('enumeration.edit', {
            parent: 'enumeration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration/enumeration-dialog.html',
                    controller: 'EnumerationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Enumeration', function(Enumeration) {
                            return Enumeration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enumeration', null, { reload: 'enumeration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('enumeration.delete', {
            parent: 'enumeration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/enumeration/enumeration-delete-dialog.html',
                    controller: 'EnumerationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Enumeration', function(Enumeration) {
                            return Enumeration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('enumeration', null, { reload: 'enumeration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
