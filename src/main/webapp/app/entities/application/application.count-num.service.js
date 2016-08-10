(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('AppCountNum', AppCountNum);

    AppCountNum.$inject = ['$resource'];

    function AppCountNum($resource) {
        var resourceUrl =  'api/_countSearch/applicationsnum';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    return data;
                }
            }
        });
    }
})();
