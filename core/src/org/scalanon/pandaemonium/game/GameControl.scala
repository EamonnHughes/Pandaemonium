package org.scalanon.pandaemonium.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import org.scalanon.pandaemonium.home.IconAdapter
import org.scalanon.pandaemonium.{Bullet, Cube, Geometry, Pandaemonium, Vec2}

import scala.collection.mutable

class GameControl(game: Game) extends IconAdapter(Nil) {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (button == 0) {
      game.bullets = Bullet(game) :: game.bullets
    } else {
      var locX = ((screenX / 48).floor) * 48
      var locY =
        (((Geometry.ScreenHeight - screenY) / (96 / 2)).floor) * 96 + locX % 96
      if (game.cubes.exists(cube => cube.loc == Vec2(locX, locY))) {
        game.cubes = game.cubes.filterNot(cube => cube.loc == Vec2(locX, locY))
      } else {
        game.cubes = Cube(locX, locY) :: game.cubes
      }
    }
    true
  }
  val keysPressed = mutable.Set.empty[Int]

  def reset = {
    keysPressed.clear()

  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    game.mouseLoc.x = screenX
    game.mouseLoc.y = Geometry.ScreenHeight - screenY

    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
      Gdx.app.exit()
    }

    keysPressed.add(keycode)

    true
  }

  override def keyUp(keycode: Int): Boolean = {
    keysPressed.remove(keycode)
    true
  }
}
