package org.monospace.configcreator;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class ConfigComponent extends JPanel {
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
	private static final long serialVersionUID = -5051561242351334873L;
	protected JLabel nameLabel;
	protected JLabel messageLabel;
	protected JComponent input;
	protected EditListener listener;
	public ConfigComponent(String name) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		nameLabel = new JLabel(name);
		add(nameLabel);
		messageLabel = new JLabel();
		messageLabel.setForeground(Color.RED);
		messageLabel.setText("Invalid input");
		messageLabel.setVisible(false);
		add(messageLabel);
	}
	protected void insertInputComponent(JComponent input) {
		if (this.input != null) {
			remove(this.input);
		}
		this.input = input;
		for (int i = 0; i < getComponentCount(); i++) {
			if (getComponent(i) == nameLabel) {
				add(input, i+1);
				break;
			}
		}
	}
	public void setEditListener(EditListener listener) {
		this.listener = listener;
	}
	public void setName(String name) {
		nameLabel.setText(name);
	}
	public abstract void setValue(String value);
	public abstract String getValue();
	public void setWarning(boolean b) {
		messageLabel.setVisible(b);
	}
}
