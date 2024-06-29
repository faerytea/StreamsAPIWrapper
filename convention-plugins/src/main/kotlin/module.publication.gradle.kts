import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
    id("com.vanniktech.maven.publish")
}

publishing {
    // Configure all publications
    publications.withType<MavenPublication> {

        groupId = "name.faerytea.kjs.wrapper.streams"

        // Provide artifacts information required by Maven Central
        pom {
            name.set("Kotlin/JS Streams API")
            version = "0.0.1"
            description.set("Bunch of external definitions of Streams API")
            url.set("https://github.com/faerytea/StreamsAPIWrapper")

            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0")
                }
            }
            developers {
                developer {
                    id.set("faerytea")
                    name.set("Valery Maevsky")
                    email.set("faerytea@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/faerytea/StreamsAPIWrapper")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

mavenPublishing {
    // publishing to https://central.sonatype.com/
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = false)

    signAllPublications()

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        )
    )
}
