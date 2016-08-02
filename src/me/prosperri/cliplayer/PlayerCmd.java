package me.prosperri.cliplayer;

import java.util.Scanner;

public class PlayerCmd extends Thread {
	
	public PlayerCmd(){

	}
	
	@Override
	public void run() {

		Scanner in = new Scanner(System.in);
		getCmd(in);
		
	}
	
	private void getCmd(Scanner in){
		System.out.println("Insert Command:");
		Main.playerCmdListener(in.nextLine());
		getCmd(in);
	}
	
}
