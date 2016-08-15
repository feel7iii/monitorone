(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'AlertService', 'ComputerCount', 'MiddNum', 'AppCountNum', 'PartySearchAll'];

    function HomeController ($scope, Principal, LoginService, $state, AlertService, ComputerCount, MiddNum, AppCountNum, PartySearchAll) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            loadCpNum();
            loadMidNum();
            loadAppNum();
            loadParty();
        });

        getAccount();
        loadCpNum();
        loadMidNum();
        loadAppNum();
        loadParty();

        function loadParty () {
            PartySearchAll.query({

            }, onSuccess, onError);
            function onSuccess(data) {
                var ss = angular.toJson(data).replace(new RegExp("id","gm"), "key").replace(new RegExp("parentId","gm"), "parent");
                var data = angular.fromJson(ss);
                var $ = go.GraphObject.make;
                var myDiagram =
                    $(go.Diagram, "myDiagramDiv",
                        {
                            initialContentAlignment: go.Spot.Center, // center Diagram contents
                            "undoManager.isEnabled": true, // enable Ctrl-Z to undo and Ctrl-Y to redo
                            layout: $(go.TreeLayout,
                                {angle: 90, layerSpacing: 20})
                        });

                // the template we defined earlier
                myDiagram.nodeTemplate =
                    $(go.Node, "Horizontal",
                        {background: "#44CCFF"},
                        $(go.Picture,
                            {margin: 7, width: 20, height: 20, background: "#00EEEE"},
                            new go.Binding("source")),
                        $(go.TextBlock, "Default Text",
                            {margin: 7, stroke: "white", font: "bold 16px sans-serif"},
                            new go.Binding("text", "name"))
                    );

                // define a Link template that routes orthogonally, with no arrowhead
                myDiagram.linkTemplate =
                    $(go.Link,
                        {routing: go.Link.Orthogonal, corner: 5},
                        $(go.Shape, {strokeWidth: 3, stroke: "#00EEEE"})); // the link shape

                var model = $(go.TreeModel);
                model.nodeDataArray = data;
                myDiagram.model = model;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

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
