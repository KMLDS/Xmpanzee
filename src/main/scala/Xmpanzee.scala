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

      val testPage = ArXivPage(argsToPairs(argList))
      val testRecords = (testPage.text \ "ListRecords" \ "record").map(new ArXivRecord(_))
      println(testRecords)
    }
  }
}
