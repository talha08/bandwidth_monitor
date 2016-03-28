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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MainIBMApplicationForm extends JFrame {
    
    public MainIBMApplicationForm() {
         
 
       
        setSize(850,320); //set size
        setTitle("Network Monitor");//Setting the title
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Setting the action close to the close button
       
       //............menu........... 
      /*  JMenuBar menuBar = new JMenuBar();
         
        // Add the menubar to the frame
        setJMenuBar(menuBar);
       // Define and add two drop down menu to the menubar
        JMenu about = new JMenu("");
        menuBar.add(about);
        
        
        menuBar.add(Box.createHorizontalGlue());
         
         
        JMenu version = new JMenu("");
        menuBar.add(version);*/
        
       //.................. 
        
        
        series1 = new XYSeries("b/w usage in KBps");                
        dataset = createDataset();
        chart = createChart(dataset);
        chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new java.awt.Dimension(850, 320));

        // chartPanel.setLayout();
        chartPanel.add(current_bandwidth_label);
        setContentPane(chartPanel);
        pmt = new PacketMonitoringThread();
        pmt.start();


        Action updateCursorAction = new AbstractAction() {

          public void actionPerformed(ActionEvent e) {

                int current_bandwidth = new Integer((int) TcpPacketCapturer.getPacketSizeTillNowAndResetSize());
                
             
                qe.add(current_bandwidth);
                current_bandwidth_label.setText("" + current_bandwidth);
                if (qe.size() > 20) {
                    System.out.println(qe.poll());
                }

                dataset = createDataset();
                chart = createChart(dataset);
                chartPanel = new ChartPanel(chart);

                //    getContentPane().repaint();
                //   SwingUtilities.updateComponentTreeUI(chartPanel);
            }
        };
        
    
        // its timer, it works like sleep, and in this apps its give you Internet speed KB/sec.
       
        new Timer(1000, updateCursorAction).start();



    }

  //main .................... 
    
   
    
    /**
     * Starting point for the  application.
     *
     * @param args  ignored.
     */

    public static void main(String args[]) {
        
        
        //show the java.library.path
     //   System.out.println("java.library.path : " + System.getProperty("java.library.path"));
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                MainIBMApplicationForm f = new MainIBMApplicationForm();
                f.setVisible(true);  // for visible the frame
       
            }
        });
    }
    
    
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */

    private XYDataset createDataset() {

        series1.clear(); // use to clear previous data

         //Iterator enables you to cycle through a collection, obtaining or removing elements
        //using this iterator object we can access each element in the collection, one element at a time.
        System.out.println("Initial Size of Queue :" + qe.size());
        Iterator it = qe.iterator();
        

        double a = 1.0;
        //hasNext()---> This method returns true if and only if this scanner has another token
        while (it.hasNext()) { 
            Integer iteratorValue = (Integer) it.next();  
            series1.add(a++, ((double) iteratorValue) / 1024);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        return dataset;

    }

    
   
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
       private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                
                // put a big space or it'll overlap with the statistics of data...  :) 
                "B/W Usage in KBps:                                                                    ", // chart title
                "Seconds                    ", // x axis label
                "  Usage", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, // Plot Orientation.. it means grap will show you statistics vertically 
                true, // include legend
                true, // tooltips
                false // urls
                );

          // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        
        
        // Background color of chart/outside plot........
        chart.setBackgroundPaint(Color.white);
        
        
       // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        //plot background color inside the graph....
        plot.setBackgroundPaint(Color.orange);                                        //org.jfree.chart.plot.XYPlot;
        //Plot vertical inner graph line.....
        plot.setDomainGridlinePaint(Color.red);
        //plot horizontal inner graph line.....
        plot.setRangeGridlinePaint(Color.blue);
        
      
        //Sets the renderer for the PGraphics object that is used for drawing to...
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        // it used to show the up and down connecting line will show or not(RED LINE of graph)
        renderer.setSeriesLinesVisible(1, true); 
        //renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);//it used to show the up and down block of data(RED Block Of GRAPH)
        
        
        
       //......................................... 

        //..... change the auto tick unit selection to integer units only
        //LIMIT OF X and Y axis GRAPH SIDE DATA (usages/second)
        //.....if I unhide these then only integer number show in X axis,it avoid floating point
        
        /*final NumberAxis domainAxis = new NumberAxis("X-Axis");
        domainAxis.setRange(0.00,1.00);
         domainAxis.setTickUnit(new NumberTickUnit(0.1));
         final NumberAxis rangeAxis = new NumberAxis("Y-Axis");
          rangeAxis.setRange(0.0,1.0);
         rangeAxis.setTickUnit(new NumberTickUnit(0.0));*/
        
       
        
     //usases(X axis) integer data counting
        //final NumberAxis rangeAxis = (NumberAxis) 
        //plot.getRangeAxis();
      //  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       // OPTIONAL CUSTOMISATION COMPLETED.
//..............................................
        
        return chart;
}
   
    
    
    
    PacketMonitoringThread pmt = null;
    public XYSeries series1 = null;
    XYDataset dataset = null;
    JFreeChart chart = null;
    ChartPanel chartPanel = null;
    Queue<Integer> qe = new LinkedList<>();
    static JLabel current_bandwidth_label = new JLabel("0");

}
