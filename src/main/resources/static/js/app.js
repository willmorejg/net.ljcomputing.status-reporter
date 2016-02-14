/**
 * @ngdoc module
 * @fileOverview Application configuration.
 * @author <a href="mailto:willmorejg@gmail.com">James G Willmore</a>
 * @version 1.0.0
 * @namespace myApp
 */
(function() {
  var myApp = angular.module('myApp', [
    'ui.router'
    , 'ngAnimate'
    , 'ngSanitize'
    , 'ngMessages'
    , 'ui.bootstrap'
    , 'ui.bootstrap.datepicker'
    , 'ui.bootstrap.tooltip'
    , 'angularUtils.directives.dirPagination'
    , 'ngAside'
    , 'dm.stickyNav'
    , 'ui.tree'
    , 'ui.router'
    , 'ui.grid'
    , 'ui.grid.pagination'
    , 'ui.grid.expandable'
    , 'ngIdle'
    , 'ui.bootstrap.contextMenu'
  ]);

  /**
   * @description Router configuration
   * @function config
   * @param {$stateProvider} state provider
   * @param {$urlRouterProvider} URL router provider
   */
  myApp.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/about');

      $stateProvider
        .state('home', {
          url: '/home',
          templateUrl: 'js/status-reporter/about.htm'
        })
        .state('dashboard', {
          url: '/dashboard',
          templateUrl: 'js/wbs/wbsList.htm',
          controller: 'wbsController'
        })
        .state('manage', {
          url: '/manage',
          templateUrl: 'js/wbs/wbsList.htm',
          controller: 'wbsController'
        })
        .state('overview', {
          url: '/overview',
          templateUrl: 'js/status-reporter/overview.htm'
        })
        .state('about', {
          url: '/about',
          templateUrl: 'js/status-reporter/about.htm'
        })
        .state('contact', {
          url: '/contact',
          templateUrl: 'js/status-reporter/contact.htm'
        });
    }
  ]);
  
  myApp.config(function($provide){
    
    $provide.decorator('$exceptionHandler', 
      function($delegate, $injector){
        return function(exception, cause){
          $delegate(exception, cause);
          
          var rScope = $injector.get('$rootScope');
          
          rScope.alerts.push({
            type: 'danger',
            msg: 'Something REALLY bad happened: An unknown error occured. ' + 
              'Refresh the page and try again.'});

          JDEV.logging.logToServer(
              'An exception occured: ' + 
              JSON.stringify(exception.message) + 
              '; ' 
              + JSON.stringify(exception.stack));
        };
    });
  });
  
  myApp.config(
      ['KeepaliveProvider', 'IdleProvider', 
       function(KeepaliveProvider, IdleProvider) {
    IdleProvider.idle(600);
    IdleProvider.timeout(60);
    IdleProvider.keepalive(false);
    KeepaliveProvider.interval(30);
  }]);
  
  /**
   * REST API constant
   * 
   * @const
   */
  myApp.constant('REST_API', {
    "WBS": {
      "BASE": 'sr/wbs'
    },
    "ACTIVITY": {
      "BASE": '/activity'
    }
  });

  /**
   * Alert messages
   * 
   * @const
   */
  myApp.constant('ALERTS', {
    "UNKNOWN": {
      "MESSAGE": 'An unknown error occured. Refresh the page and try again.'
    }
  });
  
  /**
   * Run function.
   */
  myApp.run(function($rootScope) {
    /**
     * Alerts array
     */
    $rootScope.alerts = [];
    
    /**
     * Close an alert.
     * 
     * @param index the index number of the alert to remove.
     */
    $rootScope.closeAlert = function(index) {
      $rootScope.alerts.splice(index, 1);
    };
  });

})();