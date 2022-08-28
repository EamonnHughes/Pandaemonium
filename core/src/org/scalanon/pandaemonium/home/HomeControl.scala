package org.scalanon.pandaemonium.home

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class HomeControl(home: Home) extends IconAdapter(Nil) {
  override def touchDown(
      screenX: Int,
      screenY: Int,
      pointer: Int,
      button: Int
  ): Boolean = {
    if (!home.ready) {
      home.update(10f)
      true
    } else {
      super.touchDown(screenX, screenY, pointer, button)
    }
  }

  override def keyDown(keycode: Int): Boolean = {
    if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
      Gdx.app.exit()
    }
    true
  }

  override def keyUp(keycode: Int): Boolean = {
    if (keycode == Keys.SPACE || keycode == Keys.ENTER) {
      if (!home.ready) {
        home.update(10f)
      } else {
        home.play()
      }
    }
    true
  }
}
