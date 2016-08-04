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
	private static int rate;
	
	public Player(){
		this.playlist = new ArrayList<File>();
		this.mPlayer = null;
		this.volume = 0.5;
		this.current = 0;
		this.rate = 0;
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
			if(rate > 0){
				rate--;
				play();
			}else{
				next();
			}
		});
		return mPlayer;
	}
	
	public static void play(){
		if(mPlayer != null && mPlayer.getStatus().toString().equals("PAUSED")){
			mPlayer.play();
		}else if(mPlayer == null){
			loadMPlayer().play();
		}else{
			mPlayer.stop();
			loadMPlayer().play();
			System.out.println(mPlayer.getMedia().getSource().toString());
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
	
	public static void repeat(int index){
		current = index;
		rate--;
		play();
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
					volume += Double.parseDouble(cmd.substring(1, cmd.length())) / 100;
				}else{
					volume -= Double.parseDouble(cmd.substring(1, cmd.length())) / 100;
				}
				
				if(volume > 100){ 
					volume = 100; 
				}else if(volume < 0){ 
					volume = 0; 
				}
				
				mPlayer.setVolume(volume);
				System.out.println("Volume: " + Math.round(volume * 100));
			}else if(cmd.matches("^repeat\\s+-i\\s+\\d+\\s+-r\\s+\\d+$")){
				String[] args = cmd.split("[^0-9]+");
				rate = Integer.parseInt(args[2]);
				repeat(Integer.parseInt(args[1]));
			}
			break;
		}

	}
	
}
