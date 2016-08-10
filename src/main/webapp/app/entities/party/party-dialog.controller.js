(function() {
    'use strict';

    angular
        .module('monitorApp')
        .controller('PartyDialogController', PartyDialogController);

    PartyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Party'];

    function PartyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Party) {
        var vm = this;

        vm.party = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.party.id !== null) {
                Party.update(vm.party, onSaveSuccess, onSaveError);
            } else {
                Party.save(vm.party, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monitorApp:partyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createTime = false;
        vm.datePickerOpenStatus.modifyTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
