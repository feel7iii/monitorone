(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MiddlewareDetailController', MiddlewareDetailController);

    MiddlewareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Middleware', 'Mmetadate', 'Application', 'Computer'];

    function MiddlewareDetailController($scope, $rootScope, $stateParams, entity, Middleware, Mmetadate, Application, Computer) {
        var vm = this;
        vm.middleware = entity;
        
        var unsubscribe = $rootScope.$on('monitorApp:middlewareUpdate', function(event, result) {
            vm.middleware = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
