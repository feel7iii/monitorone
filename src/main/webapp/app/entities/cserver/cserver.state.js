(function() {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cserver', {
            parent: 'entity',
            url: '/cserver?page&sort&search&words',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.cserver.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cserver/cservers.html',
                    controller: 'CserverController',
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
                    $translatePartialLoader.addPart('cserver');
                    $translatePartialLoader.addPart('cstatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cserver-detail', {
            parent: 'entity',
            url: '/cserver/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.cserver.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cserver/cserver-detail.html',
                    controller: 'CserverDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cserver');
                    $translatePartialLoader.addPart('cstatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cserver', function($stateParams, Cserver) {
                    return Cserver.get({id : $stateParams.id});
                }]
            }
        })
        .state('cserver.new', {
            parent: 'cserver',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cserver/cserver-dialog.html',
                    controller: 'CserverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                csname: null,
                                csdescription: null,
                                cstartstatus: null,
                                cport: null,
                                cstatusliveordie: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cserver', null, { reload: true });
                }, function() {
                    $state.go('cserver');
                });
            }]
        })
        .state('cserver.edit', {
            parent: 'cserver',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cserver/cserver-dialog.html',
                    controller: 'CserverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cserver', function(Cserver) {
                            return Cserver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cserver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cserver.delete', {
            parent: 'cserver',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cserver/cserver-delete-dialog.html',
                    controller: 'CserverDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cserver', function(Cserver) {
                            return Cserver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cserver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cserver.snew', {
                parent: 'cserver',
                url: '/snew?cid',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/cserver/cserver-computer-new.html',
                        controller: 'CservercController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    csname: null,
                                    csdescription: null,
                                    cstartstatus: null,
                                    cport: null,
                                    cstatusliveordie: null,
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
            });
    }

})();
