package rhymes

import scala.collection.JavaConverters._
import javafx.event.ActionEvent
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ListView, TextField}
import scalafx.scene.layout.VBox
import RhymingDictionary.{findRhymes, getSounds}


object GUI extends JFXApp {
  val dictionaryFile: String = "data/cmudict-0.7b"
  val enableLargeFont: Boolean = true

  val inputLabel: Label = new Label("Input Word:")
  val soundsLabel: Label = new Label("Sounds:")
  val wordsLabel: Label = new Label("Rhymes:")
  val inputDisplay: TextField = new TextField
  val outputSounds: TextField = new TextField { editable = false }
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
        outputSounds.text = getSounds(dictionaryFile, inputDisplay.text.value.toUpperCase).toString.substring(4)
        outputWords.getItems.clear
        outputWords.getItems addAll findRhymes(dictionaryFile, inputDisplay.text.value.toUpperCase).asJava
      }
      catch {
        case _: NoSuchElementException => outputSounds.text = "Word \"" + inputDisplay.text.value + "\" not found."
      }
    }
  }
  val container: VBox = new VBox(inputLabel, inputDisplay, button, soundsLabel, outputSounds, wordsLabel, outputWords)

  if(enableLargeFont) container.children.asScala.foreach(_.setStyle("-fx-font: 24 Arial;"))

  this.stage = new PrimaryStage {
    title = "Rhyming Dictionary"
    scene = new Scene(container, 300, 700) {
      minWidth = 300
      minHeight = 700
    }
  }
}