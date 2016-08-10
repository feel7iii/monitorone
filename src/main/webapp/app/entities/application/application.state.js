(function () {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('application', {
                parent: 'entity',
                url: '/application?page&sort&search&words',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'monitorApp.application.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/application/applications.html',
                        controller: 'ApplicationController',
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
                        $translatePartialLoader.addPart('application');
                        $translatePartialLoader.addPart('connway');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('application-detail', {
                parent: 'entity',
                url: '/application/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'monitorApp.application.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/application/application-detail.html',
                        controller: 'ApplicationDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('application');
                        $translatePartialLoader.addPart('connway');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Application', function ($stateParams, Application) {
                        return Application.get({id: $stateParams.id});
                    }]
                }
            })
            .state('application.new', {
                parent: 'application',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/application/application-dialog.html',
                        controller: 'ApplicationDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    appname: null,
                                    appdescription: null,
                                    appaccess: null,
                                    appusingport: null,
                                    appvolumepath: null,
                                    appconnpath: null,
                                    appsysadmin: null,
                                    appsyspass: null,
                                    appstatus: null,
                                    appconnway: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('application', null, {reload: true});
                    }, function () {
                        $state.go('application');
                    });
                }]
            })
            .state('application.edit', {
                parent: 'application',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/application/application-dialog.html',
                        controller: 'ApplicationDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Application', function (Application) {
                                return Application.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function () {
                        $state.go('application', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('application.delete', {
                parent: 'application',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/application/application-delete-dialog.html',
                        controller: 'ApplicationDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Application', function (Application) {
                                return Application.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function () {
                        $state.go('application', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('application.cnew', {
                parent: 'application',
                url: '/cnew?cid',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/application/application-computer-new.html',
                        controller: 'ApplicationcController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    appname: null,
                                    appdescription: null,
                                    appaccess: null,
                                    appusingport: null,
                                    appvolumepath: null,
                                    appconnpath: null,
                                    appsysadmin: null,
                                    appsyspass: null,
                                    appstatus: null,
                                    appconnway: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('computer', null, {reload: true});
                    }, function () {
                        $state.go('computer');
                    });
                }]
            });
    }

})();
