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
        cellTemplate: '<span class="ui-grid-cell-contents"' 
          + ' ng-click="grid.appScope.edit(row.entity)">' 
          + '<a>{{MODEL_COL_FIELD}}</a></span>'
      }, {
        field: 'description'
      }];
      
      /**
       * ng-grid options for the work breakdown structure grid
       */
      $scope.gridOptions = {};
      $scope.gridOptions.data= 'wbsList';
      $scope.gridOptions.enableSorting = true;
      $scope.gridOptions.enableFiltering = true;
      $scope.gridOptions.columnDefs = columnDefs;
      $scope.gridOptions.pageNumber = 1;
      $scope.gridOptions.pageSize = 10;
      $scope.gridOptions.paginationPageSizes = [10, 10, 25, 50, 100, 250, 500];
      $scope.gridOptions.enablePaginationControls = false;
      $scope.gridOptions.rowTemplate = 'js/wbs/wbsRow.htm';
      $scope.gridOptions.expandableRowHeight = 150;
      $scope.gridOptions.expandableRowTemplate = 'js/activity/activityExpandedRow.htm';
      
      $scope.gridOptions.onRegisterApi = function (gridApi) {
        gridApi.expandable.on.rowExpandedStateChanged($scope, function (row) {
//          console.log('row : ', row);
          if (row.isExpanded) {
//            console.log('row.entity : ', row.entity);
            row.entity.subGridOptions = {
                data: row.entity.activities,
                columnDefs: [
                  {
                    name: 'rowHeaderCol'
                    , displayName: ''
                    , width: 30
                    , cellTemplate: '<span class="ui-grid-cell-contents">'
                      + '<span ng-click="grid.appScope.deleteActivity(row.entity)" style="color: red;cursor: pointer;cursor: hand;"><i class="fa fa-minus-square"></i></span>'
                      +'</span>'
                    , enableSorting: false
                    , enableHiding: false
                    , enableColumnMenu: false
                  },
                  {
                    field: 'name',
                    cellTemplate: '<span class="ui-grid-cell-contents"' 
                      + ' ng-click="grid.appScope.editActivity(row.entity, grid.parentRow.entity.uuid)">'
                      + '<span style="color: blue;cursor: pointer;cursor: hand;">{{MODEL_COL_FIELD}}</span></span>'
                  }, {
                    field: 'description'
                  }
                ]
            };
          }
          
          row.entity.subGridOptions.appScopeProvider = $scope.subGridScope;
        });
        
        $scope.gridApi = gridApi;
      };
      
      $scope.subGridScope = {
          editActivity: function(data, uuid) {
            $scope.editActivity(data, uuid);
          },
          menuOptions: function($itemScope, $event, model) { 
            $scope.menuOptions($itemScope, $event, model);
          }, 
          deleteActivity: function(data) {
            $scope.deleteActivity(data);
          }
      };

      /**
       * Change the pagination page size.
       * 
       * @param pageSize
       */
      $scope.changePageSize = function(pageSize) {
        $scope.gridOptions.paginationPageSize = pageSize;
      }
      
      /**
       * Get the total number of pages based upon the total items and and pagination size.
       */
      $scope.getTotalPages = function() {
        return ($scope.gridOptions.totalItems / $scope.gridOptions.paginationPageSize) >= 1 ? 
            ($scope.gridOptions.totalItems / $scope.gridOptions.paginationPageSize) : 
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
//        ['Update', function($itemScope, $event, model) {
//          console.log('$itemScope : ', $itemScope);
//          console.log('$event : ', $event);
//          console.log('model : ', model);
//          $scope.edit(model);
//        }],
        ['Remove', function($itemScope, $event, model) {
          console.log('$itemScope : ', $itemScope);
          console.log('$event : ', $event);
          console.log('model : ', model);
          $scope.deleteByUuid(model.uuid);
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