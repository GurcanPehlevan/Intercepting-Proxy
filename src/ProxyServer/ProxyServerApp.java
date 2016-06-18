package ProxyServer; //package proxyServer;

import java.awt.*;
import java.io.*;


import javax.swing.*;


class ProxyServerApp{


	public static String portfromUser = "";

	public static void main(String[] args) {
		try {
			//Input input = new Input();
			//input.readJson();
			MainUI proxyPort = new MainUI();
			while (proxyPort.isUserInput() == false)
				Thread.currentThread().sleep(50);

			portfromUser = proxyPort.getPort();

			Proxy proxy = new Proxy(Integer.parseInt(portfromUser));
			proxy.run();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}