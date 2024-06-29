import kotlinx.browser.document

fun main() {
    when {
        document.URL.endsWith("simple-random-strings.html") -> simpleRandomStrings()
        document.URL.endsWith("pump.html") -> pumpExample()
        document.URL.endsWith("ungzip.html") -> unGzip()
        else -> console.log("Special behavior not found")
    }
}

