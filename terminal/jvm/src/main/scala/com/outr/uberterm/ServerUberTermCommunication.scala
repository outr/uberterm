package com.outr.uberterm

import com.outr.uberterm.interpreter.ScalaInterpreter

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait ServerUberTermCommunication extends UberTermCommunication {
  lazy val interpreter = new ScalaInterpreter

  private var commands = Map.empty[String, () => Unit]

  private var initialized: Boolean = false

  def init(): Unit = {
    registerCommand("help", () => showHelp(UberTermModule.all().map(m => ModuleInfo(m.prefix, m.name, m.description))))
    UberTermModule.all.foreach { module =>
      registerModule(module)
    }
    UberTermModule.registered.attach { module =>
      registerModule(module)
    }
  }

  def registerCommand(name: String, function: () => Unit): Unit = synchronized {
    commands += name -> function
  }

  def registerModule(module: UberTermModule): Unit = synchronized {
    interpreter.bind(module.prefix, module)
//    registerCommand(s"${module.prefix}.help", () => moduleHelp(module))
  }

//  private def moduleHelp(module: UberTermModule): Unit = {
//    showModuleHelp(module.help())
//  }

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
      case t: Throwable => CommandResult(Some(s"${t.getClass.getSimpleName}: ${t.getMessage}"), error = true)
    }
  }
}