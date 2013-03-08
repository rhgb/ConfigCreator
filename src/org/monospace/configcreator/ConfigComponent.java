package org.monospace.configcreator;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class ConfigComponent {
	protected JLabel nameLabel;
	protected JLabel messageLabel;
	protected JComponent input;
	protected ConfigElement configElement;
	EditListener listener;
	
	protected static final int NAME_LABEL_WIDTH = 200;
	protected static final int INPUT_WIDTH = 400;
	public ConfigComponent(ConfigElement element) {
		configElement = element;
		nameLabel = new JLabel(element.getDescription());
		messageLabel = new JLabel();
		messageLabel.setForeground(Color.RED);
		messageLabel.setText("Invalid input");
		messageLabel.setVisible(false);
	}
	public void setEditListener(EditListener listener) {
		this.listener = listener;
	}
	public void invokeEditListeners(String content) {
		listener.contentChanged(content);
	}
	public void setName(String name) {
		nameLabel.setText(name);
		nameLabel.setVisible(true);
	}
	public abstract void setValue(String value);
	public abstract String getValue();
	public void setWarning(boolean b) {
		messageLabel.setVisible(b);
	}
	public JLabel getNameLabel() {
		return nameLabel;
	}
	public JLabel getMessageLabel() {
		return messageLabel;
	}
	public JComponent getInput() {
		return input;
	}
}
