package com.outr.uberterm

import io.youi.app.screen.Screen
import io.youi.hypertext.Container
import io.youi.hypertext.style.Overflow
import io.youi.layout.VerticalBoxLayout
import io.youi.{dom, ui}
import org.scalajs.dom._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object UberTermScreen extends Screen {
  val results = new Container {
    layoutManager := Some(new VerticalBoxLayout(spacing = 5.0, fillWidth = true, fromBottom = true))
    overflow := Overflow.Hidden
    position.top := 5.0
    position.center := ui.position.center
    size.width := ui.size.width - 10.0
    size.height := InputBar.position.top - 10.0
  }

  override protected def load(): Future[Unit] = super.load().map { _ =>
    scribe.info("Client loaded!")
    UberTerm.addResult.attach { result =>
      results.children += result
    }

    document.body.style.overflow = "hidden"
    document.body.style.margin = "0"

    val style = dom.create[html.Style]("style")
    style.`type` = "text/css"
    style.innerHTML =
      """
        |input:focus {
        | outline: none;
        |}
      """.stripMargin
    dom.oneByTag[html.Head]("head").appendChild(style)

    ui.init()
    ui.title := "UberTerm"
    ui.backgroundColor := ColorScheme.base3

    ui.children += results
    ui.children += InputBar
    InputBar.input.focus()
  }
}