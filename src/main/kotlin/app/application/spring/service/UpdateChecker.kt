package app.application.spring.service

import app.application.data.VersionProperties
import app.application.utils.VersionChecker
import io.gitea.ApiClient
import io.gitea.Configuration
import io.gitea.api.RepositoryApi
import org.springframework.stereotype.Service

@Service
class UpdateChecker(val versionProperties: VersionProperties) {

    private val apiClient: ApiClient = Configuration.getDefaultApiClient()
    private val versionChecker = VersionChecker()

    private var repositoryApi: RepositoryApi? = null
    private var latestReleaseVersion: String? = null

    init {
        try{
            apiClient.basePath = "http://daluba.de:3000/api/v1"
            repositoryApi = RepositoryApi(apiClient)
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
        latestReleaseVersion = repositoryApi?.repoListReleases("Dave", "YoutubeDownloader_releases", false, false, 5, 1, 5)?.get(0)?.tagName
    }

}