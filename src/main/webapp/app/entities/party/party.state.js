(function() {
    'use strict';

    angular
        .module('monitorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('party', {
            parent: 'entity',
            url: '/party?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.party.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/party/parties.html',
                    controller: 'PartyController',
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
                    $translatePartialLoader.addPart('party');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('party-detail', {
            parent: 'entity',
            url: '/party/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'monitorApp.party.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/party/party-detail.html',
                    controller: 'PartyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('party');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Party', function($stateParams, Party) {
                    return Party.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('party.new', {
            parent: 'party',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-dialog.html',
                    controller: 'PartyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                parentId: null,
                                path: null,
                                name: null,
                                uniqueName: null,
                                description: null,
                                position: null,
                                type: null,
                                status: null,
                                securityLevel: null,
                                createTime: null,
                                modifyTime: null,
                                manageBy: null,
                                partyOne: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: true });
                }, function() {
                    $state.go('party');
                });
            }]
        })
        .state('party.edit', {
            parent: 'party',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-dialog.html',
                    controller: 'PartyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Party', function(Party) {
                            return Party.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('party.delete', {
            parent: 'party',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/party/party-delete-dialog.html',
                    controller: 'PartyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Party', function(Party) {
                            return Party.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('party', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
