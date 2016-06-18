package ProxyServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.List;


class InterceptUI extends JFrame {
    public String headersString = "", HTTPresponse;
    boolean userInput = false;
    boolean abort = false;
    public InterceptUI(Map < String, List < String >> mapData, String URL, String html) throws InterruptedException {
        JFrame f = new JFrame(URL);
        JTable table = buildTable(mapData);
        JLabel urlLabel = new JLabel("URL: " + URL);
        HTTPresponse = mapData.values().toArray()[0].toString();
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        JButton sendButton = new JButton("Send Headers");
        JButton abortButton = new JButton("Cancel Intercept");
        JTextArea htmlField = new JTextArea(10,50);
        JScrollPane scrollHtml = new JScrollPane(htmlField);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headersString = getHeaders(table);
                System.out.println(headersString);
                setHeadersString(headersString);
                setUserInput(true);
                f.setVisible(false);
            }
        });


        abortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                headersString = getHeaders(table);
                System.out.println(headersString);

                setHeadersString(headersString);
                setAbort(true);
                setUserInput(true);
                f.setVisible(false);
            }
        });


        f.setLayout(new GridBagLayout());
        f.setSize(600, 600); //400 width 500 height
        GridBagConstraints gridDimension = new GridBagConstraints();
        gridDimension.fill = GridBagConstraints.CENTER;
        gridDimension.gridx = 3;
        gridDimension.gridy = 0;
        gridDimension.gridwidth = 5;
        gridDimension.weightx = 0.0;
        gridDimension.insets = new Insets(0,0,50,0);
        f.add(table,gridDimension);

        //gridDimension.fill = GridBagConstraints.HORIZONTAL;
        //gridDimension.gridwidth = 10;
        gridDimension.gridy = 1;
        gridDimension.weightx = 1.0;
        gridDimension.gridx = 2;
        gridDimension.insets = new Insets(-50,25,0,0);
        f.add(sendButton, gridDimension);

        gridDimension.gridy = 1;
        gridDimension.gridx = 6;
        gridDimension.weightx = 0.9;
        gridDimension.insets = new Insets(-50,135,0,0);
        f.add(abortButton, gridDimension);

        gridDimension.gridy = 2;
        //gridDimension.gridwidth = 2;
        //gridDimension.gridx = 1;
        gridDimension.insets = new Insets(5,0,0,0);
        urlLabel.setPreferredSize(new Dimension(250,10));
        //urlLabel.setMaximumSize(new Dimension(250,10));
        f.add(urlLabel, gridDimension);

        //htmlField.setPreferredSize(new Dimension(200,200));
        gridDimension.insets = new Insets(25,0,0,0);
        htmlField.setText(html);
        htmlField.setMaximumSize(new Dimension(5,10));
        //gridDimension.gridx = 2;
        gridDimension.gridy = 3;
        f.add(scrollHtml, gridDimension);
        f.setVisible(true);
        System.out.println(html);
    }



    public String getheadersString() {
        return headersString;
    }

    public boolean isAbort() {
        return abort;
    }

    public void setAbort(boolean abort) {
        this.abort = abort;
    }

    public void setHeadersString(String headersString) {
        this.headersString = headersString;
    }

    public boolean isUserInput() {
        return userInput;
    }

    public void setUserInput(boolean userInput) {
        this.userInput = userInput;
    }

    private JTable buildTable(Map < String, List < String >> mapData) {

        String columnNames[] = {
                "Key",
                "Value"
        };
        Object[][] data = new Object[mapData.size()][2];

        int i = 0;
        for (Map.Entry < String, List < String >> entry: mapData.entrySet()) {
            if (entry.getKey() != null) {
                data[i][0] = entry.getKey();
                data[i][1] = entry.getValue().toString().replace("[", "").replace("]", "");
                i++;
            }
        }
        JTable table = new JTable(data, columnNames);

        return table;
    }


    private String getHeaders(JTable table) {
        StringBuffer headerString = new StringBuffer();
        for (int count = 0; count < table.getRowCount(); count++) {
            if (table.getValueAt(count, 0) != null && table.getValueAt(count, 1) != null) {
                headerString.append(table.getValueAt(count, 0).toString());
                headerString.append(": ");
                headerString.append(table.getValueAt(count, 1).toString());
                headerString.append("\r\n");
            }
        }
        headerString.append("\r\n");
        return headerString.toString();
    }
}