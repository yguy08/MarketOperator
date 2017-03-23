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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
		Text title = setTitle();
		BorderPane.setAlignment(title,  Pos.CENTER);
		borderPane.setTop(title);
				
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15,12,15,12));
		hbox.setSpacing(10);
		
		Button viewMarkets = new Button("View Markets");
		viewMarkets.setPrefSize(100, 50);
		Button entryFinderBtn = new Button("Entry Finder");
		entryFinderBtn.setPrefSize(100,50);
		Button exitFinderBtn = new Button("Exit Finder");
		exitFinderBtn.setPrefSize(100, 50);
		Button clearBtn = new Button("Clear");
		clearBtn.setPrefSize(100, 50);
		hbox.getChildren().addAll(viewMarkets,entryFinderBtn,exitFinderBtn, clearBtn);
		
		borderPane.setBottom(hbox);
		
		//Stat list
		ObservableList<String> stats = FXCollections.observableArrayList();
		ListView<String> statList = new ListView<>(stats);
		BorderPane.setAlignment(statList,  Pos.CENTER_LEFT);
		borderPane.setCenter(statList);	
		
		Scene scene = new Scene(borderPane, 650, 350);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(scene.getHeight() + " " + scene.getWidth());
		
		entryFinderBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e){
				try {
					BaseLogic.populateEntryList(stats);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		viewMarkets.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				BaseLogic.populateMarketList(stats);
			}
		});
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				stats.removeAll(stats);
			}
		});
		
	}
	
	public Text setTitle(){
		Text title = new Text("Market Operator");
		title.setFill(Color.GREEN);
		title.setFont(Font.font(null, FontWeight.NORMAL, 24));		
		return title;
		
	}
}
