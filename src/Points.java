
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Points extends Stage {

        private long startTime;
        private final File pointsFile;
        private final Pane mainPane = new Pane();
        private Text pointstxt, questions;
        private VBox vbox;
        private TextField tf;

        private final Challenge challenge;

        public Points(Challenge challenge) throws URISyntaxException {
                pointsFile = new File(Points.class.getClassLoader().getResource("resources/points.txt").toURI());

                ImageView bg = new ImageView(Points.class.getClassLoader().getResource("resources/pages.png").toURI().toString());
                bg.setFitHeight(300);
                bg.setFitWidth(500);

                pointstxt = new Text("500");
                pointstxt.setFont(new Font("Goudy Stout", 24));
                pointstxt.setFill(Color.VIOLET);

                vbox = new VBox(10);

                vbox.setLayoutY(30);
                vbox.setLayoutX(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.CENTER);

                questions = new Text();
                questions.setWrappingWidth(450);
                questions.setFont(new Font("Goudy Stout", 20));
                questions.setFill(Color.PERU);

                tf = new TextField();

                vbox.getChildren().addAll(pointstxt, questions, tf);
                mainPane.getChildren().addAll(bg, vbox);
                this.challenge = challenge;
                this.setScene(new Scene(mainPane, 500, 300));
                this.setResizable(false);
        }

        public void started(int lvl, LevelSandbox hi) throws FileNotFoundException, URISyntaxException {
                startTime = System.currentTimeMillis();

                Timeline tl = new Timeline();
                tl.setCycleCount(Timeline.INDEFINITE);
                final KeyFrame kf = new KeyFrame(Duration.millis(500), e -> pointstxt.setText(this.getPoints() + ""));
                tl.getKeyFrames().add(kf);
                tl.play();

                double answer;

                File file = new File(Challenge.class.getClassLoader().getResource("Levels/Questions.txt").toURI());
                Scanner scanner = new Scanner(file);

                for (int a = 0; a < lvl; a++) {
                        scanner.nextLine();
                        scanner.nextLine();
                }
                questions.setText(scanner.nextLine());
                answer = scanner.nextDouble();
				tf.setText("");
				
                tf.setOnAction(e
                        -> {
						try { Double.parseDouble(tf.getText()); }
						catch (NumberFormatException ex) { return; }
					
                        if (Double.parseDouble(tf.getText()) + 0.2 > answer && Double.parseDouble(tf.getText()) - 0.2 < answer) {
                                try {
                                        this.finished(lvl);
                                        challenge.challengeCompleted(lvl);
                                } catch (IOException | URISyntaxException ex) {
                                        Logger.getLogger(Challenge.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InterruptedException ex) {
                                        Logger.getLogger(Points.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                tl.stop();
                        } else {
                                challenge.challengeFailed(lvl);
                                tl.stop();
                        }
                        this.close();
                        hi.close();
                });

                this.show();
        }
        // You CAN have a negative points total.

        public long getPoints() {
                return 500 - (System.currentTimeMillis() - startTime) / 500;
        }

        public void finished(int levelNumber) throws FileNotFoundException, IOException, InterruptedException {
                long points = this.getPoints();
                long[] pointsTotal = new long[15];

                //Fill the array
                for (int a = 0; a < 15; a++) {
                        pointsTotal[a] = 501;
                }
                int i = 0;
                Scanner scanner = new Scanner(pointsFile);

                //Read the content of the file, modify the array with it
                while (scanner.hasNextLong()) {
                        pointsTotal[i] = scanner.nextLong();
                        System.out.println(pointsTotal[i]);
                        i++;
                }
                scanner.close();

                System.out.println(levelNumber);

                //Only change the value of what has just been played
                pointsTotal[levelNumber] = points;

                //Write the array in the file
                BufferedWriter fr = new BufferedWriter(new FileWriter(pointsFile));
                int z = 0;
                while (pointsTotal[z] != 501 && z < 12) {
                        fr.write(pointsTotal[z] + " ");
                        fr.flush();
                        System.out.println(pointsTotal[z] + "");
                        z++;
                }

                fr.close();
        }

        public File pointsFile() {
                return pointsFile;
        }
}
