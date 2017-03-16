package com.outr.uberterm

import io.youi.communication.Communication

trait UberTermModule extends Communication {
  def prefix: String
  def name: String
  def description: String
}
