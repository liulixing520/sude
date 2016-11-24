(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sequence-value-item', {
            parent: 'entity',
            url: '/sequence-value-item?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sequenceValueItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-items.html',
                    controller: 'SequenceValueItemController',
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
                    $translatePartialLoader.addPart('sequenceValueItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sequence-value-item-detail', {
            parent: 'entity',
            url: '/sequence-value-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sequenceValueItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-item-detail.html',
                    controller: 'SequenceValueItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sequenceValueItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SequenceValueItem', function($stateParams, SequenceValueItem) {
                    return SequenceValueItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sequence-value-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sequence-value-item-detail.edit', {
            parent: 'sequence-value-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-item-dialog.html',
                    controller: 'SequenceValueItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SequenceValueItem', function(SequenceValueItem) {
                            return SequenceValueItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sequence-value-item.new', {
            parent: 'sequence-value-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-item-dialog.html',
                    controller: 'SequenceValueItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seqId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sequence-value-item', null, { reload: 'sequence-value-item' });
                }, function() {
                    $state.go('sequence-value-item');
                });
            }]
        })
        .state('sequence-value-item.edit', {
            parent: 'sequence-value-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-item-dialog.html',
                    controller: 'SequenceValueItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SequenceValueItem', function(SequenceValueItem) {
                            return SequenceValueItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sequence-value-item', null, { reload: 'sequence-value-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sequence-value-item.delete', {
            parent: 'sequence-value-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sequence-value-item/sequence-value-item-delete-dialog.html',
                    controller: 'SequenceValueItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SequenceValueItem', function(SequenceValueItem) {
                            return SequenceValueItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sequence-value-item', null, { reload: 'sequence-value-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
