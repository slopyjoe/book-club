package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

object BookClubForm {

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "about" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

  case class Data(name: String, about: String)

  object Data {
    implicit val jsonFormat = Json.format[Data]
  }
}
