package me.prosperri.player;
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Main extends Application {
	private static String path;
	private static int current;
	private static Scanner in;
	private static ArrayList<File> queue;
	private Media media;
	private MediaPlayer mPlayer;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		AnchorPane root = new AnchorPane();		
		
		/* Init next, previous, play/pause buttons */
		Arc previous = new Arc(72.5, 75, 50, 50, 90, 180);
		Arc next = new Arc(77.5, 75, 50, 50, 270, 180);
		Circle pausePlay = new Circle(75, 75, 20);
		
		previous.setType(ArcType.ROUND);
		next.setType(ArcType.ROUND);
		previous.setFill(Color.CADETBLUE);
		next.setFill(Color.CADETBLUE);
		pausePlay.setFill(Color.ROYALBLUE);
		previous.setAccessibleText("Previous");
		next.setAccessibleText("Previous");
		previous.setCursor(Cursor.HAND);
		next.setCursor(Cursor.HAND);
		pausePlay.setCursor(Cursor.HAND);
		
		
		
		
		/* Next Song event */
		next.setOnMouseClicked(event -> {
			generateMediaPlayer(queue);
		});
		
		/* Previous Song event */
		previous.setOnMouseClicked(event -> {
			current -= 2;
			generateMediaPlayer(queue);
		});
		
		/* Pause/Play event */
		pausePlay.setOnMouseClicked(event -> {
			if(mPlayer != null){
				if(mPlayer.getStatus().equals(Status.PAUSED)){
					mPlayer.play();
				}else{
					mPlayer.pause();
				}
			}
		});
		
		
		/*	Add nodes to stage */
		
		root.getChildren().add(previous);
		root.getChildren().add(next);
		root.getChildren().add(pausePlay);
		Scene scene = new Scene(root, 150, 150);
		
		primaryStage.setTitle("Lightweight Player");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/* Activate CLI version */
		//executeCommand();		
		
		
	}
	
	private static ArrayList<File> generatePlaylist() throws IOException{
		File song;
		Scanner scanner = new Scanner(new File(System.getProperty("user.home"), path));
		ArrayList<File> songs = new ArrayList<File>();
				
		while(scanner.hasNextLine()){
			song  = new File(System.getProperty("user.home"), scanner.nextLine());
			//System.out.println(song.getPath());
			songs.add(song);
		}
		
		return songs;
	}
	
	private void generateMedia(ArrayList<File> queue){
		try{
			media = new Media(queue.get(current).toURI().toString());
		}catch(IndexOutOfBoundsException e){
			if(current == -1){
				current = queue.size() - 1;
			}else{
				current = 0;
			}
			media = new Media(queue.get(current).toURI().toString());
		}
	}
	
	private void generateMediaPlayer(ArrayList<File> queue){
		if(mPlayer != null){
			mPlayer.stop();
		}
		
		generateMedia(queue);
		
		current += 1;
	    mPlayer = new MediaPlayer(media);
	    mPlayer.setOnEndOfMedia(()->{
	    	generateMediaPlayer(queue);
	    });
		mPlayer.play();
	}
	
	private static String getCommand(){
		System.out.println("Insert command:");
		return in.nextLine();
	}
	
	private static void executeCommand(){
		switch(getCommand()){
		case "play":
			System.out.println("play");
			break;
		case "pause":
			System.out.println("pause");
			break;
		case "next":
			System.out.println("next");
			break;
		case "previous":
			System.out.println("previous");
			break;
		}
		
		executeCommand();
	}
	
	
	
	public static void main(String[] args) throws IOException {
		in = new Scanner(System.in);
		current = 0;
		
		if(args.length > 0){
			path = args[0];
		}else{
			System.out.println("Insert playlist file destionation:");
			path = in.nextLine();
		}
		
		queue = generatePlaylist();
		launch(args);
	}
}
