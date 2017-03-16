package com.outr.uberterm.interpreter

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain

class ScalaInterpreter {
  private val returnValue = new ThreadLocal[Any]
  private val settings = new Settings()
  settings.usejavacp.value = true
  settings.deprecation.value = true
  settings.embeddedDefaults[ScalaInterpreter]
  private val i = new IMain(settings)
  i.bind[ThreadLocal[Any]]("returnValue", returnValue)

  object eval {
    def apply(code: String): Any = {
      returnValue.remove()
      i.interpret(
        s"""
          |try {
          |  returnValue.asInstanceOf[ThreadLocal[Any]].set($code)
          |} catch {
          |  case t: Throwable => returnValue.asInstanceOf[ThreadLocal[Any]].set(t)
          |}
        """.stripMargin)
      returnValue.get() match {
        case t: Throwable => throw t
        case v => v
      }
    }
    def typed[T](code: String): T = apply(code).asInstanceOf[T]
  }
}
