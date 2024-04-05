package app.application.utils

import app.application.exception.CantAbortDownloadException
import app.application.exception.ExecutorTerminationException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
                throw ExecutorTerminationException(DOWNLOAD_EXECUTOR_TERMINATION_ERROR)
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