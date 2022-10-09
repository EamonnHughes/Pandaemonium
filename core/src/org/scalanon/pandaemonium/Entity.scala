package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch

trait Entity {
  def y: Float

  def x: Float

  var loc: Vec2

  def draw(batch: PolygonSpriteBatch)

  def update(delta: Float)
}

trait Build extends Entity {
  var cost: Float
  var powered: Boolean
  def checkPower: Boolean
}
