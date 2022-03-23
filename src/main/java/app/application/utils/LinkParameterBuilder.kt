package app.application.utils

import org.springframework.stereotype.Service
import java.util.HashMap

@Service
class LinkParameterBuilder {

    fun buildParameterMap(query: String?): Map<String, String> {
        val parameterMap: MutableMap<String, String> = HashMap()
        if (query != null) {
            val params = query.split("&")
            for (param in params) {
                val split = param.split("=")
                parameterMap[split[0]] = split[1]
            }
        }
        return parameterMap
    }

}