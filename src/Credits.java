
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class Credits extends Pane
{
        private String fullCredits;
		
        Credits(Pane mainPane){
			
			this.layoutXProperty().bind(mainPane.widthProperty().multiply(0.4));
			this.layoutYProperty().bind(mainPane.heightProperty().multiply(0.3));
			this.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.2));
			this.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.4));
			
			ImageView credits = new ImageView("resources/credits.png");
			Button close=new Button("", new ImageView("resources/xclose.png"));
			close.setBackground(Background.EMPTY);

			close.layoutXProperty().bind(this.widthProperty().multiply(0.8));
			close.layoutYProperty().set(0);
			((ImageView)close.getGraphic()).fitWidthProperty().bind(this.widthProperty().multiply(0.1));
			((ImageView)close.getGraphic()).fitHeightProperty().bind(this.widthProperty().multiply(0.1));
			
			close.widthProperty().addListener(e ->
			{
				System.out.println("this width: " + this.getWidth());
				System.out.println("close width: " + close.getWidth());
			});
			
			credits.layoutXProperty().set(0);
			credits.layoutYProperty().set(0);
			credits.fitHeightProperty().bind(this.heightProperty());
			credits.fitWidthProperty().bind(this.widthProperty());
			
			
			
			close.setOnAction(e -> mainPane.getChildren().remove(this));
			
			this.getChildren().addAll(credits, close);
			
			mainPane.getChildren().add(this);

        }
}