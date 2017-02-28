import xmpanzee.arxivdata._


object Xmpanzee {

  def main(args: Array[String]) = {
    if (args(0) == "download") {
      val argList = args.toList.tail
      def argsToPairs(lst: List[String]): List[(String, String)] = {
        lst match {
          case Nil => Nil
          case x::y::zs => (x, y)::argsToPairs(zs)
          case _ => throw new Exception("Odd number of arguments, needs to be key-value pairs")
        }
      }

      var conn = DataFunctions.connInit("xmpanzee", "xmpanzee") // put real values in simple config file and read in 

      val firstPage = ArXivPage(argsToPairs(argList))
      DataFunctions.processArXiv(Some(firstPage), conn)
      //      println(testPage.code)
      //      DataFunctions.listRecords(testPage).map(DataFunctions.recordToSQL(_)).foreach(DataFunctions.sqlInsert(conn, _))
      //      DataFunctions.listRecords(testPage).foreach((x: ArXivRecord) => println(DataFunctions.recordToSQL(x)))


      conn.close
    }
  }
}
