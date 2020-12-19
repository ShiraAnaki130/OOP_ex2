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
/**
 * This class is the main class of the project.
 * By using this class- the game will start at the scenario number of the game.
 * There are two options for starting the game:
 * 1. from the Command Prompt- by using the command: java -jar Ex2.jar **your id** **scenario number**.
 * 2. by running this class and input you id and scenario number at the correct fields.
 * one can choose only scenario number at the range [0,23].
 * @author Lea&Shira.
 */
public class Ex2 implements ActionListener {
	private static JLabel idLabel;
	private static JTextField idText;
	private static JLabel scenario_numLable;
	private static JTextField scenario_numText;
	private static JButton button;
	private static JLabel success;
	private static int game_scenario;
	private static int id;
    public static void main(String[] args){
    	if(args.length==0) {
        JPanel _panel= new JPanel();
        JFrame _frame= new JFrame();
        _frame.setSize(350, 170);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _panel.setLayout(null); 
        _panel.setBackground(Color.orange);
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
          	  int _id = Integer.parseInt(args[0]);
              int _scenario_num = Integer.parseInt(args[1]);
              Thread game1 = new Thread(new Ex2_Client(_id,_scenario_num));
              game1.start();	
          }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean bol=false;
		for(int i=0;(i<idText.getText().length())&&(bol==false);i++) {
			if(!(idText.getText().charAt(i)>=48&&idText.getText().charAt(i)<=57))
				bol=true;
		}
		if(bol||idText.getText().isBlank()||idText.getText().length()>9)success.setText("Invalid id number");
		else {
			id=Integer.parseInt(idText.getText());
			game_scenario= Integer.parseInt(scenario_numText.getText());
			Thread game1 = new Thread(new Ex2_Client(id,game_scenario));
			game1.start();
		}
		
	}
}
