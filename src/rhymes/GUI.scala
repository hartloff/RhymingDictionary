package rhymes

import scala.collection.JavaConverters._
import javafx.event.ActionEvent
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ListView, TextField}
import scalafx.scene.layout.VBox


object GUI extends JFXApp {
  val dictionaryFile: String = "data/cmudict-0.7b"
  val rhymingDictionary: RhymingDictionary = new RhymingDictionary(dictionaryFile)
  val enableLargeFont: Boolean = true

  val inputLabel: Label = new Label("Input Word:")
  val soundsLabel: Label = new Label("Best Rhyme:")
  val wordsLabel: Label = new Label("All Rhymes:")
  val inputDisplay: TextField = new TextField
  val bestRhyme: TextField = new TextField { editable = false }
  val outputWords: ListView[String] = new ListView[String]() {
    minHeight = 400
    maxHeight = Double.MaxValue
  }
  val button: Button = new Button {
    minHeight = 100
    minWidth = 300
    maxWidth = Double.MaxValue
    text = "Search"
    onAction = (_: ActionEvent) => {
      try {
        bestRhyme.text = rhymingDictionary.bestRhyme(inputDisplay.text.value.toUpperCase)
        outputWords.getItems.clear
        outputWords.getItems addAll rhymingDictionary.allRhymes(inputDisplay.text.value.toUpperCase).asJava
      }
      catch {
        case _: NoSuchElementException => bestRhyme.text = "Word \"" + inputDisplay.text.value + "\" not found."
      }
    }
  }
  val container: VBox = new VBox(inputLabel, inputDisplay, button, soundsLabel, bestRhyme, wordsLabel, outputWords)

  if(enableLargeFont) container.children.asScala.foreach(_.setStyle("-fx-font: 24 Arial;"))

  this.stage = new PrimaryStage {
    title = "Rhyming Dictionary"
    scene = new Scene(container, 300, 700) {
      minWidth = 300
      minHeight = 700
    }
  }
}