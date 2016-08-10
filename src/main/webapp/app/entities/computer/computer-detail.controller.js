(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ComputerDetailController', ComputerDetailController);

    ComputerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Computer', 'Middleware', 'Cserver', 'Application'];

    function ComputerDetailController($scope, $rootScope, $stateParams, entity, Computer, Middleware, Cserver, Application) {
        var vm = this;
        vm.computer = entity;
        
        var unsubscribe = $rootScope.$on('monitorApp:computerUpdate', function(event, result) {
            vm.computer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
