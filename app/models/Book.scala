package models

import java.util.UUID

case class Book(id: UUID,
                name : String,
                author: String,
                description: String,
                chapters: Int,
                tags: Seq[String] = Seq.empty)

object Book {
  import play.api.libs.json.Json

  implicit val bookFormat = Json.format[Book]

}
