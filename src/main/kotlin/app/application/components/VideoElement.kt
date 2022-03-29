package app.application.components

import app.application.controller.VideoDetailsController
import app.application.utils.ComponentUtils.Companion.loadComponent
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import org.springframework.beans.factory.annotation.Autowired

abstract class VideoElement : AnchorPane() {
    @FXML
    protected lateinit var imgThumbnail: ImageView

    @FXML
    protected lateinit var lblVideoTitle: Label

    @FXML
    protected lateinit var chkIgnore: CheckBox

    @Autowired
    protected lateinit var videoDetailsController: VideoDetailsController


    init {
        loadComponent(this)
    }

    abstract fun loadVideoDetails()

    abstract fun mouseClicked(event: MouseEvent);

}