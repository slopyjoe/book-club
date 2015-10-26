package modules

import com.google.inject.AbstractModule
import models.daos.{BookClubDAO, BookClubDAOInMemory, BookDAOInMemory, BookDAO}
import models.services.{BookClubService, DummyBookClubService, DummyBookService, BookService}
import net.codingwell.scalaguice.ScalaModule

class BookClubModule extends AbstractModule with ScalaModule {
  def configure = {
    bind[BookService].to[DummyBookService]
    bind[BookDAO].to[BookDAOInMemory]
    bind[BookClubService].to[DummyBookClubService]
    bind[BookClubDAO].to[BookClubDAOInMemory]
  }
}
