plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.dokka)
    id("module.publication")
}

kotlin {
    js(IR) {
        browser()
        binaries.library()
    }
}
