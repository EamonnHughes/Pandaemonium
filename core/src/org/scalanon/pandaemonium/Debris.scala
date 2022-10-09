package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

case class Debris(lX: Float, lY: Float) extends Build {
  var cost     = 1
  def y: Float = lY
  def x: Float = lX
  var loc      = Vec2(x, y)
  def update(delta: Float) = {}
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      Sprite,
      lX - (Pandaemonium.screenPixel * 16),
      (lY / 2) - (Pandaemonium.screenPixel * 8),
      Pandaemonium.screenPixel * 32,
      Pandaemonium.screenPixel * 64
    )
  }
  def Sprite   = AssetLoader.image("Debris.png")
}
