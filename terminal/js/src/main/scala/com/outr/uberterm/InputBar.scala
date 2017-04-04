package com.outr.uberterm

import com.outr.uberterm.result.SimpleCommandResult
import io.youi.hypertext.{Container, TextInput}
import io.youi.{Color, Key, ui}

import scala.concurrent.ExecutionContext.Implicits.global

object InputBar extends Container {
  private var history = Vector.empty[String]
  private var historyPosition = -1

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
    } else if (key.contains(Key.Up)) {
      historyPosition = math.min(history.length - 1, historyPosition + 1)
      showHistory()
    } else if (key.contains(Key.Down)) {
      historyPosition = math.max(-1, historyPosition - 1)
      showHistory()
    }
  }
  children += input

  ClientUberTermCommunication().loadHistory().foreach { history =>
    this.history = history
    historyPosition = -1
  }

  def sendCommand(): Unit = if (input.value().nonEmpty) {
    val command = input.value()
    ClientUberTermCommunication().executeCommand(command).foreach { response =>
      history = command +: history
      historyPosition = -1

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

  private def showHistory(): Unit = {
    if (historyPosition != -1) {
      input.value := history(historyPosition)
    } else {
      input.value := ""
    }
  }
}
