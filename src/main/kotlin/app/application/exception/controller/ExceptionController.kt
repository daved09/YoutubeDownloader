package app.application.exception.controller

import app.application.spring.service.DialogManager
import org.springframework.stereotype.Service

@Service
class ExceptionController(private val dialogManager: DialogManager) {

    fun error() {
        Thread.setDefaultUncaughtExceptionHandler { _: Thread?, throwable: Throwable? -> dialogManager.openExceptionDialog(throwable as Exception?) }
    }

}