package xmpanzee.arxivdata

import scalaj.http._
import scala.xml._

/** ArXivPage takes Option[String] arguments corresponding to the "set" (e.g. physics:quant-ph) variable, and the from and until dates in the format 
  YYYY-MM-DD.

  The text field contains the entire XML of the page corresponding to the parameters given.  It needs to properly handle timeouts, other server side errors (or blocks), and 
  resumption tokens.

  */
class ArXivPage(arXivSet: Option[String] = None, dateFrom: Option[String] = None, dateUntil: Option[String] = None, resumptionToken: Option[None] = None) {


  val arXivBaseURL = "http://export.arxiv.org/oai2"
  val metaDataFormat = "arXiv"
  val downloadVerb = "ListRecords"

  def buildParamList(): List[(String, String)] = {
    def prependOptional(x: Option[String], optKey: String, lst: List[(String, String)]): List[(String, String)] = {
      x match {
        case Some(y) => (optKey, y)::lst
        case None => lst
      }
    }
    val baseList = List(("verb", downloadVerb), ("metadataPrefix", metaDataFormat))
    prependOptional(resumptionToken, "resumptionToken", prependOptional(dateFrom, "from", prependOptional(dateUntil, "until", prependOptional(arXivSet, "set", baseList))))
  }

  def getArXivXML(): scala.xml.Elem = {
    val paramList = this.buildParamList
    val pageString: String = Http(arXivBaseURL).params(paramList).asString.body
    scala.xml.XML.loadString(pageString)
  }

  // private def getRecords(): List[scala.xml.Elem] = {
  //   this.text match {

  //   }
  // }


  val text = this.getArXivXML
}

/** Companion object for class ArXivPage.  Can take all 3 parameters (set, from date, until date), just the set parameter, or no parameter.
  
  */
object ArXivPage {


  def apply(arXivSet: String, dateFrom: String, dateUntil: String) = new ArXivPage(Some(arXivSet), Some(dateFrom), Some(dateUntil))
  def apply(arXivSet: String) = new ArXivPage(Some(arXivSet))
  def apply() = new ArXivPage()


}
