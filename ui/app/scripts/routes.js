'use strict';

var app = angular.module('uiApp');

app.config(function ($urlRouterProvider, $stateProvider, $httpProvider, $authProvider) {

  var authenticationCheck = function (url, templateUrl) {
    return {
      url: url,
      templateUrl: templateUrl,
      resolve: {
        authenticated: function ($q, $location, $auth) {
          var deferred = $q.defer();

          if (!$auth.isAuthenticated()) {
            $location.path('/signIn');
          } else {
            deferred.resolve();
          }

          return deferred.promise;
        }
      }
    };
  };

  $urlRouterProvider.otherwise('/home');

  $stateProvider
    .state('home', authenticationCheck('/home', '/views/home.html'))
    .state('booksView', authenticationCheck('/books/{bookId}', '/views/bookView.html'))
    .state('booksNew', authenticationCheck('/booksNew', '/views/bookNew.html'))
    .state('books', authenticationCheck('/books', '/views/bookList.html'))
    .state('bookClubsView', authenticationCheck('/bookClubs/{bookClubId}', '/views/bookClubView.html'))
    .state('bookClubsNew', authenticationCheck('/bookClubsNew', '/views/bookClubNew.html'))
    .state('bookClubs', authenticationCheck('/bookClubs', '/views/bookClubList.html'))
    .state('signUp', {url: '/signUp', templateUrl: '/views/signUp.html'})
    .state('signIn', {url: '/signIn', templateUrl: '/views/signIn.html'})
    .state('signOut', {url: '/signOut', template: null, controller: 'SignOutCtrl'});

  $httpProvider.interceptors.push(function ($q, $injector) {
    return {
      request: function (request) {
        // Add auth token for Silhouette if user is authenticated
        var $auth = $injector.get('$auth');
        if ($auth.isAuthenticated()) {
          request.headers['X-Auth-Token'] = $auth.getToken();
        }

        // Add CSRF token for the Play CSRF filter
        var cookies = $injector.get('$cookies');
        var token = cookies.get('PLAY_CSRF_TOKEN');
        if (token) {
          // Play looks for a token with the name Csrf-Token
          // https://www.playframework.com/documentation/2.4.x/ScalaCsrf
          request.headers['Csrf-Token'] = token;
        }

        return request;
      },

      responseError: function (rejection) {
        if (rejection.status === 401) {
          $injector.get('$state').go('signIn');
        }
        return $q.reject(rejection);
      }
    };
  });

  // Auth config
  $authProvider.httpInterceptor = true; // Add Authorization header to HTTP request
  $authProvider.loginOnSignup = true;
  $authProvider.loginRedirect = '/home';
  $authProvider.logoutRedirect = '/';
  $authProvider.signupRedirect = '/home';
  $authProvider.loginUrl = '/signIn';
  $authProvider.signupUrl = '/signUp';
  $authProvider.loginRoute = '/signIn';
  $authProvider.signupRoute = '/signUp';
  $authProvider.tokenName = 'token';
  $authProvider.tokenPrefix = 'satellizer'; // Local Storage name prefix
  $authProvider.authHeader = 'X-Auth-Token';
  $authProvider.platform = 'browser';
  $authProvider.storage = 'localStorage';
});