package org.scalanon.pandaemonium
package home

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

class Home() extends Scene {

  import Home._

  var state: State = HomeState
  var logoAlpha    = 0f
  var playAlpha    = 0f
  var ufoPos       = 0f
  var alienPos     = 0f
  var discard      = false
  var ready        = false

  override def init(): HomeControl = {
    state = HomeState
    new HomeControl(this)
  }

  override def update(delta: Float): Option[Scene] = {

    None
  }

  override def render(batch: PolygonSpriteBatch): Unit = {

    Text.draw(
      batch,
      Text.tinyFont,
      Color.DARK_GRAY ⍺ (.25f * playAlpha),
      s"v${Pandaemonium.version}",
      l =>
        (Geometry.ScreenWidth - l.width - Geometry.Dimension / 4) -> (l.height + Geometry.Dimension / 4)
    )
  }

  def play(): Unit = {
    state = PlayState
  }

}

object Home {

  val LogoFadeInSeconds  = 1f
  val PlayDelaySeconds   = 0.3f
  val PlayFadeInSeconds  = .3f
  val UfoMoveInSeconds   = 1.5f
  val AlienMoveInSeconds = 1.5f

  val LogoFadeOutSeconds = .5f
  val PlayFadeOutSeconds = .3f

  val Title = "TAKE ME TO YOUR LEADER!"

  val HighScoreColor = new Color(.7f, .7f, .7f, 1f)

  sealed trait State

  case object HomeState extends State
  case object PlayState extends State
}
