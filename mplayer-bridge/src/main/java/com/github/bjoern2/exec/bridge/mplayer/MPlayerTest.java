package com.github.bjoern2.exec.bridge.mplayer;


public class MPlayerTest {

	public static void main(String [] args) throws Exception {
//		MPlayer mp = new MPlayer("C:\\Program Files (x86)\\MPlayer for Windows\\mplayer.exe");
		MPlayer mp = MPlayer.newInstance();
		//mp.play("C:\\Users\\daniela\\Music\\Alben\\Kraftklub\\Kraftklub\\07 - Songs fï¿½r Liam.mp3");
		String url = "C:\\Users\\daniela\\Music\\Alben\\Kraftklub\\Kraftklub\\07 - Songs für Liam.mp3";
		//String url = "http://10.0.0.100:50002/m/NDLNA/10629.mp3";
		mp.play(url);
		
		Thread.sleep(4000);
		
		mp.pause();
		
		Thread.sleep(4000);
		
		mp.pause();
		
		Thread.sleep(4000);
		
		mp.close();
	}
	
}
