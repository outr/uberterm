package com.outr.uberterm

trait ClientUberTermCommunication extends UberTermCommunication {
  ClientUberTermCommunication.instance = this
}

object ClientUberTermCommunication {
  private var instance: ClientUberTermCommunication = _

  def apply(): ClientUberTermCommunication = instance
}