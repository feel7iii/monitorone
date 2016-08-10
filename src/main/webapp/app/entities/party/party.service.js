(function() {
    'use strict';
    angular
        .module('monitorApp')
        .factory('Party', Party);

    Party.$inject = ['$resource', 'DateUtils'];

    function Party ($resource, DateUtils) {
        var resourceUrl =  'api/parties/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createTime = DateUtils.convertDateTimeFromServer(data.createTime);
                        data.modifyTime = DateUtils.convertDateTimeFromServer(data.modifyTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
