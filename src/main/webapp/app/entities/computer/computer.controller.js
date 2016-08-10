(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ComputerController', ComputerController);

    ComputerController.$inject = ['$scope', '$state', 'Computer', 'ComputerSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants', 'AppSearch', 'MidSearch', 'CsvSearch'];

    function ComputerController ($scope, $state, Computer, ComputerSearch, ParseLinks, AlertService, pagingParams, paginationConstants, AppSearch, MidSearch, CsvSearch) {
        var vm = this;
        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.clear = clear;
        vm.search = search;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;
        vm.loadAll();

        function loadApp(computerapp) {
            AppSearch.query({
                query: computerapp.id
            }, onSuccessApp, onErrorApp);

            function onSuccessApp(data) {
                computerapp.applength = data.length;
            }
            function onErrorApp(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadMid(computermid) {
            MidSearch.query({
                query: computermid.id
            }, onSuccessMid, onErrorApp);

            function onSuccessMid(data) {
                computermid.midlength = data.length;
            }
            function onErrorApp(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadCsv(computercsv) {
            CsvSearch.query({
                query: computercsv.id
            }, onSuccessCsv, onErrorApp);

            function onSuccessCsv(data) {
                computercsv.csvlength = data.length;
            }
            function onErrorApp(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadAll () {
            if (pagingParams.search) {
                ComputerSearch.query({
                    query: pagingParams.search,
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
            } else {
                Computer.query({
                    page: pagingParams.page - 1,
                    size: paginationConstants.itemsPerPage,
                    sort: sort()
                }, onSuccess, onError);
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
                vm.computers = data;
                angular.forEach(data, function (computer) {
                    loadApp(computer);
                    loadMid(computer);
                    loadCsv(computer);
                });
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
