package app.application.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import app.application.utils.DownloadExecutorHandler.DownloaderTask
import java.lang.Runnable
import kotlin.Throws
import app.application.exception.CantAbortDownloadException
import java.util.concurrent.TimeUnit
import app.application.exception.ExecutorTerminationException
import app.application.utils.GlobalValues
import java.lang.InterruptedException

class DownloadExecutorHandler {
    private var downloadExecutorService: ExecutorService

    init {
        downloadExecutorService = Executors.newSingleThreadExecutor()
    }

    fun executeTask(downloaderTask: DownloaderTask) {
        createExecutor()
        downloadExecutorService.execute { downloaderTask.execute() }
    }

    private fun createExecutor() {
        if (downloadExecutorService.isShutdown || downloadExecutorService.isTerminated) {
            downloadExecutorService = Executors.newSingleThreadExecutor()
        }
    }

    @Throws(CantAbortDownloadException::class)
    fun killTask() {
        downloadExecutorService.shutdownNow()
        try {
            val terminationSuccess = downloadExecutorService.awaitTermination(10,
                    TimeUnit.SECONDS)
            if (!terminationSuccess) {
                throw ExecutorTerminationException(GlobalValues.DOWNLOAD_EXECUTOR_TERMINATION_ERROR)
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw CantAbortDownloadException(e)
        }
    }

    interface DownloaderTask {
        fun execute()
    }
}