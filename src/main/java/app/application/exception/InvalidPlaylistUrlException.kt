package app.application.exception

import java.lang.Exception
import java.lang.RuntimeException

class InvalidPlaylistUrlException : Exception {
    constructor(url: String) : super("Invalid playlist url: $url")
    constructor(cause: Exception?, url: String) : super("Invalid playlist url: $url", cause)
}