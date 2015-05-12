/**
 * Created by spandanbrahmbhatt on 5/12/15.
 */
import edu.arizona.sista.processors.Processor

class NLPProcessing {
  var inputString : String = ""

  def extractData() = {
    import  edu.arizona.sista.processors._
    import edu.arizona.sista.processors.corenlp.CoreNLPProcessor
    val proc:Processor = new CoreNLPProcessor(withDiscourse = true)
    val doc = annotateDoc(proc,inputString)
    var sentenceCount = 0
    import scala.collection.mutable.{Map,ListBuffer}
    var nlpData = Map[Int,String]()
    for (sentence <- doc.sentences) {
      val lemma = sentence.lemmas.get.reduce(_ + " " + _)
      nlpData += (sentenceCount -> lemma)
      sentenceCount += 1
    }
    nlpData
  }

  def annotateDoc(proc : Processor, input : String) = {
    val doc = proc.mkDocument(input)
    proc.tagPartsOfSpeech(doc)
    proc.lemmatize(doc)
    doc.clear()
    doc
  }
}

object NLPProcessing {
  def apply (input: String) : NLPProcessing = {
    val nlp = new NLPProcessing()
    nlp.inputString = input
    nlp
  }
}
