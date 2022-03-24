package app.application.data

import javafx.beans.property.StringProperty
import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:version.properties")
@ConfigurationProperties(prefix = "downloader")
@Data
open class VersionProperties {
    lateinit var version: StringProperty
}