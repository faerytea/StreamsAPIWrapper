import kotlinx.browser.document
import kotlinx.browser.window
import name.faerytea.kjs.wrapper.streams.*
import org.w3c.dom.events.Event
import kotlin.js.Promise

@JsName("simpleRandomStrings")
fun simpleRandomStrings() {
    object {
        val list1 = document.querySelector(".input ul")!!
        val list2 = document.querySelector(".output ul")!!
        val para = document.querySelector("p")!!
        val button = document.querySelector("button")!!

        val chars = buildString {
            ('A'..'Z').forEach { append(it) }
            ('a'..'z').forEach { append(it) }
            ('0'..'9').forEach { append(it) }
            append("!@#$%^&*-_=+")
        }

        fun randomString() = buildString {
            repeat(8) { append(chars.random()) }
        }

        var result = ""

        fun readStream() {
            val reader = stream.getDefaultReader()
            var received = 0
            fun processText(rr: ReadableStreamReadResult): Promise<Unit> {
                if (rr.done) {
                    console.log("Stream complete")
                    para.textContent = result
                    return Promise.resolve(Unit)
                }
                val chunk = rr.value as String
                received += chunk.length
                val li = document.createElement("li")
                li.textContent = "Read $received characters so far. Current chunk = $chunk"
                list2.appendChild(li)
                result += chunk

                return reader.read().then(::processText).unsafeCast<Promise<Unit>>()
            }
            reader.read().then(::processText)
        }

        val stream = ReadableStream(
            object : ReadableStreamDefaultSource() {
                var interval: Int = 0

                override fun start(controller: ReadableStreamDefaultController) {
                    interval = window.setInterval(
                        handler = {
                            val str = randomString()
                            controller.enqueue(str)
                            val li = document.createElement("li")
                            li.textContent = str
                            list1.appendChild(li)
                        },
                        timeout = 1000,
                    )
                    button.addEventListener("click", { _: Event ->
                        window.clearInterval(interval)
                        readStream()
                        controller.close()
                    })
                }

                override fun cancel(reason: String) {
                    window.clearInterval(interval)
                }
            }
        )
    }
}