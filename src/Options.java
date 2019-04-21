
import circuitGUI.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Options extends Stage {

        private double volume;
        private static boolean musicMute, soundFXMute;
        private Color color = Color.WHITE;

        //add imageviews
        private ImageView muteSound = new ImageView("resources/soundMute.png");
        private ImageView muteMusic = new ImageView("resources/musicMute.png");
        private ImageView unmuteSound = new ImageView("resources/soundUnmute.png");
        private ImageView unmuteMusic = new ImageView("resources/musicUnmute.png");
        MainScreen ms = new MainScreen();

        Options(Window owner) {
                volume = 0.1;
                musicMute = false;
                soundFXMute = false;
                muteSound.setFitHeight(100);
                muteSound.setFitWidth(100);
                unmuteSound.setFitHeight(100);
                unmuteSound.setFitWidth(100);
                muteMusic.setFitHeight(100);
                muteMusic.setFitWidth(100);
                unmuteMusic.setFitHeight(100);
                unmuteMusic.setFitWidth(100);

                this.initOwner(owner);
        }

        public void muteMusic() {
                ms.backgroundMusic.setMute(musicMute);
        }

        public void muteSoundFX() {
                ms.buttonHover.setMute(soundFXMute);
                Challenge.mute(soundFXMute);
        }

        public void changeBackground(ImageView img) {
        }

        void createStage() {

                StackPane pane = new StackPane();
                Scene scene = new Scene(pane);
                this.setScene(scene);
                ImageView background = new ImageView("resources/parchment.jpg");
                pane.getChildren().add(background);
                VBox optionContainer = new VBox();
                Label optionsLabel = new Label("Options");
                optionsLabel.setStyle("-fx-font-size:60");
                Label sound = new Label("Sound volume");
                sound.setStyle("-fx-font-size:30");
                Label selectBG = new Label("Select background");
                selectBG.setStyle("-fx-font-size:30");
                HBox muties = new HBox();
                HBox mutefx = new HBox();
                HBox mutes = new HBox();
                ColorPicker colorPick = new ColorPicker(color);
                Pane bgOG = new Pane();
                bgOG.setEffect(new InnerShadow(20, 0, 0, color));
                bgOG.setPrefSize(80, 80);
                bgOG.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                colorPick.setOnAction(e -> {
                        Image img = changeColor(bgOG, color = colorPick.getValue());
                        Theme.NORMAL_CELL_BG = new Background(new BackgroundImage(img, null, null, null, new BackgroundSize(0, 0, false, false, true, true)));
                });
                muties.setAlignment(Pos.CENTER);
                muties.setSpacing(40);
                muties.getChildren().addAll(mutes, mutefx);
                if (musicMute) {
                        mutes.getChildren().add(muteMusic);
                } else if (!musicMute) {
                        mutes.getChildren().add(unmuteMusic);
                }
                if (soundFXMute) {
                        mutefx.getChildren().add(muteSound);
                } else {
                        mutefx.getChildren().add(unmuteSound);
                }
                Slider volumeSlider = new Slider(0, 1, volume);
                HBox bg1 = new HBox();
                bg1.getChildren().addAll(bgOG, colorPick);
                bg1.setAlignment(Pos.CENTER);
                bg1.setSpacing(40);
                optionContainer.getChildren().addAll(optionsLabel, sound, volumeSlider, muties, selectBG, bg1);
                optionContainer.setSpacing(20);
                optionContainer.setAlignment(Pos.CENTER);
                pane.getChildren().add(optionContainer);

                volumeSlider.valueProperty().addListener(e -> {
                        MainScreen.music().setVolume(volumeSlider.getValue());
                        MainScreen.buttonHover.setVolume(volumeSlider.getValue());
                        Challenge.mp1.setVolume(volumeSlider.getValue());
                        Challenge.mp2.setVolume(volumeSlider.getValue());
                        volume = volumeSlider.getValue();
                });
                volumeSlider.setMaxSize(300, 100);
                mutes.setOnMouseClicked(e -> {
                        mutes.getChildren().clear();
                        if (musicMute) {
                                mutes.getChildren().add(unmuteMusic);
                        } else {
                                mutes.getChildren().add(muteMusic);
                        }

                        musicMute = !musicMute;
                        muteMusic();

                });
                mutefx.setOnMouseClicked(e -> {
                        mutefx.getChildren().clear();
                        if (soundFXMute) {
                                mutefx.getChildren().add(unmuteSound);
                        } else {
                                mutefx.getChildren().add(muteSound);
                        }

                        soundFXMute = !soundFXMute;
                        muteSoundFX();

                });

                this.setResizable(false);
                this.show();
        }

        private Image changeColor(Pane background, Color value) {
                background.setEffect(new InnerShadow(20, 0, 0, value));
                return background.snapshot(null, new WritableImage((int) background.getWidth(), (int) background.getHeight()));
        }

}
