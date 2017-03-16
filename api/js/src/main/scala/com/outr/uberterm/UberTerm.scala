package com.outr.uberterm

import com.outr.uberterm.result.ResultContainer
import reactify.Channel

object UberTerm {
  val addResult: Channel[ResultContainer] = Channel[ResultContainer]
}