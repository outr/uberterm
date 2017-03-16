package com.outr.uberterm.example

import com.outr.uberterm.UberTermCommunication
import com.outr.uberterm.file.FileModule
import io.youi.app.{CommunicationManager, YouIApplication}

trait UberTermExampleApplication extends YouIApplication {
  val communication: CommunicationManager[UberTermCommunication] = connectivity.communication[UberTermCommunication]

  val fileModule: CommunicationManager[FileModule] = connectivity.communication[FileModule]
}
