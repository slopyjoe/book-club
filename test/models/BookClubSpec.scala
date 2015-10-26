package models

import java.util.UUID

import org.scalatest.{FunSpec, Matchers}


class BookClubSpec extends FunSpec with Matchers {
  describe("A BookClub") {
    val uuid: UUID = UUID.randomUUID
    val book = BookClub(
      id = uuid,
      name = "some club",
      about = "craziness")

    i("should have an id") {
      book.id should equal(uuid)
    }
    it("should have a name") {
      book.name should equal("some club")
    }

    it("should have a about") {
      book.about should equal("craziness")
    }
  }
}
