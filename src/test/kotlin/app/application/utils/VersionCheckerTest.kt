package app.application.utils

import org.junit.Assert.*
import org.junit.Test

class VersionCheckerTest{

    @Test
    fun testGitVersionIsHigher(){
        val gitVersion = listOf("0", "1", "2")
        val version = listOf("0", "0", "2")

        val versionChecker = VersionChecker()

        assertTrue(versionChecker.isVersionHigher(version, gitVersion))
    }

    @Test
    fun testGitVersionIsLower(){
        val gitVersion = listOf("0", "1", "2")
        val version = listOf("0", "2", "2")

        val versionChecker = VersionChecker()

        assertFalse(versionChecker.isVersionHigher(version, gitVersion))
    }

    @Test
    fun testGitVersionIsEqual(){
        val gitVersion = listOf("0", "1", "2")
        val version = listOf("0", "1", "2")

        val versionChecker = VersionChecker()

        assertFalse(versionChecker.isVersionHigher(version, gitVersion))
    }

}