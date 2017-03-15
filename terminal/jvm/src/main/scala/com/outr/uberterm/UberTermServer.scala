package com.outr.uberterm

import java.io.File

import io.youi.app.SinglePageApplication
import io.youi.http._
import io.youi.server.UndertowServer

class UberTermServer(override val templateDirectory: File) extends UndertowServer with SinglePageApplication with UberTermApplication {
  override protected val appJSContent: Content = Content.classPath("app/uberterm-fastopt.js")
  override protected val appJSMethod: String = "application"

  handler.matcher(combined.any(path.exact("/"), path.exact("/index.html"))).htmlPage()
  handler.file(templateDirectory)
}

object UberTermServer {
  def main(args: Array[String]): Unit = {
    val server = new UberTermServer(new File("../../content"))
    server.start()
  }
}