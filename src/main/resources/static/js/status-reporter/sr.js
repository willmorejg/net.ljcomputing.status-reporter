(function() {
  var sr = angular.module('myApp');

    sr.controller('dateController', [ '$scope', '$sce', function ($scope, $sce) {
      $scope.pickerTooltip = $sce.trustAsHtml('Click to change date');
      $scope.dateInputTooltip = $sce.trustAsHtml('Use the date picker to select a date, or enter it here');
      $scope.searchTooltip = $sce.trustAsHtml('Enter text to search for in the list');
        
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
    
    sr.service('utilService', function(){
      var me = {};
      
      me.updateArray = function(ary, el) {
        var idx = _.findIndex(ary, _.pick(el, 'uuid'));
        
        if(idx !== -1) {
          ary.splice(idx, 1, el);
        } else {
          ary.push(el);
        }
      };
      
      return me;
    });
    
    sr.service('toastrService', function(){
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
          timeOut : 4000,
          showDuration : 600,
          hideDurarion : 600
        });
      };
      
      me.failure = function(message) {
        toastr.error(message, 'FAILED', {
          timeOut : 0,
          showDuration : 600,
          hideDurarion : 600
        });
      };
      
      return me;
    });
})();
