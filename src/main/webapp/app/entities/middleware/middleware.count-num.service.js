(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('MiddNum', MiddNum);

    MiddNum.$inject = ['$resource'];

    function MiddNum($resource) {
        var resourceUrl =  'api/_countSearch/middnum';

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
