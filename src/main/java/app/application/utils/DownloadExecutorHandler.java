package app.application.utils;

import app.application.exception.CantAbortDownloadException;
import app.application.exception.ExecutorTerminationException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DownloadExecutorHandler {

	private ExecutorService downloadExecutorService;

	public DownloadExecutorHandler() {
		this.downloadExecutorService = Executors.newSingleThreadExecutor();
	}

	public void executeTask(DownloaderTask downloaderTask){
		createExecutor();
		downloadExecutorService.execute(downloaderTask::execute);
	}

	private void createExecutor() {
		if(downloadExecutorService.isShutdown() || downloadExecutorService.isTerminated()){
			downloadExecutorService = Executors.newSingleThreadExecutor();
		}
	}

	public void killTask() throws CantAbortDownloadException {
		downloadExecutorService.shutdownNow();
		try {
			boolean terminationSuccess = downloadExecutorService.awaitTermination(10,
							TimeUnit.SECONDS);
			if(!terminationSuccess){
				throw new ExecutorTerminationException(GlobalValues.DOWNLOAD_EXECUTOR_TERMINATION_ERROR);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CantAbortDownloadException(e);
		}
	}

	public interface DownloaderTask {
		void execute();
	}

}
