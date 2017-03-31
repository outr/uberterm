package com.outr.uberterm

import io.youi.communication.{Communication, client, server}

import scala.concurrent.Future

trait UberTermCommunication extends Communication {
  @server def executeCommand(command: String): Future[CommandResult]
  @client def showHelp(modules: Set[ModuleInfo]): Future[Unit]
  @client def showModuleHelp(module: ModuleHelp): Future[Unit]
  @server def loadHistory(): Future[Vector[String]]
}

case class CommandResult(output: Option[String], error: Boolean)

case class ModuleInfo(prefix: String, name: String, description: String)