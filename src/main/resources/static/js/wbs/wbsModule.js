/**
 * @ngdoc module
 * @fileOverview Work breakdown structure module.
 * @author <a href="mailto:willmorejg@gmail.com">James G Willmore</a>
 * @version 1.0.0
 * @class wbsModule
 * @memberOf myApp
 */
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
    '$scope'
    , '$state'
    , '$uibModal'
    , 'uiGridConstants'
    , 'alertFactory'
    , 'utilService'
    , 'toastrService'
    , 'wbsService'
    , 'activityService',
    function(
      $scope
      , $state
      , $uibModal
      , uiGridConstants
      , alertFactory
      , utilService
      , toastrService
      , wbsService
      , activityService
    ) {
      
      /**
       * List of Wbs's
       */
      $scope.wbsList = [];

      /**
       * Work breakdown structure column definitions
       */
      var columnDefs = [{
        field: 'name',
        sort: {
          direction: uiGridConstants.ASC,
          priority: 0
        },
        cellTemplate: '<div class="ui-grid-cell-contents"' 
          + ' context-menu="grid.appScope.menuOptions"'
          + ' model="row.entity">' 
          + '<a>{{MODEL_COL_FIELD}}</a></div>'
      }, {
        field: 'description'
      }];

      var columnDefs1 = [{
        field: 'name',
        sort: {
          direction: uiGridConstants.ASC,
          priority: 0
        },
        cellTemplate: '<div class="ui-grid-cell-contents"' 
          + ' context-menu="grid.appScope.menuOptions1"'
          + ' model="row.entity">' 
          + '<a>{{MODEL_COL_FIELD}}</a></div>'
      }, {
        field: 'description'
      }];
      
      $scope.activitiesList = [];
      $scope.gridOptions1 = {};
      $scope.gridOptions1.data= 'activitiesList';
      $scope.gridOptions1.enableSorting = true;
      $scope.gridOptions1.enableFiltering = true;
      $scope.gridOptions1.flatEntityAccess = true;
      $scope.gridOptions1.columnDefs = columnDefs1;
      $scope.gridOptions1.pageNumber = 1;
      $scope.gridOptions1.pageSize = 10;
      $scope.gridOptions1.paginationPageSizes = [10, 10, 25, 50, 100, 250, 500];
      $scope.gridOptions1.enablePaginationControls = false;
      $scope.gridOptions1.rowTemplate = 'js/activity/activityRow.htm';

      /**
       * ng-grid options for the work breakdown structure grid
       */
      $scope.gridOptions = {};
      $scope.gridOptions.data= 'wbsList';
      $scope.gridOptions.enableSorting = true;
      $scope.gridOptions.enableFiltering = true;
      $scope.gridOptions.flatEntityAccess = true;
      $scope.gridOptions.columnDefs = columnDefs;
      $scope.gridOptions.pageNumber = 1;
      $scope.gridOptions.pageSize = 10;
      $scope.gridOptions.paginationPageSizes = [10, 10, 25, 50, 100, 250, 500];
      $scope.gridOptions.enablePaginationControls = false;
      $scope.gridOptions.rowTemplate = 'js/wbs/wbsRow.htm';
      
      $scope.gridOptions.enableRowSelection = true;
      $scope.gridOptions.enableRowHeaderSelection = false;
      $scope.gridOptions.multiSelect = false;
      $scope.gridOptions.modifierKeysToMultiSelect = false;
      $scope.gridOptions.noUnselect = true;
      
      $scope.gridOptions.onRegisterApi = function (gridApi) {
        $scope.gridApi = gridApi;

        gridApi.selection.on.rowSelectionChanged($scope,function(row){
//          console.log('row: ', row);
          $scope.getActivities(row);
        });
        
        $scope.gridApi = gridApi;
      };

      $scope.toggleRowSelection = function() {
        $scope.gridApi.selection.clearSelectedRows();
        $scope.gridOptions.enableRowSelection = !$scope.gridOptions.enableRowSelection;
        $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.OPTIONS);
      };
      
      $scope.getActivities = function(row) {
        activityService.getAllByWbs(row.entity.uuid)
        .success(function(data) {
          var newData = _.map(data, function(element) { 
            return _.extend({}, element, {wbs_uuid: row.entity.uuid});
          });
          $scope.activitiesList = newData;
        })
        .error(function(error) {
          $scope.status = error.message;
        });
      }

      /**
       * Change the pagination page size.
       * 
       * @param pageSize
       */
      $scope.changePageSize = function(pageSize, opts) {
        opts.paginationPageSize = pageSize;
      }
      
      /**
       * Get the total number of pages based upon the total items and and pagination size.
       */
      $scope.getTotalPages = function(opts) {
        return (opts.totalItems / opts.paginationPageSize) >= 1 ? 
            (opts.totalItems / opts.paginationPageSize) : 
              1;
      }

      /**
       * Sorting functionality
       */
      $scope.sort = function(key) {
        $scope.sortKey = key;
        $scope.reverse = !$scope.reverse;
      }

      /**
       * Context menu items
       */
      $scope.menuOptions = [
        ['Edit', function($itemScope, $event, model) {
          $scope.edit(model);
        }],
        ['Remove', function($itemScope, $event, model) {
          $scope.deleteByUuid(model.uuid);
        }]
      ];

      $scope.menuOptions1 = [
        ['Edit', function($itemScope, $event, model) {
          $scope.editActivity(model, model.wbs_uuid);
        }],
        ['Remove', function($itemScope, $event, model) {
          $scope.deleteActivity(model);
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
//            console.log('data:'+ JSON.stringify(data));
            toastrService.success('Data refreshed successfully');
          })
          .error(function(error) {
            $scope.alerts.push(alertFactory.errorAlert(error));
          });
      }

      /**
       * Get Wbs by UUID.
       * 
       * @param uuid
       */
      $scope.getByUuid = function(uuid) {
        wbsService.getByUuid(uuid)
          .success(function(data) {
            $scope.wbsList = data;
          })
          .error(function(error) {
            $scope.alerts.push(alertFactory.errorAlert(error));
          });
      }

      /**
       * Create or update a Wbs.
       * 
       * @param data
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
            $scope.alerts.push(alertFactory.errorAlert(error));
          });
      }

      /**
       * Delete Wbs by UUID.
       * 
       * @param uuid
       */
      $scope.deleteByUuid = function(uuid) {
        wbsService.deleteByUuid(uuid)
          .success(function() {
            toastrService.success('Deleted successfully');
            $scope.wbsList = [];
            getAll();
          })
          .error(function(error) {
            $scope.alerts.push(alertFactory.errorAlert(error));
          });
      }

      /**
       * Edit a wbs
       * 
       * @param data
       */
      $scope.edit = function(data) {
        $scope.wbs = data ? _.cloneDeep(data) : {
          'name': '',
          'description': ''
        };
        $scope.action = _.isUndefined(data) ? 'Add' : 'Edit';
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

      /**
       * Edit an activity
       * 
       * @param wbs
       */
      $scope.editActivity = function(data, uuid) {
        $scope.wbsUuid = uuid;
        $scope.activity = data ? _.cloneDeep(data) : {
          'name': '',
          'description': ''
        };
        $scope.action = _.isUndefined(data) ? 'Add' : 'Edit';
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
              $scope.alerts.push(alertFactory.errorAlert(error));
            });
        });
      }
      
      $scope.deleteActivity = function(data) {
        activityService.deleteByUuid(data.uuid)
          .success(function(data) {
            toastrService.success('Saved successfully');
            getAll();
          })
          .error(function(error) {
            $scope.alerts.push(alertFactory.errorAlert(error));
          });
      }
    }
  ]);

  /**
   * WBS modal controller
   */
  wbsModule.controller('wbsModalController', function($scope, $uibModalInstance) {
    /**
     * Save the wbs, and close the modal
     */
    $scope.save = function() {
      $uibModalInstance.close($scope.wbs);
    };

    /**
     * Cancel changes to the wbs, and close the modal
     */
    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    };
  });

  /**
   * Activity modal controller
   */
  wbsModule.controller('activityModalController', function($scope, $uibModalInstance) {
    /**
     * Save the activity, and close the modal
     */
    $scope.save = function() {
      $uibModalInstance.close($scope.activity);
    };

    /**
     * Cancel changes to the activity, and close the modal
     */
    $scope.cancel = function() {
      $uibModalInstance.dismiss('cancel');
    };
  });
})();