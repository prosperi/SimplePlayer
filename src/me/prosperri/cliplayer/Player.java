package me.prosperri.cliplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.Animation.Status;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Player {
	private ArrayList<File> playlist;
	private static MediaPlayer mPlayer;
	private int current;
	
	public Player(){
		this.playlist = new ArrayList<File>();
		this.mPlayer = null;
		this.current = 0;
	}
	
	public void loadPlaylist(String path) throws FileNotFoundException{
		playlist.clear();
		Scanner playlistReader = new Scanner(new File(System.getProperty("user.home"), path));
		while(playlistReader.hasNextLine()){
			playlist.add(new File(System.getProperty("user.home"), playlistReader.nextLine()));
		}		
	}
	
	private Media loadMedia(){
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
	
	private MediaPlayer loadMPlayer(){
		mPlayer = new MediaPlayer(loadMedia());
		mPlayer.setOnEndOfMedia(()->{
			next();
		});
		return mPlayer;
	}
	
	public void play(){
		if(mPlayer != null && mPlayer.getStatus().equals(Status.PAUSED)){
			mPlayer.play();
		}else{
			loadMPlayer().play();
		}
	}
	
	public void next(){
		current++;
		play();
	}
	
	public void previous(){
		current--;
		play();
	}
	
	public void pause(){
		mPlayer.pause();
	}
}