package com.outr.uberterm
import com.outr.uberterm.interpreter.ScalaInterpreter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  override def executeCommand(command: String): Future[CommandResult] = Future {
    try {
      val result = interpreter.eval(command)
      CommandResult(result.toString, error = false)
    } catch {
      case t: Throwable => CommandResult(s"${t.getClass.getSimpleName}: ${t.getMessage}", error = true)
    }
  }
}