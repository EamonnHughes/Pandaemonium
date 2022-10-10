package org.scalanon.pandaemonium.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import org.scalanon.pandaemonium.home.IconAdapter
import org.scalanon.pandaemonium.{Cube, Geometry, Pandaemonium, Vec2}

import scala.collection.mutable

class GameControl(game: Game) extends IconAdapter(Nil) {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (button == 1) {
      game.mouseDown = true
      game.mouseLoc.x = screenX - game.offset.x
      game.mouseLoc.y = Geometry.ScreenHeight - screenY - game.offset.y
    }
    true
  }

  override def touchUp(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (button == 1) {

      game.mouseDown = false

      game.player.build(
        screenX - game.offset.x,
        (Geometry.ScreenHeight - screenY) - game.offset.y
      )
    }
    true
  }

  val keysPressed = mutable.Set.empty[Int]

  def reset = {
    keysPressed.clear()

  }

  override def mouseMoved(
      screenX: Int,
      screenY: Int
  ): Boolean = {
    game.mouseLoc.x = screenX - game.offset.x
    game.mouseLoc.y = Geometry.ScreenHeight - screenY - game.offset.y

    true
  }

  override def touchDragged(
      screenX: Int,
      screenY: Int,
      pointer: Int
  ): Boolean = {
    game.mouseLoc.x = screenX - game.offset.x
    game.mouseLoc.y = Geometry.ScreenHeight - screenY - game.offset.y

    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
      Gdx.app.exit()
    }
    if (keycode == Keys.NUM_1) {
      game.player.building = 0
    } else if (keycode == Keys.NUM_2) {

      game.player.building = 1
    } else if (keycode == Keys.NUM_3) {

      game.player.building = 2
    } else if (keycode == Keys.NUM_4) {

      game.player.building = 3
    }

    keysPressed.add(keycode)

    true
  }

  override def keyUp(keycode: Int): Boolean = {
    keysPressed.remove(keycode)
    true
  }
}
