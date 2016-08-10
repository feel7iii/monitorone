(function() {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mmetadate', {
            parent: 'entity',
            url: '/mmetadate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.mmetadate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mmetadate/mmetadates.html',
                    controller: 'MmetadateController',
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
                    $translatePartialLoader.addPart('mmetadate');
                    $translatePartialLoader.addPart('msqlctype');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mmetadate-detail', {
            parent: 'entity',
            url: '/mmetadate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.mmetadate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mmetadate/mmetadate-detail.html',
                    controller: 'MmetadateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mmetadate');
                    $translatePartialLoader.addPart('msqlctype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mmetadate', function($stateParams, Mmetadate) {
                    return Mmetadate.get({id : $stateParams.id});
                }]
            }
        })
        .state('mmetadate.new', {
            parent: 'mmetadate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mmetadate/mmetadate-dialog.html',
                    controller: 'MmetadateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mcname: null,
                                mcdescription: null,
                                mcsidordatabasename: null,
                                mcusername: null,
                                mcpassword: null,
                                mcport: null,
                                msconnurl: null,
                                mcsqltype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mmetadate', null, { reload: true });
                }, function() {
                    $state.go('mmetadate');
                });
            }]
        })
        .state('mmetadate.edit', {
            parent: 'mmetadate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mmetadate/mmetadate-dialog.html',
                    controller: 'MmetadateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mmetadate', function(Mmetadate) {
                            return Mmetadate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('mmetadate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mmetadate.delete', {
            parent: 'mmetadate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mmetadate/mmetadate-delete-dialog.html',
                    controller: 'MmetadateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mmetadate', function(Mmetadate) {
                            return Mmetadate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('mmetadate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
