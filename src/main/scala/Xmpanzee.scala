import xmpanzee.arxivdata._

object Xmpanzee {
  def main(args: Array[String]) = {
    if (args(0) == "download") {
      val testPage = ArXivPage(args(1), args(2), args(3))
      val testRecords = (testPage.text \ "ListRecords" \ "record").map(new ArXivRecord(_))
      println(testRecords)
    }
  }
}
