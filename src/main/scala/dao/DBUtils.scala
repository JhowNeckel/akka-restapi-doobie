package dao

import doobie._
import cats.effect._
import com.typesafe.config.ConfigFactory

object DBUtils extends {
  val config = ConfigFactory.load()

  def getTransactor = Transactor.fromDriverManager[IO](
    config.getString("db.driver"),
    config.getString("db.url"),
    config.getString("db.user"),
    config.getString("db.password")
  )
}