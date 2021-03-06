package app.application;

import app.MyApp;
import app.application.controller.main_window.MainWindowController;
import app.application.utils.GlobalObjectHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class YoutubeDownloader extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        this.applicationContext = new SpringApplicationBuilder().sources(MyApp.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        setupHostServices();
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainWindowController.class);
        Scene scene = new Scene(root);
        stage.setTitle("Youtube Downloader");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        applicationContext.close();
        Platform.exit();
        System.exit(0);
    }

    private void setupHostServices(){
        GlobalObjectHandler globalObjectHandler = applicationContext.getBean(GlobalObjectHandler.class);
        globalObjectHandler.setHostServices(getHostServices());
    }

}
