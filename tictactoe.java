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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.io.*;
import java.util.Random;

public class tictactoe extends Application{

    private Cell[][] fields = new Cell[3][3];
    private int turn = 1;
    private boolean pvc;
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
	/*
	  start method
	  catches exceptions
	*/
	try{
	    gameMenu(primaryStage);
	}catch(FileNotFoundException e){
	    e.getMessage();
	}catch(IOException e){
	    e.getMessage();
	}
    }


    private class Cell extends Pane{

	/*
	  Private inner class Cell
	  inherits after Pane
	*/

	private char field = ' ';  //takes X or O, shows how field is filled 

	public Cell(){
	    this.setStyle("-fx-border-color: black;"); //css border style
	    this.getChildren().add(new Text(" ")); //description of current field
	    if(!pvc){

		//PVP mode

		this.setOnMousePressed(new EventHandler<MouseEvent>(){

			/*
			  catches event is mouse pressed on Cell (Pane)
			  changes player turn
			  checks who won and if draw
			 */
			
			public void handle(MouseEvent event){
			    System.out.println(((Text)getChildren().get(0)).getText());

			    //Checks player's turn (first player) and if field is empty
			    if(turn == 1 && field == ' '){

				/*
				  draws empty circle
				  (black greater and white smaller)
				*/
				
				Ellipse smallerCircle = makeCircle(42, 42, 30, 30);
				smallerCircle.setFill(Color.WHITE);
				getChildren().addAll(makeCircle(42, 42, 35, 35), smallerCircle);
				
				field = 'O'; //sets field as circle O
				turn = 2; //changes player turn


				//Checks if player O won by diagonal or horizontal or vertical
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("O won!");
				}
				
			    }
			    //Checks player's turn (second player) and if field is empty
			    else if (turn == 2 && field == ' '){

				/*
				  draws cross for second player
				  checks winner
				*/
				
				getChildren().addAll(makeLine(10, 10, 75, 75), makeLine(75, 10, 10, 75)); //draws cross by drawning two crossed lines
				field = 'X'; //sets field as X
				turn = 1; //changes player turn


				//Checks if player O won by diagonal or horizontal or vertical
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("X won!");
				}
			    }


			    //This conditional checks if array is filled
			    //if true then ends game and draw
			    if(isArrayFilled()){
				System.out.println("End");
				turn = 3;
			    }
			}
		    });
	    }else{

		//PVC mode
		
		this.setOnMousePressed(new EventHandler<MouseEvent>(){

			//Checks if player pressed this cell
			public void handle(MouseEvent event){
			    System.out.println(((Text)getChildren().get(0)).getText());

			    //if not draw
			    if(turn != 3){
				
				field = 'X'; //sets field as player field X
				getChildren().addAll(makeLine(10, 10, 75, 75), makeLine(75, 10, 10, 75)); //draws cross


				//checking winner
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("X won!");
				    return;
				}
				//checking if is draw
				else if(isArrayFilled()){
				    turn = 3;
				    return;
				}


				
				//primitive engine for AI
				//generating position for computer's circle
				
				int x,y;
				Random gen = new Random(); 

				//first generates new position
				do{
				    x = gen.nextInt(3);
				    y = gen.nextInt(3);
				}while(fields[x][y].getSign() != ' '); // do it while field's position is clear
			      
				//sets field as circle
				fields[x][y].setField('O'); //computer's circle field
				fields[x][y].getChildren().add(makeCircle(42, 42, 35, 35)); //drawing circle and adding it to the cell
				Ellipse smallerCircle = makeCircle(42, 42, 30, 30); //drawing circle
				smallerCircle.setFill(Color.WHITE); //setting fill of smaller circle
				fields[x][y].getChildren().add(smallerCircle); //adding circle to Cell


				//checks if computer won
				if (vonNeumann('O') || diagonal('O')){
				    turn = 3; //end game
				    System.out.println("O won!");
				}
				//checks if draw
				else if(isArrayFilled()){
				    turn = 3; //end game
				    return;
				}
			    }
			}
		    });
	    }
	}
	
	public void setField(char sign){
	    //sets field as a sign (X, O)
	    if(this.field==' '){
		this.field=sign;
	    }
	}

	public char getSign(){
	    //gets sign of the field
	    return field;
	}
	
    }


    public static void main(String[] args){
	//main  method
	launch(args);

    }


    public void gameMenu(Stage menu) throws IOException, FileNotFoundException{

	/*
	  Creates main menu stage
	  throws IOException and FileNotFoundException for not found image or another IO exception
	*/
	
	Button start = new Button();
	Button startPVC = new Button();
	Button quit = new Button();

	InputStream inp = new FileInputStream("img/ttt.png");
	
	Image mm = new Image(inp);
	ImageView myImage = new ImageView(mm);

	myImage.setFitHeight(150);
	myImage.setFitWidth(150);
	
	start.setText("Start game");
	startPVC.setText("Start with computer");
	quit.setText("Quit");
	quit.setCancelButton(true);

	start.setMinHeight(25);
	start.setMinWidth(50);

	startPVC.setMinHeight(25);
	startPVC.setMinWidth(50);

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

	startPVC.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
		    System.out.println("comp");
		    menu.close();
		    pvc = true;
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
	menuScreen.setPrefSize(250, 275);
	menuScreen.getChildren().addAll(myImage, start, startPVC, quit);
	menuScreen.setAlignment(Pos.CENTER);
	menuScreen.setBackground(new Background(new BackgroundFill(Color.PINK, null, null)));
	//Group menuGroup = new Group();
	//menuGroup.getChildren().add(menuScreen);

	Scene menuScene = new Scene(menuScreen, 250, 275);
	menu.setTitle("Main menu");
	menu.setScene(menuScene);
	menu.show();
    }
    
    private void gameStart(Stage game){

	/*
	  Creates game stage
	  calls mathod  to create field 
	  as a mesh of cells (private class)
	*/
	
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
    }

    private void makeMesh(GridPane pane){

	/*
	  Makes a mesh of cells
	  for our gridpane
	  it uses RowConstraint and ColumnConstraints to create table
	  and fills it
	 */
	
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


    private Ellipse makeCircle(int xa, int ya, int xb, int yb){
	//creates circle
	Ellipse circle = new Ellipse(xa, ya, xb, yb);
	//circle.setStroke(Color.RED);
	//circle.setFill(Color.RED);
	return circle;
    }

    private Line makeLine(int xa, int ya, int xb, int yb){
	//creates line
	Line line = new Line(xa, ya, xb, yb);
	line.setStrokeWidth(5);
	return line;
    }

    private boolean vonNeumann(char sign){
	
	//checks winner horizontal and vertical

	for (int col=0; col<fields.length; col++){
	    for (int row=0; row<fields.length; row++){
		int count=0;

		for (int tcol=col-1; tcol<=col+1; tcol++){
		    if(tcol<0 || tcol>fields.length-1 || tcol==col)
			continue;
		    if(fields[col][row].getSign() == fields[tcol][row].getSign())
			if(fields[col][row].getSign() == sign)
			    count++;
		}
		if(count == 2)
		    return true;
		
		count=0;
		for (int trow=row-1; trow<=row+1; trow++){
		    if (trow < 0 || trow > fields.length-1 || trow == row)
			continue;
		    if (fields[col][row].getSign() == fields[col][trow].getSign())
			if(fields[col][row].getSign() == sign)
			    count++;
		}

		if(count == 2)
		    return true;
	    }
	}
	return false;
    }

    private boolean diagonal(char sign){

	//checks winner diagonal
	
	if (fields[1][1].getSign() == fields[0][0].getSign() && fields[0][0].getSign() == fields[2][2].getSign())
	    if(fields[1][1].getSign() == sign)
		return true;
	
	if (fields[1][1].getSign() == fields[0][2].getSign() && fields[0][2].getSign() == fields[2][0].getSign())
	    if (fields[1][1].getSign() == sign)
		return true;

	return false;	    
    }

    private boolean isArrayFilled(){

	//checks if array is filled (draw)
	
	for (int col=0; col<fields.length; col++){
	    for (int row=0; row<fields.length; row++){
		if (fields[col][row].getSign() == ' ')
		    return false;
	    }
	}
	System.out.println("Draw!");
	return true;
    }
}
