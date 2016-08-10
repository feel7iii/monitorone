(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'AlertService', 'ComputerCount', 'MiddNum', 'AppCountNum'];

    function HomeController ($scope, Principal, LoginService, $state, AlertService, ComputerCount, MiddNum, AppCountNum) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        loadCpNum();
        loadMidNum();
        loadAppNum();

        function loadCpNum () {
            ComputerCount.get({

            }, onSuccess, onError);
            function onSuccess(data) {
                angular.forEach(data, function (computer, index, array) {
                    vm.cpNum = array[0];
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadMidNum () {
            MiddNum.get({

            }, onSuccess, onError);
            function onSuccess(data) {
                angular.forEach(data, function (computer, index, array) {
                    vm.middNum = array[0];
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadAppNum () {
            AppCountNum.get({

            }, onSuccess, onError);
            function onSuccess(data) {
                angular.forEach(data, function (computer, index, array) {
                    vm.appNum = array[0];
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
