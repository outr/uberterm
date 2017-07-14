package com.outr.uberterm.example

import java.io.File

import io.youi.app.SinglePageApplication
import io.youi.http.{Content, combined, path}
import io.youi.server.UndertowServer

object UberTermExampleServer extends UndertowServer with SinglePageApplication with UberTermExampleApplication {
  override val templateDirectory: File = new File("../../content")

  override protected val appJSContent: Content = Content.classPath("app/uberterm-example-fastopt.js")
  override protected val appJSMethod: String = "application"

  handler.matcher(combined.any(path.exact("/"), path.exact("/index.html"))).htmlPage()
  handler.file(templateDirectory)
}