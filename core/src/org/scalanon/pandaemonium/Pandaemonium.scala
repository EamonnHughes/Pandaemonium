package org.scalanon.pandaemonium

import com.badlogic.gdx.Application.ApplicationType
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.{ApplicationAdapter, Gdx, Input}
import org.scalanon.pandaemonium.home.Home
import org.scalanon.pandaemonium.util.{GarbageCan, TextureWrapper}

import java.util.Properties

class Pandaemonium extends ApplicationAdapter {
  import Pandaemonium.garbage

  private var batch: PolygonSpriteBatch = _
  private var scene: Scene              = _

  override def create(): Unit = {

    Gdx.input.setCatchKey(Input.Keys.BACK, true)

    Prefs.loadPreferences()

    batch = garbage.add(new PolygonSpriteBatch())

    val properties = new Properties
    val is         = Pandaemonium.getClass.getResourceAsStream("/app.properties")
    if (is ne null) {
      properties.load(is)
      is.close()
    }
    Pandaemonium.version = properties.getProperty("version", "Unknown")
    Pandaemonium.key = properties.getProperty("key", "unset")

    Text.loadFonts()

    setScene(new Home)
  }

  override def render(): Unit = {
    val delta = Gdx.graphics.getDeltaTime
    scene.update(delta) foreach setScene
    ScreenUtils.clear(0, 0, 0, 1)
    batch.begin()
    scene.render(batch)
    batch.end()
  }

  override def dispose(): Unit = {
    garbage.dispose()
    AssetLoader.clear()
  }

  private def setScene(newScene: Scene): Unit = {
    scene = newScene
    Gdx.input.setInputProcessor(scene.init())
  }

}

object Pandaemonium {
  implicit val garbage: GarbageCan = new GarbageCan

  var version: String = _
  var key: String     = _

  val screenPixel =
    ((Geometry.ScreenWidth min Geometry.ScreenHeight) / 25).floor

  var walkPlayer: TextureWrapper = _

  var globalHigh: Int = _

  def mobile: Boolean = isMobile(Gdx.app.getType)

  private def isMobile(tpe: ApplicationType) =
    tpe == ApplicationType.Android || tpe == ApplicationType.iOS
}
