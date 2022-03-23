package app.application.config

import app.application.config.adapter.BooleanPropertyAdapter
import app.application.config.adapter.StringPropertyAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javafx.beans.property.BooleanProperty
import javafx.beans.property.StringProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JsonConfig {

    @Bean
    open fun gsonConfigurator(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setPrettyPrinting()
        gsonBuilder.registerTypeAdapter(StringProperty::class.java, StringPropertyAdapter())
        gsonBuilder.registerTypeAdapter(BooleanProperty::class.java, BooleanPropertyAdapter())
        return gsonBuilder.create()
    }
}