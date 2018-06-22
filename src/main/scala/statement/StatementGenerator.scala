package statement

import util.StringUtils._

trait StatementGenerator[A] {
  def select(table: String): String

  def insert(table: String): String

  def parsert(entity: A): Map[String, Any]
}

object StatementGenerator {

  def apply[A](implicit sg: StatementGenerator[A]): StatementGenerator[A] = sg

  implicit def genericGenerator[A](implicit fieldLister: FieldLister[A]): StatementGenerator[A] = new StatementGenerator[A] {

    override def select(table: String): String = {
      val fields = fieldLister.list.map(uperCamelToLowerSnake).mkString(",")
      s"SELECT $fields FROM $table"
    }

    override def insert(table: String): String = {
      val fieldNames = fieldLister.list.map(uperCamelToLowerSnake)
      val fields = fieldNames.mkString(",")

      val placeholders = List.fill(fieldNames.size)("?").mkString(",")

      s"INSERT INTO $table($fields) VALUES ($placeholders)"
    }

    override def parsert(entity: A): Map[String, Any] = fieldLister.map(entity)
  }

}