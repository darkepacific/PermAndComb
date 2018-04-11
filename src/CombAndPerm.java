import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CombAndPerm extends JPanel implements ActionListener {
    protected JTextField textField;
    protected static JTextArea textArea;
    protected JTextArea textAreaHeader;
    private final static String newline = "\n";
    
    //Variables
    int counter=0;
    int numberOfItems=0;
    int numberToChoose=0;
    char order = 'A';
    int count=0;
    int permTotal=1;
    int j=1;
    int colDivision=1;
    int total = 0;
    String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CombAndPerm() {
        super(new GridBagLayout());

        textAreaHeader = new JTextArea(2, 40);
        textAreaHeader.setEditable(false);
        JScrollPane scrollPaneHeader = new JScrollPane(textAreaHeader);
        
        textField = new JTextField(40);
        textField.addActionListener(this);

        textArea = new JTextArea(30, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPaneHeader, c);
        
        textAreaHeader.setText("Enter the Number of Items:");
        
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }

    public void actionPerformed(ActionEvent evt) {
    	
    	//Gets the current text from the user
    	String text = textField.getText();
    	
    	//The escape sequence
    	if(text.toUpperCase().equals("RESET") || text.toUpperCase().equals("R")){
    		textAreaHeader.setText("Enter the Number of Items:");
    		textArea.setText("");
    		resetVariables();
    	}
    	else{
    		 counter++;
    	}
    	
    		//Input works on a counter system
    		//Going from 1=numberOfItems, 2=numberToChoose, 3=P or C
    		
    		if(counter==1){
    			numberOfItems = Integer.parseInt(text);
    			textArea.append("¥ Number of Items: " + numberOfItems +newline);
    			textAreaHeader.setText("Enter the Number to Choose:");
    			
    		}
    		
    		if(counter==2){
    			numberToChoose = Integer.parseInt(text);
    			
    			if (numberToChoose > numberOfItems){
    				textAreaHeader.setText("The number to choose must be less than the total number of items!");
    				textArea.append("-----" + newline + "Type 'RESET' to reset." + newline);
    			}
    			else{
    			textArea.append("¥ Number to Choose: " + numberToChoose +newline);
    			textAreaHeader.setText("Enter 'P' or 'C' to print the permutations or combinations:");
    			}
    			
    		}
    		/*PERMUTATIONS*/
    		if(counter==3){
    			order = text.charAt(0);
    			order = Character.toUpperCase(order);
    			if(order=='P'){
    				
    			textArea.append("Printing the Permutations: " + newline);
    			
    			String permutations = new String();
    				//for(int i=1; i<(numberOfItems+1); i++){
    					permutations= alphabet.substring(0,numberOfItems);
    				//}
    				
    			for(int i=0; i<(numberOfItems); i++){
    					permTotal = permTotal*(numberOfItems-i);
    				}
    				colDivision=permTotal;
    			for(int i=0; i<(numberToChoose); i++){
    					colDivision=colDivision/(numberOfItems-i);
    				}
    			
    			textArea.append(permutations + newline + "-----" + newline);
    				
    				
    			StringBuffer strBuf = new StringBuffer(permutations);
    			doPerm(strBuf, numberOfItems);
    			//Prompts the user to reset at the end
    			textArea.append("Total Permutations: " + total + newline);
    			textArea.append("-----" + newline + "Type 'RESET' to reset." + newline);
    			
    			}			/*COMBINATIONS*/
    			else if(order=='C'){
    			
    			
    			int N = Integer.parseInt("" + numberOfItems);
				   String elements = alphabet.substring(0, N);
				   
				   textArea.append("Printing the Combinations: " + newline + elements + newline + "-----" + newline);
				   	
				   // using first implementation
				   comb1(elements);
				   textArea.append("Total Combinations: " + total + newline);
				   textArea.append("-----" + newline + "Type 'RESET' to reset." + newline);
    			}
    			else if(order!='P' || order!='C'){
    			textArea.append("You entered and invalid character. Type 'RESET' to reset." + newline);
    			counter=3;
					
    			}
    			
    			
    		}
    		
    		
         
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Combinations and Permutations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new CombAndPerm());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        //frame.setIconImage(Toolkit.getDefaultToolkit().getImage("AppIcon.icns"));
        
    }

    public static void main(String[] args) throws Exception {
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
    }
    
    private String doPerm(StringBuffer str, int index){
        if(index <= 0){
        	count++;
        	if(count==colDivision*j){
        		textArea.append(str.toString().substring(0, numberToChoose) + newline);
        		j++;
        		total+=1;
        		//textArea.append("j: " + j + " p/nI " + (perm/numberOfItems) + " c: " + count + newline);
        	}
        	
            }  
        else { //recursively solve this by placing all other chars at current first pos
            doPerm(str, index-1);
            int currPos = str.length()-index;
            for (int i = currPos+1; i < str.length(); i++) {//start swapping all other chars with current first char
                swap(str,currPos, i);
                doPerm(str, index-1);
                swap(str,i, currPos);//restore back my string buffer
            }
        }
    	return str.toString();
    }
    
    private  static void swap(StringBuffer str, int pos1, int pos2){
        char t1 = str.charAt(pos1);
        str.setCharAt(pos1, str.charAt(pos2));
        str.setCharAt(pos2, t1);
    } 
    
    private void resetVariables(){	//resets all reset-relying variables
    	counter=0;
		numberOfItems=0;
		numberToChoose=0;
		order = 'A';
		count=0;
		permTotal=1;
		j=1;
		colDivision=1;
		total = 0;
    }
    
 // print all subsets of the characters in s
 	public void comb1(String s) { comb1("", s); }
 	
 	// print all subsets of the remaining elements, with given prefix
 	private void comb1(String prefix, String s) {
	    if (s.length() > 0) {
	    	if((prefix + s.charAt(0)).length()==numberToChoose){
	    	textArea.append(prefix + s.charAt(0) + newline);
	    	total+=1;
	    	}
	        comb1(prefix + s.charAt(0), s.substring(1));
	        comb1(prefix,               s.substring(1));
	    }
	}  
}
