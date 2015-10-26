package models.daos

import java.util.UUID

import models.Book

import scala.collection.mutable
import scala.concurrent.Future

class BookDAOInMemory extends BookDAO {

  import BookDAOInMemory.{books, user2Books}

  override def find(id: UUID)(implicit userId: UUID): Future[Option[Book]] = {
    val userBook = user2Books.find { user2Book =>
      user2Book._1 == userId && user2Book._2 == id
    }.flatMap(matched => books.get(matched._2))

    Future.successful(userBook)
  }

  override def findBooks(bookIds: Seq[UUID])(implicit userId:UUID): Future[Seq[Book]] = {
    Future.successful{
      books.filterKeys(bookIds.contains).values.toSeq
    }
  }

  override def list(implicit userId: UUID): Future[Seq[Book]] = {
    val userBooks = user2Books.filter(_._1 == userId).map(_._2)

    Future.successful(books.filterKeys(userBooks.contains).values.toSeq)
  }

  override def save(book: Book)(implicit userId: UUID): Future[Book] = {
    books += (book.id -> book)
    user2Books += ((userId, book.id))

    Future.successful(book)
  }
}

object BookDAOInMemory {
  val bobId = UserDAOImpl.bobUser.userID
  val book = Book(UUID.randomUUID, "Perfect Shadow", "author", "description", 4, Seq("play", "learn"))
  val book2 = Book(UUID.randomUUID, "Not allowed by anyone", "author", "description", 4, Seq("play", "learn"))
  val books: mutable.HashMap[UUID, Book] = mutable.HashMap(book.id -> book, book2.id -> book2)
  val user2Books: mutable.Set[(UUID, UUID)] = mutable.Set((bobId, book.id))

}
