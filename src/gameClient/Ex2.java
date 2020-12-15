package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

<<<<<<< HEAD
public class Ex2 implements ActionListener {
	private static JLabel idLabel;
	private static JTextField idText;
	private static JLabel scenario_numLable;
	private static JTextField scenario_numText;
	private static JButton button;
	private static JLabel success;
	private static JLabel pic;
	private static BufferedImage image;
	private static int game_scenario=-1;
	private static int id=-1;
	
	
    public static void main(String[] args){
    	if(args.length==0) {
        JPanel _panel= new JPanel();
        JFrame _frame= new JFrame();
        _frame.setSize(350, 250);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        _panel.setLayout(null); 
        //_panel.setBackground(Color.YELLOW);
        try {
			image = ImageIO.read(new File("data/pokemonsStart.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        pic=new JLabel(new ImageIcon(image)); 
      //  pic.show();
        _panel.add(pic);
        Font font= new Font("Forte", Font.BOLD, 18); 
        idLabel=new JLabel("id");
        idLabel.setFont(font);
        idLabel.setBounds(10, 20, 80, 25);
        _panel.add(idLabel);
        scenario_numLable=new JLabel("game num");
        scenario_numLable.setFont(font);
        scenario_numLable.setBounds(10, 50, 100, 25);
        _panel.add(scenario_numLable);
        idText= new JTextField(20);
        idText.setBounds(100, 20, 165, 25);
        _panel.add(idText);
        scenario_numText= new JTextField();
        scenario_numText.setBounds(100, 50, 165, 25);
        _panel.add(scenario_numText);
        button= new JButton("start");
        button.setFont(font);
        button.setBounds(130,100,80,25);
        button.addActionListener(new Ex2());
        _panel.add(button);
        success= new JLabel("");
        success.setBounds(10,110,300,25);
        _panel.add(success);
        _frame.add(_panel);
        _frame.setVisible(true);
    	}
    	else {
          	  int _id = 208375600; //Integer.parseInt(args[0]);
              int _scenario_num = 11; //Integer.parseInt(args[1]);
              Thread game1 = new Thread(new Ex2_Client(_id,_scenario_num));
              game1.start();
          	
          }
    
=======
        int _id = 999; //Integer.parseInt(args[0]);
        int _scenario_num = 22; //Integer.parseInt(args[1]);
        Thread game1 = new Thread(new Ex2_Client(_id,_scenario_num));
        game1.start();
>>>>>>> 67ba3b21ee8e68bb9ee07436f1c1d4e60ea30f2f
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		id=Integer.parseInt(idText.getText());
		game_scenario= Integer.parseInt(scenario_numText.getText());
		if(id==-1||game_scenario<0||game_scenario>23) {
			success.setText("Invalid game number");
		}
		else {
			Thread game1 = new Thread(new Ex2_Client(id,game_scenario));
			game1.start();
		}
		
	}
}
