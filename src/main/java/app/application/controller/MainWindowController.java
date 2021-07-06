package app.application.controller;

import app.application.utils.*;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
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
    private TextField txtDownloadLink;

    @FXML
    private ImageView imgThumbnail;

    @FXML
    private AnchorPane videoPane;

    @FXML
    private AnchorPane playlistPanel;

    @FXML
    private Label lblVideoTitle;

    @FXML
    private ComboBox<String> boxQuality;

    @FXML
    private TextArea txtDescription;

    @FXML
    private ProgressBar downloadProgress;

    @FXML
    private CheckBox chkAudioOnly;

    @FXML
    private TextField txtPlaylistLink;

    @FXML
    private ListView listPlaylist;

    @FXML
    private Label txtPlaylistTitle;

    @FXML
    private TextField txtDownloadPath;

    @Autowired
    private YoutubeVideoDownloadService youtubeVideoDownloadService;

    @Autowired
    private YoutubePlaylistDownloadService youtubePlaylistDownloadService;

    @Autowired
    private YoutubeIdExtractor youtubeIdExtractor;

    @Autowired
    private UserConfigHandler userConfigHandler;

    @FXML
    public void initialize(){
        youtubeVideoDownloadService.setYoutubeDownloadListener(new YoutubeDownloadListener(downloadProgress));
        videoPane.setVisible(false);
        playlistPanel.setVisible(false);
        txtDownloadPath.textProperty().bindBidirectional(userConfigHandler.getUserConfig().getDownloadDir());
    }


    public void btnSearch_click(){
        VideoDetails details = youtubeVideoDownloadService.getVideoDetails(youtubeIdExtractor.getVideoIdFromLink(txtDownloadLink.getText()));
        imgThumbnail.setImage(new Image(details.thumbnails().get(0).split("\\?sqp")[0]));
        lblVideoTitle.setText(details.title());
        refreshQualityBox(youtubeVideoDownloadService.getQualityLabels());
        txtDescription.setText(details.description());
        videoPane.setVisible(true);
    }

    public void btnDownloadVideo_click(){
        if(chkAudioOnly.isSelected()){
            youtubeVideoDownloadService.downloadAudioOnlyAsync();
        }
        else{
            youtubeVideoDownloadService.downloadVideoAsync(boxQuality.getSelectionModel().getSelectedItem());
        }
    }

    public void btnSearchPlaylist_click(){
        String listLink = "https://www.youtube.com/playlist?list=PLwjcmSTfw6SAe2LAvJLnfjUh4wxTw2Ncc";
//        String listLink = "https://www.youtube.com/playlist?list=PLNmsVeXQZj7o46LI06XkxAqcg4Ucm7pwn";
        txtPlaylistTitle.setText(youtubePlaylistDownloadService.getPlaylistDetails(youtubeIdExtractor.getPlayListIdFromLink(listLink)).title());
        for (PlaylistVideoDetails video : youtubePlaylistDownloadService.getYoutubePlaylist().videos()) {
            
        }
        playlistPanel.setVisible(true);
    }

    public void btnSave_click(){
        userConfigHandler.writeConfig();
    }


    private void refreshQualityBox(List<String> listWithOptions){
        boxQuality.getItems().clear();
        boxQuality.getItems().addAll(listWithOptions);
        boxQuality.getSelectionModel().select(0);
    }

}
