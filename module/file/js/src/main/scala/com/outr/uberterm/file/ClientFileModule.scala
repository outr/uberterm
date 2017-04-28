package com.outr.uberterm.file

import com.outr.uberterm.{ColorScheme, UberTerm}
import com.outr.uberterm.result.ResultContainer
import io.youi.hypertext.layout.{FlowLayout, VerticalBoxLayout}
import io.youi.hypertext.{Container, Label}
import reactify._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ClientFileModule extends FileModule {
  override def displayFiles(directory: String, directoryNames: List[String], fileNames: List[String]): Future[Unit] = Future {
    UberTerm.addResult := new FileListResults(directory, directoryNames, fileNames)
  }

  override def updateFiles(directory: String, directoryNames: List[String], fileNames: List[String]): Future[Unit] = {
    val container = UberTerm.current().get.asInstanceOf[FileListResults]
    container.removeFromParent()
    displayFiles(directory, directoryNames, fileNames)
  }
}

class FileListResults(heading: String, directoryNames: List[String], fileNames: List[String]) extends ResultContainer {
  layoutManager := Some(new VerticalBoxLayout(5.0))

  val headingLabel = new Label {
    text := heading
    font.size := 32.0
    font.family := "sans-serif"
    color := ColorScheme.blue
    position.left := 10.0
  }
  children += headingLabel

  children += new Container {
    layoutManager := Some(new FlowLayout(10.0, 10.0, 15.0, 15.0))
    size.width := FileListResults.this.size.width - 10.0

    directoryNames.foreach { item =>
      children += new Label {
        text := item
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.yellow
        position.left := 10.0
      }
    }

    fileNames.foreach { item =>
      children += new Label {
        text := item
        font.size := 24.0
        font.family := "sans-serif"
        color := ColorScheme.white
        position.left := 10.0
      }
    }
  }
}