package xmpanzee.arxivdata

/** Class holding data for invidividual records in an arXiv XML response.
  
  */
class ArXivRecord(node: scala.xml.Node) {

  private val base = node \ "metadata" \ "arXiv"
  val id = (base \ "id").text
  val authorKeynames = (base \ "authors" \ "author").map((x: scala.xml.NodeSeq) => (x \ "keyname").text)
  val authorForenames = (base \ "authors" \ "author").map((x: scala.xml.NodeSeq) => (x \ "forenames").text)
  val title = (base \ "title").text
  val categories = (base \ "categories").text.split(" ")
  val comments = (base \ "comments").text
  val journalRef = (base \ "journal-ref").text
  val doi = (base \ "doi").text
  val license = (base \ "license").text
  val arXivAbstract = (base \ "abstract").text


  override def toString = "arXiv ID: " + id
}
