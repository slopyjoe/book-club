package models.services

import java.util.UUID

import com.google.inject.Inject
import models.BookClub
import models.Book
import models.User
import models.daos.{BookDAO, UserDAO, BookClubDAO}
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future


trait BookClubService {
  def find(id: UUID)(implicit userId: UUID): Future[(Option[BookClub], Seq[User], Seq[Book])]

  def list(implicit userId: UUID): Future[Seq[BookClub]]

  def save(book: BookClub)(implicit userId: UUID): Future[BookClub]
}


class DummyBookClubService @Inject()(bookClubDAO: BookClubDAO, userDAO: UserDAO, bookDAO: BookDAO) extends BookClubService {

  override def find(id: UUID)(implicit userId: UUID): Future[(Option[BookClub], Seq[User], Seq[Book])] = {
    for {
      bookClubDetails <- bookClubDAO.find(id)
      books <- bookDAO.findBooks(bookClubDetails.map(_.bookIds).getOrElse(Seq.empty))
      users <- userDAO.findUsers(bookClubDetails.map(_.userIds).getOrElse(Seq.empty))
    } yield {
      (bookClubDetails.map(_.bookClub), users, books)
    }
  }

  override def list(implicit userId: UUID): Future[Seq[BookClub]] = bookClubDAO.list

  override def save(book: BookClub)(implicit userId: UUID): Future[BookClub] = {
    bookClubDAO.save(book)
  }
}
