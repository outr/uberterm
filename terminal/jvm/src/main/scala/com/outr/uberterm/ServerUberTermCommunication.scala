package com.outr.uberterm

import com.outr.uberterm.interpreter.ScalaInterpreter
import io.youi.app.YouIApplication

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  private var commands = Map.empty[String, () => Unit]

  private var initialized: Boolean = false

  def init(): Unit = {
    val modules = YouIApplication().connectivityEntries().flatMap(_.communicationManagers.flatMap(_.byConnection(connection)).collect {
      case module: UberTermModule => {
        registerModule(module)
        module
      }
    })

    registerCommand("help", () => showHelp(modules.map(m => ModuleInfo(m.prefix, m.name, m.description))))
  }

  def registerCommand(name: String, function: () => Unit): Unit = synchronized {
    commands += name -> function
  }

  def registerModule(module: UberTermModule): Unit = synchronized {
    interpreter.bind(module.prefix, module)
  }

  override def executeCommand(command: String): Future[CommandResult] = Future {
    // Initialize on the first command
    ServerUberTermCommunication.this.synchronized {
      if (!initialized) {
        init()
        initialized = true
      }
    }

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
      case t: Throwable => {
        scribe.error(s"An error occurred while executing command: $command:")
        scribe.error(t)
        CommandResult(Some(s"${t.getClass.getSimpleName}: ${t.getMessage}"), error = true)
      }
    }
  }
}