package app.application.config

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
@ConfigurationPropertiesBinding
class StringPropertySpringConverter : Converter<String?, StringProperty> {

    override fun convert(source: String?): StringProperty {
        return SimpleStringProperty(source)
    }
}