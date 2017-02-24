package xmpanzee.arxivdata

import java.sql.{Connection, DriverManager}

object DataFunctions {

  def listRecords(page: ArXivPage): Seq[ArXivRecord] = {
    (page.text \ "ListRecords" \ "record").map(new ArXivRecord(_))
  }

  def nextPage(page: ArXivPage): Option[ArXivPage] = {
    val token = page.text \ "ListRecords" \ "resumptionToken"
    if (token.isEmpty) None
    else Some(ArXivPage(List(("resumptionToken", token.text))))
  }

  def connInit(username: String, password: String, dbName: String = "xmpanzee", port: String = "5432"): Connection = {
    val url = "jdbc:postgresql://localhost:" + port + "/" + dbName
    val driver = "org.postgresql.Driver"
    Class.forName(driver);
    var connection: Connection = DriverManager.getConnection(url, username, password)
    connection
  }

  def sqlInsert(conn: Connection, sql: String): Unit = {
    try {
      val statement = conn.createStatement
      statement.executeQuery(sql)
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  def recordToSQL(record: ArXivRecord): String = {
    val keynameList = listToSQLArray(record.authorKeynames)
    val forenameList = listToSQLArray(record.authorForenames)
    val categoriesList = listToSQLArray(record.categories)
    s"""INSERT INTO abstracts
VALUES (${record.id}, '${keynameList}', '${forenameList}', ${record.title}, '${categoriesList}',
${record.comments}, ${record.journalRef}, ${record.doi}, ${record.license}, ${record.arXivAbstract})
"""
  }

  def listToSQLArray(lst: Seq[String]): String = {
    val listString = lst.reduce(_ + "," + _)
    "{" + listString + "}"
  }
}
