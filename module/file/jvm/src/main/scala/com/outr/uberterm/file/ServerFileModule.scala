package com.outr.uberterm.file

import java.io.File

import com.outr.uberterm.NoResult
import reactify.Var

trait ServerFileModule extends FileModule {
  val directory: Var[File] = Var[File](new File("."))
  val filesList: Var[List[File]] = Var(Nil)

  def changeDirectory(path: String): String = {
    val dir = if (path.startsWith("/")) new File(path) else new File(directory(), path)
    if (dir.isDirectory) {
      directory := dir
      s"Directory changed to ${dir.getCanonicalPath}"
    } else {
      s"Not a valid directory: $path."
    }
  }

  def up(): String = {
    val dir = directory().getParentFile
    directory := dir
    s"Directory changed to ${dir.getCanonicalPath}"
  }

  def list(): NoResult = {
    filesList := directory.listFiles().toList

    val directoryNames = filesList().filter(_.isDirectory).map(_.getName)
    val fileNames = filesList().filter(_.isFile).map(_.getName)
    displayFiles(directory.getCanonicalPath, directoryNames, fileNames)

    NoResult
  }

  def filterByExtension(extension: String, caseSensitive: Boolean = false): NoResult = {
    filesList := filesList().filter { file =>
      val fileName = if (caseSensitive) file.getName else file.getName.toLowerCase
      val index = fileName.lastIndexOf('.')
      if (index == -1) {
        false
      } else {
        val ext = fileName.substring(index + 1)
        ext == extension
      }
    }

    val directoryNames = filesList().filter(_.isDirectory).map(_.getName)
    val fileNames = filesList().filter(_.isFile).map(_.getName)
    updateFiles(directory.getCanonicalPath, directoryNames, fileNames)

    NoResult
  }
}
