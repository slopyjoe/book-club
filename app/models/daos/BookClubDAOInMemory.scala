package models.daos

import java.util.UUID

import models.{BookClub, Book}

import scala.collection.mutable
import scala.concurrent.Future

class BookClubDAOInMemory extends BookClubDAO {

  import BookClubDAOInMemory.{bookClubs, user2BookClubs, book2BookClubs}

  override def find(id: UUID)(implicit userId: UUID): Future[Option[BookClubDetails]] = {
    val userBookClub = user2BookClubs.find { user2BookClub =>
      user2BookClub._1 == userId && user2BookClub._2 == id
    }.flatMap(matched => bookClubs.get(matched._2))

    Future.successful(
      userBookClub.map(bc => BookClubDetails(
        bc,
        user2BookClubs.filter(_._2 == bc.id).map(_._1).toSeq,
        book2BookClubs.filter(_._2 == bc.id).map(_._1).toSeq
      )))
  }

  override def list(implicit userId: UUID): Future[Seq[BookClub]] = {
    val userBooks = user2BookClubs.filter(_._1 == userId).map(_._2)

    Future.successful(bookClubs.filterKeys(userBooks.contains).values.toSeq)
  }

  override def save(book: BookClub)(implicit userId: UUID): Future[BookClub] = {
    bookClubs += (book.id -> book)
    user2BookClubs += ((userId, book.id))

    Future.successful(book)
  }
}


object BookClubDAOInMemory {
  val bobId = UserDAOImpl.bobUser.userID
  val perfectShadowId = BookDAOInMemory.book.id

  val bookClub = BookClub(UUID.randomUUID, "Secret Club", "It's a secret")
  val bookClub2 = BookClub(UUID.randomUUID, "Secret Club 2", "It's a secret")

  val bookClubs: mutable.HashMap[UUID, BookClub] = mutable.HashMap(bookClub.id -> bookClub, bookClub2.id -> bookClub2)
  val user2BookClubs: mutable.Set[(UUID, UUID)] = mutable.Set((bobId, bookClub.id))
  val book2BookClubs: mutable.Set[(UUID, UUID)] = mutable.Set((perfectShadowId, bookClub.id))

}


