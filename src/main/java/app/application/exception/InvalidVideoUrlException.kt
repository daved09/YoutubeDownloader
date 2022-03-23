package app.application.exception

import java.lang.Exception
import java.lang.RuntimeException

class InvalidVideoUrlException : Exception {
    constructor(url: String) : super("Invalid url: $url")
    constructor(e: Exception?, url: String) : super("Invalid url: $url", e)
}