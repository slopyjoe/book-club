'use strict';

/*global app: false */

app.factory('BookFactory', function ($http) {
  return {
    list: function () {
      return $http.get('/books');
    },
    find: function (bookId) {
      return $http.get('/books/' + bookId);
    },
    create: function (book) {
      return $http.post('/books', book);
    }
  };
});
