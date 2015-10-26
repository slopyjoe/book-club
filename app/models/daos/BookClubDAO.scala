package models.daos

import java.util.UUID

import models.BookClub

import scala.concurrent.Future


case class BookClubDetails(bookClub:BookClub, userIds:Seq[UUID], bookIds:Seq[UUID])

trait BookClubDAO {
  def find(id: UUID)(implicit userId: UUID): Future[Option[BookClubDetails]]

  def list(implicit userId: UUID): Future[Seq[BookClub]]

  def save(book: BookClub)(implicit userId: UUID): Future[BookClub]
}
