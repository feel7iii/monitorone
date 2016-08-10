(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('ComputerSearch', ComputerSearch);

    ComputerSearch.$inject = ['$resource'];

    function ComputerSearch($resource) {
        var resourceUrl =  'api/_search/computers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
