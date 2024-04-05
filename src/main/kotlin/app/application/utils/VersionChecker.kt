package app.application.utils

class VersionChecker {

    fun isVersionHigher(currentVersion: List<String>, gitVersion: List<String>?): Boolean{
        gitVersion?.also {
            for (i in currentVersion.indices) {
                if (getAsInt(it[i]) > getAsInt(currentVersion[i])) {
                    return true
                }
                if (getAsInt(it[i]) < getAsInt(currentVersion[i])) {
                    return false
                }
            }
        }
        return false
    }

    private fun getAsInt(version: String) : Int {
        return version.toInt()
    }

}