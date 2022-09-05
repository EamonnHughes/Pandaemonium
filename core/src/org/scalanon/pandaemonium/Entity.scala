package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

trait Entity {
  def y: Float
  def x: Float
  def draw(batch: PolygonSpriteBatch)
}
