package me.prosperri.cliplayer;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in);
		Player player = new Player();
		
		System.out.println("Insert playlist destination:");
		player.loadPlaylist(in.nextLine());
		System.out.println("Insert Command:");
		executeCommand(player, in);	
		
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		launch(args);

	}
	
	public static void executeCommand(Player player, Scanner in){
		
		switch(in.nextLine()){
		case "play":
			player.play();
			break;
		case "pause":
			player.pause();
			break;
		case "next":
			player.next();
			break;
		case "prev":
			player.previous();
			break;
		}

	}

}
