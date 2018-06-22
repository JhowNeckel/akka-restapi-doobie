package statement

trait TableSQL[A] {
  val tableName: String

  lazy val insertEntitySQL = StatementGenerator[A].insert(tableName)
  lazy val listEntitySQL = StatementGenerator[A].select(tableName)
  lazy val selectEntitySQL = (pk: Long) => StatementGenerator[A].selectById(tableName, pk)
  lazy val removeEntitySQL = (pk: Long) => StatementGenerator[A].remove(tableName, pk)
}
