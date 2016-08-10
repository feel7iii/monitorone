(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('CsvSearch', CsvSearch);

    CsvSearch.$inject = ['$resource'];

    function CsvSearch($resource) {
        var resourceUrl =  'api/_countSearch/cservers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
