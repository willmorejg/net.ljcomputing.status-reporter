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
})();
