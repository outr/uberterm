package com.outr.uberterm
import com.outr.uberterm.interpreter.ScalaInterpreter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  override def executeCommand(command: String): Future[CommandResult] = Future {
    val result = interpreter.eval(command)
    scribe.info(s"evaluated result: $result")
    CommandResult(result.toString)
  }
}