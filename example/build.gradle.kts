plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        jsMain {
            dependencies {
//                implementation(project(":kjs-streams-api"))
                implementation("name.faerytea.kjs.wrapper.streams:kjs-streams-api-js:0.0.1")
            }
        }
    }
}
