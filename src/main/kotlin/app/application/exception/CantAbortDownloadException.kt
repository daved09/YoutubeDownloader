package app.application.exception

import java.lang.Exception
import java.lang.RuntimeException

class CantAbortDownloadException(e: Exception?) : Exception(e)