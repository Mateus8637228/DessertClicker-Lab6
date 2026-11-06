package com.example.dessertclicker

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.FileInputStream

class DessertScenarioTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun salesAndRotation_keepCountersAndCurrentPrice() {
        rule.activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        rule.waitUntil(10000) { rule.activity.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT }
        repeat(5) { rule.onNodeWithContentDescription("Sell dessert").performClick() }
        rule.onNodeWithText("5").assertExists()
        rule.onNodeWithText("$25").assertExists()
        screenshot("dessert-portrait")
        val oldActivity = rule.activity
        rule.activityRule.scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
        rule.waitUntil(10000) { rule.activity !== oldActivity && rule.activity.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE }
        rule.onNodeWithText("5").assertExists()
        rule.onNodeWithText("$25").assertExists()
        screenshot("dessert-landscape")
        rule.onNodeWithContentDescription("Sell dessert").performClick()
        rule.onNodeWithText("6").assertExists()
        rule.onNodeWithText("$35").assertExists()
        rule.activityRule.scenario.recreate()
        rule.onNodeWithText("6").assertExists()
        rule.onNodeWithText("$35").assertExists()
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.uiAutomation.waitForIdle(1000, 10000)
        val file = File(instrumentation.targetContext.getExternalFilesDir("evidence"), "$name.png")
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
        bitmap.recycle()
        instrumentation.uiAutomation.executeShellCommand("cp ${file.absolutePath} /data/local/tmp/lab6-$name.png").use { descriptor ->
            FileInputStream(descriptor.fileDescriptor).use { it.readBytes() }
        }
    }
}
