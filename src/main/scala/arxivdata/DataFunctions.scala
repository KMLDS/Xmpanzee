package xmpanzee.arxivdata

import java.sql.{Connection, DriverManager}
import scala.util.matching.Regex

object DataFunctions {

  def listRecords(page: ArXivPage): Seq[ArXivRecord] = {
    (page.text \ "ListRecords" \ "record").map(new ArXivRecord(_))
  }

  def nextPage(page: ArXivPage): Option[ArXivPage] = {
    if (page.code != 200) {
      val retryString = (page.text \ "body" \ "h1").text
      val digitRegex = """\d+""".r
      val retryTime = digitRegex.findFirstIn(retryString).getOrElse("0").toInt // in seconds, Thread.sleep takes ms
      println(s"Code ${page.code}, waiting ${retryTime} seconds...")
      Thread.sleep(1000 * retryTime)
      Some(ArXivPage(page.params))
    } else {
      val token = (page.text \ "ListRecords" \ "resumptionToken").text
      println(s"Resumption token: ${token} ")
      if (token.isEmpty) None
      else Some(ArXivPage(List(("verb", "ListRecords"), ("resumptionToken", token))))
    }
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
      statement.executeUpdate(sql)
    } catch {
      case e: Exception => println(e)
    }
  }

  def recordToSQL(record: ArXivRecord): String = {
    val keynameList = listToSQLArray(record.authorKeynames)
    val forenameList = listToSQLArray(record.authorForenames)
    val categoriesList = listToSQLArray(record.categories)
    s"""INSERT INTO abstracts
VALUES ('${record.id}', '${keynameList}', '${forenameList}', '${fixSingleQuote(record.title)}', '${categoriesList}',
'${fixSingleQuote(record.comments)}', '${fixSingleQuote(record.journalRef)}', '${record.doi}', '${fixSingleQuote(record.license)}', '${fixSingleQuote(record.arXivAbstract)}')
"""
  }

  def listToSQLArray(lst: Seq[String]): String = {
    val listString = lst.map(fixSingleQuote(_)).map('"'+_+'"').reduce(_+','+_)
    "{" + listString + "}"
  }

  def fixSingleQuote(x: String): String = {
    x.replaceAll("'", "''")
  }

  def processArXiv(pageOpt: Option[ArXivPage], conn: Connection): Unit = {
    pageOpt match {
      case None => println("Finished.")
      case Some(page) => {
        val records = listRecords(page)
        records.map(recordToSQL(_)).foreach(sqlInsert(conn, _))
        processArXiv(nextPage(page), conn)
      }
    }
  }
}
