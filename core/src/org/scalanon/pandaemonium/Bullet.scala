package org.scalanon.pandaemonium

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import org.scalanon.pandaemonium.game.Game
import org.scalanon.pandaemonium.util.TextureWrapper

case class Bullet(game: Game) extends Entity {
  var loc: Vec2        = Vec2(0, 0)
  loc.x = game.player.loc.x
  loc.y = game.player.loc.y
  val direction: Float = game.player.dir

  def Projectile: TextureWrapper = AssetLoader.image("square.png")

  def draw(batch: PolygonSpriteBatch): Unit = {
    batch.setColor(Color.GRAY)
    batch.draw(
      Projectile,
      loc.x - Pandaemonium.screenPixel,
      loc.y / 2 - Pandaemonium.screenPixel / 2 + Pandaemonium.screenPixel * 32,
      Pandaemonium.screenPixel * 2,
      Pandaemonium.screenPixel
    )
    batch.setColor(Color.WHITE)
  }
  def update(delta: Float) = {
    loc.x += Math.cos(direction).toFloat * delta * 700
    loc.y += Math.sin(direction).toFloat * delta * 700
    game.debris.foreach(debri => {
      if (debri.loc.manhattanDistance(loc) / Pandaemonium.screenPixel <= 16) {
        game.debris = game.debris.filterNot(d => d eq debri)
        game.bullets = game.bullets.filterNot(b => b eq this)
        game.player.stone += 1
      }
    })
  }
  def y: Float = loc.y
  def x: Float = loc.x
}
