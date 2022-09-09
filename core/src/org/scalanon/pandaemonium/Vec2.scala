package org.scalanon.pandaemonium

final case class Vec2(var x: Float, var y: Float) {
  def set(x: Float, y: Float): Unit = {
    this.x = x
    this.y = y
  }

  def set(v: Vec2): Unit = {
    this.x = v.x
    this.y = v.y
  }

  def +(vec22: Vec2): Vec2 = {
    Vec2(
      this.x + vec22.x,
      this.y + vec22.y
    )
  }
  def -(vec22: Vec2): Vec2 = {
    Vec2(
      this.x - vec22.x,
      this.y - vec22.y
    )
  }
  def *(float: Float): Vec2 = {
    Vec2(
      this.x * float,
      this.y * float
    )
  }
  def /(float: Float): Vec2 = {
    Vec2(
      this.x / float,
      this.y / float
    )
  }

  def add(dx: Float, dy: Float): Unit = {
    x += dx
    y += dy
  }

  def mulAdd(dx: Float, dy: Float, mul: Float): Unit = {
    x += dx * mul
    y += dy * mul
  }

  def mulAdd(v: Vec2, mul: Float): Unit = {
    x += v.x * mul
    y += v.y * mul
  }

  def distanceFrom(v2: Vec2): Float = {
    Math.sqrt(((x - v2.x) * (x - v2.x)) + ((y - v2.y) * (y - v2.y))).toFloat
  }

  def manhattanDistance(v2: Vec2): Float = {
    (x - v2.x).abs + (y - v2.y).abs
  }
}

object Vec2 {
  def zero: Vec2 = new Vec2(0, 0)
}
