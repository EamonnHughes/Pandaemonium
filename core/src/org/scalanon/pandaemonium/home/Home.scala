package org.scalanon.pandaemonium
package home

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.menu.{NavMenu, menuItem}

class Home() extends Scene {

  import Home._

  var state: State       = HomeState
  var logoAlpha          = 0f
  var playAlpha          = 0f
  var ufoPos             = 0f
  var alienPos           = 0f
  var discard            = false
  var start              = false
  var StartMenu: NavMenu = NavMenu(
    List[menuItem](
      menuItem("BEGIN", () => { start = true }),
      menuItem("EXIT", () => { System.exit(666) })
    ),
    Vec2(
      Geometry.ScreenWidth / (2 * Pandaemonium.screenPixel),
      Geometry.ScreenHeight / (2 * Pandaemonium.screenPixel) - 1
    ),
    2,
    6,
    Color.RED,
    Color.BLACK
  )

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
      Color.WHITE,
      s"PANDAEMONIUM v0.1",
      l =>
        (Geometry.ScreenWidth - l.width - Geometry.Dimension / 4) -> (l.height + Geometry.Dimension / 4)
    )
    StartMenu.draw(batch)
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

  val Title = "Pandaemonium"

  val HighScoreColor = new Color(.7f, .7f, .7f, 1f)

  sealed trait State

  case object HomeState extends State
  case object PlayState extends State
}
