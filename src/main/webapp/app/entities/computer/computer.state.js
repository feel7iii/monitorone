(function() {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('computer', {
            parent: 'entity',
            url: '/computer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.computer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/computer/computers.html',
                    controller: 'ComputerController',
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
                    $translatePartialLoader.addPart('computer');
                    $translatePartialLoader.addPart('powersupply');
                    $translatePartialLoader.addPart('secretornot');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('computer-detail', {
            parent: 'entity',
            url: '/computer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.computer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/computer/computer-detail.html',
                    controller: 'ComputerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('computer');
                    $translatePartialLoader.addPart('powersupply');
                    $translatePartialLoader.addPart('secretornot');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Computer', function($stateParams, Computer) {
                    return Computer.get({id : $stateParams.id});
                }]
            }
        })
        .state('computer.new', {
            parent: 'computer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/computer/computer-dialog.html',
                    controller: 'ComputerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cpname: null,
                                cpmoreinfo: null,
                                cposx: null,
                                cpedition: null,
                                cploginname: null,
                                cploginpassword: null,
                                cpip: null,
                                cplocation: null,
                                cpnetwordarea: null,
                                cpstatus: null,
                                cppowersupply: null,
                                cpsecretornot: null,
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
        .state('computer.edit', {
            parent: 'computer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/computer/computer-dialog.html',
                    controller: 'ComputerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Computer', function(Computer) {
                            return Computer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('computer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('computer.delete', {
            parent: 'computer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/computer/computer-delete-dialog.html',
                    controller: 'ComputerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Computer', function(Computer) {
                            return Computer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('computer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
