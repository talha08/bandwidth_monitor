/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networkmonitor;

/**
 *
 * @author Talha-Pc 
 * @version 1.0.1
 * @since 07-06-2015
 */

import java.io.IOException;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;



/**
 * 
 * this class use for capturing data
 * and also for stop capturing
 */
public class PacketMonitoringThread extends Thread {
    
     /**
      * create  a Jpcap object with reference of JpcapCaptor and put its value Null
      */
     private JpcapCaptor jpcap = null; 

    @Override
    public void run() {
             /**
              * show all the active Network device of the computer
              */
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
         System.out.println(devices.length);
             
         System.out.println("Active Network Device list: \n");
         for (int i = 0; i < devices.length; i++) {
       
             System.out.println( i + ": " + devices[i].name + "(" + devices[i].description + ")");
           
              // Get which network interface to use for capturing from the user.
         }
         try {
              
           jpcap = JpcapCaptor.openDevice(devices[1], 65535, true, 20); //capturing data
              
            // jpcap=JpcapCaptor.openFile(null);
             
         } catch (IOException e) {
             //System.out.println(e);
         }

         
        /**
         * capturing data
         */
        jpcap.loopPacket(-2, new TcpPacketCapturer()); 
    }
    
    

    /**
     * this method for stop capturing data
     */
    
    public void stopCapturing() {
        jpcap.breakLoop(); // stop capturing data
    }
}
