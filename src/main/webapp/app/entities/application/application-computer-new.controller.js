(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('ApplicationcController', ApplicationcController);

    ApplicationcController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Application', 'Mmetadate', 'Computer', 'Middleware', 'MidSearch', 'AlertService'];

    function ApplicationcController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Application, Mmetadate, Computer, Middleware, MidSearch, AlertService) {
        var vm = this;
        vm.application = entity;
        vm.mmetadates = Mmetadate.query();
        vm.application.computer = Computer.get({id: $stateParams.cid});
        loadMid();

        function loadMid() {
            MidSearch.query({
                query: $stateParams.cid
            }, onSuccessMid, onErrorApp);

            function onSuccessMid(data) {
                vm.middlewares = data;
            }
            function onErrorApp(error) {
                AlertService.error(error.data);
            }
        }

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
                console.log(vm.application);
                Application.save(vm.application, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
