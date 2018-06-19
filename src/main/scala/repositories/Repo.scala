package repositories

import redis.ByteStringSerializer

import scala.concurrent.Future
import scala.concurrent.duration.Duration

trait Repo {
  def del(key: String): Future[Long]
  def upsert[V](key: String, value: V, expire: Option[Duration] = None)(implicit ev: ByteStringSerializer[V]): Future[Boolean]
  def get(key: String): Future[Option[String]]
}
