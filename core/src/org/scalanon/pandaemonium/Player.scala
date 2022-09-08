package org.scalanon.pandaemonium

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.math.MathUtils
import org.scalanon.pandaemonium.game.Game
import org.scalanon.pandaemonium.util.TextureWrapper

case class Player(game: Game) extends Entity {
  def PlayerWalk: TextureWrapper  = AssetLoader.image("PlayerWalkSheet.png")
  def PlayerStill: TextureWrapper = AssetLoader.image("PlayerStillSheet.png")
  var direction: Vec2             = Vec2(0, 0)
  var state: Player.Action        = Player.still
  def y: Float                    = loc.y
  def x: Float                    = loc.x
  def PlayerCurrent: TextureWrapper = {
    state match {
      case Player.still =>
        PlayerStill
      case Player.walk  =>
        PlayerWalk
    }
  }
  var loc: Vec2                   = Vec2(Geometry.ScreenWidth / 2, Geometry.ScreenHeight / 2)
  def dir: Float = {
    MathUtils.atan2(game.mouseLoc.y - loc.y, game.mouseLoc.x - loc.x).degrees
  }
  def dirAn: Int = {
    if (dir < -68 && dir >= -112) { 7 }
    else if (dir < 112 && dir >= 68) { 6 }
    else if (dir < 22 && dir >= -22) { 5 }
    else if (dir < -158 || dir >= 158) { 4 }
    else if (dir < 68 && dir >= 22) { 3 }
    else if (dir < 158 && dir >= 112) { 2 }
    else if (dir < -112 && dir >= -158) { 1 }
    else { 0 }
  }
  var anTick                      = 0f
  def animate(delta: Float) = {
    anTick += delta

    if (anTick >= 0.2f) {
      anStage += 1
      if (anStage >= 3) {
        anStage = 0
      }

      anTick = 0
    }
  }
  def move(delta: Float): Unit = {
    if (
      (game.control.keysPressed.contains(Keys.W) ||
        game.control.keysPressed.contains(Keys.UP)) &&
      !game.control.keysPressed.contains(Keys.S) &&
      !game.control.keysPressed.contains(Keys.DOWN)
    ) direction.y = 1
    else if (
      (game.control.keysPressed.contains(Keys.S) ||
        game.control.keysPressed.contains(Keys.DOWN)) &&
      !game.control.keysPressed.contains(Keys.W) &&
      !game.control.keysPressed.contains(Keys.UP)
    ) direction.y = -1
    else direction.y = 0
    if (
      (game.control.keysPressed.contains(Keys.D) ||
        game.control.keysPressed.contains(Keys.RIGHT)) &&
      !game.control.keysPressed.contains(Keys.A) &&
      !game.control.keysPressed.contains(Keys.LEFT)
    ) direction.x = 1
    else if (
      (game.control.keysPressed.contains(Keys.A) ||
        game.control.keysPressed.contains(Keys.LEFT)) &&
      !game.control.keysPressed.contains(Keys.D) &&
      !game.control.keysPressed.contains(Keys.RIGHT)
    ) direction.x = -1
    else direction.x = 0
    if (direction.x != 0 || direction.y != 0) {
      state = Player.walk
    } else {
      state = Player.still
    }
    var moveC = Vec2(
      direction.x * Pandaemonium.screenPixel * 4 * delta,
      direction.y * Pandaemonium.screenPixel * 2 * delta
    )
    game.everything
      .filterNot(e => e eq this)
      .foreach(ev => {
        if (
          (loc + Vec2(moveC.x, 0))
            .manhattanDistance(ev.loc) <= Pandaemonium.screenPixel * 2
        ) {
          moveC.x = 0
        }
        if (
          (loc + Vec2(0, moveC.y))
            .manhattanDistance(ev.loc) <= Pandaemonium.screenPixel * 2
        ) {
          moveC.y = 0
        }

      })
    loc += moveC
  }

  def update(delta: Float): Unit = {
    println(dir + "  " + dirAn + "  " + loc)
    animate(delta)
    move(delta)

  }
  var anStage                     = 0

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      PlayerCurrent,
      loc.x - Pandaemonium.screenPixel,
      loc.y - Pandaemonium.screenPixel,
      0,
      0,
      Pandaemonium.screenPixel * 2,
      Pandaemonium.screenPixel * 4,
      1,
      1,
      0,
      32 * dirAn,
      64 * anStage,
      32,
      64,
      false,
      false
    )

  }
}
object Player {
  sealed trait Action
  case object still extends Action
  case object walk  extends Action
}
