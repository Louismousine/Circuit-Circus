
import circuitGUI.Sandbox;
import java.io.File;
import java.net.URISyntaxException;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

public class LevelSandbox extends Sandbox {

        public LevelSandbox(int levelIndex) throws URISyntaxException {
                super(new File(LevelSandbox.class.getClassLoader().getResource("Levels/Level" + (levelIndex + 1) + ".cir").toURI()));
                Sandbox.sandboxes.add(null);
				
                for (Node n : ((Pane) this.getScene().getRoot()).getChildren())
                    if (n instanceof MenuBar)
                        ((MenuBar)n).getMenus().clear();
					else if (n instanceof Pane)
						n.setOnContextMenuRequested(null);
				
				this.toolbox.selectTool(circuitGUI.Toolbox.TOOLTYPE.MOVE);
				this.breadboard.speed.set(0);
        }
}
