import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.dom.addClass
import kotlinx.dom.appendText
import kotlinx.dom.removeClass
import name.faerytea.kjs.wrapper.streams.*
import name.faerytea.kjs.wrapper.streams.compression.DecompressionStream
import name.faerytea.kjs.wrapper.streams.compression.GZIP
import org.w3c.dom.HTMLInputElement
import org.w3c.files.get

fun unGzip() {
    val out = document.getElementById("content")!!
    val inp = document.getElementById("input") as HTMLInputElement
    val td = js("new TextDecoder();")

    inp.onchange = {
        val file = it.target.unsafeCast<HTMLInputElement>().files!![0]!!
        console.log(file)
        if (file.type == "application/gzip") {
            console.log("yup")
            val ds = DecompressionStream(GZIP)
            out.innerHTML = ""
            file.stream()
                .pipeThrough(ds)
                .pipeTo(
                    WritableStream(
                        object : WritableStreamSink {
                            override fun start(controller: WritableStreamDefaultController) {
                                console.log("started")
                                out.removeClass("ready", "dead")
                                out.addClass("loading")
                            }

                            override fun write(chunk: dynamic, controller: WritableStreamDefaultController) {
                                console.log("got chunk:")
                                console.log(chunk)
                                out.appendText(td.decode(chunk).unsafeCast<String>())
                            }

                            override fun close(controller: WritableStreamDefaultController) {
                                console.log("close called")
                                out.removeClass("loading")
                                out.addClass("ready")
                            }

                            override fun abort(reason: String) {
                                console.log("abort: $reason")
                                out.removeClass("loading")
                                out.addClass("dead")
                                window.alert(reason)
                            }
                        }
                    )
                )
                .then { console.log("done") }
        } else {
            window.alert("$file is not a GZIP package (it is ${file.type})")
        }
    }
}