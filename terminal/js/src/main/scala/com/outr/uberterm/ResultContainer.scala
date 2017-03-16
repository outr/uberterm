package com.outr.uberterm

import io.youi.hypertext.{Container, Label}
import io.youi.hypertext.border.BorderStyle
import reactify._

class ResultContainer extends Container {
  border.radius := 5.0
  border.width := Some(2.0)
  border.color := Some(ColorScheme.base1)
  border.style := Some(BorderStyle.Solid)
}

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
    position.middle := resultHeading.position.middle
  }

  size.height := resultLabel.position.bottom + 10.0

  children += commandHeading
  children += resultHeading
  children += commandLabel
  children += resultLabel
}