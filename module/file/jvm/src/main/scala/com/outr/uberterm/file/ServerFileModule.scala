package com.outr.uberterm.file

import java.io.File

import com.outr.uberterm.NoResult
import reactify.Var

trait ServerFileModule extends FileModule {
  val directory: Var[File] = Var[File](new File("."))

  def list(): NoResult = {
    val fileNames = directory.listFiles().map(_.getName).toList
    displayFiles(directory.getCanonicalPath, fileNames)

    NoResult
  }
}
