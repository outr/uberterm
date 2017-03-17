package com.outr.uberterm

import io.youi.communication.Communication
import reactify.{Channel, Var}

import scala.concurrent.Future
import scala.reflect.runtime.{universe => ru}

trait UberTermModule extends Communication {
  def prefix: String
  def name: String
  def description: String

  UberTermModule.register(this)

  private[uberterm] def help(): ModuleHelp = {
    val mirror = ru.runtimeMirror(getClass.getClassLoader)
    val instance = mirror.reflect(this)
    scribe.info(s"Symbol: ${instance.symbol}")
    scribe.info(s"Full Name: ${instance.symbol.asClass.fullName}")

    def isInvalidName(name: String): Boolean = {
      name.contains("$") ||
      name.contains("<") ||
      name.contains(">") ||
      Set("prefix", "help", "error", "name", "description", "comm", "errorSupport", "connection", "shared").contains(name)
    }

    val commands = instance.symbol.asClass.info.members.flatMap { member =>
      if (member.isMethod) {
        val m = member.asMethod
        if (m.isPublic &&
            m.owner.toString != "class Object" &&
            m.owner.toString != "class Any" &&
            !m.returnType.toString.startsWith("scala.concurrent.Future") &&
            !m.returnType.toString.startsWith("reactify.") &&
            !isInvalidName(member.name.decodedName.toString)) {
          scribe.info(s"Method: ${member.name.decodedName}, ${m.returnType}, ${m.paramLists}")
          Some(CommandHelp(member.name.decodedName.toString, m.paramLists.headOption.getOrElse(Nil).map(_.toString)))
        } else {
          None
        }
      } else {
        None
      }
    }.toList
    ModuleHelp(commands)
  }
}

object UberTermModule {
  val registered: Channel[UberTermModule] = Channel[UberTermModule]
  val all: Var[Set[UberTermModule]] = Var[Set[UberTermModule]](Set.empty)

  def byPrefix(prefix: String): Option[UberTermModule] = all().find(_.prefix == prefix)

  def register(module: UberTermModule): Unit = synchronized {
    all.setStatic(all() + module)
    registered := module
  }
}

case class ModuleHelp(commands: List[CommandHelp])

case class CommandHelp(name: String, args: List[String])