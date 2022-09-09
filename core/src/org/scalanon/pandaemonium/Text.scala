package org.scalanon.pandaemonium

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.{
  BitmapFont,
  GlyphLayout,
  PolygonSpriteBatch
}
import org.scalanon.pandaemonium.Geometry.Dimension
import org.scalanon.pandaemonium.home.Home
import org.scalanon.pandaemonium.util.GarbageCan

object Text {
  def loadFonts()(implicit garbage: GarbageCan): Unit = {
    val generator = new FreeTypeFontGenerator(
      Gdx.files.internal("VT323-Regular.ttf")
    )
    val parameter = new FreeTypeFontGenerator.FreeTypeFontParameter
    parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + CharExtras
    parameter.size = Pandaemonium.screenPixel.toInt * 32
    mediumFont = garbage.add(generator.generateFont(parameter))
    parameter.size = Pandaemonium.screenPixel.toInt * 28
    smallFont = garbage.add(generator.generateFont(parameter))
    parameter.size = Pandaemonium.screenPixel.toInt * 4
    tinyFont = garbage.add(generator.generateFont(parameter))
    generator.dispose()
  }

  private val CharExtras = Home.Title

  var mediumFont: BitmapFont = _
  var smallFont: BitmapFont  = _
  var tinyFont: BitmapFont   = _

  def draw(
      batch: PolygonSpriteBatch,
      font: BitmapFont,
      color: Color,
      text: String,
      y: Float,
      x: Float = 0f,
      width: Float = Geometry.ScreenWidth
  ): Unit = {
    font.setColor(color)
    font.draw(batch, text, x, y, width, CenterAlign, false)
  }

  def draw(
      batch: PolygonSpriteBatch,
      font: BitmapFont,
      color: Color,
      text: String,
      position: GlyphLayout => (Float, Float)
  ): Unit = {
    font.setColor(color)
    val layout = new GlyphLayout(font, text)
    val (x, y) = position(layout)
    font.draw(batch, layout, x, y)
  }

}
