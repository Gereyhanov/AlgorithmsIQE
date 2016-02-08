package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneBuilder;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setScene(SceneBuilder.create()
                .root(root)
                .width(1600).height(900)
               //  .stylesheets(Main.class.getResource("style/CurveFittedChart.css").toExternalForm())
                .build()
        );

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
