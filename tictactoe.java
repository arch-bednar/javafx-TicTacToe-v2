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
	try{
	    gameMenu(primaryStage);
	}catch(FileNotFoundException e){
	    e.getMessage();
	}catch(IOException e){
	    e.getMessage();
	}
    }


    private class Cell extends Pane{

	private char field = ' ';

	public Cell(){
	    this.setStyle("-fx-border-color: black;");
	    this.getChildren().add(new Text(" "));
	    if(!pvc){
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
			    System.out.println(((Text)getChildren().get(0)).getText());
			    
			    if(turn == 1 && field == ' '){
				
				Ellipse smallerCircle = makeCircle(42, 42, 30, 30);
				smallerCircle.setFill(Color.WHITE);
				getChildren().addAll(makeCircle(42, 42, 35, 35), smallerCircle);
				
				field = 'O';
				turn = 2;
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("O won!");
				}
				
			    }
			    else if (turn == 2 && field == ' '){
				getChildren().addAll(makeLine(10, 10, 75, 75), makeLine(75, 10, 10, 75));
				field = 'X';
				turn = 1;
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("X won!");
				}
			    }
			    if(isArrayFilled()){
				System.out.println("End");
				turn = 3;
			    }
			}
		    });
	    }else{
		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
			    System.out.println(((Text)getChildren().get(0)).getText());

			    if(turn != 3){

				field = 'X';
				getChildren().addAll(makeLine(10, 10, 75, 75), makeLine(75, 10, 10, 75));
				if(vonNeumann(field) || diagonal(field)){
				    turn = 3;
				    System.out.println("X won!");
				    return;
				}
				else if(isArrayFilled()){
				    turn = 3;
				    return;
				}
				
				int x,y;
				Random gen = new Random();
				
				do{
				    x = gen.nextInt(3);
				    y = gen.nextInt(3);
				}while(fields[x][y].getSign() != ' ');
				
				fields[x][y].setField('O');
				fields[x][y].getChildren().add(makeCircle(42, 42, 35, 35));
				Ellipse smallerCircle = makeCircle(42, 42, 30, 30);
				smallerCircle.setFill(Color.WHITE);
				fields[x][y].getChildren().add(smallerCircle);

				if (vonNeumann('O') || diagonal('O')){
				    turn = 3;
				    System.out.println("O won!");
				}
				else if(isArrayFilled()){
				    turn = 3;
				    return;
				}
			    }
			}
		    });
	    }
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
	menuScreen.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
	//Group menuGroup = new Group();
	//menuGroup.getChildren().add(menuScreen);

	Scene menuScene = new Scene(menuScreen, 250, 275);
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


    private Ellipse makeCircle(int xa, int ya, int xb, int yb){
	Ellipse circle = new Ellipse(xa, ya, xb, yb);
	//circle.setStroke(Color.RED);
	//circle.setFill(Color.RED);
	return circle;
    }

    private Line makeLine(int xa, int ya, int xb, int yb){
	Line line = new Line(xa, ya, xb, yb);
	line.setStrokeWidth(5);
	return line;
    }

    private boolean vonNeumann(char sign){
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
	if (fields[1][1].getSign() == fields[0][0].getSign() && fields[0][0].getSign() == fields[2][2].getSign())
	    if(fields[1][1].getSign() == sign)
		return true;
	
	if (fields[1][1].getSign() == fields[0][2].getSign() && fields[0][2].getSign() == fields[2][0].getSign())
	    if (fields[1][1].getSign() == sign)
		return true;

	return false;	    
    }

    private boolean isArrayFilled(){
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
