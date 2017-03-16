package com.outr.uberterm

import io.youi.communication.Communication
import reactify.{Channel, Var}

trait UberTermModule extends Communication {
  def prefix: String
  def name: String
  def description: String

  UberTermModule.register(this)
}

object UberTermModule {
  val registered: Channel[UberTermModule] = Channel[UberTermModule]
  val all: Var[Set[UberTermModule]] = Var[Set[UberTermModule]](Set.empty)

  def register(module: UberTermModule): Unit = synchronized {
    all.setStatic(all() + module)
    registered := module
  }
}