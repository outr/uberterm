package com.outr.uberterm

import com.outr.uberterm.result.ResultContainer
import io.youi.hypertext.border.BorderStyle
import io.youi.hypertext.{Container, Label}
import io.youi.layout.{GridLayout, VerticalBoxLayout}
import reactify.State

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ClientUberTermCommunication extends UberTermCommunication {
  ClientUberTermCommunication.instance = this

  override def showHelp(modules: Set[ModuleHelp]): Future[Unit] = Future {
    UberTerm.addResult := new HelpResult(modules)
  }
}

object ClientUberTermCommunication {
  private var instance: ClientUberTermCommunication = _

  def apply(): ClientUberTermCommunication = instance
}

class HelpResult(modules: Set[ModuleHelp]) extends ResultContainer {
  layoutManager := Some(new VerticalBoxLayout(5.0))

  children += new Label {
    text := "UberTerm Help"
    font.size := 32.0
    font.family := "sans-serif"
    color := ColorScheme.blue
    position.left := 10.0
  }

  children += new Label {
    text := "General Help:"
    font.size := 28.0
    font.family := "sans-serif"
    color := ColorScheme.blue
    position.left := 10.0
  }

  // TODO: explain basic functionality

  children += new Label {
    text := "Modules:"
    font.size := 28.0
    font.family := "sans-serif"
    color := ColorScheme.blue
    position.left := 10.0
  }

  modules.toList.sortBy(_.prefix).foreach { module =>
    children += new Container {
      position.left := 10.0
      border.width := Some(2.0)
      border.color := Some(ColorScheme.blue)
      border.style := Some(BorderStyle.Solid)
      border.top.radius := 10.0
      layoutManager := Some(new GridLayout(2, 5.0, 5.0, 5.0, 5.0))

      children += new Label {
        text := "Prefix:"
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.green
      }

      children += new Label {
        text := module.prefix
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.white
      }

      children += new Label {
        text := "Name:"
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.green
      }

      children += new Label {
        text := module.name
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.white
      }

      children += new Label {
        text := "Description:"
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.green
      }

      children += new Label {
        text := module.description
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.white
      }
    }
  }
}