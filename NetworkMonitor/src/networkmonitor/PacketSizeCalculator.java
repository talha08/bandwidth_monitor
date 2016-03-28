
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

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class PacketSizeCalculator implements Serializable {

    @Id
    private float band;
    
    
 /**
  * return nothing
  */
    public PacketSizeCalculator() {
    }
    
    
/**
 * create a new PacketSizeCalculator with the data
 * @param band the data which we get
 */
    public PacketSizeCalculator(float band) {
        this.band = band;
    }
    
    
    
/**
 *  
 * @return bandwidth 
 */
    public Float getBand() {
        return band;
    }
}
