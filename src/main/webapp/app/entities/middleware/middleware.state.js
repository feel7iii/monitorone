(function() {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('middleware', {
            parent: 'entity',
            url: '/middleware?page&sort&search&words',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.middleware.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/middleware/middlewares.html',
                    controller: 'MiddlewareController',
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
                        search: $stateParams.search,
                        words: $stateParams.words
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('middleware');
                    $translatePartialLoader.addPart('midtype');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('middleware-detail', {
            parent: 'entity',
            url: '/middleware/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.middleware.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/middleware/middleware-detail.html',
                    controller: 'MiddlewareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('middleware');
                    $translatePartialLoader.addPart('midtype');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Middleware', function($stateParams, Middleware) {
                    return Middleware.get({id : $stateParams.id});
                }]
            }
        })
        .state('middleware.new', {
            parent: 'middleware',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/middleware/middleware-dialog.html',
                    controller: 'MiddlewareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                midname: null,
                                midport: null,
                                midpath: null,
                                midmoreinfo: null,
                                midtype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('middleware', null, { reload: true });
                }, function() {
                    $state.go('middleware');
                });
            }]
        })
        .state('middleware.edit', {
            parent: 'middleware',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/middleware/middleware-dialog.html',
                    controller: 'MiddlewareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Middleware', function(Middleware) {
                            return Middleware.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('middleware', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('middleware.delete', {
            parent: 'middleware',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/middleware/middleware-delete-dialog.html',
                    controller: 'MiddlewareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Middleware', function(Middleware) {
                            return Middleware.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('middleware', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('middleware.mnew', {
            parent: 'middleware',
            url: '/mnew?cid',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/middleware/middleware-computer-new.html',
                    controller: 'MiddlewarecController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                midname: null,
                                midport: null,
                                midpath: null,
                                midmoreinfo: null,
                                midtype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('computer', null, { reload: true });
                }, function() {
                    $state.go('computer');
                });
            }]
        })
        ;
    }

})();
