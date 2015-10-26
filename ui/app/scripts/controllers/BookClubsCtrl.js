'use strict';

/*global app: false */

app.controller('BookClubListCtrl', ['$scope', '$alert', 'BookClubFactory',
  function ($scope, $alert, BookClubFactory) {

  $scope.bookClubs = [];

  $scope.init = function () {
    BookClubFactory.list()
      .success(function (data) {
        $scope.bookClubs = data;
      })
      .error(function (error) {
        $alert({
          content: error.message,
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        });
      });
  };
}]);

app.controller('BookClubViewCtrl', ['$scope', '$alert', '$stateParams', 'BookClubFactory',
  function ($scope, $alert, $stateParams, BookClubFactory) {

    $scope.bookClub = {};
    var bookClubId = $stateParams.bookClubId;

    $scope.init = function () {
      BookClubFactory.find(bookClubId)
        .success(function (data) {
          $scope.bookClub = data.bookClub;
          $scope.users = data.users;
          $scope.users = data.users;
          $scope.books = data.books;
        })
        .error(function (error) {
          $alert({
            content: error.message,
            animation: 'fadeZoomFadeDown',
            type: 'material',
            duration: 3
          });
        });
    };
}]);

app.controller('BookClubNewCtrl', ['$scope', '$alert', 'BookClubFactory',
  function ($scope, $alert, BookClubFactory) {

    $scope.submit = function() {
      BookClubFactory.create({
        name: $scope.name,
        about: $scope.about
      }).then(function(){
        $alert({
          content: $scope.name + ', successfully created!',
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        })
      }).catch(function(response) {
        $alert({
          content: response.data.message,
          animation: 'fadeZoomFadeDown',
          type: 'material',
          duration: 3
        });
      });
    };

}]);
