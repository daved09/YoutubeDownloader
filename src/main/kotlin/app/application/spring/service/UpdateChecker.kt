package app.application.spring.service

import app.application.data.VersionProperties
import app.application.utils.VersionChecker
import org.kohsuke.github.GHRepository
import org.kohsuke.github.GitHub
import org.springframework.stereotype.Service

@Service
class UpdateChecker(val versionProperties: VersionProperties) {

    private val versionChecker = VersionChecker()

    private var github: GitHub? = null
    private var repository: GHRepository? = null
    private var latestReleaseVersion: String? = null

    init {
        try{
            github = GitHub.connectAnonymously()
            repository = github?.getRepository("daved09/YoutubeDownloader");
            getReleaseVersion()
        }
        catch (ignored: Exception){}
    }

    fun hasNewUpdate(): Boolean{
        val version = versionProperties.version.get().split(".")
        val gitVersion = latestReleaseVersion?.split(".")
        return versionChecker.isVersionHigher(version, gitVersion)
    }

    private fun getReleaseVersion() {
        latestReleaseVersion = repository?.latestRelease?.tagName
    }

}