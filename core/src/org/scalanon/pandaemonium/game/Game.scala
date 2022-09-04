package org.scalanon.pandaemonium
package game

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.game.Game._
import org.scalanon.pandaemonium.home.Home
import org.scalanon.pandaemonium.menu.{NavMenu, menuItem}
import org.scalanon.pandaemonium.util.TextureWrapper
import sun.security.ec.point.ProjectivePoint.Mutable

class Game() extends Scene {
  var mouseLoc: Vec2 = Vec2(0, 0)
  var cube           = Cube(Geometry.ScreenWidth / 2, Geometry.ScreenHeight / 2)
  val control        = new GameControl(this)

  var player: Player         = Player(this)
  var state: State           = PlayState
  def everything: List[Entity] = {
    List(player, cube)
      .sortBy(e => -e.y)
  }
  override def init(): GameControl = {
    state = PlayState
    control
  }

  override def update(delta: Float): Option[Scene] = {
    player.update(delta)
    state match {
      case ExitState =>
        Some(new Home)
      case PlayState => None
      case DieState  => None
    }
  }
  def Square: TextureWrapper = AssetLoader.image("square.png")

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
  }

}

object Game {
  val Title = "Pandaemonium"
  sealed trait State

  case object ExitState extends State
  case object DieState  extends State
  case object PlayState extends State
}
