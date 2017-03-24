package com.outr.uberterm

import com.outr.uberterm.result.SimpleCommandResult
import io.youi.hypertext.{Container, TextInput}
import io.youi.{Color, Key, ui}

import scala.concurrent.ExecutionContext.Implicits.global

object InputBar extends Container {
  backgroundColor := ColorScheme.base1

  size.width := ui.size.width - 10.0
  size.height := 40.0
  border.radius := 5.0

  position.center := ui.position.center
  position.bottom := ui.position.bottom - 5.0

  val input = new TextInput
  input.size.width := size.width - 15.0
  input.position.center := size.center
  input.position.middle := size.middle
  input.color := ColorScheme.white
  input.font.size := 24.0
  input.font.family := "sans-serif"
  input.backgroundColor := Color.Clear
  input.border.size := Some(0.0)
  input.event.key.up.attach { evt =>
    val key = Key.byCode(evt.keyCode)
    if (key.contains(Key.Enter) || key.contains(Key.Return)) {
      evt.preventDefault()
      evt.stopPropagation()
      sendCommand()
    }
  }
  children += input

  def sendCommand(): Unit = if (input.value().nonEmpty) {
    val command = input.value()
    ClientUberTermCommunication().executeCommand(command).foreach { response =>
      response.output match {
        case Some(output) => {
          val result = new SimpleCommandResult(command, output, response.error)
          UberTermScreen.results.children += result
        }
        case None => // Will be handled by the implementation
      }
      input.value := ""
    }
  } else {
    scribe.info("No value!")
  }
}
