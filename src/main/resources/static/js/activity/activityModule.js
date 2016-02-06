(function() {
  /**
   * Module related to Activity functionality.
   */
  var activityModule = angular.module('myApp');

  /**
   * Factory related to Activity functionality
   */
  activityModule.factory('activityFactory', ['REST_API', '$http', function(REST_API, $http) {
    $http.defaults.headers.post["Content-Type"] = 'application/json';
    /**
     * Base REST API URL.
     */
    var wbsPath = REST_API.WBS.BASE + '/';

    /**
     * Base REST API URL.
     */
    var path = REST_API.ACTIVITY.BASE;

    /**
     * Constructor.
     */
    var me = {};

    /**
     * Get all the Activity's.
     */
    me.getAll = function() {
      return $http.get(path);
    }

    /**
     * Get Activity by UUID.
     */
    me.getByUuid = function(uuid) {
      return $http.get(path + '/' + uuid);
    }

    /**
     * Create or update a Activity.
     */
    me.createOrUpdate = function(data, wbsUuid) {
      var url = wbsPath + wbsUuid + path;
      return $http.post(url, data);
    }

    /**
     * Delete Activity by UUID.
     */
    me.deleteByUuid = function(uuid) {
      return $http.delete(path + '/' + uuid);
    }

    return me;
  }]);
  /**
   * Service related to Activity functionality
   */
  activityModule.service('activityService', ['activityFactory', function(activityFactory) {
    var me = {};

    /**
     * Get all the Activity's.
     */
    me.getAll = function() {
      return activityFactory.getAll();
    }

    /**
     * Get Activity by UUID.
     */
    me.getByUuid = function(uuid) {
      return activityFactory.getByUuid(uuid);
    }

    /**
     * Create or update a Activity.
     */
    me.createOrUpdate = function(data, wbsUuid) {
      return activityFactory.createOrUpdate(data, wbsUuid);
    }

    /**
     * Delete Activity by UUID.
     */
    me.deleteByUuid = function(uuid) {
      return activityFactory.deleteByUuid(uuid);
    }

    return me;
  }]);
  /**
   * Controller related to Activity functionality
   */
  activityModule.controller('activityController', [
    '$scope', '$state', '$uibModal', 'utilService', 'toastrService', 'activityService',
    function(
      $scope, $state, $uibModal, utilService, toastrService, activityService
    ) {

      /**
       * List of Activity's
       */
      $scope.activityList;

      /**
       * Sorting functionality
       */
      $scope.sort = function(key) {
        $scope.sortKey = key;
        $scope.reverse = !$scope.reverse;
      }

      /**
       * Get all the Activity's.
       */
      function getAll() {
        activityService.getAll()
          .success(function(data) {
            $scope.activityList = data;
          })
          .error(function(error) {
            $scope.status = error.message;
          });
      }

      /**
       * Get Activity by UUID.
       */
      $scope.getByUuid = function(uuid) {
        activityService.getByUuid(uuid)
          .success(function(data) {
            $scope.activityList = data;
          })
          .error(function(error) {
            $scope.status = error.message;
          });
      }

      /**
       * Create or update a Activity.
       */
      $scope.createOrUpdate = function(data, wbsUuid) {
        activityService.createOrUpdate(data, wbsUuid)
          .success(function() {
            $scope.status = 'Saved successfully';
          })
          .error(function(error) {
            $scope.status = error.message;
          });
      }

      /**
       * Delete Activity by UUID.
       */
      $scope.deleteByUuid = function(uuid) {
        activityService.deleteByUuid(uuid)
          .success(function() {
            $scope.status = 'Deleted successfully';
          })
          .error(function(error) {
            $scope.status = error.message;
          });
      }
    }
  ]);
})();