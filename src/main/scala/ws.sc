import cats.effect._
import doobie._
import doobie.implicits._
import org.json4s.DefaultJsonFormats

def getTransactor = Transactor.fromDriverManager[IO](
  "org.postgresql.Driver",
  "jdbc:postgresql:world",
  "postgres",
  ""
)

val xa = getTransactor

case class Person(id: Long, name: String, age: Int)

val query = sql"select * from person".query[Person].to[List]

val persons = Some(query.transact(xa).unsafeRunSync())
persons.foreach(println)


Composite[Person]
