package org.scalanon.pandaemonium
package game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.Cube
import org.scalanon.pandaemonium.game.Game._
import org.scalanon.pandaemonium.home.Home
import org.scalanon.pandaemonium.menu.{NavMenu, menuItem}
import org.scalanon.pandaemonium.util.TextureWrapper
import sun.security.ec.point.ProjectivePoint.Mutable

class Game() extends Scene {
  var mouseLoc: Vec2    = Vec2(0, 0)
  var bullets           = List.empty[Bullet]
  var mouseDown         = false
  var cubes: List[Cube] = List(
    Cube(
      Pandaemonium.screenPixel * 1 * 16,
      Pandaemonium.screenPixel * 5 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 2 * 16,
      Pandaemonium.screenPixel * 4 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 2 * 16,
      Pandaemonium.screenPixel * 6 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 3 * 16,
      Pandaemonium.screenPixel * 3 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 3 * 16,
      Pandaemonium.screenPixel * 7 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 4 * 16,
      Pandaemonium.screenPixel * 2 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 4 * 16,
      Pandaemonium.screenPixel * 8 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 5 * 16,
      Pandaemonium.screenPixel * 1 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 5 * 16,
      Pandaemonium.screenPixel * 9 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 6 * 16,
      Pandaemonium.screenPixel * 2 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 6 * 16,
      Pandaemonium.screenPixel * 8 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 7 * 16,
      Pandaemonium.screenPixel * 3 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 8 * 16,
      Pandaemonium.screenPixel * 4 * 16
    ),
    Cube(
      Pandaemonium.screenPixel * 9 * 16,
      Pandaemonium.screenPixel * 5 * 16
    )
  )
  val control           = new GameControl(this)

  var player: Player         = Player(this)
  var state: State           = PlayState
  def everything: List[Entity] = {
    (player :: cubes ::: bullets)
      .sortBy(e => -e.y)
  }
  override def init(): GameControl = {
    state = PlayState
    control
  }

  override def update(delta: Float): Option[Scene] = {
    player.update(delta)
    bullets.foreach(b => b.update(delta))
    state match {
      case ExitState =>
        Some(new Home)
      case PlayState => None
      case DieState  => None
    }
  }
  def Square: TextureWrapper = AssetLoader.image("square.png")
  def mLoc: TextureWrapper   = AssetLoader.image("Mloc.png")

  override def render(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(Square, 0, 0, Geometry.ScreenWidth, Geometry.ScreenHeight)
    everything.foreach(e => e.draw(batch))
    Text.draw(
      batch,
      Text.tinyFont,
      Color.WHITE,
      s"PANDAEMONIUM v0.1",
      l =>
        (Geometry.ScreenWidth - l.width - Geometry.Dimension / 4) -> (l.height + Geometry.Dimension / 4)
    )
    if (mouseDown) {
      var locX = ((mouseLoc.x / 16 / Pandaemonium.screenPixel).floor) * 16
      var locY = {
        (((mouseLoc.y / Pandaemonium.screenPixel) / (16)).floor) * 16 + (locX % 32) / 2
      }
      batch.setColor(Color.RED)
      batch.draw(
        mLoc,
        (locX - 16) * Pandaemonium.screenPixel,
        (locY - 8) * Pandaemonium.screenPixel,
        32 * Pandaemonium.screenPixel,
        16 * Pandaemonium.screenPixel
      )
      batch.setColor(Color.WHITE)

    }
  }

}

object Game {
  val Title = "Pandaemonium"
  sealed trait State

  case object ExitState extends State
  case object DieState  extends State
  case object PlayState extends State
}
