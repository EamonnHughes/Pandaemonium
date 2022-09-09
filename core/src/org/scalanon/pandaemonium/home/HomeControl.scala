package org.scalanon.pandaemonium.home

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import org.scalanon.pandaemonium.{Geometry, Pandaemonium, Vec2}

class HomeControl(home: Home) extends IconAdapter(Nil) {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    home.StartMenu.click(
      Vec2(
        screenX / Pandaemonium.screenPixel,
        (Geometry.ScreenHeight - screenY) / Pandaemonium.screenPixel
      )
    )
    true
  }

  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    home.StartMenu.hover(
      Vec2(
        screenX / Pandaemonium.screenPixel,
        (Geometry.ScreenHeight - screenY) / Pandaemonium.screenPixel
      )
    )
    true
  }

  override def keyDown(keycode: Int): Boolean = {
    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
      Gdx.app.exit()
    }
    true
  }

  override def keyUp(keycode: Int): Boolean = {
    if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
      home.StartMenu.used()
    }
    if (keycode == Keys.UP || keycode == Keys.W) {
      home.StartMenu.up()
    }
    if (keycode == Keys.DOWN || keycode == Keys.S) {
      home.StartMenu.down()
    }

    true
  }
}
