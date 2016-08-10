(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MmetadateDeleteController',MmetadateDeleteController);

    MmetadateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mmetadate'];

    function MmetadateDeleteController($uibModalInstance, entity, Mmetadate) {
        var vm = this;
        vm.mmetadate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Mmetadate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
