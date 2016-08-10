(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ComputerDialogController', ComputerDialogController);

    ComputerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Computer', 'Middleware', 'Cserver', 'Application'];

    function ComputerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Computer, Middleware, Cserver, Application) {
        var vm = this;
        vm.computer = entity;
        vm.middlewares = Middleware.query();
        vm.cservers = Cserver.query();
        vm.applications = Application.query();

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('monitorApp:computerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.computer.id !== null) {
                Computer.update(vm.computer, onSaveSuccess, onSaveError);
            } else {
                Computer.save(vm.computer, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
