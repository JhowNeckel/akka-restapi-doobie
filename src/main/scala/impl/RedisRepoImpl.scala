package impl

import redis.{ByteStringSerializer, RedisClient}
import repositories.Repo

import scala.concurrent.Future
import scala.concurrent.duration.Duration

abstract class RedisRepoImpl extends Repo {

  def db: RedisClient

  override def del(key: String): Future[Long] = db.del(key)

  override def upsert[V](key: String, value: V, expire: Option[Duration] = None)(implicit ev: ByteStringSerializer[V]): Future[Boolean] =
    db.set(key, value)

  override def get(key: String): Future[Option[String]] = db.get[String](key)

}
