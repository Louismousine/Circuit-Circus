
import circuitGUI.Sandbox;
import circuitGUI.Theme;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainScreen extends Application {

        static Pane mainPane;
        static MediaPlayer backgroundMusic;
        static MediaPlayer buttonHover;
        static Scene scene;
        static Stage primaryStage;
        static Options options;

        @Override
        public void start(Stage primaryStage) throws Exception {
                this.primaryStage = primaryStage;
                mainPane = new Pane();
                Media media = new Media(MainScreen.class.getClassLoader().getResource("resources/backgroundMusic.mp3").toString());
                Media mediaButt = new Media(MainScreen.class.getClassLoader().getResource("resources/buttonHover.mp3").toString());
                buttonHover = new MediaPlayer(mediaButt);
                buttonHover.setCycleCount(1);
                buttonHover.setVolume(1);
                backgroundMusic = new MediaPlayer(media);
                backgroundMusic.setCycleCount(Integer.MAX_VALUE);
                backgroundMusic.setVolume(0.1);
                backgroundMusic.play();
                scene = new Scene(mainPane, 500, 300);
                ImageView background = new ImageView(MainScreen.class.getClassLoader().getResource("resources/MainScreen.png").toString());
                background.fitHeightProperty().bind(scene.heightProperty());
                background.fitWidthProperty().bind(scene.widthProperty());
                mainPane.getChildren().add(background);
                mainPane.prefWidthProperty().bind(scene.widthProperty());
                mainPane.prefHeightProperty().bind(scene.heightProperty());
                options = new Options(primaryStage);
                paintStage(primaryStage);
                primaryStage.setScene(scene);
                scene.getStylesheets().add("Style/style.css");
                System.out.println("");
                primaryStage.getIcons().add(new Image("resources/Icon.png"));
                primaryStage.setMaximized(true);
                primaryStage.setResizable(true);
                primaryStage.show();
        }

        public static void paintStage(Stage primaryStage) throws Exception {

                Button optionsButton = new Button("", new ImageView(MainScreen.class.getClassLoader().getResource("resources/optionsButton.png").toString()));
                optionsButton.layoutXProperty().bind(scene.widthProperty().multiply(0.6));
                optionsButton.layoutYProperty().bind(scene.heightProperty().multiply(0.7));
                optionsButton.scaleXProperty().bind(scene.widthProperty().multiply(0.0008));
                optionsButton.scaleYProperty().bind(scene.heightProperty().multiply(0.001));
                Button credits = new Button("", new ImageView(MainScreen.class.getClassLoader().getResource("resources/creditButton.png").toString()));
                credits.layoutXProperty().bind(scene.widthProperty().multiply(0.2));
                credits.layoutYProperty().bind(scene.heightProperty().multiply(0.7));
                credits.scaleXProperty().bind(scene.widthProperty().multiply(0.0008));
                credits.scaleYProperty().bind(scene.heightProperty().multiply(0.001));
                Button challenge = new Button("", new ImageView(MainScreen.class.getClassLoader().getResource("resources/challengeButton.png").toString()));
                challenge.layoutXProperty().bind(scene.widthProperty().multiply(0.2));
                challenge.layoutYProperty().bind(scene.heightProperty().multiply(0.5));
                challenge.scaleXProperty().bind(scene.widthProperty().multiply(0.0008));
                challenge.scaleYProperty().bind(scene.heightProperty().multiply(0.001));
                Button sandbox = new Button("", new ImageView(MainScreen.class.getClassLoader().getResource("resources/sandboxButton.png").toString()));
                sandbox.layoutXProperty().bind(scene.widthProperty().multiply(0.6));
                sandbox.layoutYProperty().bind(scene.heightProperty().multiply(0.5));
                sandbox.scaleXProperty().bind(scene.widthProperty().multiply(0.0008));
                sandbox.scaleYProperty().bind(scene.heightProperty().multiply(0.001));
                Button exit = new Button("", new ImageView(MainScreen.class.getClassLoader().getResource("resources/exit.png").toString()));
                exit.layoutXProperty().bind(scene.widthProperty().multiply(0.4));
                exit.layoutYProperty().bind(scene.heightProperty().multiply(0.85));
                exit.scaleXProperty().bind(scene.widthProperty().multiply(0.0008));
                exit.scaleYProperty().bind(scene.heightProperty().multiply(0.001));
                mainPane.getChildren().addAll(optionsButton, credits, challenge, sandbox, exit);
                buttonHover.setVolume(1.5);
                optionsButton.setOnAction(e -> {
                        options.createStage();

                });

                for (Button b : new Button[]{optionsButton, credits, challenge, sandbox, exit}) {
                        b.setOnMouseEntered(e
                                -> {
                                buttonHover.stop();
                                buttonHover.play();
                        });

                }

                credits.setOnAction(e -> new Credits(mainPane));
                Challenge challengePane = new Challenge();
                challenge.setOnAction(e -> {
                        mainPane.getChildren().clear();
                        ImageView background = new ImageView(MainScreen.class.getClassLoader().getResource("resources/backgroundOG.jpg").toString());
                        mainPane.getChildren().add(background);
                        background.fitWidthProperty().bind(MainScreen.scene.widthProperty());
                        background.fitHeightProperty().bind(MainScreen.scene.heightProperty());
                        try {
                                challengePane.displaySelection(mainPane);
                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                });
                sandbox.setOnAction(e ->
				{
                        Sandbox.sandboxes.add(null);
                        Sandbox sandyhook = new Sandbox();
                        primaryStage.hide();
						
                        sandyhook.setOnHiding(ex -> primaryStage.show());
                        sandyhook.setMaximized(true);
						sandyhook.show();

                });
                exit.setOnAction((ActionEvent e) -> {

                        System.exit(0);
                });
        }

        public static void main(String[] args) {

                new Theme();

                Application.launch(args);

        }

        public static MediaPlayer music() {
                return backgroundMusic;
        }
}
