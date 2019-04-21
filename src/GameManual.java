
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
public class GameManual extends Stage{
    private int page=0;
    private final int TOTALPAGES =3;
    
    public void createStage(){
        ImageView pgUpIMG = new ImageView("resources/arrow.png");
        pgUpIMG.setFitHeight(15);
        pgUpIMG.setFitWidth(30/2);
        Pane bigPane = new Pane();
        Pane pane = new Pane();
        pane.setLayoutX(20);
        pane.setLayoutY(0);
        bigPane.setBackground(new Background(new BackgroundImage(new Image("resources/pages.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        ImageView pgDownIMG = new ImageView("resources/arrow.png");
        pgDownIMG.setFitHeight(30/2);
        pgDownIMG.setFitWidth(30/2);
        pgUpIMG.getTransforms().add(new Rotate(180, 5, 5));
        Button pgUp = new Button("", pgUpIMG);
        Button pgDown = new Button("",pgDownIMG);
        pgUp.setLayoutX(390);
        pgUp.setLayoutY(210);
        pgUp.setPrefHeight(10);
        pgUp.setPrefWidth(10);
        pgDown.setLayoutX(390);
        pgDown.setLayoutY(260);
        pgDown.setPrefHeight(10);
        pgDown.setPrefWidth(10);
        bigPane.getChildren().addAll(pane,pgUp,pgDown);
        ImageView[] pages = new ImageView[TOTALPAGES];
        for(int a = 0; a<TOTALPAGES;a++){
            pages[a] = new ImageView("resources/page"+(a+1)+".png");
        }
        pane.getChildren().add(pages[0]);
        pgUp.setOnAction(e->{
            if(page>0){
                page--;
                pane.getChildren().clear();
                pane.getChildren().add(pages[page]);
            }
        });
        pgDown.setOnAction(e->{
            if(page+1<TOTALPAGES){
                page++;
                pane.getChildren().clear();
                pane.getChildren().add(pages[page]);
            }
        });
        Scene scene = new Scene(bigPane);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}
