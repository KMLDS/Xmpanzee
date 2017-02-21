import xmpanzee.arxivdata._

object Xmpanzee {
  def main(args: Array[String]) = {
    if (args(0) == "download") {
      def resumeQuery(page: ArXivPage): Option[ArXivPage] = {
        val resumptionToken = page.text \ "ListRecords" \ "resumptionToken"
        if (resumptionToken.isEmpty) None
        else Some
      }
      val testPage = ArXivPage(args(1), args(2), args(3))
      val testRecords = (testPage.text \ "ListRecords" \ "record").map(new ArXivRecord(_))

    }
  }
}
