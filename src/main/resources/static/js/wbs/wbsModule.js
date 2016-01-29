(function () {
    /**
     * Module related to Wbs functionality.
     */
    var wbsModule = angular.module('myApp');

    /**
     * Base REST API URL.
     */
    var path = 'sr/wbs';
    
    /**
     * Factory related to Wbs functionality
     */
    wbsModule.factory('wbsFactory', ['$http', function($http) {
      
      /**
       * Constructor.
       */
      var wbs = {};
      
      /**
       * Get all the Wbs's.
       */
      wbs.getAll = function() {
        return $http.get(path);
      }
      
      /**
       * Get Wbs by UUID.
       */
      wbs.getByUuid = function(uuid) {
        return $http.get(path + '/' + uuid);
      }
      
      /**
       * Create or update a Wbs.
       */
      wbs.createOrUpdate = function(data) {
        return $http.post(url, data);
      }
      
      /**
       * Delete Wbs by UUID.
       */
      wbs.deleteByUuid = function(uuid) {
        return $http.delete(path + '/' + uuid);
      }
      
      return wbs;
    }]);
    /**
     * Service related to Wbs functionality
     */
    wbsModule.service('wbsService', ['$http', 'wbsFactory', function($http, wbs){
      /**
       * Get all the Wbs's.
       */
      wbs.getAll = function() {
        return $http.get(path);
      }
      
      /**
       * Get Wbs by UUID.
       */
      wbs.getByUuid = function(uuid) {
        return $http.get(path + '/' + uuid);
      }
      
      /**
       * Create or update a Wbs.
       */
      wbs.createOrUpdate = function(data) {
        return $http.post(url, data);
      }
      
      /**
       * Delete Wbs by UUID.
       */
      wbs.deleteByUuid = function(uuid) {
        return $http.delete(path + '/' + uuid);
      }
      
      return wbs;
    }]);
    /**
     * Controller related to Wbs functionality
     */
    wbsModule.controller('wbsController', ['$scope', 'wbsService', function($scope, wbsService){
      /**
       * Status
       */
      $scope.status;
      
      /**
       * List of Wbs's
       */
      $scope.wbsList;
  
      getAll();
      
      /**
       * Get all the Wbs's.
       */
      function getAll() {
        wbsService.getAll()
          .success(function(data){
            $scope.wbsList = data;
            console.log('data : ', data);
            console.log('$scope.wbsList : ', $scope.wbsList);
          })
          .error(function(error){
            $scope.status = error.message;
          });
      }
      
      /**
       * Get Wbs by UUID.
       */
      $scope.getByUuid = function(uuid) {
        wbsService.getByUuid(uuid)
          .success(function(data){
            $scope.wbsList = data;
          })
          .error(function(error){
            $scope.status = error.message;
          });
      }
      
      /**
       * Create or update a Wbs.
       */
      $scope.createOrUpdate = function(data) {
        wbsService.createOrUpdate(data)
          .success(function(){
            $scope.status = 'Saved successfully';
          })
          .error(function(error){
            $scope.status = error.message;
          });
      }
      
      /**
       * Delete Wbs by UUID.
       */
      $scope.deleteByUuid = function(uuid) {
        wbsService.deleteByUuid(uuid)
          .success(function(){
            $scope.status = 'Deleted successfully';
          })
          .error(function(error){
            $scope.status = error.message;
          });
      }
    }]);
})();