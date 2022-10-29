package org.scalanon.pandaemonium

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2

object Geometry {
  val Columns = 20
  val Rows    = 20

  def cartesianL          = ((64) / Math.sqrt(2)).toFloat
  def mouseGridLoc(mouseLoc: Vec2, offset: Vec2): Vec2 = {

    val originToMouse =
      new Vector2(mouseLoc.x - offset.x, (mouseLoc.y - offset.y) * 2)
    originToMouse.rotateDeg(45)
    val xc            = (originToMouse.x / cartesianL).floor
    val yc            = (originToMouse.y / cartesianL).floor
    Vec2(
      Pandaemonium.screenPixel * (16 + 16 * (yc + xc)),
      Pandaemonium.screenPixel * (-16 * (xc - yc))
    )
  }
  val ScreenWidth: Float  = Gdx.graphics.getWidth.toFloat
  val ScreenHeight: Float = Gdx.graphics.getHeight.toFloat
  // dimension of one block
  val Dimension: Float    =
    ((ScreenWidth * 2 / (Columns * 2 + 1)) min (ScreenHeight * 2 / (Rows * 2 + 5))).floor
  val OffsetX: Float      = ((ScreenWidth - Dimension * Columns) / 2).floor
  val OffsetY: Float      =
    ((ScreenHeight - Dimension * (Rows + 2) + Dimension / 2) / 2).floor
  val Bevel: Float        = (Dimension / 48).floor max 1

}
