package com.outr.uberterm
import com.outr.uberterm.interpreter.ScalaInterpreter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  private var commands = Map.empty[String, () => Unit]

  registerCommand("help", () => showHelp(UberTermModule.all().map(m => ModuleHelp(m.prefix, m.name, m.description))))
  UberTermModule.all.foreach { module =>
    interpreter.bind(module.prefix, module)
  }
  UberTermModule.registered.attach { module =>
    interpreter.bind(module.prefix, module)
  }

  def registerCommand(name: String, function: () => Unit): Unit = synchronized {
    commands += name -> function
  }

  override def executeCommand(command: String): Future[CommandResult] = Future {
    try {
      val resultString = commands.get(command) match {
        case Some(function) => {
          function()
          None
        }
        case None => interpreter.eval(command) match {
          case NoResult => None
          case result => Option(result.toString)
        }
      }
      CommandResult(resultString, error = false)
    } catch {
      case t: Throwable => CommandResult(Some(s"${t.getClass.getSimpleName}: ${t.getMessage}"), error = true)
    }
  }
}