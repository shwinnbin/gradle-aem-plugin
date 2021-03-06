package com.cognifide.gradle.aem.internal.notifier

import com.cognifide.gradle.aem.api.AemNotifier
import dorkbox.notify.Notify
import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import javax.imageio.ImageIO

class DorkboxNotifier(val project: Project, val configurer: Notify.() -> Unit) : Notifier {

    override fun notify(title: String, text: String, level: LogLevel) {
        try {
            Notify.create()
                    .image(ImageIO.read(javaClass.getResourceAsStream(AemNotifier.IMAGE_PATH)))
                    .apply(configurer)
                    .apply {
                        title(title)
                        text(StringUtils.replace(text, "\n", "<br>"))
                    }
                    .show()
        } catch (e: Exception) {
            project.logger.debug("Cannot show system notification", e)
        }
    }

}