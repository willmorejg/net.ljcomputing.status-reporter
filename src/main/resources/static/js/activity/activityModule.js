(function () {
/**
 * Module related to Activity functionality.
 */
var activityModule = angular.module('myApp');
  /**
   * Factory related to Activity functionality
   */
  activityModule.factory('activityFactory', ['$http', function($http) {
    /**
     * Base REST API URL.
     */
    var wbsPath = 'sr/wbs';

    /**
     * Base REST API URL.
     */
    var path = '/activity';

    /**
     * Constructor.
     */
    var activity = {};
    
    /**
     * Get all the Activity's.
     */
    activity.getAll = function() {
      return $http.get(path);
    }
    
    /**
     * Get Activity by UUID.
     */
    activity.getByUuid = function(uuid) {
      return $http.get(path + '/' + uuid);
    }
    
    /**
     * Create or update a Activity.
     */
    activity.createOrUpdate = function(data, wbsUuid) {
      var url = wbsPath + wbsUuid + path;
      return $http.post(url, data);
    }
    
    /**
     * Delete Activity by UUID.
     */
    activity.deleteByUuid = function(uuid) {
      return $http.delete(path + '/' + uuid);
    }
    
    return activity;
  }]);
  /**
   * Service related to Activity functionality
   */
  activityModule.service('activityService', ['$http', function($http){
    /**
     * Base REST API URL.
     */
    var path = '';
    
    /**
     * Get all the Activity's.
     */
    activity.getAll = function() {
      return $http.get(path);
    }
    
    /**
     * Get Activity by UUID.
     */
    activity.getByUuid = function(uuid) {
      return $http.get(path + '/' + uuid);
    }
    
    /**
     * Create or update a Activity.
     */
    activity.createOrUpdate = function(data, wbsUuid) {
      var url = wbsPath + wbsUuid + path;
      return $http.post(url, data);
    }
    
    /**
     * Delete Activity by UUID.
     */
    activity.deleteByUuid = function(uuid) {
      return $http.delete(path + '/' + uuid);
    }
    
    return activity;
  }]);
  /**
   * Controller related to Activity functionality
   */
  activityModule.controller('activityController', ['$scope', 'activityService', function($scope, activityService){
    /**
     * Status
     */
    $scope.status;
    
    /**
     * List of Activity's
     */
    $scope.activityList;

    //getAll();
    
    /**
     * Get all the Activity's.
     */
    function getAll() {
      activityService.getAll()
        .success(function(data){
          $scope.activityList = data;
        })
        .error(function(error){
          $scope.status = error.message;
        });
    }
    
    /**
     * Get Activity by UUID.
     */
    $scope.getByUuid = function(uuid) {
      activityService.getByUuid(uuid)
        .success(function(data){
          $scope.activityList = data;
        })
        .error(function(error){
          $scope.status = error.message;
        });
    }
    
    /**
     * Create or update a Activity.
     */
    $scope.createOrUpdate = function(data, wbsUuid) {
      activityService.createOrUpdate(data, wbsUuid)
        .success(function(){
          $scope.status = 'Saved successfully';
        })
        .error(function(error){
          $scope.status = error.message;
        });
    }
    
    /**
     * Delete Activity by UUID.
     */
    $scope.deleteByUuid = function(uuid) {
      activityService.deleteByUuid(uuid)
        .success(function(){
          $scope.status = 'Deleted successfully';
        })
        .error(function(error){
          $scope.status = error.message;
        });
    }
    
    return activity;
  }]);
})();