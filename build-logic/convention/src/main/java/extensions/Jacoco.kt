package extensions

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.util.Locale

// Comando para toda a aplicacao ./gradlew jacocoTestReport
// Comando para modulo indivudual ./gradlew :module:testDebugUnitTest

/**
 * ./gradlew connectedCheck testar toda a aplicacao
 * ./gradlew testDebugUnitTest testar testes de unidade
 * ./gradlew connectedDebugAndroidTest testar testes instrumentados
 */

private val coverageExclusions =
    listOf(
        // Android
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
    )

private fun String.capitalize() =
    replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }

internal fun Project.configureJacoco(androidComponentsExtension: AndroidComponentsExtension<*, *, *>) {
    configure<JacocoPluginExtension> {
        toolVersion = "0.8.8"

        val jacocoTestReport = tasks.create("jacocoTestReport")

        androidComponentsExtension.onVariants { variant ->
            val unitTestTaskName = "test${variant.name.capitalize()}UnitTest"

            val reportTask =
                tasks.register("jacoco${unitTestTaskName.capitalize()}Report", JacocoReport::class) {
                    dependsOn(unitTestTaskName)

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                    }

                    classDirectories.setFrom(
                        fileTree("${layout.buildDirectory}/tmp/kotlin-classes/${variant.name}") {
                            exclude(coverageExclusions)
                        },
                    )

                    sourceDirectories.setFrom(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
                    executionData.setFrom(file("${layout.buildDirectory}/jacoco/$unitTestTaskName.exec"))
                }

//            jacocoTestReport.dependsOn(reportTask)

            // Configuração para testes de UI (Connected Android Tests)
            val uiTestTaskName = "connectedAndroidTest"

            val uiReportTask =
                tasks.register("jacoco${variant.name.capitalize()}Report", JacocoReport::class) {
                    dependsOn(uiTestTaskName)

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                    }

                    classDirectories.setFrom(
                        fileTree("${layout.buildDirectory}/tmp/kotlin-classes/${variant.name}") {
                            exclude(coverageExclusions)
                        },
                    )

                    sourceDirectories.setFrom(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
                    executionData.setFrom(file("${layout.buildDirectory}/jacoco/$uiTestTaskName.exec"))
                }

            jacocoTestReport.dependsOn(reportTask, uiReportTask)
        }

        tasks.withType<Test>().configureEach {
            configure<JacocoTaskExtension> {
                // Required for JaCoCo + Robolectric
                // https://github.com/robolectric/robolectric/issues/2230
                // TODO: Consider removing if not we don't add Robolectric
                isIncludeNoLocationClasses = true

                // Required for JDK 11 with the above
                // https://github.com/gradle/gradle/issues/5184#issuecomment-391982009
                excludes = listOf("jdk.internal.*")
            }
        }
    }
}
