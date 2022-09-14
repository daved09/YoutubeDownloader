package app.application.spring.service.data

import com.github.kiulian.downloader.YoutubeDownloader

abstract class YoutubeDataService(val youtubeDownloader: YoutubeDownloader)