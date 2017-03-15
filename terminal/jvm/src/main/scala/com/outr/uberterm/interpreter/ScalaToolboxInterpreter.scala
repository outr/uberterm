package com.outr.uberterm.interpreter

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

class ScalaToolboxInterpreter {
  private lazy val toolbox = currentMirror.mkToolBox()

  object eval {
    def apply(code: String): Any = toolbox.eval(toolbox.parse(code))
    def typed[T](code: String): T = apply(code).asInstanceOf[T]
  }

  object compile {
    def apply(code: String): () => Any = toolbox.compile(toolbox.parse(code))
    def typed[T](code: String): () => T = apply(code).asInstanceOf[() => T]
  }
}
