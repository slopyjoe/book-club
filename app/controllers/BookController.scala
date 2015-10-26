package controllers

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import forms.BookForm
import models.services.BookService
import models.{Book, User}
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json

import scala.concurrent.Future


class BookController @Inject()(val bookService: BookService,
                               val messagesApi: MessagesApi,
                               val env: Environment[User, JWTAuthenticator])
  extends Silhouette[User, JWTAuthenticator] {

  import models.Book.bookFormat

  def index = SecuredAction.async { implicit request =>
    bookService.list(request.identity.userID).map { books =>
      Ok(Json.toJson(books))
    }
  }

  def find(id: UUID) = SecuredAction.async { implicit request =>
    bookService.find(id)(request.identity.userID).map { maybeBook =>
      maybeBook.map { book =>
        Ok(Json.toJson(book))
      }.getOrElse(NotFound)
    }.recover {
      case error => InternalServerError("Book controller error")
    }
  }

  def create = SecuredAction.async(parse.json) { implicit request =>
    request.body.validate[BookForm.Data].map { bookData =>
      val book = Book(
        id = UUID.randomUUID,
        name = bookData.name,
        author = bookData.author,
        description = bookData.description,
        chapters = bookData.chapters,
        tags = bookData.tags)

      for {
        createdBook <- bookService.save(book)(request.identity.userID)
      } yield {
        Ok(Json.toJson(createdBook))
      }
    }.recoverTotal {
      case error =>
        Future.successful(InternalServerError("Can't create book"))
    }
  }
}
