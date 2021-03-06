/**
 * @ngdoc module
 * @fileOverview Status reporter module.
 * @author <a href="mailto:willmorejg@gmail.com">James G Willmore</a>
 * @version 1.0.0
 * @class sr
 * @memberOf myApp
 */
(function() {
  var sr = angular.module('myApp');

  sr.controller('dateController', ['$scope', '$sce', function($scope, $sce) {
    $scope.pickerTooltip = $sce.trustAsHtml('Click to change date');
    $scope.dateInputTooltip = $sce.trustAsHtml('Use the date picker to select a date, or enter it here');
    $scope.searchTooltip = $sce.trustAsHtml('Enter text to search for in the list');
    $scope.contextMenuTooltip = $sce.trustAsHtml('Right mouse click a name to edit or update');

    $scope.today = function() {
      $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function() {
      $scope.dt = null;
    };

    $scope.picker = {
      opened: false
    };

    $scope.openPicker = function() {
      $scope.picker.opened = true;
    };

    $scope.setDate = function(year, month, day) {
      $scope.dt = new Date(year, month, day);
    };

    $scope.dateOptions = {
      formatYear: 'yy',
      startingDay: 1
    };

    $scope.format = 'MMMM dd, yyyy';
    $scope.altInputFormats = ['M!/d!/yyyy'];
  }]);

  sr.service('utilService', function() {
    var me = {};

    me.updateArray = function(ary, el) {
      var idx = _.findIndex(ary, _.pick(el, 'uuid'));

      if (idx !== -1) {
        ary.splice(idx, 1, el);
      }
      else {
        ary.push(el);
      }

      return _.sortBy(ary, 'name');
    };

    return me;
  });
  
  sr.factory('alertFactory', ['ALERTS', function(ALERTS){
    var me = {};
    
    me.errorAlert = function(error) {
      var msg = (error && error.message) ? error.message : ALERTS.UNKNOWN.MESSAGE;
      
      return {
        type: 'danger',
        msg: msg
      };
    }
    
    return me;
  }]);

  sr.service('toastrService', function() {
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

    var me = {};

    me.success = function(message) {
      toastr.success(message, 'Success!', {
        timeOut: 4000,
        showDuration: 600,
        hideDurarion: 600
      });
    };

    me.failure = function(message) {
      toastr.error(message, 'FAILED', {
        timeOut: 0,
        showDuration: 600,
        hideDurarion: 600
      });
    };

    return me;
  });
  
  sr.controller('idleCtrl', 
    ['$scope', 'Idle', 'Keepalive', '$uibModal', 
     function($scope, Idle, Keepalive, $uibModal) {
      $scope.started = true;
      Idle.watch();
      closeModals();
  
      function closeModals() {
        if ($scope.warning) {
          $scope.warning.close();
          $scope.warning = null;
        }
  
        if ($scope.timedout) {
          $scope.timedout.close();
          $scope.timedout = null;
        }
      }
  
      $scope.$on('IdleStart', function() {
        closeModals();
  
        $scope.warning = $uibModal.open({
          templateUrl: 'warning-dialog.html',
          windowClass: 'modal-danger'
        });
      });
  
      $scope.$on('IdleEnd', function() {
        closeModals();
      });
  
      $scope.$on('IdleTimeout', function() {
        closeModals();
        $scope.timedout = $uibModal.open({
          templateUrl: 'timedout-dialog.html',
          windowClass: 'modal-danger'
        });
      });
  
      $scope.stop = function() {
        closeModals();
        Idle.unwatch();
        $scope.started = false;
      };
  }]);
  
  sr.controller('asideCtrl', function($scope, $aside) {
    
    $scope.asideState = {
      open: false
    };
    
    $scope.openAside = function(position, backdrop) {
      $scope.asideState = {
        open: true,
        position: position
      };
      
      function postClose() {
        $scope.asideState.open = false;
      }
      
      $aside.open({
        templateUrl: 'js/status-reporter/mainMenu.htm',
        placement: position,
        size: 'sm',
        backdrop: backdrop,
        controller: function($scope, $uibModalInstance) {
          $scope.ok = function(e) {
            $uibModalInstance.close();
            e.stopPropagation();
          };
          $scope.cancel = function(e) {
            $uibModalInstance.dismiss();
            e.stopPropagation();
          };
        }
      }).result.then(postClose, postClose);
    }
  });
})();