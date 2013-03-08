package org.monospace.configcreator;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ConfigTextComponent extends ConfigComponent {
	private JTextField textField;
	public ConfigTextComponent(ConfigElement element) {
		super(element);
		textField = new JTextField();
		textField.setColumns(18);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				invokeEditListeners(textField.getText().trim());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				invokeEditListeners(textField.getText().trim());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				invokeEditListeners(textField.getText().trim());
			}
		});
		input = textField;
	}
	@Override
	public void setValue(String value) {
		textField.setText(value);
		textField.setVisible(true);
	}
	@Override
	public String getValue() {
		return textField.getText();
	}
}