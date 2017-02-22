package xmpanzee.arxivdata

import scalaj.http._
import scala.xml._

/** ArXivPage takes Seq[(String, String)] key-value pairs to pass directly to scalaj.http.Http.params() .

  The text field contains the entire XML of the page corresponding to the parameters given.  It needs to properly handle timeouts, other server side errors (or blocks), and 
  resumption tokens.  Some useful key-value pairs include ("verb", "Listrecords"), ("metadataPrefix", "arXiv"), ("from", "YYYY-MM-DD"), ("until", "YYYY-MM-DD"), and ("set", "physics:quant-ph") for example.

  */
class ArXivPage(httpParams: Seq[(String, String)]) {

  val arXivBaseURL = "http://export.arxiv.org/oai2"

  def getArXivXML(): scala.xml.Elem = {
    val pageString: String = Http(arXivBaseURL).params(httpParams).asString.body
    scala.xml.XML.loadString(pageString)
  }

  val text = this.getArXivXML
}


/** Companion object for class ArXivPage.  Can take all 3 parameters (set, from date, until date), just the set parameter, or no parameter.
  
  */
object ArXivPage {

  def apply(httpParams: Seq[(String, String)]) = new ArXivPage(httpParams)

}
