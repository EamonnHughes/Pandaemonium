package org.scalanon.pandaemonium

import com.badlogic.gdx.backends.lwjgl3.{
  Lwjgl3Application,
  Lwjgl3ApplicationConfiguration
}
import de.damios.guacamole.gdx.StartOnFirstThreadHelper

object DesktopLauncher extends App {
  StartOnFirstThreadHelper.executeIfJVMValid(() => {
    val config = new Lwjgl3ApplicationConfiguration
    config.setForegroundFPS(60)
    config.setWindowedMode(2880, 1800)
    new Lwjgl3Application(new Pandaemonium, config)
  })
}
