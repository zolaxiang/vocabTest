import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Test {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Model model = new Model();
			HiFrame frame = new HiFrame(model);
			frame.setTitle("VocabTest");
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}

class HiFrame extends JFrame {
	Model model;
	JTextField text;
	Clipboard clipboard;

	public HiFrame(Model model) {
		this.model = model;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem saveItem = new JMenuItem("Save Results");
		fileMenu.add(saveItem);
		saveItem.addActionListener(new saveAction());

		text = new JTextField(20);
		text.setText(model.getWord());
		JButton yesButton = new JButton("Yes");
		JButton noButton = new JButton("No");
		JButton nextButton = new JButton("Next");
		yesButton.addActionListener(new yesAction());
		noButton.addActionListener(new noAction());
		nextButton.addActionListener(new nextAction());

		JPanel panel = new JPanel();
		panel.add(text);
		panel.add(yesButton);
		panel.add(noButton);
		panel.add(nextButton);
		add(panel);
		pack();

		addWindowListener(new windowClose());
	}

	private class yesAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			model.yes();
			text.setText(model.getWord());
		}
	}

	private class noAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String str = text.getText();
			int i = str.indexOf('.');
			if (i != -1) // has a '.'
				str = str.substring(0, i);
			StringSelection selection = new StringSelection(str);
			clipboard.setContents(selection, null);
		}
	}

	private class nextAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			text.setText(model.getWord());
		}
	}

	private class saveAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			model.saveResults();
		}
	}

	private class windowClose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			int selection = JOptionPane.showConfirmDialog(null, "Save Results?", "Title", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (selection == JOptionPane.OK_OPTION)
				model.saveResults();
		}
	}
}






