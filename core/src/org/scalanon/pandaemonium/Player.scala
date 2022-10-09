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
  var stone                       = 0
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
    MathUtils
      .atan2((game.mouseLoc.y * 2) - (loc.y), game.mouseLoc.x - loc.x)
  }
  var building: Int               = 0
  def build(screenX: Float, screenY: Float) {
    var locX = ((screenX / 48).floor) * 48
    var locY = {
      (((screenY) / (96 / 2)).floor) * 96 + locX % 96

    }
    if (game.builds.exists(cube => cube.loc == Vec2(locX, locY))) {
      game.builds.foreach(b => {
        if (b.loc == Vec2(locX, locY)) {
          stone += b.cost.toInt
          game.builds = game.builds.filterNot(bu => bu eq b)
        }
      })

    } else {
      if (building == 0) {

        if (stone > 0) {
          game.builds = Cube(locX, locY) :: game.builds
          stone -= 1
        }

      } else if (building == 1) {

        if (stone >= 10) {
          game.builds = Miner(locX, locY, game) :: game.builds
          stone -= 10
        }
      }
    }
  }
  def dirAn: Int = {
    var dirD = dir.degrees
    if (dirD < -68 && dirD >= -112) { 7 }
    else if (dirD < 112 && dirD >= 68) { 6 }
    else if (dirD < 22 && dirD >= -22) { 5 }
    else if (dirD < -158 || dirD >= 158) { 4 }
    else if (dirD < 68 && dirD >= 22) { 3 }
    else if (dirD < 158 && dirD >= 112) { 2 }
    else if (dirD < -112 && dirD >= -158) { 1 }
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
    val dX     = direction.x * Pandaemonium.screenPixel * 70 * delta
    val dY     = direction.y * Pandaemonium.screenPixel * 70 * delta
    val tmpLoc = new Vec2(loc.x + dX, loc.y + dY)
    if (canMoveTo(tmpLoc)) {
      loc.set(tmpLoc)
    } else {
      val px    = (loc.x / Pandaemonium.screenPixel).round
      val py    = (loc.y / Pandaemonium.screenPixel).round
      var dPX   = (dX / Pandaemonium.screenPixel).round
      var dPY   = (dY / Pandaemonium.screenPixel).round
      var moved = false
      tmpLoc.set(px * Pandaemonium.screenPixel, py * Pandaemonium.screenPixel)
      while (moved) {
        moved = false
        val dDPX = dPX.sign
        val dDPY = dPY.sign
        tmpLoc.x += dDPX * Pandaemonium.screenPixel
        if (canMoveTo(tmpLoc)) {
          loc.set(tmpLoc)
          moved = true
        }
        tmpLoc.y += dDPY * Pandaemonium.screenPixel
        if (canMoveTo(tmpLoc)) {
          loc.set(tmpLoc)
          moved = true
        }
        dPX -= dDPX
        dPY -= dDPY
      }
    }
  }

  private def canMoveTo(newLoc: Vec2): Boolean = {
    game.everything
      .forall(e =>
        (e eq this) || newLoc.manhattanDistance(
          e.loc
        ) >= Pandaemonium.screenPixel * 32
      )
  }

  def update(delta: Float): Unit = {
    animate(delta)
    move(delta)

  }
  var anStage                     = 0

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.WHITE)
    batch.draw(
      PlayerCurrent,
      loc.x - (Pandaemonium.screenPixel * 16),
      (loc.y - (Pandaemonium.screenPixel * 16)) / 2,
      0,
      0,
      Pandaemonium.screenPixel * 32,
      Pandaemonium.screenPixel * 64,
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
