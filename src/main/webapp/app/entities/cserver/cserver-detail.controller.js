(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('CserverDetailController', CserverDetailController);

    CserverDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cserver', 'Mmetadate', 'Computer'];

    function CserverDetailController($scope, $rootScope, $stateParams, entity, Cserver, Mmetadate, Computer) {
        var vm = this;
        vm.cserver = entity;
        
        var unsubscribe = $rootScope.$on('monitorApp:cserverUpdate', function(event, result) {
            vm.cserver = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
