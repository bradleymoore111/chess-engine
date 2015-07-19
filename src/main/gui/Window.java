package main.gui;

import java.awt.*;
import java.awt.event.*;
public class Window extends Frame implements ActionListener{
	private Label labelCount;
	private TextField textFieldCount;
	private Button buttonCount;
	private int count=0;
	public Window(){
		setLayout(new FlowLayout());

		labelCount = new Label("Total times pressed");
		add(labelCount);
		textFieldCount = new TextField("0",10);
		textFieldCount.setEditable(false);
		add(textFieldCount);

		buttonCount = new Button("ADD SHIT");
		add(buttonCount);

		buttonCount.addActionListener(this);

		setTitle("My Counter");
		setSize(250,100);

		setVisible(true);
	}

	public static void main(String[] args) {
		Window applet = new Window();
	}

	@Override
	public void actionPerformed(ActionEvent evt){
		count++;

		textFieldCount.setText(count+"");
	}
}