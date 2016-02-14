/**
 * @ngdoc module
 * @fileOverview Event module.
 * @author <a href="mailto:willmorejg@gmail.com">James G Willmore</a>
 * @version 1.0.0
 * @class eventModule
 * @memberOf myApp
 */
(function() {
  /**
   * Module related to Event functionality.
   */
  var eventModule = angular.module('myApp');
  /**
   * Factory related to Event functionality
   */
  eventModule.factory('eventFactory', ['$http', function($http) {
    /**
     * Base REST API URL.
     */
    var path = '';

    /**
     * Constructor.
     */
    var event = {};

    /**
     * Get all the Event's.
     */
    event.getAll = function() {
      return $http.get(path);
    }

    /**
     * Get Event by UUID.
     */
    event.getByUuid = function(uuid) {
      return $http.get(path + '/' + uuid);
    }

    /**
     * Create or update a Event.
     */
    event.createOrUpdate = function(data) {
      return $http.post(url, data);
    }

    /**
     * Delete Event by UUID.
     */
    event.deleteByUuid = function(uuid) {
      return $http.delete(path + '/' + uuid);
    }

    return event;
  }]);
  /**
   * Service related to Event functionality
   */
  eventModule.service('eventService', ['$http', function($http) {
    /**
     * Base REST API URL.
     */
    var path = '';

    /**
     * Get all the Event's.
     */
    event.getAll = function() {
      return $http.get(path);
    }

    /**
     * Get Event by UUID.
     */
    event.getByUuid = function(uuid) {
      return $http.get(path + '/' + uuid);
    }

    /**
     * Create or update a Event.
     */
    event.createOrUpdate = function(data) {
      return $http.post(url, data);
    }

    /**
     * Delete Event by UUID.
     */
    event.deleteByUuid = function(uuid) {
      return $http.delete(path + '/' + uuid);
    }

    return event;
  }]);
  /**
   * Controller related to Event functionality
   */
  eventModule.controller('eventController', ['$scope', 'eventService', function($scope, eventService) {
    /**
     * Status
     */
    $scope.status;

    /**
     * List of Event's
     */
    $scope.eventList;

    getAll();

    /**
     * Get all the Event's.
     */
    function getAll() {
      eventService.getAll()
        .success(function(data) {
          $scope.eventList = data;
        })
        .error(function(error) {
          $scope.status = error.message;
        });
    }

    /**
     * Get Event by UUID.
     */
    $scope.getByUuid = function(uuid) {
      eventService.getByUuid(uuid)
        .success(function(data) {
          $scope.eventList = data;
        })
        .error(function(error) {
          $scope.status = error.message;
        });
    }

    /**
     * Create or update a Event.
     */
    $scope.createOrUpdate = function(data) {
      eventService.createOrUpdate(data)
        .success(function() {
          $scope.status = 'Saved successfully';
        })
        .error(function(error) {
          $scope.status = error.message;
        });
    }

    /**
     * Delete Event by UUID.
     */
    $scope.deleteByUuid = function(uuid) {
      eventService.deleteByUuid(uuid)
        .success(function() {
          $scope.status = 'Deleted successfully';
        })
        .error(function(error) {
          $scope.status = error.message;
        });
    }

    return event;
  }]);
})();