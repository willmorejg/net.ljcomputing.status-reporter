/**
 * @fileOverview Activity module.
 * @author <a href="mailto:willmorejg@gmail.com">James G Willmore</a>
 * @version 1.0.0
 * @module
 */
(function() {
  /**
   * Module related to Activity functionality.
   */
  var activityModule = angular.module('myApp');

  /**
   * Factory related to Activity functionality
   * @constructor
   * 
   * @exports activityFactory
   */
  activityModule.factory('activityFactory', ['REST_API', '$http', function(REST_API, $http) {
    $http.defaults.headers.post["Content-Type"] = 'application/json';
    /**
     * Base REST API URL.
     * @private
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
     * Get all the Activities.
     * @access public
     * @method
     * @name getAll
     * @returns {Object} HttpPromise
     */
    me.getAll = function() {
      return $http.get('sr' + path);
    }

    /**
     * Get Activity by UUID.
     * @access public
     * @method
     * @name getByUuid
     * @param {number} activity.uuid Activity UUID
     * @returns {Object} HttpPromise
     */
    me.getByUuid = function(uuid) {
      return $http.get('sr' + path + '/' + uuid);
    }

    me.getAllByWbs = function(wbsUuid) {
      var url = wbsPath + wbsUuid + path;
      return $http.get(url);
    }

    /**
     * Create or update a Activity.
     * @access public
     * @method
     * @name createOrUpdate
     * @param {Object} JSON Activity JSON data
     * @param {number} wbs.uuid WBS UUID
     * @returns {Object} HttpPromise
     */
    me.createOrUpdate = function(data, wbsUuid) {
      var url = wbsPath + wbsUuid + path;
      return $http.post(url, data);
    }

    /**
     * Delete Activity by UUID.
     * @access public
     * @method
     * @name deleteByUuid
     * @param {number} activity.uuid Activity UUID
     * @returns {Object} HttpPromise
     */
    me.deleteByUuid = function(uuid) {
      return $http.delete('sr' + path + '/' + uuid);
    }

    return me;
  }]);
  
  /**
   * Service related to Activity functionality
   * @constructor
   * 
   * @exports activityService
   */
  activityModule.service('activityService', ['activityFactory', function(activityFactory) {
    var me = {};

    /**
     * Get all the Activity's.
     * @access public
     * @method
     * @name getAll
     * @returns {Object} HttpPromise
     */
    me.getAll = function() {
      return activityFactory.getAll();
    }

    me.getAllByWbs = function(wbsUuid) {
      return activityFactory.getAllByWbs(wbsUuid);
    }

    /**
     * Get Activity by UUID.
     * @access public
     * @method
     * @name getByUuid
     * @param {number} activity.uuid Activity UUID
     * @returns {Object} HttpPromise
     */
    me.getByUuid = function(uuid) {
      return activityFactory.getByUuid(uuid);
    }

    /**
     * Create or update a Activity.
     * @access public
     * @method
     * @name createOrUpdate
     * @param {Object} JSON Activity JSON data
     * @param {number} wbs.uuid WBS UUID
     * @returns {Object} HttpPromise
     */
    me.createOrUpdate = function(data, wbsUuid) {
      return activityFactory.createOrUpdate(data, wbsUuid);
    }

    /**
     * Delete Activity by UUID.
     * @access public
     * @method
     * @name deleteByUuid
     * @param {number} activity.uuid Activity UUID
     * @returns {Object} HttpPromise
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