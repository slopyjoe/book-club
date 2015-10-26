package models.daos

import java.util.UUID

import models.Book

import scala.concurrent.Future

trait BookDAO {
  def find(id: UUID)(implicit userId:UUID): Future[Option[Book]]

  def findBooks(bookIds: Seq[UUID])(implicit userId:UUID): Future[Seq[Book]]

  def list(implicit userId:UUID): Future[Seq[Book]]

  def save(book: Book)(implicit userId:UUID): Future[Book]
}
