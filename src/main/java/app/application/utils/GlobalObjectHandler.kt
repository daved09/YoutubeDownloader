package app.application.utils;

import javafx.application.HostServices;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class GlobalObjectHandler {

	HostServices hostServices;


}
