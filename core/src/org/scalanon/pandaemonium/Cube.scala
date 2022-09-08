package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

case class Cube(lX: Float, lY: Float) extends Entity {
  def y: Float   = lY
  def x: Float   = lX
  var loc        = Vec2(x, y)
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      cubeSprite,
      lX - Pandaemonium.screenPixel,
      lY - Pandaemonium.screenPixel,
      Pandaemonium.screenPixel * 2,
      Pandaemonium.screenPixel * 4
    )
  }
  def cubeSprite = AssetLoader.image("Cube.png")
}
