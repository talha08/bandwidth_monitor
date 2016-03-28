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

import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class TcpPacketCapturer implements PacketReceiver {

    public static float totalSize = 0;
    public static float totalSizeToBeReturned = 0;
    public static float totalSizeToBeReturned1 = 0;
    public static float store =0;
    
   
    
   
     
     /**
      * getting all data and
      * get the total size of data.
      */
    @Override
    public void receivePacket(Packet packet) {
        totalSize += packet.len;
    }

    
    /**
     * get data form device and calculate  
     * @return data in KBps
     */
    public static float getPacketSizeTillNowAndResetSize() {
        totalSizeToBeReturned = totalSize;
   
        System.out.println("Current size and reset: " + totalSizeToBeReturned / 1024);
       
        store+=totalSizeToBeReturned / 1024;
        
        System.out.println("Sum of Uses Data: " +store);
        
        totalSize = 0;
        
        return totalSizeToBeReturned / 1024;
        
    }
  
  
}
