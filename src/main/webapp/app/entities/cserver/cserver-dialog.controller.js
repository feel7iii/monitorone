(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('CserverDialogController', CserverDialogController);

    CserverDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cserver', 'Mmetadate', 'Computer'];

    function CserverDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cserver, Mmetadate, Computer) {
        var vm = this;
        vm.cserver = entity;
        vm.mmetadates = Mmetadate.query();
        vm.computers = Computer.query();

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('monitorApp:cserverUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cserver.id !== null) {
                Cserver.update(vm.cserver, onSaveSuccess, onSaveError);
            } else {
                Cserver.save(vm.cserver, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
