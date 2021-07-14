package app.application.controller.main_window;

import app.application.data.VersionProperties;
import app.application.utils.*;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView("/views/MainWindow.fxml")
public class MainWindowController {


    @FXML
    private AnchorPane playlistPanel;

    @FXML
    private TextField txtPlaylistLink;

    @FXML
    private ListView<String> listPlaylist;

    @FXML
    private Label txtPlaylistTitle;

    @FXML
    private Label lblDownloadProgress;

    @FXML
    private TextField txtDownloadPath;


    @FXML
    private Button btnSearchPlaylist;

    @FXML
    private TextField txtVerion;


    @Autowired
    private YoutubePlaylistDownloadService youtubePlaylistDownloadService;

    @Autowired
    private YoutubeIdExtractor youtubeIdExtractor;

    @Autowired
    private UserConfigHandler userConfigHandler;

    @Autowired
    private VersionProperties versionProperties;

    @FXML
    public void initialize(){
        youtubePlaylistDownloadService.setLabel(lblDownloadProgress);
        txtDownloadPath.textProperty().bindBidirectional(userConfigHandler.getUserConfig().getDownloadDir());
        btnSearchPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()));
        txtVerion.textProperty().bind(versionProperties.getVersion());
    }



    public void btnSearchPlaylist_click(){
        txtPlaylistTitle.setText(youtubePlaylistDownloadService.getPlaylistDetails(
                youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText())).title());
        listPlaylist.getItems().addAll(youtubePlaylistDownloadService.getVideoTitles());
        playlistPanel.setVisible(true);
    }

    public void btnPlaylistDownload_click(){
        new Thread(() -> youtubePlaylistDownloadService.downloadPlaylist()).start();
    }

    public void btnSave_click(){
        userConfigHandler.writeConfig();
    }


}
