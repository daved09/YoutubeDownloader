package app.application.exception

import java.lang.RuntimeException

class ExecutorTerminationException(message: String?) : RuntimeException(message)