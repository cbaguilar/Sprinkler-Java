package com.christian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class SprinklerEngine implements ActionListener {

    Main parent;
    Networking net;
    Gson gp;
    JsonElement command = new JsonObject();
    JsonParser jp;
    ProgramObject cmdObj;
    int index = 1;

    SprinklerEngine(Main parent) {
        this.parent = parent;
        System.out.println(parent);
        gp = new Gson();
        jp = new JsonParser();
        System.out.println("Initialized Sprinkler Engine");
    }

    @    SuppressWarnings("unchecked")
    
    public void actionPerformed(ActionEvent e) {
        Object acted = e.getSource();
        
        if (acted == parent.connect) {
            parent.enable(true);
            
            if (net == null) {
            	
                try {
                    net = new Networking(parent.getAddress(), Integer.parseInt(parent.getPort()));
                    parent.setConnStatus(" Connected");
                    String str = net.receive();
                    command = jp.parse(str);
                    System.out.println(command.toString());
                    System.out.println(str);
                    cmdObj = new ProgramObject();
                    cmdObj.programlist = gp.fromJson(str, SingleProgram[].class);
                    parent.setProgram(cmdObj, parent.programs.getSelectedIndex());
                    System.out.println(str);
                } catch (IOException e1) {
                    parent.setConnStatus(" Could not connect");
                    System.out.println("O noes! something went wrong connecting to the server!" + "\n IOException" + e1);
                    parent.write("System Output: Could not connect " + e1);
                } catch (ParseException pe1) {
                    System.out.println("There was an error in the recieved program...");
                    parent.write("Error in recived program");
                }
            }
        }
        
        if (acted == parent.StartTime) {/*do nothing*/}

        if (acted == parent.send) {

            try {
                net.send(parent.getText());
            } catch (IOException e5) {
                System.out.println("Could not send: " + e5);
                parent.write("System Output: could not send" + e5);
            } catch (NullPointerException npe1) {
                System.out.println("Not connected: " + npe1);
                parent.write("System Output: Not Connected: " + npe1);
            }
        }

        if (acted == parent.disconnect) {
        	
            try {
                net.close();
                net = null;
                System.out.println("Closed Connection");
            } catch (IOException ioe2) {
                System.out.println("Could not disconnect: " + ioe2);
                parent.write("Failed to Disconnect: " + ioe2);
            } catch (NullPointerException npe2) {
                System.out.println("What? You never connected! dummuy! " + npe2);
                parent.write("System Output: You never connected. " + npe2);
            }
            parent.setConnStatus(" Not Connected");
        }

        if (acted == parent.programs) {
            cmdObj.programlist[index].days = parent.getDays();
            cmdObj.programlist[index].times = parent.getTimes();
            cmdObj.programlist[index].start = parent.getStartTime();


            try {
                parent.setProgram(cmdObj, parent.programs.getSelectedIndex());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        index = parent.programs.getSelectedIndex();
        System.out.println("Current on program " + index);



        //System.out.println(parent.getStartTime(0));


        if (acted == parent.update) {
        	 cmdObj.programlist[index].start = parent.getStartTime();
        	 cmdObj.programlist[index].days = parent.getDays();
        	 cmdObj.programlist[index].times = parent.getTimes();
            System.out.println(cmdObj.programlist);
            int k = 0;
            for (SingleProgram singlep : cmdObj.programlist) {
                System.out.println("Printing days");
              
                //singlep.days = parent.getDays(k);

                for (String gtime : parent.getTimes()) {
                    try {
                        if (Integer.parseInt(gtime) > 15) {}
                        parent.write("WARNING: TIME IS SET LONG! TIME IS OVER 15 MINUTES!!");
                    } catch (NumberFormatException nfe) {}
                }

             // singlep.times = parent.getTimes(k);
            }
            cmdObj.type = "program";

            System.out.println(cmdObj);

            String cmdstr = gp.toJson(cmdObj);
            System.out.println(cmdstr);
            JsonObject fin = new JsonObject();

            fin.add("programlist", jp.parse(cmdstr));

            try {
                net.send(cmdstr);
                System.out.println(fin.toString());
            } catch (IOException ioe) {
                parent.write("Error Sending: " + ioe);

            } catch (NullPointerException npe1) {
                System.out.println("Not connected: " + npe1);
                parent.write("System Output: Not Connected: " + npe1);
            }
        }
    }
}