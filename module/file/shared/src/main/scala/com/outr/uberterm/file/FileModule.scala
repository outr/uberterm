package com.outr.uberterm.file

import com.outr.uberterm.UberTermModule
import io.youi.communication.client

import scala.concurrent.Future

trait FileModule extends UberTermModule {
  override def prefix: String = "files"
  override def name: String = "File Support"
  override def description: String = "Functionality for working with files on the file system."

  @client def displayFiles(directory: String, fileNames: List[String]): Future[Unit]
}