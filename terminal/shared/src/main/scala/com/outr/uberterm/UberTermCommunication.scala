package com.outr.uberterm

import io.youi.communication.{Communication, client, server}

import scala.concurrent.Future

trait UberTermCommunication extends Communication {
  @server def executeCommand(command: String): Future[CommandResult]
  @client def showHelp(modules: Set[ModuleHelp]): Future[Unit]
}

case class CommandResult(output: Option[String], error: Boolean)

case class ModuleHelp(prefix: String, name: String, description: String)