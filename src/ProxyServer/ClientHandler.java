package ProxyServer; //package untitled;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ClientHandler {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader clientReader;
	private OutputStream clientOutputStream;

	public ClientHandler(ServerSocket socket) {
		this.serverSocket = socket;
	}

	public void accept() throws IOException {
		//accept
		clientSocket = serverSocket.accept();

		//input reader
		InputStream inputStream = clientSocket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		clientReader = new BufferedReader(inputStreamReader);

		//output
		OutputStream outputStream = clientSocket.getOutputStream();
		clientOutputStream = new DataOutputStream(outputStream);
	}

	public String readFromClient() throws IOException {
		String request = "";
		String inputLine = "";
		System.out.println("\nRequest:");
		while (clientReader != null && !(inputLine = clientReader.readLine()).equals("")) {
			System.out.println(inputLine); //Request'leri konsola yazd?.
			request += inputLine + "\r\n";
		}

		return request;
	}

	public String handle() throws IOException {
		accept();
		return readFromClient();
	}

	public void writeHeader(String data) throws IOException {
		PrintStream pw = new PrintStream(clientSocket.getOutputStream());
		pw.print(data);
		pw.flush();
	}

	public void writeClient(byte[] data) throws IOException {
		clientOutputStream.write(data);
	}

	public void closeConnection() throws IOException {
		clientSocket.close();
	}
}
