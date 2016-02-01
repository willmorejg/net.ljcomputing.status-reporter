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
      $http.defaults.headers.post["Content-Type"] = 'application/json';
      
      /**
       * Constructor.
       */
      var me = {};
      
      /**
       * Get all the Wbs's.
       */
      me.getAll = function() {
        return $http.get(path);
      }
      
      /**
       * Get Wbs by UUID.
       */
      me.getByUuid = function(uuid) {
        return $http.get(path + '/' + uuid);
      }
      
      /**
       * Create or update a Wbs.
       */
      me.createOrUpdate = function(data) {
        return $http.post(path, data);
      }
      
      /**
       * Delete Wbs by UUID.
       */
      me.deleteByUuid = function(uuid) {
        return $http.delete(path + '/' + uuid);
      }
      
      return me;
    }]);
    /**
     * Service related to Wbs functionality
     */
    wbsModule.service('wbsService', ['wbsFactory', function(wbsFactory){
      var me = {};
      
      /**
       * Get all the Wbs's.
       */
      me.getAll = function() {
        return wbsFactory.getAll();
      }
      
      /**
       * Get Wbs by UUID.
       */
      me.getByUuid = function(uuid) {
        return wbsFactory.getByUuid(uuid);
      }
      
      /**
       * Create or update a Wbs.
       */
      me.createOrUpdate = function(data) {
        return wbsFactory.createOrUpdate(data);
      }
      
      /**
       * Delete Wbs by UUID.
       */
      me.deleteByUuid = function(uuid) {
        return wbsFactory.deleteByUuid(uuid);
      }
      
      return me;
    }]);
    /**
     * Controller related to Wbs functionality
     */
    wbsModule.controller('wbsController', ['$scope', '$state', '$uibModal', 'wbsService', function($scope, $state, $uibModal, wbsService){
      toastr.options = {
          "closeButton": true,
          "debug": false,
          "newestOnTop": true,
          "progressBar": true,
          "positionClass": "toast-top-right",
          "preventDuplicates": true,
          "onclick": null,
          "showDuration": "600",
          "hideDuration": "600",
          "timeOut": "0",
          "extendedTimeOut": "6000",
          "showEasing": "swing",
          "hideEasing": "linear",
          "showMethod": "fadeIn",
          "hideMethod": "fadeOut"
        };
      
      /**
       * List of Wbs's
       */
      $scope.wbsList;
      
      /**
       * Sorting functionality
       */
      $scope.sort = function(key) {
        $scope.sortKey = key;
        $scope.reverse = !$scope.reverse;
      }
  
      getAll();
      
      /**
       * Get all the Wbs's.
       */
      function getAll() {
        wbsService.getAll()
          .success(function(data){
            $scope.wbsList = data;
          })
          .error(function(error){
            toastr.error(error.message);
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
            toastr.error(error.message);
          });
      }
      
      /**
       * Create or update a Wbs.
       */
      $scope.createOrUpdate = function(data) {
        wbsService.createOrUpdate(data)
          .success(function(data){
            if(!$scope.wbsList) {
              $scope.wbsList = [];
            }

            $scope.wbsList.push(data);
            toastr.success('Saved successfully');
          })
          .error(function(error){
            toastr.error(error.message);
          });
      }
      
      /**
       * Delete Wbs by UUID.
       */
      $scope.deleteByUuid = function(uuid) {
        wbsService.deleteByUuid(uuid)
          .success(function(){
            toastr.success('Deleted successfully');
            $scope.wbsList = [];
            getAll();
          })
          .error(function(error){
            toastr.error(error.message);
          });
      }
      
      $scope.edit = function(data) {
        $scope.wbs = data ? data : {'name' : '', 'description' : ''};
        var modalInstance = $uibModal.open({
          templateUrl : 'js/wbs/wbsModal.htm',
          controller : 'wbsModalController',
          backdrop : 'static',
          scope : $scope
        });
        
        modalInstance.result.then(function (data) {
          $scope.createOrUpdate(data);
        }, function () {
        });

      }
    }]);
    
    wbsModule.controller('wbsModalController', function($scope, $uibModalInstance){
      $scope.save = function () {
        $uibModalInstance.close($scope.wbs);
      };

      $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
      };
    });
})();