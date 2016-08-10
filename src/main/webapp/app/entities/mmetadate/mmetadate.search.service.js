(function() {
    'use strict';

    angular
        .module('monitorApp')
        .factory('MmetadateSearch', MmetadateSearch);

    MmetadateSearch.$inject = ['$resource'];

    function MmetadateSearch($resource) {
        var resourceUrl =  'api/_search/mmetadates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
