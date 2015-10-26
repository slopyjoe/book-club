package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{Environment, LogoutEvent, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.User
import play.api.i18n.MessagesApi
import play.api.libs.json.Json

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param env The Silhouette environment.
 */
class ApplicationController @Inject() (
  val messagesApi: MessagesApi,
  val env: Environment[User, JWTAuthenticator])
  extends Silhouette[User, JWTAuthenticator] {

  /**
   * Returns the user.
   *
   * @return The result to display.
   */
  def user = SecuredAction.async { implicit request =>
    Future.successful(Ok(Json.toJson(request.identity)))
  }

  /**
   * Manages the sign out action.
   */
  def signOut = SecuredAction.async { implicit request =>
    env.eventBus.publish(LogoutEvent(request.identity, request, request2Messages))
    env.authenticatorService.discard(request.authenticator, Ok)
  }

  /**
   * Provides the desired template.
   *
   * @param template The template to provide.
   * @return The template.
   */
  def view(template: String) = UserAwareAction { implicit request =>
    template match {
      case "home" => Ok(views.html.home())
      case "signUp" => Ok(views.html.security.signUp())
      case "signIn" => Ok(views.html.security.signIn())
      case "navigation" => Ok(views.html.navigation())
      case "bookList" => Ok(views.html.books.booksList())
      case "bookView" => Ok(views.html.books.bookView())
      case "bookNew" => Ok(views.html.books.bookNew())
      case "bookClubList" => Ok(views.html.bookClubs.bookClubsList())
      case "bookClubView" => Ok(views.html.bookClubs.bookClubView())
      case "bookClubNew" => Ok(views.html.bookClubs.bookClubNew())
      case _ => NotFound
    }
  }
}
