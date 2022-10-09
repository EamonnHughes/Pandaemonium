package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

case class Cube(lX: Float, lY: Float) extends Build {
  var cost: Float         = 1
  def y: Float            = lY
  def x: Float            = lX
  var loc                 = Vec2(x, y)
  def update(delta: Float) = {}

  def checkPower: Boolean = true
  var powered             = true
  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.draw(
      cubeSprite,
      lX - (Pandaemonium.screenPixel * 16),
      (lY / 2) - (Pandaemonium.screenPixel * 8),
      Pandaemonium.screenPixel * 32,
      Pandaemonium.screenPixel * 64
    )
  }
  def cubeSprite          = AssetLoader.image("Cube.png")
}
