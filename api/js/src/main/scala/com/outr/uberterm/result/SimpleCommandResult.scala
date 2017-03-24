package com.outr.uberterm.result

import com.outr.uberterm.ColorScheme
import io.youi.hypertext.Label
import reactify._

class SimpleCommandResult(command: String, result: String, error: Boolean) extends ResultContainer {
  val commandHeading = new Label {
    text := "Command:"
    color := ColorScheme.blue
    font.size := 20.0
    font.family := "sans-serif"

    position.left := 10.0
    position.top := 10.0
  }

  val resultHeading = new Label {
    text := "Result:"
    color := (if (error) ColorScheme.red else ColorScheme.blue)
    font.size := 20.0
    font.family := "sans-serif"

    position.left := 10.0
    position.top := commandHeading.position.bottom + 10.0
  }

  val commandLabel = new Label {
    text := command
    color := ColorScheme.white
    font.size := 18.0
    font.family := "fixed"

    position.left := commandHeading.position.right + 10.0
    position.middle := commandHeading.position.middle
  }
  val resultLabel = new Label {
    text := result
    color := ColorScheme.white
    font.size := 18.0
    font.family := "fixed"

    position.left := resultHeading.position.right + 10.0
    position.top := resultHeading.position.top
  }

  size.height := resultLabel.position.bottom + 10.0

  children += commandHeading
  children += resultHeading
  children += commandLabel
  children += resultLabel
}