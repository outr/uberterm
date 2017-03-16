package com.outr.uberterm

import com.outr.uberterm.result.ResultContainer
import reactify.{Channel, State, Var}

object UberTerm {
  val current: State[Option[ResultContainer]] = Var[Option[ResultContainer]](None)
  val addResult: Channel[ResultContainer] = Channel[ResultContainer]

  addResult.attach { result =>
    current.asInstanceOf[Var[Option[ResultContainer]]] := Some(result)
  }
}