package org.monospace.configcreator;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import org.monospace.configcreator.ConfigSelectElement.ConfigOption;

public class ConfigSelectComponent extends ConfigComponent {
	/**
	 * 
	 */
	private JComboBox comboBox;
	private Vector<ConfigOption> options;
	public ConfigSelectComponent(ConfigElement element) {
		super(element);
		this.options = ((ConfigSelectElement) element).getOptions();
		comboBox = new JComboBox(options);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					invokeEditListeners(((ConfigOption) e.getItem()).getValue());
				}
			}
		});
		input = comboBox;
	}
	@Override
	public void setValue(String value) {
		int index = -1;
		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).equals(value)) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			comboBox.setSelectedIndex(index);
		} else {
			comboBox.setSelectedIndex(0);
		}
		comboBox.setVisible(true);
	}
	@Override
	public String getValue() {
		return ((ConfigOption) comboBox.getSelectedItem()).getValue();
	}
}