(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ComputerDeleteController',ComputerDeleteController);

    ComputerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Computer'];

    function ComputerDeleteController($uibModalInstance, entity, Computer) {
        var vm = this;
        vm.computer = entity;
        console.log(vm.computer);
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Computer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
