package org.scalanon.pandaemonium
package game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.Matrix4
import org.scalanon.pandaemonium.Cube
import org.scalanon.pandaemonium.game.Game._
import org.scalanon.pandaemonium.home.Home
import org.scalanon.pandaemonium.menu.{NavMenu, menuItem}
import org.scalanon.pandaemonium.util.TextureWrapper
import sun.security.ec.point.ProjectivePoint.Mutable

class Game() extends Scene {
  var mouseLoc: Vec2 = Vec2(0, 0)
  var mouseDown      = false
  val matrix         = new Matrix4()

  var builds: List[Build] = List(
    Debris(
      Pandaemonium.screenPixel * 1 * 16,
      Pandaemonium.screenPixel * 5 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 2 * 16,
      Pandaemonium.screenPixel * 4 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 2 * 16,
      Pandaemonium.screenPixel * 6 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 3 * 16,
      Pandaemonium.screenPixel * 3 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 3 * 16,
      Pandaemonium.screenPixel * 7 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 4 * 16,
      Pandaemonium.screenPixel * 2 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 4 * 16,
      Pandaemonium.screenPixel * 8 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 5 * 16,
      Pandaemonium.screenPixel * 1 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 5 * 16,
      Pandaemonium.screenPixel * 9 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 6 * 16,
      Pandaemonium.screenPixel * 2 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 6 * 16,
      Pandaemonium.screenPixel * 8 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 7 * 16,
      Pandaemonium.screenPixel * 3 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 8 * 16,
      Pandaemonium.screenPixel * 4 * 16
    ),
    Debris(
      Pandaemonium.screenPixel * 9 * 16,
      Pandaemonium.screenPixel * 5 * 16
    )
  )
  val control             = new GameControl(this)

  var player: Player = Player(this)
  var state: State   = PlayState
  def offset: Vec2   =
    Vec2(Geometry.ScreenWidth / 2, Geometry.ScreenHeight / 2) - Vec2(
      player.loc.x,
      player.loc.y / 2
    )
  def everything: List[Entity] = {
    (player :: builds)
      .sortBy(e => -e.y)
  }
  override def init(): GameControl = {
    state = PlayState
    control
  }

  override def update(delta: Float): Option[Scene] = {
    everything.foreach(e => e.update(delta))
    state match {
      case ExitState =>
        Some(new Home)
      case PlayState => None
      case DieState  => None
    }
  }

  def Square: TextureWrapper   = AssetLoader.image("square.png")
  def mLoc: TextureWrapper     = AssetLoader.image("Mloc.png")
  def Isometer: TextureWrapper = AssetLoader.image("Isometer.png")

  override def render(batch: PolygonSpriteBatch): Unit = {

    batch.setColor(Color.WHITE)

    batch.draw(Square, 0, 0, Geometry.ScreenWidth, Geometry.ScreenHeight)

    Text.draw(
      batch,
      Text.mediumFont,
      Color.BLACK,
      player.stone.toString,
      l => (20f) -> (Geometry.ScreenHeight - 20f)
    )
    batch.setTransformMatrix(
      matrix.setToTranslation(offset.x, offset.y, 0)
    )
    everything.foreach(e => e.draw(batch))

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
    batch.setTransformMatrix(matrix.idt())

    batch.setColor(Color.BLUE)
    batch.draw(
      Square,
      Geometry.ScreenWidth - 100 * Pandaemonium.screenPixel,
      10 * Pandaemonium.screenPixel,
      100 * Pandaemonium.screenPixel,
      100 * Pandaemonium.screenPixel
    )
    everything
      .filter(e =>
        e.loc.manhattanDistance(player.loc) < Geometry.ScreenWidth / 2
      )
      .foreach(e => {
        if (e.isInstanceOf[Player]) {
          batch.setColor(Color.RED)
        } else {
          batch.setColor(Color.GREEN)
        }
        batch.draw(
          Isometer,
          Geometry.ScreenWidth - (100 * Pandaemonium.screenPixel) + (((e.loc.x + offset.x) / (Geometry.ScreenHeight / Pandaemonium.screenPixel)) * Pandaemonium.screenPixel * 20) - (Pandaemonium.screenPixel * 3),
          (((e.loc.y + offset.y * 2) / (Geometry.ScreenHeight / Pandaemonium.screenPixel)) * Pandaemonium.screenPixel * 20) - (Pandaemonium.screenPixel * 3),
          Pandaemonium.screenPixel * 6,
          Pandaemonium.screenPixel * 6
        )
      })
    batch.setColor(Color.LIGHT_GRAY)
    batch.draw(
      Square,
      Geometry.ScreenWidth - 100 * Pandaemonium.screenPixel,
      0,
      100 * Pandaemonium.screenPixel,
      10 * Pandaemonium.screenPixel
    )
    batch.draw(
      Square,
      Geometry.ScreenWidth - 100 * Pandaemonium.screenPixel,
      100 * Pandaemonium.screenPixel,
      100 * Pandaemonium.screenPixel,
      20 * Pandaemonium.screenPixel
    )
    batch.draw(
      Square,
      Geometry.ScreenWidth - 110 * Pandaemonium.screenPixel,
      0,
      10 * Pandaemonium.screenPixel,
      120 * Pandaemonium.screenPixel
    )
  }

}

object Game {
  val Title = "Pandaemonium"
  sealed trait State

  case object ExitState extends State
  case object DieState  extends State
  case object PlayState extends State
}
