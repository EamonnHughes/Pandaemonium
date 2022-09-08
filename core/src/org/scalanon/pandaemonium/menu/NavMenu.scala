package org.scalanon.pandaemonium.menu

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.{AssetLoader, Pandaemonium, Text, Vec2}

case class NavMenu(
    var itList: List[menuItem],
    location: Vec2,
    var length: Int,
    lX: Int,
    selCol: Color,
    unSelCol: Color
) {

  def Square   = AssetLoader.image("square.png")
  var selected = 0

  def up() = {
    if (itList.nonEmpty) { selected = (selected - 1) max 0 }
  }
  def down() = {
    if (itList.nonEmpty) { selected = (selected + 1) min itList.length - 1 }
  }
  def used() = {
    if (itList.nonEmpty) {
      itList(selected).use()
    }
  }
  def click(loc: Vec2) = {
    if (itList.nonEmpty) {
      itList.zipWithIndex.foreach({ case (item, index) =>
        if (
          loc.x >= location.x - lX / 2 && loc.x <= location.x + lX / 2 && loc.y <= location.y - (index * 2) && loc.y >= location.y - (index * 2) - 2
        ) {
          item.use()
        }
      })
    }
  }
  def hover(loc: Vec2) = {
    if (itList.nonEmpty) {
      itList.zipWithIndex.foreach({ case (item, index) =>
        if (
          loc.x >= location.x - (lX / 2) && loc.x <= location.x + (lX / 2) && loc.y <= location.y - (index * 2) && loc.y >= location.y - (index * 2) - 2
        ) {
          selected = index
        }
      })
    }
  }
  def update(): Unit = {
    if (selected >= length + startDrawingAt) {
      startDrawingAt = selected
    }
    if (startDrawingAt > selected) {
      startDrawingAt = (selected - length + 1) max 0
    }

    if (selected >= itList.length) {
      selected = itList.length - 1
    }
  }

  var startDrawingAt = 0
  def draw(
      batch: PolygonSpriteBatch
  ): Unit = {
    if (itList.nonEmpty) {

      var listDrawn: List[menuItem] =
        itList.slice(startDrawingAt, startDrawingAt + length)

      batch.setColor(selCol)
      batch.draw(
        Square,
        (location.x - (lX / 2)) * Pandaemonium.screenPixel,
        (location.y - ((selected * 2) + 2 - startDrawingAt)) * Pandaemonium.screenPixel,
        lX * Pandaemonium.screenPixel,
        Pandaemonium.screenPixel * 2
      )

      listDrawn.zipWithIndex.foreach({ case (item, index) =>
        val color = if (index + startDrawingAt != selected) selCol else unSelCol
        Text.draw(
          batch,
          Text.mediumFont,
          color,
          item.name,
          layout =>
            (
              (location.x) * Pandaemonium.screenPixel - layout.width / 2,
              (location.y - index * 2) * Pandaemonium.screenPixel
            )
        )
      })
    }
  }
}
case class menuItem(var name: String, use: () => Unit) {}
