package controllers

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import forms.BookClubForm
import models.services.BookClubService
import models.{BookClub, User}
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json

import scala.concurrent.Future


class BookClubController @Inject()(val bookClubService: BookClubService,
                                   val messagesApi: MessagesApi,
                                   val env: Environment[User, JWTAuthenticator])
  extends Silhouette[User, JWTAuthenticator] {

  import models.BookClub.bookClubFormat
  import models.Book.bookFormat
  import models.User.jsonFormat

  def index = SecuredAction.async { implicit request =>
    bookClubService.list(request.identity.userID).map { bookClubs =>
      Ok(Json.toJson(bookClubs))
    }
  }

  def find(id: UUID) = SecuredAction.async { implicit request =>
    bookClubService.find(id)(request.identity.userID).map {
      case (Some(bookClub), users, books) => {
        Ok(Json.obj("bookClub" -> bookClub, "users" -> users, "books" -> books))
      }
      case _ => NotFound
    }.recover {
      case error => InternalServerError("BookClub controller error")
    }
  }

  def create = SecuredAction.async(parse.json) { implicit request =>
    request.body.validate[BookClubForm.Data].map { bookData =>
      val bookClub = BookClub(
        id = UUID.randomUUID,
        name = bookData.name,
        about = bookData.about)

      for {
        createdBookClub <- bookClubService.save(bookClub)(request.identity.userID)
      } yield {
        Ok(Json.toJson(createdBookClub))
      }
    }.recoverTotal {
      case error =>
        Future.successful(InternalServerError("Can't create book"))
    }
  }
}
