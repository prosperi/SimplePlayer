package me.prosperri.cliplayer;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Set;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaylistLoader extends SimpleFileVisitor<Path> {

	private static ArrayList<File> playlist = new ArrayList<>();
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("About to visit playlist directory...");
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		ArrayList<String> formats = new ArrayList<String>();
		File song = new File("", file.toString());
		
		formats.add("mp3");
		formats.add("wav");
		
		if(attrs.isRegularFile() && formats.contains(song.getName().toString().substring(song.getName().toString().length()-3))){
			playlist.add(song);
			System.out.println("Found Song " + file.getFileName());
		}
		
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println(exc.getMessage());
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		System.out.println("Finished Loading");
		return FileVisitResult.CONTINUE;
	}
	
	public ArrayList<File> getPlaylist(){
		return playlist;
	}
	
}
