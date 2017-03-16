package com.outr.uberterm.example

import java.io.File

import io.youi.app.SinglePageApplication
import io.youi.http.{Content, combined, path}
import io.youi.server.UndertowServer

class UberTermExampleServer(override val templateDirectory: File) extends UndertowServer with SinglePageApplication with UberTermExampleApplication {
  override protected val appJSContent: Content = Content.classPath("app/uberterm-fastopt.js")
  override protected val appJSMethod: String = "application"

  handler.matcher(combined.any(path.exact("/"), path.exact("/index.html"))).htmlPage()
  handler.file(templateDirectory)
}

object UberTermExampleServer {
  def main(args: Array[String]): Unit = {
    val server = new UberTermExampleServer(new File("../../content"))
    server.start()
  }
}