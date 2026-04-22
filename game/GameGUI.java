package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextArea sceneDescription;
	private static JLabel lblImage;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScene;
	private static AdventureGame game;
	private static JTextField userInventory;
	private static JTextField txtItemsRoom;
	private static JButton btnPickupItem;
	
	

	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		AdventureGame game = new AdventureGame();
        //game.play();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGUI() {
		game = new AdventureGame();
		currentScene = game.getCurrentScene();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 530);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ADVENTURE GAME");
		lblTitle.setFont(new Font("Mongolian Baiti", Font.BOLD, 10));
		lblTitle.setBounds(270, 21, 166, 46);
		contentPane.add(lblTitle);
		
		lblImage = new JLabel("");
		
		
		ImageIcon icon = new ImageIcon(GameGUI.class.getResource("/images2/lobby.png"));
		Image img = icon.getImage();
		
		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		
		lblImage.setIcon(scaledIcon);
		lblImage.setBounds(209, 64, 244, 247);
		contentPane.add(lblImage);
		
		sceneDescription = new JTextArea();
		sceneDescription.setWrapStyleWord(true);
		sceneDescription.setBounds(209, 367, 244, 71);
		contentPane.add(sceneDescription);
		
		btnChoice1 = new JButton("New button");
		btnChoice1.setBackground(new Color(255, 255, 255));
		btnChoice1.setBounds(10, 193, 84, 82);
		contentPane.add(btnChoice1);
		
		btnChoice1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				int nextId = currentScene.getChoices().get(0).getNextSceneId();
	            currentScene = game.getScenes().findSceneById(nextId);
	            
	            updateSceneDisplay(); 
	            
	         
			}
			
		});
		
		
		btnChoice2 = new JButton("New button");
		btnChoice2.setBackground(new Color(255, 255, 255));
		btnChoice2.setBounds(592, 193, 84, 82);
		contentPane.add(btnChoice2);
		
		userInventory = new JTextField();
		userInventory.setBounds(208, 448, 245, 33);
		contentPane.add(userInventory);
		userInventory.setColumns(10);
		
		txtItemsRoom = new JTextField();
		txtItemsRoom.setText("Inventory: empty");
		txtItemsRoom.setColumns(10);
		txtItemsRoom.setBounds(153, 324, 358, 33);
		contentPane.add(txtItemsRoom);
		
		btnPickupItem = new JButton("Pick Up Item");
		btnPickupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Item itemInRoom = currentScene.getItem();
				
		        if (currentScene.getItem() != null) {
		            game.getPlayer().addItem(itemInRoom);
		            currentScene.removeItem();
		            updateSceneDisplay();
		            
		            if (currentScene.getSceneId() == 5) {
		            	checkWinCondition();
		            }
			}
				
			}
		});
		btnPickupItem.setBounds(26, 392, 152, 46);
		contentPane.add(btnPickupItem);
		
		btnChoice2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				int nextId = currentScene.getChoices().get(1).getNextSceneId();
	            currentScene = game.getScenes().findSceneById(nextId);
	            
	            updateSceneDisplay();
	            
	            if (currentScene.getSceneId() == 5) {
	            	checkWinCondition();
	            }
			}
		});
		
		updateSceneDisplay();
	}
		
	public void checkWinCondition() {
	    boolean hasKeycard = game.getPlayer().hasItem("Keycard");
	    boolean hasCodeNote = game.getPlayer().hasItem("Code Note");

	    if (hasKeycard && hasCodeNote) {
	        javax.swing.JOptionPane.showMessageDialog(this,
	            "You used the Keycard and the Code Note to unlock the exit.\nYou escaped.");
	    } 
	    else {
	        javax.swing.JOptionPane.showMessageDialog(this,
	            "The exit will not open.\nYou are missing the required items.\nYou need the Keycard and the Code Note.");
	    }
	    
	    btnChoice1.setEnabled(false);
	    btnChoice2.setEnabled(false);
	    btnPickupItem.setEnabled(false);
	}
		
		
	

		public void updateSceneDisplay() {
			

		
		sceneDescription.setText(currentScene.getDescription());
		System.out.println(currentScene);
		Item itemInRoom = currentScene.getItem();
		
		if(itemInRoom == null)
			txtItemsRoom.setText("Nothing In Here");
		else
			txtItemsRoom.setText(currentScene.getItem().toString());
		
		userInventory.setText(game.getPlayer().getInventoryText());

		ImageIcon icon = new ImageIcon(currentScene.getImagePath());
		Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblImage.setIcon(new ImageIcon(img));

		btnChoice1.setText("<html>" + currentScene.getChoices().get(0).getText() + "</html>");
		btnChoice2.setText("<html>" + currentScene.getChoices().get(1).getText() + "</html>");

		if (currentScene.getSceneId() == 5) {
		    checkWinCondition();
		}
		
		

	}
}
