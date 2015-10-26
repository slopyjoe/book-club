'use strict';

/*global app: false */

app.controller('BookListCtrl', ['$scope', '$alert', 'BookFactory',
  function ($scope, $alert, BookFactory) {

  $scope.books = [];

  $scope.init = function () {
    BookFactory.list()
      .success(function (data) {
        $scope.books = data;
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

app.controller('BookViewCtrl', ['$scope', '$alert', '$stateParams', 'BookFactory',
  function ($scope, $alert, $stateParams, BookFactory) {

    $scope.book = {};
    var bookId = $stateParams.bookId;

    $scope.init = function () {
      BookFactory.find(bookId)
        .success(function (data) {
          $scope.book = data;
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

app.controller('BookNewCtrl', ['$scope', '$alert', 'BookFactory',
  function ($scope, $alert, BookFactory) {

    $scope.submit = function() {
      BookFactory.create({
        name: $scope.name,
        author: $scope.author,
        description: $scope.description,
        chapters: $scope.chapters,
        tags: [$scope.tags]
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
