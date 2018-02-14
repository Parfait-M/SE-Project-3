

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlarmVisual extends JFrame {

	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton snoozeButton = new JButton("Snooze");
		snoozeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Sorry, this ain't implemented yet");
			}
		});

		JButton stopButton = new JButton("Stop");
		stopButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		stopButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel title_label = new JLabel(obj.getName());
		title_label.setFont(new Font("Tahoma", Font.PLAIN, 32));

		JLabel time_label = new JLabel(obj.getTime());
		time_label.setFont(new Font("Tahoma", Font.PLAIN, 30));

		JLabel date_label = new JLabel(obj.getDate());
		date_label.setFont(new Font("Tahoma", Font.PLAIN, 28));

		JLabel image = new JLabel("");
		image.setIcon(new ImageIcon(AlarmVisual.class.getResource("/media/alarm.gif")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(123)
					.addComponent(title_label)
					.addContainerGap(137, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(image)
							.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(time_label)
								.addComponent(date_label)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(36)
							.addComponent(snoozeButton)
							.addPreferredGap(ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
							.addComponent(stopButton)))
					.addGap(51))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(title_label)
					.addGap(21)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(time_label)
							.addGap(18)
							.addComponent(date_label)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(image)
							.addGap(18)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(snoozeButton)
						.addComponent(stopButton))
					.addGap(24))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public AlarmVisual() {
		// TODO Auto-generated constructor stub
		this(new Alarm());
	}
}
