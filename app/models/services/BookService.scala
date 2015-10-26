package models.services

import java.util.UUID

import com.google.inject.Inject
import models.Book
import models.daos.BookDAO

import scala.concurrent.Future

trait BookService {
  def find(id: UUID)(implicit userId:UUID): Future[Option[Book]]

  def list(implicit userId:UUID): Future[Seq[Book]]

  def save(book: Book)(implicit userId:UUID): Future[Book]
}


class DummyBookService @Inject()(bookDAO: BookDAO) extends BookService {
  override def find(id: UUID)(implicit userId:UUID): Future[Option[Book]] = bookDAO.find(id)

  override def list(implicit userId:UUID): Future[Seq[Book]] = bookDAO.list

  override def save(book: Book)(implicit userId:UUID): Future[Book] = {
    bookDAO.save(book)
  }
}
