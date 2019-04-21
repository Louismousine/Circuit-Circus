
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Challenge {

        final static public MediaPlayer mp1 = new MediaPlayer(new Media(getResource("resources/win.mp3").toString()));
		public static final MediaPlayer mp2 = new MediaPlayer(new Media(getResource("resources/lose.mp3").toString()));
        private int biggestChallengeCompleted;
        Points pts;
        int fitheight = 620;
        int fitwidth = 450;
        static boolean mute;

        Challenge() throws FileNotFoundException {

                try {
                        pts = new Points(this);
                } catch (URISyntaxException ignore) {
                        System.exit(1);
                }
                File file = pts.pointsFile();
                Scanner scan = new Scanner(file);
                int i = -1;
                while (scan.hasNextLong()) {
                        scan.nextLong();
                        i++;
                }
                biggestChallengeCompleted = i;
        }

        static void mute(boolean mute) {
                Challenge.mute = mute;
        }

        void challengeFailed(int lvl) {
                ImageView backgro = new ImageView(getResource("resources/pages.png").toString());
                Button replay = new Button("", new ImageView(getResource("resources/replay.png").toString()));
                MainScreen.backgroundMusic.stop();
                mp2.play();
                mp2.setMute(mute);
                mp2.setVolume(0.2);
                Pane topper = new Pane();
                topper.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
                topper.prefWidthProperty().bind(MainScreen.mainPane.widthProperty());
                topper.prefHeightProperty().bind(MainScreen.mainPane.heightProperty());
                topper.setLayoutX(0);
                topper.setLayoutY(0);
                ImageView sadScientist = new ImageView(getResource("resources/lose.gif").toString());
                VBox vbox = new VBox(10);
                vbox.setAlignment(Pos.CENTER);
                backgro.layoutXProperty().bind(vbox.layoutXProperty().subtract(30));
                backgro.layoutYProperty().bind(vbox.layoutYProperty().subtract(80));
                backgro.setFitHeight(fitheight);
                backgro.setFitWidth(fitwidth);
                Button backtochallenge = new Button("", new ImageView(getResource("resources/challengeButton.png").toString()));
                backtochallenge.setOnMouseEntered(e -> MainScreen.buttonHover.play());
                backtochallenge.setOnMouseExited(e -> MainScreen.buttonHover.stop());
                backtochallenge.setOnAction(e -> {
                        mp2.stop();
                        MainScreen.mainPane.getChildren().remove(topper);
                        MainScreen.backgroundMusic.play();
                });
                replay.setOnMouseEntered(e -> MainScreen.buttonHover.play());
                replay.setOnMouseExited(e -> MainScreen.buttonHover.stop());
                replay.setOnAction(e -> {
                        try {
                                playLevel(lvl);
                                mp2.stop();
                                MainScreen.mainPane.getChildren().remove(topper);
                                MainScreen.backgroundMusic.play();
                        } catch (FileNotFoundException ex) {
                                Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (URISyntaxException ex) {
                                Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                        }
                });
                vbox.getChildren().addAll(sadScientist, backtochallenge, replay);
                vbox.layoutXProperty().bind(MainScreen.mainPane.widthProperty().multiply(0.4));
                vbox.layoutYProperty().bind(MainScreen.mainPane.heightProperty().multiply(0.3));

                topper.getChildren().addAll(backgro, vbox);
                MainScreen.mainPane.getChildren().add(topper);
        }

        void challengeCompleted(int lvl) throws URISyntaxException {
                ImageView backgro = new ImageView(getResource("resources/pages.png").toString());
                MainScreen.backgroundMusic.stop();
                mp1.setVolume(0.2);
                mp1.setMute(mute);
                mp1.play();
                Pane topper = new Pane();
                topper.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
                topper.prefWidthProperty().bind(MainScreen.mainPane.widthProperty());
                topper.prefHeightProperty().bind(MainScreen.mainPane.heightProperty());
                topper.setLayoutX(0);
                topper.setLayoutY(0);

                ImageView happyScientist;
                if (lvl != 11) {
                        happyScientist = new ImageView(getResource("resources/win.gif").toString());
                } else {
                        happyScientist = new ImageView(getResource("resources/Einstein.jpg").toString());
                }
                happyScientist.setScaleX(2);
                happyScientist.setScaleY(2);
                VBox vbox = new VBox();
                vbox.setSpacing(80);
                vbox.setAlignment(Pos.CENTER);
                backgro.layoutXProperty().bind(vbox.layoutXProperty().subtract(30));
                backgro.layoutYProperty().bind(vbox.layoutYProperty().subtract(80));
                backgro.setFitHeight(fitheight);
                backgro.setFitWidth(fitwidth);
                Text pointsTxt = new Text("Correct! Points:" + pts.getPoints());
                pointsTxt.setFont(new Font("Goudy Stout", 20));
                pointsTxt.setUnderline(true);
                pointsTxt.setFill(Color.VIOLET);
                Button backtochallenge = new Button("", new ImageView(getResource("resources/challengeButton.png").toString()));
                backtochallenge.setOnMouseEntered(e -> MainScreen.buttonHover.play());
                backtochallenge.setOnMouseExited(e -> MainScreen.buttonHover.stop());
                backtochallenge.setOnAction(e -> {
                        MainScreen.mainPane.getChildren().remove(topper);
                        MainScreen.backgroundMusic.play();
                        MainScreen.mainPane.getChildren().clear();
                        ImageView background = new ImageView(MainScreen.class.getClassLoader().getResource("resources/backgroundOG.jpg").toString());
                        background.fitHeightProperty().bind(MainScreen.scene.heightProperty());
                        background.fitWidthProperty().bind(MainScreen.scene.widthProperty());
                        MainScreen.mainPane.getChildren().add(background);
                        mp1.stop();
                        try {
                                displaySelection(MainScreen.mainPane);
                        } catch (Exception ex) {
                        }
                });
                vbox.getChildren().addAll(pointsTxt, happyScientist, backtochallenge);
                vbox.layoutXProperty().bind(MainScreen.mainPane.widthProperty().multiply(0.4));
                vbox.layoutYProperty().bind(MainScreen.mainPane.heightProperty().multiply(0.3));

                topper.getChildren().addAll(backgro, vbox);
                MainScreen.mainPane.getChildren().add(topper);

                if (lvl > biggestChallengeCompleted) {
                        biggestChallengeCompleted = lvl;
                }
        }

        private void playLevel(int lvl) throws FileNotFoundException, URISyntaxException {
                LevelSandbox hi = new LevelSandbox(lvl);
                hi.show();

                pts.started(lvl, hi);
        }

        void displaySelection(Pane mainP) throws URISyntaxException {
                try {
                        final double SPACING = 0.05;

                        GridPane gridOfLevels = new GridPane();
                        gridOfLevels.prefWidthProperty().bind(mainP.widthProperty().multiply(0.9));
                        ScrollPane scroller = new ScrollPane(gridOfLevels);

                        ImageView[] challenge = new ImageView[12];
                        Pane[] panes = new Pane[12];
                        for (int a = 0; a < 12; a++) {
                                challenge[a] = new ImageView("LevelPics/lvl" + (a + 1) + ".png");
                                challenge[a].fitWidthProperty().bind(mainP.widthProperty().divide(6));
                                challenge[a].fitHeightProperty().bind(challenge[a].fitWidthProperty().multiply(0.8));
                                panes[a] = greyscaleIMG(challenge[a], a);
                        }

                        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                        scroller.prefWidthProperty().bind(mainP.widthProperty().multiply(0.93));
                        scroller.prefHeightProperty().bind(mainP.heightProperty().multiply(0.8));
                        scroller.setStyle("-fx-font-size: 40px;");
                        gridOfLevels.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        scroller.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        gridOfLevels.hgapProperty().bind(scroller.widthProperty().multiply(SPACING));
                        gridOfLevels.vgapProperty().bind(scroller.heightProperty().multiply(SPACING));
                        scroller.layoutXProperty().bind(mainP.widthProperty().multiply(0.05));
                        scroller.layoutYProperty().bind(mainP.heightProperty().multiply(0.05));
                        int j = 0;
                        for (int a = 0; a < 12; a++) {
                                if (a != 0 && a % 4 == 0) {
                                        j++;
                                }
                                gridOfLevels.add(panes[a], a % 4, j);
                                final int c = a;
                                panes[a].setOnMouseClicked(e -> {

                                        try {
                                                playLevel(c);
                                        } catch (FileNotFoundException | URISyntaxException ex) {
                                                Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                });

                        }

                        Button bMainMenu = new Button("", new ImageView(getResource("resources/backtomainmenu.png").toString()));
                        bMainMenu.layoutXProperty().bind(MainScreen.scene.widthProperty().multiply(0.4));
                        bMainMenu.layoutYProperty().bind(MainScreen.scene.heightProperty().multiply(0.9));
                        MainScreen.mainPane.getChildren().addAll(bMainMenu, scroller);
                        bMainMenu.setOnMouseEntered(e -> MainScreen.buttonHover.play());
                        bMainMenu.setOnMouseExited(e -> MainScreen.buttonHover.stop());
                        bMainMenu.setOnAction(e -> {
                                MainScreen.mainPane.getChildren().clear();
                                ImageView background = new ImageView(MainScreen.class.getClassLoader().getResource("resources/MainScreen.png").toString());
                                background.fitWidthProperty().bind(MainScreen.scene.widthProperty());
                                background.fitHeightProperty().bind(MainScreen.scene.heightProperty());
                                MainScreen.mainPane.getChildren().add(background);
                                try {
                                        MainScreen.paintStage(MainScreen.primaryStage);
                                } catch (Exception ex) {
                                        Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        });
                } catch (FileNotFoundException ex) {
                        Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        private Pane greyscaleIMG(ImageView img, int level) throws URISyntaxException, FileNotFoundException {
                Pane levelPane = new StackPane();
                ImageView frame = new ImageView("resources/frameLVL.png");
                frame.fitWidthProperty().bind(img.fitWidthProperty().multiply(1.1));
                frame.fitHeightProperty().bind(img.fitHeightProperty().multiply(1.1));
                levelPane.getChildren().addAll(frame, img);
                if (level <= biggestChallengeCompleted + 1) {
                        levelPane.setDisable(false);
                        if (level <= biggestChallengeCompleted) {
                                ImageView box = new ImageView("resources/pointsBG.png");
                                File ptssss = pts.pointsFile();
                                Scanner scan = new Scanner(ptssss);
                                long ptsss = 0;
                                String ptsString = "0";
                                for (int a = 0; a <= level; a++) {
                                        if (scan.hasNextLong()) {
                                                ptsString = scan.nextLong() + "";

                                        }
                                }
                                scan.close();
                                Text ptss = new Text(ptsString);
                                ptsss = Long.parseLong(ptsString);
                                ptss.setStyle("-fx-font-weight: bold;");
                                ptss.setStrokeWidth(2);
                                ptss.setStroke(Color.BLACK);
                                if (ptsss > 300) {
                                        ptss.setFill(Color.GREEN);
                                } else if (ptsss > 100) {
                                        ptss.setFill(Color.YELLOW);
                                } else {
                                        ptss.setFill(Color.RED);
                                }
                                box.setLayoutX(130);
                                box.setLayoutY(260);
                                box.setFitHeight(50);
                                box.setFitWidth(90);
                                ptss.setLayoutX(131);
                                ptss.setLayoutY(267);
                                levelPane.getChildren().addAll(box, ptss);
                        }
                } else {
                        ImageView greyVeil = new ImageView(getResource("resources/greyVeil.png").toString());
                        greyVeil.fitWidthProperty().bind(img.fitWidthProperty());
                        greyVeil.fitHeightProperty().bind(img.fitHeightProperty());
                        levelPane.getChildren().add(greyVeil);
                        levelPane.setDisable(true);
                }

                return levelPane;
        }

        public static URL getResource(String res)
		{
			return Challenge.class.getClassLoader().getResource(res); 
        }
}
