package app.application.utils

class VersionChecker {

    fun isVersionHigher(currentVersion: List<String>, gitVersion: List<String>?): Boolean{
        for (i in currentVersion.indices) {
            if (getAsInt(gitVersion!![i]) > getAsInt(currentVersion[i])) {
                return true
            }
            if (getAsInt(gitVersion[i]) < getAsInt(currentVersion[i])) {
                return false
            }
        }
        return false
    }

    private fun getAsInt(version: String) : Int {
        return version.toInt()
    }

}