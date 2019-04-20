
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.*;

public class TheWeather extends JFrame implements ItemListener {

	Weather weather;
	JLabel w_info;
	String unit="metric";
/*	JLabel w_desc;
	JLabel w_temp;
	JLabel w_pressure;
	JLabel */
	JRadioButton unit_m = new JRadioButton("Metric", true); //selected
	JRadioButton unit_i = new JRadioButton("Imperial");
	JTextField citytf = new JTextField("Location");
	JLabel imglabel = new JLabel();
	String iconurl = null;
	
	TheWeather() {
		setLayout(new GridBagLayout());


		JButton gobtn = new JButton("GO");
		JButton currbtn = new JButton("Current City");
		JPanel searchpanel = new JPanel(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1;
		c2.weighty = 0.5;
		searchpanel.add(citytf, c2);
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 1;
		c2.gridy = 0;
		c2.weightx = 0;
		c2.weighty = 0.5;
		searchpanel.add(gobtn, c2);
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 2;
		c2.gridy = 0;
		c2.weightx = 0;
		c2.weighty = 0.5;
		searchpanel.add(currbtn, c2);

		gobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				w_info.setText("Loading...");
				loadWeatherInfo();
			}
		});

		currbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String city = IPLocation.getCity("");
					citytf.setText(city);
					w_info.setText("Loading...");
					loadWeatherInfo();
				} catch(Exception ex) {
					String errstr = "Check your internet connection and try again.";
					System.out.println(errstr);
					ex.printStackTrace();
					showError(errstr);
				}
			}
		});

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridwidth = 2;
		add(searchpanel, c);

		JPanel units_panel = new JPanel();
		units_panel.add(new JLabel("Units: "));
		units_panel.add(unit_m);
		units_panel.add(unit_i);
		ButtonGroup units_group = new ButtonGroup();
		units_group.add(unit_m);
		units_group.add(unit_i);

		unit_m.addItemListener(this);
		unit_i.addItemListener(this);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridwidth = 2;
		add(units_panel, c);

		JPanel attr_panel = new JPanel(new GridLayout());
		w_info = new JLabel();
		weather = null;
		w_info.setText("Loading...");
		try {
			String city = IPLocation.getCity("");
			citytf.setText(city);
			loadWeatherInfo();
		} catch(Exception e) {
			String errstr = "Check your internet connection and try again.";
			System.out.println(errstr);
			e.printStackTrace();
			showError(errstr);
			dispose();
		}
		

		attr_panel.add(w_info);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(imglabel, c);

		attr_panel.setBorder(BorderFactory.createLineBorder(Color.blue));
		

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0.5;
		add(attr_panel,c);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(800,600);
	}

	public void itemStateChanged(ItemEvent e) {
		if(unit_m.isSelected())
			weather.unit = unit = "metric";
		else weather.unit = unit = "imperial";
		w_info.setText("Loading...");
		loadWeatherInfo();
	}

	void loadWeatherInfo() {
		try {
			weather = Weather.fromCity(citytf.getText(),unit);
			w_info.setText(weather.getWeatherInfo());
			//w_info.setText("<html>Sometext<br/>Some more text<br/>sadfsdf<br/>hello</html>");
			iconurl = weather.getIconURL();
		} catch(Exception e) {
			String errstr = "Couldn't get weather info.";
			System.out.println(errstr);
			e.printStackTrace();
			showError(errstr);
		};
		try {
			InputStream is;
			if(iconurl == null) {
				is = new FileInputStream(new File("error.png"));
			} else is = (new URL(iconurl)).openConnection().getInputStream();
			Image weath_image = ImageIO.read(is);
			imglabel.setIcon(new ImageIcon(weath_image));
			//JPanel curweathpanel = new JPanel();
		} catch(Exception e) {
			System.out.println("Error while loading image");
			//showError("Error while loading weather icon");

		}
	}

	void showError(String str) {
		JOptionPane.showMessageDialog(this, str, "A problem occurred", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String args[]) {
		new TheWeather();
	}
}
