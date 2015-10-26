'use strict';

/**
 * The application.
 */
var app = angular.module('uiApp', [
  'ngResource',
  'ngMessages',
  'ngCookies',
  'ui.router',
  'mgcrea.ngStrap',
  'satellizer'
]);

/**
 * The run configuration.
 */
app.run(function($rootScope) {

  /**
   * The user data.
   *
   * @type {{}}
   */
  $rootScope.user = {};
});