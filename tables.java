package week06_Lab_GUI_Table;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class tables {

	private JFrame frame;
	private JTable table;
	 private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tables window = new tables();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public tables() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 451, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 21, 309, 149);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(model=new DefaultTableModel(
));
		loadDataFromFile("students_header.txt");
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Maintance");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Add New Student");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				   int columnCount = model.getColumnCount();
			        String[] newRow = new String[columnCount];

			        // Ask the user for each column's value
			        for (int i = 0; i < columnCount; i++) {
			            newRow[i] = JOptionPane.showInputDialog(frame, "Enter " + model.getColumnName(i) + ":");
			            if (newRow[i] == null) return; // Cancelled input
			        }

			        model.addRow(newRow);		
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Delete Selected Student");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				        int selectedRow = table.getSelectedRow();
				        if (selectedRow >= 0) {
				            model.removeRow(selectedRow);
				        } else {
				            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
				        }
				
				
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Query");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Find Highest Grade Student");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double max=0;
				int maxRow=0;
			    for (int row = 0; row < model.getRowCount(); row++) {               
	                    Object value = model.getValueAt(row, 3);
	                    if (Double.parseDouble(value.toString())>max) {
	                    	max=Double.parseDouble(value.toString());
	                    	maxRow=row;
	                    }                    	      
	            }
	            JOptionPane.showMessageDialog(frame, "The max GPA is=" + max);
	            
	            table.setRowSelectionInterval(maxRow, maxRow);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Calculate Average Grade");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = model.getRowCount();
				
				if(rowCount==0) {
					JOptionPane.showMessageDialog(frame, "NO DATA");	
				}
				
				double sum =0;
				for(int i=0; i<rowCount; i++) {
					Object value = model.getValueAt(i, 3);
					sum += Double.parseDouble(value.toString());
				}
				
				double average = sum/ rowCount;
				JOptionPane.showMessageDialog(frame," The average GPA is " + average);
				
				
				
				JOptionPane.showMessageDialog(frame,"the average gpa is : " + average);
				
			}
			
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Count # of Students >= given Grade");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String grade =JOptionPane.showInputDialog("enter a grade");
				int rowCount = model.getRowCount();
				if(rowCount==0) {
					JOptionPane.showMessageDialog(frame, "NO DATA");	
				}
				int sum = 0;
				for(int i=0; i<rowCount; i++) {
					Object value = model.getValueAt(i, 3);
				    if(Double.parseDouble(grade)<=Double.parseDouble(value.toString())) {
				    	sum++;
				    	
				    }
				    
				}
				JOptionPane.showMessageDialog(frame," number of students " + sum);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenu mnNewMenu_3 = new JMenu("File");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Load students.txt");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataFromFile("students_header.txt");
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Save students.txt");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    saveDataToFile("students_copy.txt");
				
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_7);
		
		JMenu mnNewMenu_2 = new JMenu("Help");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("About");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, " App v1.0");
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);
	
	}
	private void loadDataFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            model.setDataVector(new Object[][] {}, new Object[] {});
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (isFirstLine) {
                    // First line = column headers
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }
                    model.setColumnIdentifiers(values);
                    isFirstLine = false;
                } else {
                   model.addRow(values);
                  
                }
            }
                    	      
     
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	 private void saveDataToFile(String fileName) {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
	            // Write column headers
	            for (int i = 0; i < model.getColumnCount(); i++) {
	                bw.write(model.getColumnName(i));
	                if (i < model.getColumnCount() - 1)
	                    bw.write(",");
	            }
	            bw.newLine();

	            // Write rows
	            for (int row = 0; row < model.getRowCount(); row++) {
	                for (int col = 0; col < model.getColumnCount(); col++) {
	                    Object value = model.getValueAt(row, col);
	                    bw.write(value == null ? "" : value.toString());
	                    if (col < model.getColumnCount() - 1)
	                        bw.write(",");
	                }
	                bw.newLine();
	            }

	            JOptionPane.showMessageDialog(frame, "Data saved successfully to " + fileName);

	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(frame, "Error saving file: " + e.getMessage());
	        }
	    }
}
