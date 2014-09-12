package com.github.bjoern2.exec.bridge.mplayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

public class MPlayer {

	private String path;
	private Process proc;

	private PrintStream input;

	public MPlayer(String path) throws IOException {
		this.path = path;
		open();
	}
	
	public static MPlayer newInstance() throws IOException {
		if (SystemUtils.IS_OS_WINDOWS) {
			String path1 = System.getenv("ProgramFiles") + "\\MPlayer for Windows\\mplayer.exe";
			if (new File(path1).exists()) {
				return new MPlayer(path1);
			}
			String path2 = System.getenv("ProgramFiles(x86)") + "\\MPlayer for Windows\\mplayer.exe";
			if (new File(path2).exists()) {
				return new MPlayer(path2);
			}
		}
		return null;
	}

	public void open() throws IOException {
		proc = Runtime.getRuntime().exec(path + " -slave -quiet -idle");

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				proc.destroy();
			}
		});

		input = new PrintStream(proc.getOutputStream());
		new Thread(new Runnable() {

			public void run() {
				try {
					
					IOUtils.copy(proc.getInputStream(), System.out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		// IOUtils.copy(proc.getInputStream(), System.out);
		// IOUtils.copy(proc.getErrorStream(), System.out);
	}

	private void sendCommand(String command) {
		input.print(command);
		input.print("\n");
		input.flush();
	}

	public void close() {
		sendCommand("quit");
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
		}
	}

	public void play(String file) {
		file = file.replace("\\", "/");
		sendCommand("loadfile \"" + file + "\" 0");
	}
	
	public void pause() {
		sendCommand("pause");
	}

}
