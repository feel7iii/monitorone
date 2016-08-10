(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('MiddlewarecController', MiddlewarecController);

    MiddlewarecController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Middleware', 'Mmetadate', 'Application', 'Computer'];

    function MiddlewarecController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Middleware, Mmetadate, Application, Computer) {
        var vm = this;
        vm.middleware = entity;
        vm.mmetadates = Mmetadate.query();
        vm.applications = Application.query();
        vm.middleware.computer = Computer.get({id: $stateParams.cid});

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
