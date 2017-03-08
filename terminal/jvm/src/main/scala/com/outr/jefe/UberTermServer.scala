package com.outr.jefe

import java.io.File

import io.youi.app.SinglePageApplication
import io.youi.http._
import io.youi.server.UndertowServer

class UberTermServer(override val templateDirectory: File) extends UndertowServer with SinglePageApplication with UberTermApplication {
  override protected def appJSContent: Content = Content.classPath("app/jefe-console-fastopt.js")
  override protected def appJSMethod: String = "application"

  handler.matcher(combined.any(path.exact("/"), path.exact("/index.html"))).htmlPage()
}

object UberTermServer {
  def main(args: Array[String]): Unit = {
    val server = new UberTermServer(new File("."))
    server.start()
  }
}