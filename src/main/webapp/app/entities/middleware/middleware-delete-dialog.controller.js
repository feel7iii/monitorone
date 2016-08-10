(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MiddlewareDeleteController',MiddlewareDeleteController);

    MiddlewareDeleteController.$inject = ['$uibModalInstance', 'entity', 'Middleware'];

    function MiddlewareDeleteController($uibModalInstance, entity, Middleware) {
        var vm = this;
        vm.middleware = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Middleware.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
