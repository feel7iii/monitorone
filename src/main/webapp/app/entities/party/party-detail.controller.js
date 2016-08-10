(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('PartyDetailController', PartyDetailController);

    PartyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Party'];

    function PartyDetailController($scope, $rootScope, $stateParams, entity, Party) {
        var vm = this;

        vm.party = entity;

        var unsubscribe = $rootScope.$on('monitorApp:partyUpdate', function(event, result) {
            vm.party = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
