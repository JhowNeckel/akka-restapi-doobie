package statement

abstract class Table[A](implicit st: StatementGenerator[A]) {
  val tableName: String

  lazy val insertEntitySQL = st.insert(tableName)
  lazy val listEntitySQL = st.select(tableName)
  lazy val selectEntitySQL = (pk: Long) => st.selectById(tableName, pk)
  lazy val removeEntitySQL = (pk: Long) => st.remove(tableName, pk)
}
