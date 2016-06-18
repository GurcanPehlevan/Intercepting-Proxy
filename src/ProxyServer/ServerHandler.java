package ProxyServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class ServerHandler {
	private URL url;
	private boolean aborted = false;
	String html;

	public ServerHandler(String urlString) throws MalformedURLException {
		url = new URL(urlString);
	}

	public byte[] makeRequest() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;

		try {
			is = url.openStream();
			byte[] byteChunk = new byte[2048];
			int n;

			while ((n = is.read(byteChunk)) != -1) {
				baos.write(byteChunk, 0, n);
			}
		} catch (IOException e) {
			System.err.printf("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return baos.toByteArray();
	}

	public String getHeaders() throws InterruptedException, IOException {
		URL obj = null;
		try {
			//creating new url object
			obj = new URL(url.toString());
			//getting html source code for echoing later in InterceptUI
			html = new Scanner(new URL(url.toString()).openStream(), "UTF-8").useDelimiter("\\A").next();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URLConnection conn = null;
		try {
			// make connection to the url
			conn = obj.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		String headers = "GET HTTP/1.1 200 OK\r\n";
		//getting all headers
		Map < String, List < String >> map = conn.getHeaderFields();

		//sending headers to InterceptUI
		InterceptUI editedHeaders = new InterceptUI(map, url.toString(), html);
		while (editedHeaders.isUserInput() == false)
			Thread.currentThread().sleep(50);
		if (editedHeaders.isAbort() == true || aborted == true) {
			aborted = true;
			for (Map.Entry < String, List < String >> entry: map.entrySet()) {
				if (entry.getKey() != null) {
					headers += entry.getKey();
					headers += ": ";
					headers += entry.getValue().toString().replace("[", "").replace("]", "");
					headers += "\r\n";
				}
			}
			headers += "\r\n";
			//System.out.println("++++++++++++++++++++++++" + headers);
			return headers;
		} else {
			if (editedHeaders.getheadersString().toString() != null)
				headers += editedHeaders.getheadersString().toString();
			//headers += "\r\n";
			System.out.println(headers);
			return headers;
		}
	}
}