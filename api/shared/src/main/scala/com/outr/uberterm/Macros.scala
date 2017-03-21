package com.outr.uberterm

import scala.annotation.compileTimeOnly
import scala.reflect.macros.blackbox

@compileTimeOnly("Enable Macros for expansion")
object Macros {
  def module[M <: UberTermModule](context: blackbox.Context)
                                 (implicit m: context.WeakTypeTag[M]): context.Expr[M] = {
    import context.universe._

    val typeString = m.tpe.toString
    val (preType, postType) = if (typeString.indexOf('.') != -1) {
      val index = typeString.lastIndexOf('.')
      typeString.substring(0, index + 1) -> typeString.substring(index + 1)
    } else {
      "" -> typeString
    }

    val clientTypeString = s"${preType}Client$postType"
    val serverTypeString = s"${preType}Server$postType"
    val clientType = try {
      Some(context.universe.rootMirror.staticClass(clientTypeString))
    } catch {
      case _: ScalaReflectionException => None
    }
    val serverType = try {
      Some(context.universe.rootMirror.staticClass(serverTypeString))
    } catch {
      case exc: ScalaReflectionException => None
    }

    val communicationType = serverType match {
      case Some(t) => t
      case None => clientType match {
        case Some(t) => t
        case None => context.abort(context.enclosingPosition, s"Unable to find implementation trait $clientTypeString or $serverTypeString for $typeString.")
      }
    }

    val application = context.prefix.tree

    context.Expr[M](
      q"""
          val module = new $m()
          module.init()
          module
       """)
  }

  def help(context: blackbox.Context)(): context.Expr[ModuleHelp] = {
    import context.universe._

    val test = context.prefix.tree
    scribe.info(s"Module: ${test.tpe.members}")
//    module.symbol

    context.abort(context.enclosingPosition, "WIP")
  }
}
