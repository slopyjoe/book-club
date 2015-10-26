package models

import java.util.UUID

import org.scalatest.{FunSpec, Matchers}


class BookSpec extends FunSpec with Matchers{
  describe("A Book") {
    val uuid = UUID.randomUUID
    val book = Book(
      id = uuid,
      name = "a name",
      author = "an author",
      description = "a description",
      chapters = 4)

    it("should have an id") {
      book.id should equal(uuid)
    }

    it("should have a name") {
      book.name should equal ("a name")
    }

    it("should have an author") {
      book.author should equal ("an author")
    }

    it("should have a description") {
      book.description should equal ("a description")
    }

    it("should have a chapters") {
      book.chapters should equal (4)
    }

    describe("tags") {
      it("should be empty by default") {
        book.tags shouldBe empty
      }

      it("should contain only strings") {
        val bookWithTags = book.copy(tags = Seq("A", "tag"))
        bookWithTags.tags should not be empty
        bookWithTags.tags should contain allOf ("A", "tag")
      }
    }
  }
}
