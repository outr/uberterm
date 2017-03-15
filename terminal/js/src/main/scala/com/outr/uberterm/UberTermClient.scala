package com.outr.uberterm

import io.youi.app.ClientApplication
import io.youi.hypertext.Container
import io.youi.hypertext.style.Overflow
import io.youi.layout.VerticalBoxLayout
import io.youi.{dom, ui}
import reactify.Var
import org.scalajs.dom._

import scala.scalajs.js.annotation.JSExportTopLevel

object UberTermClient extends UberTermApplication with ClientApplication {
  val colorScheme: Var[ColorScheme] = Var(ColorScheme.Solarized.Dark)

  val results = new Container {
    layoutManager := Some(new VerticalBoxLayout(spacing = 5.0, fillWidth = true, fromBottom = true))
    overflow := Overflow.Hidden
    position.top := 5.0
    position.center := ui.position.center
    size.width := ui.size.width - 10.0
    size.height := InputBar.position.top - 10.0
  }

  @JSExportTopLevel("application")
  def main(): Unit = {
    scribe.info("Client loaded!")

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
    ui.backgroundColor := colorScheme.base3

    ui.children += results
    ui.children += InputBar
    InputBar.input.focus()
  }
}
