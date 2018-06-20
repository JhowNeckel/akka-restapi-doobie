package repositories

import scala.concurrent.Future
import scala.concurrent.duration.Duration

trait Repo {
  def del(key: String): Future[Long]
  def upsert[V](key: String, value: V, expire: Option[Duration] = None): Future[Boolean]
  def get(key: String): Future[Option[String]]
}
