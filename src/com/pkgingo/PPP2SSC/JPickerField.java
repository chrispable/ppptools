package com.pkgingo.PPP2SSC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class JPickerField implements ActionListener
{
	final private JTextField[] textFields;
	final private int fNo;
	static public String dir = System.getProperty("user.home");

	JPickerField(final JTextField[] theFields, int fieldNo)
	{
		super();
		textFields = theFields;
		fNo=fieldNo;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser(dir);
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();

			//remember picked directory for later
			dir=selectedFile.getParent();
			if (dir==null) //just in case of a problem
			{
				dir = System.getProperty("user.home");
			}
			//set the path to the picked file
			String file=selectedFile.getAbsolutePath();
			textFields[fNo].setText(file);
			
			//get prefixes
			String prefix = file.substring(0,file.length()-5);
			String suffix = file.substring(file.length()-4,file.length());
			
			//check for charts in AC naming format, but only those AFTER the ones we pick.
			//Some charts will not have para para charts, some wont have anothers
			if (fNo>0)
			{
				for (int i=fNo+1;i<textFields.length;i++)
				{
					setField(prefix, suffix, i);
				}
				
			}

		}
	}
	
	//cheap trick to set all the fields to the right file name if it matches AC spec
	private void setField(String prefix, String suffix, int field)
	{
		String c = "1";
		switch(field)
		{
			default:
			case 1:
				c="1";
				break;
			case 2:
				c="A";
				break;
			case 3:
				c="B";
				break;
			case 4:
				c="C";
				break;
			case 5:
				c="F";
				break;
		}
		File f = new File(prefix+c+suffix);
		if (f.exists())
		{
			textFields[field].setText(f.getAbsolutePath());
		}
	}

}
