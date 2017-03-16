package com.outr.uberterm.file

import com.outr.uberterm.{ColorScheme, UberTerm}
import com.outr.uberterm.result.ResultContainer
import io.youi.hypertext.Label
import io.youi.layout.VerticalBoxLayout
import reactify._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ClientFileModule extends FileModule {
  override def displayFiles(directory: String, fileNames: List[String]): Future[Unit] = Future {
    scribe.info(s"Display files! $directory, fileNames: $fileNames")
    UberTerm.addResult := new ListResults(directory, fileNames)
  }
}

class ListResults(heading: String, items: List[String]) extends ResultContainer {
  layoutManager := Some(new VerticalBoxLayout(5.0))

  val headingLabel = new Label {
    text := heading
    font.size := 32.0
    font.family := "sans-serif"
    color := ColorScheme.blue
    position.left := 10.0
  }
  children += headingLabel

  var bottom: State[Double] = headingLabel.position.bottom

  items.foreach { item =>
    children += new Label {
      text := item
      font.size := 24.0
      font.family := "sans-serif"
      color := ColorScheme.white
      position.left := 10.0
      bottom = position.bottom
    }
  }

  size.height := bottom + 10.0
}