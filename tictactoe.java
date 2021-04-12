import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.geometry.Pos;
import java.io.*;

public class tictactoe extends Application{

    //    private Cell[][] fields = new Cell[3][3];
    
    /*    public tictactoe(){
	for(int row=0; row<fields.length; row++){
	    for(int col=0; col<fields.length; row++){
		this.fields=new Cell();
	    }
	}
    }

    //Driver class cannot have constructor!!!
    */

    
    @Override
    public void start(Stage primaryStage){
	try{
	    gameMenu(primaryStage);
	}catch(FileNotFoundException e){
	    e.getMessage();
	}catch(IOException e){
	    e.getMessage();
	}
    }


    public class Cell extends Pane{

	private char field = ' ';

	public Cell(){
	    //	    this.setStyle("-fx-border-color: black;");
	}

	public void setField(char sign){
	    if(this.field==' '){
		this.field=sign;
	    }
	}
	
    }


    public static void main(String[] args){
	launch(args);

    }

    
    public void gameMenu(Stage menu) throws IOException, FileNotFoundException{
	Button start = new Button();
	Button quit = new Button();

	InputStream inp = new FileInputStream("img/ttt.png");
	
	Image mm = new Image(inp);
	ImageView myImage = new ImageView(mm);

	myImage.setFitHeight(150);
	myImage.setFitWidth(150);
	
	start.setText("Start game");
	quit.setText("Quit");
	quit.setCancelButton(true);

	start.setMinHeight(25);
	start.setMinWidth(50);

	quit.setMinHeight(25);
	quit.setMinWidth(50);

	start.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
		    System.out.println("siema");
		    menu.close();
		    gameStart(menu);
		}
	    });

	quit.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
		    menu.close();
		}
	    });

	VBox menuScreen = new VBox(10);
	menuScreen.setPrefSize(250, 250);
	menuScreen.getChildren().addAll(myImage, start, quit);
	menuScreen.setAlignment(Pos.CENTER);
	menuScreen.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
	//Group menuGroup = new Group();
	//menuGroup.getChildren().add(menuScreen);

	Scene menuScene = new Scene(menuScreen, 250, 250);
	menu.setTitle("Main menu");
	menu.setScene(menuScene);
	menu.show();
    }
    
    public void gameStart(Stage game){
	
    }

}
