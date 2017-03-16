package com.outr.uberterm.file

import com.outr.uberterm.UberTermModule
import io.youi.app.YouIApplication
import io.youi.communication.{Communication, client}

import scala.concurrent.Future

trait FileModule extends UberTermModule {
  override def prefix: String = "file"
  override def name: String = "File Support"
  override def description: String = "Functionality for working with files on the file system."

  @client def displayFiles(): Future[Unit]
}