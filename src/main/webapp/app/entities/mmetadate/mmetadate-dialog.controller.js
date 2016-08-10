(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MmetadateDialogController', MmetadateDialogController);

    MmetadateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mmetadate', 'Cserver', 'Application', 'Middleware'];

    function MmetadateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mmetadate, Cserver, Application, Middleware) {
        var vm = this;
        vm.mmetadate = entity;
        vm.cservers = Cserver.query();
        vm.applications = Application.query();
        vm.middlewares = Middleware.query();

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('monitorApp:mmetadateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.mmetadate.id !== null) {
                Mmetadate.update(vm.mmetadate, onSaveSuccess, onSaveError);
            } else {
                Mmetadate.save(vm.mmetadate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
