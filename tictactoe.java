import javafx.application.Application;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.*;
import java.io.*;

public class tictactoe extends Application{

    private Cell[][] fields = new Cell[3][3];
    private int turn = 1;
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
	    this.setStyle("-fx-border-color: black;");
	    this.getChildren().add(new Text(" "));
	    this.setOnMousePressed(new EventHandler<MouseEvent>(){
		    public void handle(MouseEvent event){
			System.out.println(((Text)getChildren().get(0)).getText());
			if(turn == 1 && field == ' '){
			    getChildren().add(makeCircle());
			    field = 'O';
			    turn = 2;
			}
			else if (field == ' '){
			    getChildren().addAll(makeLine(10, 10, 75, 75), makeLine(75, 10, 10, 75));
			    field = 'X';
			    turn = 1;
			}
		    }
		});
	}

	public void setField(char sign){
	    if(this.field==' '){
		this.field=sign;
	    }
	}

	public char getSign(){
	    return field;
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
    
    private void gameStart(Stage game){

	GridPane mesh = new GridPane();
	makeMesh(mesh);
	mesh.setPrefSize(300, 300);
	mesh.setAlignment(Pos.CENTER);

	Button exit = new Button();
	exit.setText("Exit to home screen");
	exit.setCancelButton(true);
	exit.setPrefHeight(30);
	exit.setPrefWidth(180);
	exit.setFont(new Font(15));
	
	exit.setOnAction(new EventHandler<ActionEvent>(){
		public void handle(ActionEvent event) { game.close(); }
	    });
	//	exit.setAlignment(Pos.CENTER);
	
	VBox plane = new VBox(10);
	plane.getChildren().addAll(mesh, exit);
	plane.setAlignment(Pos.CENTER);
	plane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
	    
	Scene gameBoard = new Scene(plane, 300, 390);
	game.setScene(gameBoard);
	game.setTitle("tictactoe");
	game.show();
	//engine();
    }

    private void engine(){

    }

    private void makeMesh(GridPane pane){
	for(int col = 0; col<fields.length; col++){
	    pane.getColumnConstraints().add(new ColumnConstraints(85));
	}

	for(int row=0; row<fields.length; row++){
	    pane.getRowConstraints().add(new RowConstraints(85));
	}
	for(int row=0; row<fields.length; row++){
	    for(int col=0; col<fields.length; col++){
		Cell cell = new Cell();

		Text position = new Text(String.valueOf(row) + ":" + String.valueOf(col));
		position.setVisible(false);
		
		cell.getChildren().set(0, position);
		
		/*Ellipse ellipse = new Ellipse();
		ellipse.setCenterX(42);
		ellipse.setCenterY(42);
		ellipse.setRadiusX(20);
		ellipse.setRadiusY(20);
		cell.getChildren().add(ellipse);
		*/
		
		/*
		Line l1 = new Line(10, 10, 75, 75);
		Line l2 = new Line(75, 10, 10, 75);
		cell.getChildren().addAll(l1, l2);

		*/
		
		cell.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		fields[row][col] = cell;
		pane.add(fields[row][col], row, col);
	    }
	}
    }


    private Ellipse makeCircle(){
	Ellipse circle = new Ellipse(42, 42, 30, 30);
	return circle;
    }

    private Line makeLine(int xa, int ya, int xb, int yb){
	Line line = new Line(xa, ya, xb, yb);
	return line;
    }

    private boolean vonNeumann(){
	for (int col=0; col<fields.length; col++){
	    for (int row=0; row<fields.length; row++){
		int count=0;

		for (int tcol=col-1; tcol<=col+1; tcol++){
		    if(tcol<0 || tcol>fields.length || tcol==col)
			continue;
		    if(fields[col][row].getSign() == fields[tcol][row].getSign())
			if(fields[col][row].getSign() == 'X' || fields[col][row].getSign() == 'O')
			    count++;
		}
		if(count == 2)
		    return true;
		
		count=0;
		for (int trow=row-1; trow<=row+1; trow++){
		    if (trow < 0 || trow > fields.length || trow == row)
			continue;
		    if (fields[col][row].getSign() == fields[col][trow].getSign())
			if(fields[col][row].getSign() == 'X' || fields[col][row].getSign() == 'O')
			    count++;
		}

		if(count == 2)
		    return true;
	    }
	}
	return false;
    }

    private boolean diagonal(){
	if (fields[1][1].getSign() == fields[0][0].getSign() && fields[0][0].getSign() == fields[2][2].getSign()){
	    if (fields[1][1].getSign() == fields[0][2].getSign() && fields[0][2].getSign() == fields[2][0].getSign())
		if (fields[1][1].getSign() == 'X' || fields[1][1].getSign() == 'O')
		    return true;
	}
	return false;	    
    }
}
