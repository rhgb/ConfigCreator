package org.monospace.configcreator;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import java.awt.BorderLayout;

public class ConfigPreviewFrame extends JFrame {
	private static final long serialVersionUID = -6043751542326053006L;
	private JTextPane previewPane;
	private ConfigSet configSet;
	public ConfigPreviewFrame(ConfigSet set) {
		setTitle("Preview");
		setLocationByPlatform(true);
		setSize(400, 400);
		previewPane = new JTextPane();
		previewPane.setEditable(false);
		getContentPane().add(previewPane, BorderLayout.CENTER);
		configSet = set;
		refreshContent();
	}
	public void refreshContent() {
		previewPane.setText("");
		for (int i = 0; i < configSet.size(); i++) {
			try {
				previewPane.getDocument().insertString(previewPane.getDocument().getLength(), configSet.get(i).toString()+"\n", null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
