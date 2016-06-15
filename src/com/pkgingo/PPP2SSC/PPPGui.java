package com.pkgingo.PPP2SSC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.pkgingo.GenericUtils.DataUtils;
import com.pkgingo.PPPUtils.PPPFile;
import com.pkgingo.SSCUtils.SSCFileCreator;

public class PPPGui extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5720756021003207128L;
	public PPPGui() {
        
    }
	
	static JButton OKButton;
	static JButton HelpButton;
	static JButton QuitButton;
	static String[] labels = {"Music File", "Para Para Chart", "Normal Chart", "Hard Chart", "Expert Chart", "Another Chart"};
	static JButton[] browseButtons = new JButton[labels.length];
    static JTextField[] textFields = new JTextField[labels.length];
    
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	private static void createAndShowGUI() {
		if (DataUtils.isWindows())
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (ClassNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (InstantiationException e1)
			{
				e1.printStackTrace();
			}
			catch (IllegalAccessException e1)
			{
				e1.printStackTrace();
			}
			catch (UnsupportedLookAndFeelException e1)
			{
				e1.printStackTrace();
			}
		}
        
        int numPairs = labels.length;
       
        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i] + ": ", JLabel.TRAILING);
            p.add(l);
            textFields[i] = new JTextField(35);
            l.setLabelFor(textFields[i]);
            p.add(textFields[i]);
            browseButtons[i] = new JButton();
            browseButtons[i].setText("Browse");
            browseButtons[i].addActionListener(new JPickerField(textFields,i));
            p.add(browseButtons[i]);
        }

        OKButton = new JButton();
        HelpButton = new JButton();
        QuitButton = new JButton();
        
        HelpButton.setText("Help");
        QuitButton.setText("Quit");
        OKButton.setText("OK!");
        
        
        
        QuitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				disableUI();
				System.exit(0);
				
			}

        });
        
        HelpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				disableUI();
				JOptionPane.showMessageDialog(null, DataUtils.programName()+"\nThis application converts PPP bin charts located in the game's SD directory to the SSC format.\nFiles are named by CDDATrackNo_CHART.BIN.\nThe following chart naming conventions apply:\n1: Para Para Mode\nA: Freestyle Normal\nB: Freestyle Hard\nC: Freestyle Expert\nF: Freestyle Another");
				enableUI();
			}

        });
        
        OKButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				disableUI();
				beginConversion();
				enableUI();
			}

        });
        
        
        p.add(QuitButton);
        p.add(HelpButton);
        p.add(OKButton);
        
 
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                                        numPairs+1, 3, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
 
        //Create and set up the window.
        JFrame frame = new JFrame(DataUtils.programName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Set up the content pane.
        p.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(p);
 
        //Display the window.
        frame.pack();

        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
	
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public static void beginConversion()
    {
    	boolean[] filesOK = new boolean[labels.length];
    	boolean alert=false;
    	for (int i=0;i<labels.length;i++)
    	{
    		filesOK[i]=true;
    		String fname = textFields[i].getText();
    		if (fname.equals(""))
    		{
    			System.out.println(labels[i] + " is empty!");
    			filesOK[i]=false;
    			continue;
    		}
    		File f = new File(fname);
    		if (!f.exists())
    		{
    			System.out.println(f.getAbsolutePath() + " doesn't exist!");
    			filesOK[i]=false;
    			continue;
    		}
    	}
    	String missingData = "";
    	for (int i=0;i<filesOK.length;i++)
    	{
    		if (!filesOK[i])
    		{
    			alert=true;
    			missingData+=(labels[i] + "\n");
    		}
    	}
    	
    	if (alert)
    	{
    		JOptionPane.showMessageDialog(null, "Warning! The following files are not defined or do not exist:\n\n" + missingData + "\nAttempting to proceed anyway with defaults if nessecary...");
    	}
    	
    	SSCFileCreator ssc= new SSCFileCreator();
    	
    	if (filesOK[0])//music file
    	{
    		ssc.setMusicFileName(new File(textFields[0].getText()).getName());
    	}
    	if (filesOK[1])//para para chart file
    	{
    		ssc.addParaParaChart(new PPPFile(textFields[1].getText()));
    	}
    	if (filesOK[2])//normal chart file
    	{
    		ssc.addNormalChart(new PPPFile(textFields[2].getText()));
    	}
    	if (filesOK[3])//hard chart file
    	{
    		ssc.addHardChart(new PPPFile(textFields[3].getText()));
    	}
    	if (filesOK[4])//expert chart file
    	{
    		ssc.addExpertChart(new PPPFile(textFields[4].getText()));
    	}
    	if (filesOK[5])//another chart file
    	{
    		ssc.addAnotherChart(new PPPFile(textFields[5].getText()));
    	}
    	
    	//get filename for user to save to
    	PrintWriter out;
    	String fname="";
    	
    	JFileChooser fileChooser = new JFileChooser(JPickerField.dir);
    	
		int returnValue = fileChooser.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			fname=fileChooser.getSelectedFile().getAbsolutePath();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "User canceled save request!");
			return;
		}
		if (fileChooser.getSelectedFile().exists())
		{
			int reply = JOptionPane.showConfirmDialog(null, "The file " + fname + " already exists!\n Overwrite?", "Overwrite file?", JOptionPane.YES_NO_OPTION);
	        if (reply != JOptionPane.YES_OPTION) {
	        	JOptionPane.showMessageDialog(null, "User canceled save request!");
	          return;
	        }
		}
		
		//try to save it
    	try
		{
			 out = new PrintWriter(new FileOutputStream(fname, false));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not open file for saving!\nCanceling!");
			return;
		}
    	
    	//write it out
    	out.write(ssc.generateSSCFile());
    	
    	out.close();
    	JOptionPane.showMessageDialog(null, "Done!");
    }
    
    //helper functions for disabling the GUI during processing
    public static void disableUI()
    {
    	UISet(false);
    }
    
    public static void enableUI()
    {
    	UISet(true);
    }
    
    private static void UISet(boolean b)
    {
    	for (int i=0;i<textFields.length;i++)
    	{
    		browseButtons[i].setEnabled(b);
    		textFields[i].setEnabled(b);
    	}
    	OKButton.setEnabled(b);
    	HelpButton.setEnabled(b);
    	QuitButton.setEnabled(b);
    	
    	
    }

}