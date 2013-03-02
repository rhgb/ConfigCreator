package org.monospace.configcreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JLabel;

public abstract class ConfigComponent {
	public interface EditListener extends EventListener {
		void contentChanged(EditEvent e);
	}
	public class EditEvent extends EventObject {
		private static final long serialVersionUID = -2819107129731143463L;
		public EditEvent(String content) {
			super(content);
		}
		public String getContent() {
			return (String) source;
		}
	}
	protected JLabel nameLabel;
	protected JLabel messageLabel;
	protected JComponent input;
	protected ArrayList<EditListener> listenerList;
	
	protected static final int NAME_LABEL_WIDTH = 200;
	protected static final int INPUT_WIDTH = 400;
	public ConfigComponent(String name) {
		nameLabel = new JLabel(name);
		messageLabel = new JLabel();
		messageLabel.setForeground(Color.RED);
		messageLabel.setText("Invalid input");
		messageLabel.setVisible(false);
		listenerList = new ArrayList<EditListener>(2);
	}
	public void addEditListener(EditListener listener) {
		listenerList.add(listener);
	}
	public void invokeEditListeners(EditEvent e) {
		for (int i = 0; i < listenerList.size(); i++) {
			listenerList.get(i).contentChanged(e);
		}
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
