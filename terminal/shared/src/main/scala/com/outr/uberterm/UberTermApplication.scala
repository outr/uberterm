package com.outr.uberterm

import io.youi.app.{CommunicationManager, YouIApplication}

trait UberTermApplication extends YouIApplication {
  val communication: CommunicationManager[UberTermCommunication] = createCommunication[UberTermCommunication](connectivity)
}
