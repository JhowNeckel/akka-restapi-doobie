package dao

import cats.effect.IO
import com.typesafe.config.Config
import doobie.Transactor

class DatabaseConnector(implicit config: Config) {
  val driver = config.getString("db.driver")
  val url = config.getString("db.url")
  val user = config.getString("db.user")
  val pass = config.getString("db.password")

  def getTransactor = Transactor.fromDriverManager[IO](driver,url,user,pass)
}
