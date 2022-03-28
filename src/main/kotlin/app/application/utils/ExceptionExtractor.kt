package app.application.utils

import org.springframework.stereotype.Service
import java.lang.reflect.InvocationTargetException

@Service
class ExceptionExtractor {

    fun getTargetOfException(exception: Exception?): Throwable? {
        val innerException: Exception? = exception?.cause as Exception?
        return if (!isTargetException(innerException)) {
            innerException?.cause
        } else (innerException as InvocationTargetException?)?.targetException
    }

    private fun isTargetException(exception: Exception?): Boolean {
        return exception is InvocationTargetException
    }
}