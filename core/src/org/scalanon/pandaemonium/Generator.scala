package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.game.Game

case class Generator(lX: Float, lY: Float, game: Game) extends Build {
  def y: Float            = lY
  var cost                = 10
  def x: Float            = lX
  var stage               = 0
  var loc                 = Vec2(x, y)
  var time                = 0f
  var powered             = true
  def checkPower: Boolean = true
  def update(delta: Float) = {
    time += delta
    if (time >= .2f) {
      stage += 1
      if (stage == 4) {
        stage = 0
      }
      time = 0f
    }
  }
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      cubeSprite,
      loc.x - (Pandaemonium.screenPixel * 16),
      (loc.y - (Pandaemonium.screenPixel * 16)) / 2,
      0,
      0,
      Pandaemonium.screenPixel * 32,
      Pandaemonium.screenPixel * 64,
      1,
      1,
      0,
      32 * stage,
      0,
      32,
      64,
      false,
      false
    )
  }
  def cubeSprite          = AssetLoader.image("Generator.png")
}
