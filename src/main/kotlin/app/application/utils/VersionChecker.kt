package app.application.utils

class VersionChecker {

    fun isVersionHigher(currentVersion: List<String>, gitVersion: List<String>?): Boolean{
        for (i in currentVersion.indices) {
            if (gitVersion!![i] > currentVersion[i]) {
                return true
            }
            if (gitVersion[i] < currentVersion[i]) {
                return false
            }
        }
        return false
    }

}