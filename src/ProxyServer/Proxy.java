package ProxyServer;

import java.io.IOException;
import java.net.ServerSocket;

class Proxy {
    private int port;
    private ServerSocket serverSocket;
    public Proxy(int port) throws IOException {
        this.port = port;
        initServer();
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Proxy server has started on port: " + port);
    }

    private String request2Address(String request) {
        String firstLine = request.split("\n")[0];
        System.err.println(firstLine);
        if (firstLine.contains(":443") || firstLine.contains("https:"))
            return null;
        String[] splitedFirstLine = firstLine.split(" ");
        if (splitedFirstLine.length < 2)
            return null;
        if (request.contains("http")) {
            return splitedFirstLine[1];
        } else {
            return "http://" + splitedFirstLine[1];
        }
    }


    private void handleRequest(String request, ClientHandler clientHandler) throws IOException, InterruptedException {
        if (request == null || request.trim().equals("")) {
            System.out.println("continue...");
            return;
        }
        System.out.println("\nxxxx\n");
        String address = request2Address(request);
        if (address == null) {
            System.out.println("address hatali");
            clientHandler.closeConnection();
            return;
        }
        ServerHandler serverHandler = new ServerHandler(address);
        byte[] response = serverHandler.makeRequest();
        String headers = serverHandler.getHeaders();
        clientHandler.writeHeader(headers);
        clientHandler.writeClient(response);
        clientHandler.closeConnection();
    }

    public void run() {
        while (true) {
            try {
                final ClientHandler clientHandler = new ClientHandler(serverSocket);
                final String request = clientHandler.handle();
                new Thread(() -> {
                    try {
                        handleRequest(request, clientHandler);
                    } catch (IOException e) {

                        e.printStackTrace();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}