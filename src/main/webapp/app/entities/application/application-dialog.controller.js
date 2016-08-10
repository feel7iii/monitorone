(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ApplicationDialogController', ApplicationDialogController);

    ApplicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Application', 'Mmetadate', 'Computer', 'Middleware'];

    function ApplicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Application, Mmetadate, Computer, Middleware) {
        var vm = this;
        vm.application = entity;
        vm.mmetadates = Mmetadate.query();
        vm.computers = Computer.query();
        vm.middlewares = Middleware.query();

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('monitorApp:applicationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.application.id !== null) {
                Application.update(vm.application, onSaveSuccess, onSaveError);
            } else {
                Application.save(vm.application, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
