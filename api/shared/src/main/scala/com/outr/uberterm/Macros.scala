package com.outr.uberterm

import scala.annotation.compileTimeOnly
import scala.reflect.macros.blackbox

@compileTimeOnly("Enable Macros for expansion")
object Macros {
  def help(context: blackbox.Context)(): context.Expr[ModuleHelp] = {
    import context.universe._

    val test = context.prefix.tree
    scribe.info(s"Module: ${test.tpe.members}")
//    module.symbol

    context.abort(context.enclosingPosition, "WIP")
  }
}
