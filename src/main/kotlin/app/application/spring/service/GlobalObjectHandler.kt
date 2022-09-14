package app.application.spring.service

import javafx.application.HostServices
import lombok.Data
import org.springframework.stereotype.Service

@Data
@Service
class GlobalObjectHandler {
    var hostServices: HostServices? = null
}