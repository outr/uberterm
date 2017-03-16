package com.outr.uberterm
import com.outr.uberterm.interpreter.ScalaInterpreter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  scribe.info("Communication!")
  UberTermModule.all.foreach { module =>
    interpreter.bind(module.prefix, module)
  }
  UberTermModule.registered.attach { module =>
    interpreter.bind(module.prefix, module)
  }

  override def executeCommand(command: String): Future[CommandResult] = Future {
    try {
      val resultString = interpreter.eval(command) match {
        case NoResult => None
        case result => Option(result.toString)
      }
      CommandResult(resultString, error = false)
    } catch {
      case t: Throwable => CommandResult(Some(s"${t.getClass.getSimpleName}: ${t.getMessage}"), error = true)
    }
  }
}