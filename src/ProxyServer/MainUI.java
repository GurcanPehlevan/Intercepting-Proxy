package ProxyServer;

import sun.applet.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUI extends JPanel {

    public boolean isUserInput = false;
    public String Port;

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public boolean isUserInput() {
        return isUserInput;
    }

    public void setUserInput(boolean userInput) {
        isUserInput = userInput;
    }

    public MainUI() {


        FlowLayout layout = new FlowLayout();
       // layout.setHgap(10);
        //layout.setVgap(10);
        JPanel firstLine = new JPanel();
        JPanel secondLine = new JPanel();


        JFrame frame = new JFrame("Proxy Interceptor");
        JButton interceptJSON = new JButton("Intercept Manually");
        JButton interceptManual = new JButton("Intercept Using JSON");
        JLabel portLabel = new JLabel("Proxy Server Port:");
        JTextField portField = new JTextField();

        interceptJSON.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setPort(portField.getText());
                setUserInput(true);
                frame.setVisible(false);
               // Port = this;
              //  setHeadersString(headersString);
               // setAbort(true);
               // setUserInput(true);
               // f.setVisible(false);
            }
        });


        firstLine.add(interceptJSON, BorderLayout.LINE_START);
        firstLine.add(interceptManual, BorderLayout.LINE_END);
        firstLine.setLayout(new BoxLayout(firstLine,BoxLayout.Y_AXIS));
        frame.add(firstLine);

        secondLine.add(portLabel, BorderLayout.LINE_START);
        secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.Y_AXIS));
        secondLine.add(portField, BorderLayout.LINE_END);
        secondLine.setLayout(new BoxLayout(secondLine, BoxLayout.Y_AXIS));
        frame.add(secondLine);

        frame.setLayout(layout);
        frame.setSize(500,500);
        frame.pack();
        frame.setVisible(true);
    }
}
