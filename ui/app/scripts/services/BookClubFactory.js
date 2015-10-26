'use strict';

/*global app: false */

app.factory('BookClubFactory', function ($http) {
  return {
    list: function () {
      return $http.get('/bookClubs');
    },
    find: function (bookClubId) {
      return $http.get('/bookClubs/' + bookClubId);
    },
    create: function (bookClub) {
      return $http.post('/bookClubs', bookClub);
    }
  };
});
