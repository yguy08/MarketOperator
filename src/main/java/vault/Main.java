package vault;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application  {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Market Operator");
		
		//border pane
		BorderPane borderPane = new BorderPane();
		
		//header top
		Text title = new Text("Market Operator");
		title.setFill(Color.GREEN);
		title.setFont(Font.font(null, FontWeight.NORMAL, 24));
		BorderPane.setAlignment(title,  Pos.CENTER);
		borderPane.setTop(title);
		
		//flow pane left
		FlowPane flowPane = new FlowPane();
		flowPane.setPadding(new Insets(10, 10, 10, 10));
		flowPane.setVgap(4);
		flowPane.setHgap(4);
		flowPane.setPrefWrapLength(210);
		
		Button entryFinderBtn = new Button("Entry Finder");
		entryFinderBtn.setPrefSize(100,50);
		Button exitFinderBtn = new Button("Exit Finder");
		exitFinderBtn.setPrefSize(100, 50);
		flowPane.getChildren().addAll(entryFinderBtn, exitFinderBtn);
		borderPane.setLeft(flowPane);
		
		//Stat list
		ObservableList<String> stats = FXCollections.observableArrayList();
		ListView<String> statList = new ListView<>(stats);
		statList.setMaxWidth(250);
		BorderPane.setAlignment(statList,  Pos.CENTER_LEFT);
		borderPane.setCenter(statList);
		
		//right chat
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(Color.BLACK);
		borderPane.setRight(rectangle);
		
		//bottom copyright or ticker?
		Text copyright = new Text("2017");
		BorderPane.setAlignment(copyright, Pos.CENTER);
		borderPane.setBottom(copyright);
		
		
		Scene scene = new Scene(borderPane, 650, 350);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(scene.getHeight() + " " + scene.getWidth());
		
		
		entryFinderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e){
				try {
					StockLogic.populateEntryList(stats);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
}
