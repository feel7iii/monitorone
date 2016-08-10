(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MiddlewareDialogController', MiddlewareDialogController);

    MiddlewareDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Middleware', 'Mmetadate', 'Application', 'Computer'];

    function MiddlewareDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Middleware, Mmetadate, Application, Computer) {
        var vm = this;
        vm.middleware = entity;
        vm.mmetadates = Mmetadate.query();
        vm.applications = Application.query();
        vm.computers = Computer.query();

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('monitorApp:middlewareUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.middleware.id !== null) {
                Middleware.update(vm.middleware, onSaveSuccess, onSaveError);
            } else {
                Middleware.save(vm.middleware, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
