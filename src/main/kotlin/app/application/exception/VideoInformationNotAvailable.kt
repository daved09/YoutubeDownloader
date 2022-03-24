package app.application.exception

import java.lang.RuntimeException

class VideoInformationNotAvailable : RuntimeException("The requesting video information are not available.")