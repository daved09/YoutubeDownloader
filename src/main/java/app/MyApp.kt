package app;


import app.application.YoutubeDownloader;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApp {

    public static void main(String[] args) {
        Application.launch(YoutubeDownloader.class, args);
    }

}
