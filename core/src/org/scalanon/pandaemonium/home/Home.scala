package org.scalanon.pandaemonium
package home

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.game.Game
import org.scalanon.pandaemonium.menu.{NavMenu, menuItem}
import org.scalanon.pandaemonium.util.TextureWrapper

class Home() extends Scene {

  import Home._

  var state: State         = HomeState
  def icon: TextureWrapper = AssetLoader.image("Icon.png")
  var StartMenu: NavMenu   = NavMenu(
    List[menuItem](
      menuItem("BEGIN", () => { state = PlayState }),
      menuItem("EXIT", () => { System.exit(666) })
    ),
    Vec2(
      Geometry.ScreenWidth / (2 * Pandaemonium.screenPixel),
      (Geometry.ScreenHeight / (2 * Pandaemonium.screenPixel)) + 16
    ),
    2,
    6,
    Color.RED,
    new Color(0.2f, 0f, 0f, 1)
  )

  override def init(): HomeControl = {
    state = HomeState
    new HomeControl(this)
  }

  var rot = 0f

  override def update(delta: Float): Option[Scene] = {
    rot += 10 * delta
    state match {
      case PlayState =>
        Some(new Game)
      case HomeState => None
    }
  }

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(0.2f, 0f, 0f, 1)
    batch.draw(
      icon,
      (Geometry.ScreenWidth - (Geometry.ScreenHeight)) / 2,
      0,
      Geometry.ScreenHeight / 2,
      Geometry.ScreenHeight / 2,
      Geometry.ScreenHeight,
      Geometry.ScreenHeight,
      1,
      1,
      rot,
      0,
      0,
      128,
      128,
      false,
      false
    )

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
