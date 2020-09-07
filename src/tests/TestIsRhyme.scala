package tests

import org.scalatest._
import rhymes.RhymingDictionary

class TestIsRhyme extends FunSuite {


  test("check if two words rhyme using the isRhyme method") {
    val dictionaryFilename: String = "data/cmudict-0.7b"
    val rhymingDictionary = new RhymingDictionary(dictionaryFilename)

  }

}
