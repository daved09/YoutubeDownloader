package app.application.spring.service

import app.application.data.VersionProperties
import app.application.utils.VersionChecker
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.springframework.stereotype.Service

@Service
class UpdateChecker(private val versionProperties: VersionProperties) {

    private val versionChecker = VersionChecker()

    private var latestReleaseVersion: String? = null


    fun hasNewUpdate(): Boolean{
        getReleaseVersion()
        val version = versionProperties.version.get().split(".")
        val gitVersion = latestReleaseVersion?.split(".")
        return versionChecker.isVersionHigher(version, gitVersion)
    }

    private fun getReleaseVersion() {
        val github = GitHub.connectAnonymously()
        val repository = github?.getRepository("daved09/YoutubeDownloader")
        latestReleaseVersion = repository?.latestRelease?.tagName
    }

}