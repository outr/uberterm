package com.outr.uberterm.result

import com.outr.uberterm.ColorScheme
import io.youi.hypertext.Container
import io.youi.hypertext.border.BorderStyle

class ResultContainer extends Container {
  border.radius := 5.0
  border.width := Some(2.0)
  border.color := Some(ColorScheme.base1)
  border.style := Some(BorderStyle.Solid)
}