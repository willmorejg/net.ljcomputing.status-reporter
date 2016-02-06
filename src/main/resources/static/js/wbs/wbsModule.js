(function() {
  /**
   * Module related to Wbs functionality.
   */
  var wbsModule = angular.module('myApp');

  /**
   * Factory related to Wbs functionality
   */
  wbsModule.factory('wbsFactory', ['REST_API', '$http', function(REST_API, $http) {
    $http.defaults.headers.post["Content-Type"] = 'application/json';

    /**
     * Base REST API URL.
     */
    var path = REST_API.WBS.BASE;

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
  wbsModule.service('wbsService', ['wbsFactory', function(wbsFactory) {
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
  wbsModule.controller('wbsController', [
    '$scope', '$state', '$uibModal', 'uiGridConstants', 'utilService', 'toastrService', 'wbsService', 'activityService',
    function(
      $scope, $state, $uibModal, uiGridConstants, utilService, toastrService, wbsService, activityService
    ) {

      /**
       * List of Wbs's
       */
      $scope.wbsList = [];

      var columnDefs = [{
        field: 'name',
        sort: {
          direction: uiGridConstants.ASC,
          priority: 0
        }
      }, {
        field: 'description'
      }];
      
      $scope.gridOptions = {};
      $scope.gridOptions.data= 'wbsList';
      $scope.gridOptions.enableSorting = true;
      $scope.gridOptions.enableFiltering = true;
      $scope.gridOptions.columnDefs = columnDefs;
      $scope.gridOptions.pageNumber = 1;
      $scope.gridOptions.pageSize = 10;
      $scope.gridOptions.paginationPageSizes = [10, 25, 50, 100, 250, 500];
      $scope.gridOptions.enablePaginationControls = false;
      
      $scope.gridOptions.onRegisterApi = function (gridApi) {
        $scope.gridApi = gridApi;
      }
      
      console.log('$scope.gridOptions : ', $scope.gridOptions);

      /**
       * Sorting functionality
       */
      $scope.sort = function(key) {
        $scope.sortKey = key;
        $scope.reverse = !$scope.reverse;
      }

      $scope.menuOptions = [
        ['Update', function($itemScope, $event) {
          $scope.edit($itemScope.wbs);
        }],
        ['Remove', function($itemScope, $event) {
          $scope.deleteByUuid($itemScope.wbs.uuid);
        }]
      ];

      getAll();

      /**
       * Get all the Wbs's.
       */
      function getAll() {
        wbsService.getAll()
          .success(function(data) {
            $scope.wbsList = data;
            toastrService.success('Data refreshed successfully');
          })
          .error(function(error) {
            toastrService.failure(error.message);
          });
      }

      /**
       * Get Wbs by UUID.
       */
      $scope.getByUuid = function(uuid) {
        wbsService.getByUuid(uuid)
          .success(function(data) {
            $scope.wbsList = data;
          })
          .error(function(error) {
            toastrService.failure(error.message);
          });
      }

      /**
       * Create or update a Wbs.
       */
      $scope.createOrUpdate = function(data) {
        wbsService.createOrUpdate(data)
          .success(function(data) {
            if (!$scope.wbsList) {
              $scope.wbsList = [];
            }

            getAll();
            toastrService.success('Saved successfully');
          })
          .error(function(error) {
            toastrService.failure(error.message);
          });
      }

      /**
       * Delete Wbs by UUID.
       */
      $scope.deleteByUuid = function(uuid) {
        wbsService.deleteByUuid(uuid)
          .success(function() {
            toastrService.success('Deleted successfully');
            $scope.wbsList = [];
            getAll();
          })
          .error(function(error) {
            toastrService.failure(error.message);
          });
      }

      $scope.edit = function(data) {
        $scope.wbs = data ? _.cloneDeep(data) : {
          'name': '',
          'description': ''
        };
        $scope.action = _.isUndefined(data) ? 'Edit' : 'Add';
        var modalInstance = $uibModal.open({
          templateUrl: 'js/wbs/wbsModal.htm',
          controller: 'wbsModalController',
          backdrop: 'static',
          scope: $scope
        });

        modalInstance.result.then(function(data) {
          $scope.createOrUpdate(data);
        });

      }

      $scope.addActivity = function(wbs) {
        $scope.wbs = wbs
        $scope.wbsUuid = wbs.uuid;
        $scope.activity = {
          'name': '',
          'description': ''
        };
        $scope.action = 'Add';
        var modalInstance = $uibModal.open({
          templateUrl: 'js/activity/activityModal.htm',
          controller: 'activityModalController',
          backdrop: 'static',
          scope: $scope
        });

        modalInstance.result.then(function(data) {
          activityService.createOrUpdate(data, $scope.wbsUuid)
            .success(function(data) {
              toastrService.success('Saved successfully');
              getAll();
            })
            .error(function(error) {
              toastrService.failure(error.message);
            });
        });
      }
    }
  ]);

  wbsModule.controller('wbsModalController', function($scope, $uibModalInstance) {
    $scope.save = function() {
      $uibModalInstance.close($scope.wbs);
    };

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    };
  });

  wbsModule.controller('activityModalController', function($scope, $uibModalInstance) {
    $scope.save = function() {
      $uibModalInstance.close($scope.activity);
    };

    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    };
  });
})();