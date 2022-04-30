package app.application.factories

import app.application.data.UserConfig
import org.springframework.stereotype.Service

@Service
class ConfigurationFactory {
    fun createDefaultConfiguration(): UserConfig {
        return UserConfig()
    }
}