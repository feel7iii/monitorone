(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('CserverDeleteController',CserverDeleteController);

    CserverDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cserver'];

    function CserverDeleteController($uibModalInstance, entity, Cserver) {
        var vm = this;
        vm.cserver = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cserver.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
