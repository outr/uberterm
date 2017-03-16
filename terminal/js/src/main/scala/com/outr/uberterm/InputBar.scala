package com.outr.uberterm

import io.youi.hypertext.{Container, TextInput}
import io.youi.{Color, Key, ui}

import scala.concurrent.ExecutionContext.Implicits.global

object InputBar extends Container {
  backgroundColor := UberTermClient.colorScheme.base1

  size.width := ui.size.width - 10.0
  size.height := 40.0
  border.radius := 5.0

  position.center := ui.position.center
  position.bottom := ui.position.bottom - 5.0

  val input = new TextInput
  input.size.width := size.width - 15.0
  input.position.center := size.center
  input.position.middle := size.middle
  input.color := UberTermClient.colorScheme.white
  input.font.size := 24.0
  input.font.family := "sans-serif"
  input.backgroundColor := Color.Clear
  input.border.width := Some(0.0)
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
    UberTermClient.communication(UberTermClient.connection).executeCommand(command).map { response =>
      val result = new SimpleCommandResult(command, response.output, response.error)
      UberTermClient.results.children += result
      input.value := ""
    }
  } else {
    scribe.info("No value!")
  }
}
