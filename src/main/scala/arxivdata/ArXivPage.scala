package xmpanzee.arxivdata

import scalaj.http._
import scala.xml._


/** ArXivPage takes Seq[(String, String)] key-value pairs to pass directly to scalaj.http.Http.params() .

  The text field contains the entire XML of the page corresponding to the parameters given.  It needs to properly handle timeouts, other server side errors (or blocks), and 
  resumption tokens.  Some useful key-value pairs include ("verb", "ListRecords"), ("metadataPrefix", "arXiv"), ("from", "YYYY-MM-DD"), ("until", "YYYY-MM-DD"), and ("set", "physics:quant-ph") for example.

  */
class ArXivPage(httpParams: Seq[(String, String)]) {

  val arXivBaseURL = "http://export.arxiv.org/oai2"
  val response = Http(arXivBaseURL).timeout(connTimeoutMs=60000, readTimeoutMs=300000).params(httpParams).asString
  
  def getArXivXML(): scala.xml.Elem = {
    val pageString = this.response.body
    scala.xml.XML.loadString(pageString)
  }

  val text = this.getArXivXML
  val code = this.response.code
  val params = httpParams
}


/** Companion object for class ArXivPage.  Call with key-value pairs as in the class.
  
  */
object ArXivPage {

  def apply(httpParams: Seq[(String, String)]) = new ArXivPage(httpParams)

}
