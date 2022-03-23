package app.application.exception.controller

import app.application.utils.DialogManager
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ExceptionController(private val dialogManager: DialogManager) {

    @PostConstruct
    fun error() {
        Thread.setDefaultUncaughtExceptionHandler { thread: Thread?, throwable: Throwable? -> dialogManager.openExceptionDialog(throwable as Exception?) }
    }

}