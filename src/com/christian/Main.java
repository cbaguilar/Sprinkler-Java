package com.christian;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;


/*main sprinkler class, includes GUI.
 * test
 * 
 */

public class Main {


    Boolean[][] allDays;
    String[][] allTimes;
    String[] allStartTimes;

    Main parent;
    /*Here is the place where all of the components are declared.
     * I may remove the declaration for the sprinkler radio buttons/list*/
    //test
    JLabel connStatus, enabled;
    JPanel programSprinklers, directControl;
    JPanel connectInfo, outputPan;
    JPanel sprinklerList, map;
    /*
    	JLabel	l1,l2,l3,l4,l5,l6,
    		  	l7,l8,l9,l10,l11,l12;
    	
    	JTextField 	t1,t2,t3,t4,t5,t6,
    				t7,t8,t9,t10,t11,t12;
    	
    	JLabel 	e1,e2,e3,e4,e5,e6,
    			e7,e8,e9,e10,e11,e12;
    	
    	JRadioButton r1,r2,r3,r4,r5,r6,
    				 r7,r8,r9,r10,r11,r12;
    	
    */
    JTextField custInput, address, port, StartTime;
    JButton send, connect, disconnect, update;
    JPanel customMessage, connInfo, programbox, timebox;
    JComboBox<String> programs;
    JCheckBox enabledProgram;
    JTabbedPane tabs, diffProgs;
    JTextPane output;

    /* Changeable objects, sprinkler label, time, enabled,
     *  and group panel, respectively
     */

    ArrayList < JLabel > asprinklers;
    ArrayList < JTextField > asprinklerTime;
    ArrayList < JPanel > asprinklerPanel;


    // these arent used, not sure why they are here
    //ArrayList<JLabel> conSprinklers;
    //ArrayList<JToggleButton> conToggle;

    /* List of the checkboxes for date/time and their panel
     * 
     */
    ArrayList < JCheckBox > daycheck;
    ArrayList < JPanel > daypanels;
    //list of programs
    String[] programsList = {
        "Program 1", "Program 2", "Program 3"
    };

    //List of sprinkler names, can be changed like "garden" etc.
    String[] sprinkLabels = {
        "Front Yard Island",
        "Front Yard Grass Strip",
        "Front Yard Grass & Shrubs",
        "Front Yard Grass Corner",
        "Patio Drip",
        "Backyard Grass South Corner",
        "Fruit Trees Drip",
        "Backyard Grass North",
        "East Slope",
        "Side Grass Strip",
        "South Slope Drip",
        "Vegetable Garden Drip"
    };

    //String for days name, (for translating in spanish? lol)

    String[] days = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    };


    /*Direct control section, panels and buttons for direct control.
     * 
     */
    JButton control;
    JToggleButton onoff;
    JComboBox < Object[] > sprinklercombo;





    /*Basically everything that is in the main class...
     * 
     */








    Main() {

        allDays = new Boolean[3][12];
        allTimes = new String[3][12];
        allStartTimes = new String[12];



        //engine
        final SprinklerEngine engine = new SprinklerEngine(this);


        //set the tabs
        tabs = new JTabbedPane();



        /*
         * Direct control area
         */

        directControl = new JPanel();






        /*
         * This is info about the help page, currently stored in help.html
         */
        JPanel help = new JPanel();
        help.setLayout(new GridLayout());
        JTextPane para = new JTextPane();
        try {
            para.setContentType("text/html");
            para.setText(readFile("src/com/christian/help.html", Charset.defaultCharset()));
        } catch (IOException e) {
            System.out.println(e);
        }


        help.add(para);





        //Set up main sprinkler programming panel




        //image of the house
        ImageIcon icon = new ImageIcon("src/res/house2.png");

        programSprinklers = new JPanel();
        connInfo = new JPanel();
        customMessage = new JPanel();
        programbox = new JPanel();
        timebox = new JPanel();
        sprinklerList = new JPanel();
        outputPan = new JPanel();


        //adding the little tabs..

        tabs.addTab("Program", programSprinklers);
        tabs.addTab("Direct Control", directControl);
        tabs.addTab("Help", help);


        //start setting values of text panes
        output = new JTextPane();
        output.setText("Output");

        connStatus = new JLabel(" Not Connected");
        enabled = new JLabel(" Enabled");

        programs = new JComboBox<String>(programsList);
        programs.addActionListener(engine);
        programs.setEnabled(false);




        custInput = new JTextField(30);
        address = new JTextField(10);
        StartTime = new JTextField(2);
        port = new JTextField(5);
        custInput.setText("Command:");
        address.setText("192.168.2.70");
        port.setText("42001");


        send = new JButton("Send");
        connect = new JButton("Connect");
        disconnect = new JButton("Disconnect");
        update = new JButton("UPDATE");

        send.addActionListener(engine);
        connect.addActionListener(engine);
        disconnect.addActionListener(engine);
        update.addActionListener(engine);
        StartTime.addActionListener(engine);

        asprinklers = new ArrayList < JLabel > ();
        asprinklerTime = new ArrayList < JTextField > ();
        //asprinklerEnabled = new ArrayList<JRadioButton>();
        asprinklerPanel = new ArrayList < JPanel > ();



        for (int i = 0; i < 12; i++) {
            GridLayout g = new GridLayout();
            JTextField jtf = new JTextField(2);

            asprinklerPanel.add(new JPanel());
            asprinklers.add(new JLabel((i + 1) + ": " + sprinkLabels[i]));


            jtf.setEnabled(false);
            jtf.addActionListener(engine);

            asprinklerTime.add(jtf);
            //asprinklerEnabled.add(new JRadioButton()); not using this as mentioned before

            asprinklerPanel.get(i).setLayout(g);

            asprinklerPanel.get(i).add(asprinklers.get(i));
            asprinklerPanel.get(i).add(asprinklerTime.get(i));
            //asprinklerPanel.get(i).add(asprinklerEnabled.get(i));




            sprinklerList.add(asprinklerPanel.get(i));
        }

        JPanel daypanel = new JPanel();
        daypanel.setLayout(new BoxLayout(daypanel, 1));
        daycheck = new ArrayList < JCheckBox > ();
        daypanels = new ArrayList < JPanel > ();



        for (int i = 0; i < 7; i++) {
            GridLayout g = new GridLayout();
            JCheckBox jcb = new JCheckBox();
            JPanel jp = new JPanel();

            jcb.setEnabled(false);

            daycheck.add(jcb);
            daypanels.add(jp);



            JPanel current = daypanels.get(i);
            current.add(new JLabel(days[i]));
            current.add(daycheck.get(i));
            current.setLayout(g);
            daypanel.add(current);
        }
        timebox.add(new JLabel("Start Time (24 hr format) "));
        StartTime.setEnabled(false);
        timebox.add(StartTime);
        timebox.add(new JLabel(":00  "));
        daypanel.add(timebox);


        map = new JPanel();

        //map.setLayout();
        map.add(daypanel);
        map.add(sprinklerList);
        map.add(new JLabel(icon));


        BoxLayout box = new BoxLayout(programSprinklers, 1);
        /*GridLayout gl2 = new GridLayout();
        customMessage.setLayout(gl);
        connInfo.setLayout(gl);*/
        programSprinklers.setLayout(box);
        sprinklerList.setLayout(new BoxLayout(sprinklerList, 1));

        outputPan.setLayout(new GridLayout());


        connInfo.add(address);
        connInfo.add(port);
        connInfo.add(connect);
        connInfo.add(disconnect);
        connInfo.add(connStatus);

        programbox.add(new JLabel("Program: "));
        programbox.add(programs);
        //programbox.add(enabled);
        //programbox.add(enabledProgram);
        //programbox.add(new JLabel(" Time (24 hr format) "));
        //programbox.add(time);
        //programbox.add(new JLabel(":00  "));
        programbox.add(update);



        outputPan.add(output);

        customMessage.add(custInput);
        customMessage.add(send);

        programSprinklers.add(connInfo);
        programSprinklers.add(programbox);
        programSprinklers.add(map);
        programSprinklers.add(outputPan);
        programSprinklers.add(customMessage);



        JFrame frame = new JFrame("Christian's Sprinkler Pi Program!");

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {

                    engine.net.close();
                    System.out.println("closing sock");
                } catch (NullPointerException npe2) {
                    System.out.println("Could not close socket");
                    write("unable to close!! were dooomed!");
                } catch (IOException p) {
                    write("Something happened");
                    System.out.println("socket closing phailde");
                }

            }
        });

        frame.setContentPane(tabs);

        frame.pack();

        frame.setVisible(true);
    }



    public static void main(String[] args) {




        @
        SuppressWarnings("unused")
        Main main = new Main();

    }

    public int getProgramIndex() {
        return programs.getSelectedIndex();
    }

    public String getText() {
        System.out.println(custInput.getText());
        return custInput.getText();

    }

    public void setConnStatus(String status) {
        connStatus.setText(status);
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPort() {
        return port.getText();
    }

    public String getStartTime() {
        String strt = "0";
        strt = StartTime.getText();
        return strt;
    }

    public void write(String out) {
        output.setText(out);
    }

    public void setLabel(int l, String text) {
        asprinklers.get(l).setText(text);
    }

    //~(:^(l))
    //TODO finish multiple prigram setup nao!
    public void setProgram(ProgramObject program, int programNum) throws ParseException {
        SingleProgram Iprogram = program.programlist[programNum];

        for (int i = 0; i < 12; i++) {
            if (Iprogram.times[i] != null || Iprogram.times[i] != "null") {
                System.out.println(Iprogram.times[i]);
                asprinklerTime.get(i).setText(Iprogram.times[i]);
            } else {
                asprinklerTime.get(i).setText((String)
                    "0");
            }

        }

        for (int i = 0; i < 7; i++) {

            if (Iprogram.days != null) {

                daycheck.get(i).setSelected(Iprogram.days[i]);

            } else {
                daycheck.get(i).setSelected(false);
            }
        }
        if (Iprogram.start != null) {
            StartTime.setText(Iprogram.start);
        } else {
            StartTime.setText((String)
                "0");
        }
    }

    public boolean toBoolean(Object object) {
        if (object.getClass().equals(String.class)) {
            return Boolean.parseBoolean((String) object);
        }
        if (object.getClass().equals(boolean.class) || object.getClass().equals(Boolean.class)) {
            return (boolean) object;
        } else {
            return false;
        }


    }



    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public boolean[] getDays() {

        boolean[] daysj = new boolean[7];

            for (int i = 0; i < 7; i++) {
                daysj[i] = daycheck.get(i).isSelected();
                allDays[pro][i] = (daycheck.get(i).isSelected());
            }
        return daysj;

    }

    public String[] getTimes(int pro) {


        String[] timesj = new String[12];
        if (programs.getSelectedIndex() == pro) {
            for (int i = 0; i < 12; i++) {
                String t = asprinklerTime.get(i).getText();

                if (t.equals("")) {
                    timesj[i] = "0";
                    allTimes[pro][i] = "0";
                } else {
                    timesj[i] = t;
                    allTimes[pro][i] = t;
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                if (allTimes[pro][i] != null) {
                    timesj[i] = allTimes[pro][i];
                } else {
                    allTimes[pro][i] = asprinklerTime.get(i).getText();
                    timesj[i] = allTimes[pro][i];
                }

            }
        }
        return timesj;
    }


    public void enable(boolean tf) {
        StartTime.setEnabled(tf);
        programs.setEnabled(tf);

        for (JTextField jtf: asprinklerTime) {
            jtf.setEnabled(tf);
        }

        for (JCheckBox jcb: daycheck) {
            jcb.setEnabled(tf);
        }





    }





}