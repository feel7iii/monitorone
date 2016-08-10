(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MmetadateDetailController', MmetadateDetailController);

    MmetadateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Mmetadate', 'Cserver', 'Application', 'Middleware'];

    function MmetadateDetailController($scope, $rootScope, $stateParams, entity, Mmetadate, Cserver, Application, Middleware) {
        var vm = this;
        vm.mmetadate = entity;
        
        var unsubscribe = $rootScope.$on('monitorApp:mmetadateUpdate', function(event, result) {
            vm.mmetadate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
