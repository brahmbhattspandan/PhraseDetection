/**
 * Created by spandanbrahmbhatt on 5/12/15.
 */
object PhraseExtract extends App{
  val a = NLPProcessing("He met his child. He was cooking dinner. He fried the fish with grease.")
  val b = CorticalExt(a.extractData())
  print(b.generateTriGram())
}
