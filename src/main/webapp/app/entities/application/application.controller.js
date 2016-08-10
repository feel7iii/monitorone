(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ApplicationController', ApplicationController);

    ApplicationController.$inject = ['$scope', '$state', 'Application', 'ApplicationSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'AppSearch', 'Computer'];

    function ApplicationController ($scope, $state, Application, ApplicationSearch, ParseLinks, AlertService, pagingParams, paginationConstants, AppSearch, Computer) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.loadAllCp = loadAllCp;
        vm.clear = clear;
        vm.search = search;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.loadAll();
        vm.loadAllCp();

        function loadAllCp() {
            Computer.query({
                page: pagingParams.page - 1,
                size: paginationConstants.itemsPerPage,
                sort: sort()
            }, onSuccessCp, onError);
            function onSuccessCp(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.computers = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                //AlertService.error(error.data.message);
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
        }

        function loadAll () {
            if (pagingParams.search) {
                ApplicationSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else if(pagingParams.words) {
                AppSearch.query({
                    query: pagingParams.words,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccessApp, onError);
            } else {
                Application.query({
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            }
            function onSuccessApp(data) {
                vm.applications = data;
            }
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.applications = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }

        function search (searchQuery) {
            if (!searchQuery){
                return vm.clear();
            }
            vm.links = null;
            vm.page = 1;
            vm.predicate = '_score';
            vm.reverse = false;
            vm.currentSearch = searchQuery;
            vm.transition();
        }

        function clear () {
            vm.links = null;
            vm.page = 1;
            vm.predicate = 'id';
            vm.reverse = true;
            vm.currentSearch = null;
            vm.transition();
        }

    }
})();
