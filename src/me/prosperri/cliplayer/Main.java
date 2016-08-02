package me.prosperri.cliplayer;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application{
	private static Player player;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Scanner in = new Scanner(System.in);
		player = new Player();
		PlayerCmd playerCmd = new PlayerCmd();
		
		System.out.println("Insert playlist destination:");
		player.loadPlaylist(in.nextLine());
		
		try {
			playerCmd.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void playerCmdListener(String cmd){
		player.executeCommand(cmd);
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		launch(args);

	}
	
	

}
