import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class AlarmVisual extends JFrame {
	
	private JPanel contentPane;
	private boolean isSnoozed = false;	// used to detect if user snoozed alarm
	// Array of song names located in the media package
	private static String [] tunes = {"clock_buzzer.wav","old_bell.wav","railroad_bell.wav",
							"school_bell.wav","submarine.wav","watch_alarm.wav"};
	private Clip clip;
	
	// called by view to pass on whether user snoozed or not
	public boolean getStatus() {
		return isSnoozed;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlarmVisual frame = new AlarmVisual();
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
	public AlarmVisual(Alarm obj) {
		// Code for the frame. If user presses the 'X' button, it just closes
		// the frame. Not the actual application
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Try to open a music file located in the media folder.
		// if it fails to open, alarm still gets displayed
		try {
			int index = (int)(Math.random()*tunes.length);
			URL url = this.getClass().getClassLoader().getResource("media\\"+tunes[index]);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}catch(Exception e) {
			//should throw exception
		}

		JButton snoozeButton = new JButton("Snooze");
		snoozeButton.setFont(new Font("Sylfaen", Font.BOLD, 18));
		snoozeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				clip.stop();	// stop playing music, if not done.
				isSnoozed = true;
				dispose();
			}
		});

		// Snooze stays as false, because it wasn't pressed...
		JButton stopButton = new JButton("Stop");
		stopButton.setFont(new Font("Sylfaen", Font.BOLD, 19));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				clip.stop();	// just in case song is still playing, stop it
				dispose();
			}
		});
		stopButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		stopButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel time_label = new JLabel(obj.getTime());
		time_label.setFont(new Font("Tahoma", Font.PLAIN, 30));

		String formatted_date = obj.getMonthName().substring(0,3)+" "+obj.getDay()+", "+obj.getYear();
		JLabel date_label = new JLabel(formatted_date);
		date_label.setFont(new Font("Tahoma", Font.PLAIN, 28));

		// Try to load image for alarm. If it fails, just displays text in its place
		JLabel image = new JLabel("");
		try{
			image.setIcon(new ImageIcon(AlarmVisual.class.getResource("/media/alarm.gif")));
		}catch(Exception e) {
			image.setText("Couldn't load image");
		}
		
		JLabel title_label = new JLabel(obj.getName());
		title_label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(image))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(snoozeButton)))
					.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(date_label)
							.addComponent(time_label))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(stopButton)
							.addGap(33)))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(title_label, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(title_label, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(time_label)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(date_label)
							.addGap(36))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(image)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(snoozeButton)
						.addComponent(stopButton))
					.addGap(12))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public AlarmVisual() {
		// TODO Auto-generated constructor stub
		this(new Alarm());
	}
}
