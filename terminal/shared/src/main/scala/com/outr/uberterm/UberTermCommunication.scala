package com.outr.uberterm

import io.youi.communication.{Communication, server}

import scala.concurrent.Future

trait UberTermCommunication extends Communication {
  @server def executeCommand(command: String): Future[CommandResult]
}

case class CommandResult(output: String)