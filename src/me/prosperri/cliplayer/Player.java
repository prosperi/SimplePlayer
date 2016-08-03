package me.prosperri.cliplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.Animation.Status;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {
	private static ArrayList<File> playlist;
	private static MediaPlayer mPlayer;
	private static double volume;
	private static int current;
	
	public Player(){
		this.playlist = new ArrayList<File>();
		this.mPlayer = null;
		this.volume = 0.5;
		this.current = 0;
	}
	
	public void loadPlaylist(String path) throws FileNotFoundException{
		playlist.clear();
		Scanner playlistReader = new Scanner(new File(System.getProperty("user.home"), path));
		while(playlistReader.hasNextLine()){
			playlist.add(new File(System.getProperty("user.home"), playlistReader.nextLine()));
		}		
	}
	
	private static Media loadMedia(){
		Media song;
		if(current < playlist.size() && current >= 0){
			song = new Media(playlist.get(current).toURI().toString());
		}else if(current >= playlist.size()){
			current = 0;
			song = new Media(playlist.get(current).toURI().toString());
		}else{
			current = playlist.size() + current;
			song = new Media(playlist.get(current).toURI().toString());
		}
		return song;
	}
	
	private static MediaPlayer loadMPlayer(){
		mPlayer = new MediaPlayer(loadMedia());
		mPlayer.setVolume(volume);
		mPlayer.setOnEndOfMedia(()->{
			next();
		});
		return mPlayer;
	}
	
	public static void play(){
		if(mPlayer != null && mPlayer.getStatus().toString().equals("PAUSED")){
			System.out.println("Here");
			mPlayer.play();
		}else if(mPlayer == null){
			loadMPlayer().play();
		}else{
			mPlayer.stop();
			loadMPlayer().play();
		}
	}
	
	public static void next(){
		current++;
		play();
	}
	
	public static void previous(){
		current--;
		play();
	}
	
	public static void pause(){
		mPlayer.pause();
	}
	
	public static void executeCommand(String cmd){
		switch(cmd){
		case "play":
			play();
			break;
		case "pause":
			pause();
			break;
		case "next":
			next();
			break;
		case "prev":
			previous();
			break;
		default:
			if(cmd.matches("^[+-]\\d{1,2}$")){
				if(cmd.charAt(0) == '+'){
					volume += Double.parseDouble(cmd.substring(1, 3)) / 100;
					mPlayer.setVolume(volume);
					System.out.println("Volume: " + Math.ceil(volume * 100));
				}else{
					volume -= Double.parseDouble(cmd.substring(1, 3)) / 100;
					mPlayer.setVolume(volume);
					System.out.println("Volume: " + Math.ceil(volume * 100));
				}
			}
			break;
		}

	}
	
}
