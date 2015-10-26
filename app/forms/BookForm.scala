package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

object BookForm {

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "author" -> nonEmptyText,
      "description" -> text,
      "chapters" -> number,
      "tags" -> list(text)
    )(Data.apply)(Data.unapply)
  )

  case class Data(
                   name:String,
                   author:String,
                   description:String,
                   chapters:Int,
                   tags:List[String])

  object Data {
    implicit val jsonFormat = Json.format[Data]
  }
}
