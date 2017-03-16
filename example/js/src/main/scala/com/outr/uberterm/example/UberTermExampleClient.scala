package com.outr.uberterm.example

import com.outr.uberterm.UberTermScreen
import io.youi.app.ClientApplication

import scala.scalajs.js.annotation.JSExportTopLevel

object UberTermExampleClient extends UberTermExampleApplication with ClientApplication {
  @JSExportTopLevel("application")
  def main(): Unit = {
    active := UberTermScreen
  }
}
