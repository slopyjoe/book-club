package models

import java.util.UUID

case class BookClub(id: UUID, name: String, about: String)

object BookClub {
  import play.api.libs.json.Json

  implicit val bookClubFormat = Json.format[BookClub]
}
