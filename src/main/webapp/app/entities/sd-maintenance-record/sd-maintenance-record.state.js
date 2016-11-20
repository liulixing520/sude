(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-maintenance-record', {
            parent: 'carManager',
            url: '/sd-maintenance-record?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdMaintenanceRecord.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-records.html',
                    controller: 'SdMaintenanceRecordController',
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
                    $translatePartialLoader.addPart('sdMaintenanceRecord');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-maintenance-record-detail', {
            parent: 'carManager',
            url: '/sd-maintenance-record/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdMaintenanceRecord.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-record-detail.html',
                    controller: 'SdMaintenanceRecordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdMaintenanceRecord');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdMaintenanceRecord', function($stateParams, SdMaintenanceRecord) {
                    return SdMaintenanceRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-maintenance-record',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-maintenance-record-detail.edit', {
            parent: 'sd-maintenance-record-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-record-dialog.html',
                    controller: 'SdMaintenanceRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdMaintenanceRecord', function(SdMaintenanceRecord) {
                            return SdMaintenanceRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-maintenance-record.new', {
            parent: 'sd-maintenance-record',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-record-dialog.html',
                    controller: 'SdMaintenanceRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                carNo: null,
                                maintainDate: null,
                                sender: null,
                                sendReason: null,
                                repairer: null,
                                repaiResult: null,
                                repaiCosts: null,
                                remark: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-maintenance-record', null, { reload: 'sd-maintenance-record' });
                }, function() {
                    $state.go('sd-maintenance-record');
                });
            }]
        })
        .state('sd-maintenance-record.edit', {
            parent: 'sd-maintenance-record',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-record-dialog.html',
                    controller: 'SdMaintenanceRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdMaintenanceRecord', function(SdMaintenanceRecord) {
                            return SdMaintenanceRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-maintenance-record', null, { reload: 'sd-maintenance-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-maintenance-record.delete', {
            parent: 'sd-maintenance-record',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-maintenance-record/sd-maintenance-record-delete-dialog.html',
                    controller: 'SdMaintenanceRecordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdMaintenanceRecord', function(SdMaintenanceRecord) {
                            return SdMaintenanceRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-maintenance-record', null, { reload: 'sd-maintenance-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
