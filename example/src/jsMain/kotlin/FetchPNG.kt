import kotlinx.browser.document
import name.faerytea.kjs.wrapper.streams.*
import org.w3c.dom.Image
import org.w3c.dom.url.URL
import org.w3c.fetch.Response
import kotlin.js.Promise

external fun fetch(req: Any): Promise<Response>

fun pumpExample() {
    val image = document.getElementById("image") as Image
    fetch("./cat.png")
        .then {
            val reader = it.readableStream.getDefaultReader()
            return@then ReadableStream(
                object : ReadableStreamDefaultSource() {
                    fun pump(controller: ReadableStreamDefaultController): Promise<dynamic> = reader.read().then { rr ->
                        return@then if (rr.done) {
                            controller.close()
                        } else {
                            controller.enqueue(rr.value)
                            pump(controller)
                        }
                    }

                    override fun start(controller: ReadableStreamDefaultController): Any {
                        return pump(controller)
                    }
                }
            )
        }
        .then { Response(it) }
        .then { it.blob() }
        .then { URL.createObjectURL(it) }
        .then {
            image.src = it
            console.log(it)
        }
        .catch { console.error(it) }
}