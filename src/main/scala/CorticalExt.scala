/**
 * Created by spandanbrahmbhatt on 5/12/15.
 */

import java.io.StringReader

import io.cortical.services.RetinaApis
import io.cortical.rest.model.{Metric, Text}
import org.apache.lucene.analysis.shingle.ShingleFilter
import org.apache.lucene.analysis.standard.{StandardFilter, StandardTokenizer}
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.util.Version
import scala.collection.mutable.{ListBuffer, Map}

class CorticalExt {
  val RETINA_NAME = "en_associative"
  val RETINA_IP = "api.cortical.io"
  val API_KEY = "5b9bd530-da47-11e4-a409-7159d0ac8188"
  val retinaApisInstance = new RetinaApis(RETINA_NAME, RETINA_IP, API_KEY)
  var data : Map[Int,String]= Map()

  def generateTriGram() = {
    val a = "Grease my palm"
    val tri =data.map(i => getNGram(i._2,3)).toList
    val li = tri.map(i => i.map(j => compareTerms(a,j))).flatten
    val li_size = li.filter(_>50).size
    val ans = if(li_size>0) "Suspicious" else "Safe"
    ans
  }

  def getNGram(sent: String, n : Int) ={
    val li : collection.mutable.ListBuffer[String] = ListBuffer()
    val reader = new StringReader(sent)
    val source = new StandardTokenizer(Version.LUCENE_36, reader)
    val tokenStream = new StandardFilter(Version.LUCENE_36, source)
    val sf = new ShingleFilter(tokenStream,n)
    sf.setOutputUnigrams(false)
    sf.setMinShingleSize(n)
    val charTermAttribute = sf.addAttribute(classOf[CharTermAttribute])
    sf.reset()
    while (sf.incrementToken()) {
      li+=charTermAttribute.toString()
    }
    sf.end()
    sf.close()
    li.toList
  }

  def compareTerms(term1:String,term2:String) = {
    val compareApiInstance = retinaApisInstance.compareApi()
    var score = 0.0
    try {
      val metric = compareApiInstance.compare(new Text(term1), new Text(term2))
      score = rms(Seq(metric.getCosineSimilarity,1- metric.getEuclideanDistance,1 - metric.getJaccardDistance))
    }
    catch {
      case ex : Exception => {}
    }
    (score * 100).toInt
  }

  def rms(nums: Seq[Double]) = math.sqrt(nums.map(math.pow(_, 2)).sum / nums.size)

}
object CorticalExt {
  def apply (input: Map[Int,String]) : CorticalExt = {
    val cor = new CorticalExt()
    cor.data = input
    cor
  }
}