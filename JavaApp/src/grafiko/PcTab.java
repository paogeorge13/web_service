package grafiko;

import adder.CacheMemory;
import context.AccessPoint;
import context.WiredInterface;
import context.WirelessInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PcTab extends JPanel {

    private JComboBox wiredBox, wirelessBox, APBox;
    private static JButton refresh, refresh2, refresh3;
    private JLabel resultLabel;
    private BackgroundPanel wiredPanel, wirelessPanel, wirelessPanel2, APPanel, devicePanel, PCPanel;
    private JList deviceList;
    private JTable wiredTable, wirelessTable, wirelessTable2, APTable;
    private String device[] = {"ubuntu", "Aristotelis-PC"};
    private String selectedDevice;
    private DefaultTableModel dtmw, dtmwf, dtmwg, dtmap;
    private DefaultComboBoxModel dbmw, dbmwf, dbmap;
    private ArrayList<String[]> ListA, ListB, ListC, ListA1, ListB1, ListC1, resultList1, resultList2, resultList3;
    private HashMap<String, ArrayList<String[]>> wiredMap;
    private HashMap<String, ArrayList<String[]>> wirelessMap;
    private HashMap<String, ArrayList<String[]>> apMap;
    private CacheMemory mem;

    /*-----------------------------*/
    public PcTab(CacheMemory mem) {
        ListA = new ArrayList<String[]>();
        ListB = new ArrayList<String[]>();
        ListC = new ArrayList<String[]>();
        ListA1 = new ArrayList<String[]>();
        ListB1 = new ArrayList<String[]>();
        ListC1 = new ArrayList<String[]>();
        resultList1 = new ArrayList<String[]>();
        resultList2 = new ArrayList<String[]>();
        resultList3 = new ArrayList<String[]>();

        wiredMap = new HashMap<String, ArrayList<String[]>>();
        wirelessMap = new HashMap<String, ArrayList<String[]>>();
        apMap = new HashMap<String, ArrayList<String[]>>();

        this.mem = mem;

        wiredMap = mem.getWiredMap();
        wirelessMap = mem.getWirelessMap();
        apMap = mem.getApMap();

        this.setLayout(null);
    }

    public void customizePCTab() {
        /* Create header jpanel*/
        PCPanel = new BackgroundPanel("images/silver.jpg");
        PCPanel.setLayout(new BorderLayout());
        PCPanel.setBorder(BorderFactory.createTitledBorder("PC/Laptop"));
        PCPanel.setOpaque(true);
        add(PCPanel);
        PCPanel.setBounds(140, 0, 991, 80);

        /* Create jpanel with the jlist of devices*/
        devicePanel = new BackgroundPanel("images/gray.jpg");
        devicePanel.setLayout(new BorderLayout());
        devicePanel.setBorder(BorderFactory.createTitledBorder("Devices"));
        devicePanel.setOpaque(true);
        add(devicePanel);
        devicePanel.setBounds(0, 0, 140, 514);

        /* Create jlist with devices and add it to devicePanel*/
        Font displayfont = new Font("Arial", Font.BOLD, 11);
        deviceList = new JList(device);
        deviceList.setFont(displayfont);
        JScrollPane listPane1 = new JScrollPane(deviceList);
        devicePanel.add(listPane1, BorderLayout.NORTH);
        listPane1.setBounds(20, 35, 90, 350);
        /* Create jlabel with total number of devices and add it to devicePane*/
        resultLabel = new JLabel("Total: " + deviceList.getModel().getSize());
        resultLabel.setBackground(Color.WHITE);
        resultLabel.setLayout(new BorderLayout());
        resultLabel.setOpaque(true);
        devicePanel.add(resultLabel, BorderLayout.PAGE_END);

        /* Create JPanel with table of wired interfaces*/
        wiredPanel = new BackgroundPanel("images/silver.jpg");
        wiredPanel.setLayout(null);
        wiredPanel.setBorder(BorderFactory.createTitledBorder("Wired Interfaces"));
        add(wiredPanel);
        wiredPanel.setBounds(140, 80, 990, 135);
        /* Create jcoblobox with wired interfaces*/
        dbmw = new DefaultComboBoxModel();
        wiredBox = new JComboBox(dbmw);
        wiredPanel.add(wiredBox);
        wiredBox.setBounds(15, 35, 100, 30);



        refresh = new JButton("Refresh");
        wiredPanel.add(refresh);
        refresh.setBounds(15, 85, 80, 30);


        /* Create jtable with wired interfaces*/
        String wiredColumnNames[] = {"MAC", "IP", "NetAddress", "BfCast", "Default Gateway", "Packet Error"};
        dtmw = new DefaultTableModel();
        wiredTable = new JTable(dtmw);
        for (int i = 0; i < wiredColumnNames.length; i++) {
            dtmw.addColumn(wiredColumnNames[i]);
        }
        wiredTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader wiredHeader = wiredTable.getTableHeader();
        wiredHeader.setBackground(Color.BLACK);
        JScrollPane pane = new JScrollPane(wiredTable);
        wiredPanel.add(pane);
        pane.setBounds(170, 35, 800, 50);

        /* Create JPanel with table of wireless interfaces*/
        wirelessPanel = new BackgroundPanel("images/silver.jpg");
        wirelessPanel.setLayout(null);
        wirelessPanel.setBorder(BorderFactory.createTitledBorder("Wireless Interfaces"));
        add(wirelessPanel);
        wirelessPanel.setBounds(140, 215, 990, 160);
        /* Create jcoblobox with wireless interfaces*/
        dbmwf = new DefaultComboBoxModel();
        wirelessBox = new JComboBox(dbmwf);
        wirelessPanel.add(wirelessBox);
        wirelessBox.setBounds(15, 35, 100, 30);


        refresh2 = new JButton("Refresh");
        wirelessPanel.add(refresh2);
        refresh2.setBounds(15, 85, 80, 30);

        /* Create jtable with wired interfaces*/
        String wirelessColumnNames[] = {"MAC", "IP", "Mask", "NetAddress", "BCast", "Default Gateway", "Max transfer", "Current transfer", "ConsumedRate", "PacketError"};
        dtmwf = new DefaultTableModel();
        wirelessTable = new JTable(dtmwf);
        for (int i = 0; i < wirelessColumnNames.length; i++) {
            dtmwf.addColumn(wirelessColumnNames[i]);
        }
        wirelessTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader wirelessHeader = wirelessTable.getTableHeader();
        wirelessHeader.setBackground(Color.BLACK);
        JScrollPane wirelesspane = new JScrollPane(wirelessTable);
        wirelessPanel.add(wirelesspane);
        wirelesspane.setBounds(170, 25, 800, 50);


        /*Second table for wireless interfaces*/
        String wirelessColumnNames2[] = {"BaseStationMAC", "ESSID", "Channel", "Mode", "TransmitPower", "LinkQuality", "SignalLevel", "NoisePower", "DiscardedPackets"};
        dtmwg = new DefaultTableModel();
        wirelessTable2 = new JTable(dtmwg);
        for (int i = 0; i < wirelessColumnNames2.length; i++) {
            dtmwg.addColumn(wirelessColumnNames2[i]);
        }
        wirelessTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader wirelessHeader2 = wirelessTable2.getTableHeader();
        wirelessHeader2.setBackground(Color.BLACK);
        JScrollPane wirelesspane2 = new JScrollPane(wirelessTable2);
        wirelessPanel.add(wirelesspane2);
        wirelesspane2.setBounds(170, 93, 800, 50);



        /* Create JPanel with table of access points*/
        APPanel = new BackgroundPanel("images/silver.jpg");
        APPanel.setLayout(null);
        APPanel.setBorder(BorderFactory.createTitledBorder("Access Points"));
        add(APPanel);
        APPanel.setBounds(140, 374, 990, 140);
        /* Create jcoblobox with access points*/
        dbmap = new DefaultComboBoxModel();
        APBox = new JComboBox(dbmap);
        APPanel.add(APBox);
        APBox.setBounds(15, 35, 100, 30);

        refresh3 = new JButton("Refresh");
        APPanel.add(refresh3);
        refresh3.setBounds(15, 85, 80, 30);


        /* Create jtable with access points*/
        String APColumnNames[] = {"MAC", "Channel", "Mode", "SignalLevel"};
        dtmap = new DefaultTableModel();
        APTable = new JTable(dtmap);
        for (int i = 0; i < APColumnNames.length; i++) {
            dtmap.addColumn(APColumnNames[i]);
        }
        APTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader APHeader = APTable.getTableHeader();
        APHeader.setBackground(Color.BLACK);
        JScrollPane appane = new JScrollPane(APTable);
        APPanel.add(appane);
        appane.setBounds(170, 35, 800, 50);


        /* What happens when user chooses a device*/
        deviceList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    selectedDevice = deviceList.getSelectedValue().toString();
                }
                if (!dtmw.getDataVector().isEmpty()) {
                    dtmw.getDataVector().removeAllElements();
                }
                if (!dtmwf.getDataVector().isEmpty()) {
                    dtmwf.getDataVector().removeAllElements();
                }
                if (!dtmap.getDataVector().isEmpty()) {
                    dtmap.getDataVector().removeAllElements();
                }


                dbmw.removeAllElements();
                dbmwf.removeAllElements();
                dbmap.removeAllElements();

                if (!wiredMap.isEmpty()) {
                    resultList1 = wiredMap.get(deviceList.getSelectedValue());
                    if (resultList1 != null) {
                        for (int i = 0; i != resultList1.size(); ++i) {
                            dbmw.addElement(resultList1.get(i)[0]);
                        }
                    }
                }
                if (!wirelessMap.isEmpty()) {
                    resultList2 = wirelessMap.get(deviceList.getSelectedValue());
                    if (resultList2 != null) {
                        for (int i = 0; i < resultList2.size(); i++) {
                            dbmwf.addElement(resultList2.get(i)[0]);
                        }
                    }
                }
                if (!apMap.isEmpty()) {
                    resultList3 = apMap.get(deviceList.getSelectedValue());
                    if (resultList3 != null) {
                        for (int i = 0; i < resultList3.size(); i++) {
                            dbmap.addElement(resultList3.get(i)[0]);
                        }
                    }
                }
            }
        });


        /* What happens when user chooses a wired interface*/
        wiredBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ie) {
                        dtmw.getDataVector().removeAllElements();
                        String str = (String) ie.getItem();
                        WiredInterface w = CacheMemory.getInstance().getInfoOfWired(selectedDevice, str);
                        String wrow[] = {w.get_InterfaceMAC(), w.get_InterfaceIP(), w.get_NetworkAddress(), w.get_Bcast(), w.get_DefaultGetway(), w.get_PacketError()};
                        dtmw.addRow(wrow);
                    }
                });


        /* What happens when user chooses a wireless interface*/
        wirelessBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (!dtmwf.getDataVector().isEmpty()) {
                    dtmwf.getDataVector().removeAllElements();
                }
                if (!dtmwg.getDataVector().isEmpty()) {
                    dtmwg.getDataVector().removeAllElements();
                }
                String str = (String) ie.getItem();
                WirelessInterface w = CacheMemory.getInstance().getInfoOfWireless(selectedDevice, str);
                String wrowf[] = {w.get_InterfaceMAC(), w.get_InterfaceIP(), w.get_InterfaceMask(), w.get_NetworkAddress(), w.get_Bcast(), w.get_DefaultGetway(), w.get_MaxTransfer(), w.get_CurrentTransfer(), w.get_ConsumedRate(), w.get_PacketError()};
                String wrowf1[] = {w.get_BaseStationMAC(), w.get_ESSID(), w.get_Channel(), w.get_Mode(), w.get_TransmitPower(), w.get_LinkQuality(), w.get_SignalLevel(), w.get_NoisePower(), w.get_DiscardedPackets()};
                dtmwf.addRow(wrowf);
                dtmwg.addRow(wrowf1);
            }
        });


        /* What happens when user chooses an access point*/
        APBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (!dtmap.getDataVector().isEmpty()) {
                    dtmap.getDataVector().removeAllElements();
                }
                String str = (String) ie.getItem();
                AccessPoint ap = CacheMemory.getInstance().getInfoOfAP(selectedDevice, str);
                String aprow[] = {ap.get_APMAC(), ap.get_APChannel(), ap.get_APMode(), ap.get_APSignalLevel()};
                dtmap.addRow(aprow);
            }
        });




    }
}
