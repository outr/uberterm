package com.outr.uberterm.result

import com.outr.uberterm.ColorScheme
import io.youi.hypertext.Container
import io.youi.hypertext.border.BorderStyle
import io.youi._
import io.youi.hypertext.style.Overflow

class ResultContainer extends Container {
  border.radius := 5.0
  border.width := Some(2.0)
  border.color := Some(ColorScheme.base1)
  border.style := Some(BorderStyle.Solid)

  size.width := ui.size.width - 15.0
  size.height := math.min((0.0 :: children.map(_.position.bottom()).toList).max, ui.size.height - 70.0)

  overflow.x := Overflow.Hidden
  overflow.y := Overflow.Auto
}