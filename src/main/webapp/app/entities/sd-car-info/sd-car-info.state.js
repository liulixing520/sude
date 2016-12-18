(function() {
    'use strict';

    angular
        .module('sudeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sd-car-info', {
            parent: 'carManager',
            url: '/sd-car-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCarInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-car-info/sd-car-infos.html',
                    controller: 'SdCarInfoController',
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
                    $translatePartialLoader.addPart('sdCarInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sd-car-info-detail', {
            parent: 'carManager',
            url: '/sd-car-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'sudeApp.sdCarInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sd-car-info/sd-car-info-detail.html',
                    controller: 'SdCarInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sdCarInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SdCarInfo', function($stateParams, SdCarInfo) {
                    return SdCarInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sd-car-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sd-car-info-detail.edit', {
            parent: 'sd-car-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-car-info/sd-car-info-dialog.html',
                    controller: 'SdCarInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCarInfo', function(SdCarInfo) {
                            return SdCarInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-car-info.new', {
            parent: 'sd-car-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-car-info/sd-car-info-dialog.html',
                    controller: 'SdCarInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                carNo: null,
                                carType: null,
                                engineNumber: null,
                                buyDate: null,
                                checkLoad: null,
                                checkVolume: null,
                                carLength: null,
                                carWidth: null,
                                carHeight: null,
                                vehicleNo: null,
                                policyNo: null,
                                carrier: null,
                                runNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sd-car-info', null, { reload: 'sd-car-info' });
                }, function() {
                    $state.go('sd-car-info');
                });
            }]
        })
        .state('sd-car-info.edit', {
            parent: 'sd-car-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-car-info/sd-car-info-dialog.html',
                    controller: 'SdCarInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SdCarInfo', function(SdCarInfo) {
                            return SdCarInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-car-info', null, { reload: 'sd-car-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sd-car-info.delete', {
            parent: 'sd-car-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sd-car-info/sd-car-info-delete-dialog.html',
                    controller: 'SdCarInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SdCarInfo', function(SdCarInfo) {
                            return SdCarInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sd-car-info', null, { reload: 'sd-car-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
