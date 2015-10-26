package models.daos

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import scala.collection.mutable
import scala.concurrent.Future

/**
 * Give access to the user object.
 */
class UserDAOImpl extends UserDAO {
  import UserDAOImpl.users
  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo) = {
    Future.successful(users.find { case (id, user) => user.loginInfo == loginInfo }.map(_._2))
  }

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID) = {
    Future.successful(users.get(userID))
  }

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(user: User) = {
    users += (user.userID -> user)
    Future.successful(user)
  }

  def findUsers(userIds: Seq[UUID]): Future[Seq[User]] = Future.successful{
    users.filterKeys(userIds.contains).values.toSeq
  }
}

/**
 * The companion object.
 */
object UserDAOImpl {
  val bobUser = User(UUID.randomUUID, LoginInfo("credentials", "bob@email.com")
  ,Some("bob"),Some("bob"),Some("Bob Bob"),Some("bob@email.com"),None)
  val users: mutable.HashMap[UUID, User] = mutable.HashMap(bobUser.userID -> bobUser)
}
