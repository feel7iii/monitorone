(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ApplicationDetailController', ApplicationDetailController);

    ApplicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Application', 'Mmetadate', 'Computer', 'Middleware'];

    function ApplicationDetailController($scope, $rootScope, $stateParams, entity, Application, Mmetadate, Computer, Middleware) {
        var vm = this;
        vm.application = entity;

        var unsubscribe = $rootScope.$on('monitorApp:applicationUpdate', function(event, result) {
            vm.application = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
