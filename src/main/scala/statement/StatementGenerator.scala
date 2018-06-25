package statement

import util.StringUtils._

trait StatementGenerator[A] {
  def select(table: String): String

  def selectById(table: String, pk: Long): String

  def update(table: String, pk: Long): String

  def insert(table: String): String

  def remove(table: String, pk: Long): String

  def parsert(entity: A): Map[String, Any]
}

object StatementGenerator {

  def apply[A](implicit sg: StatementGenerator[A]): StatementGenerator[A] = sg

  implicit def genericGenerator[A](implicit fieldLister: FieldLister[A]): StatementGenerator[A] = new StatementGenerator[A] {

    override def select(table: String): String = {
      val fields = fieldLister.list.map(uperCamelToLowerSnake).mkString(",")
      s"select $fields from $table"
    }

    override def selectById(table: String, pk: Long): String = {
      val fields = fieldLister.list.map(uperCamelToLowerSnake).mkString(",")
      s"select $fields from $table where id = $pk"
    }

    override def update(table: String, pk: Long): String = {
      val fieldNames = fieldLister.list.map(uperCamelToLowerSnake)
      val placeholders = List.fill(fieldNames.size)("?").mkString(",")
      val fields = (fieldNames zip placeholders).map(x => s"${x._1} = ${x._2}").mkString(",")

      s"update $table set $fields where id = $pk"
    }

    override def insert(table: String): String = {
      val fieldNames = fieldLister.list.map(uperCamelToLowerSnake)
      val fields = fieldNames.mkString(",")

      val placeholders = List.fill(fieldNames.size)("?").mkString(",")

      s"insert into $table($fields) values ($placeholders)"
    }

    override def remove(table: String, pk: Long): String = {
      s"delete from $table where id = $pk"
    }

    override def parsert(entity: A): Map[String, Any] = fieldLister.map(entity)
  }

}