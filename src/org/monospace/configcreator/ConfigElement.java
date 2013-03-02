package org.monospace.configcreator;

import org.monospace.configcreator.ConfigComponent.EditEvent;
import org.monospace.configcreator.ConfigComponent.EditListener;

public abstract class ConfigElement implements Comparable<ConfigElement> {
	private String key;
	private String value;
	private String description;
	private int priority;
	private ConfigComponent component;
	public ConfigElement(String key, String desc, int priority) {
		this.setKey(key);
		this.setDescription(desc);
		this.setPriority(priority);
		this.value = "";
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		if (key == null) throw new NullPointerException();
		this.key = key.trim().toUpperCase();
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value, boolean sync) {
		if (value == null) throw new NullPointerException();
		if (!value.trim().equals(this.value)) {
			this.value = value.trim();
		}
		if (sync && !this.value.equals(component.getValue())) {
			component.setValue(this.value);
		}
	}
	public void clearValue() {
		this.value = "";
		component.setValue("");
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		if (description == null) throw new NullPointerException();
		this.description = description;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority > 0 ? priority : 0;
	}
	public boolean isValid() {
		return value != null && value.trim().length() != 0;
	}
	public boolean checkValidity() {
		boolean res = isValid();
		if (component != null) {
			component.setWarning(!res);
		}
		return res;
	}
	@Override
	public boolean equals(Object that) {
		if (that instanceof ConfigElement) {
			return that.getClass() == getClass() && ((ConfigElement) that).getKey().equals(key);
		} else if (that instanceof String) {
			return ((String) that).equals(key);
		}
		return false;
	}
	@Override
	public String toString() {
		return key + "=\"" + value + "\"";
	}
	public ConfigComponent getComponent() {
		return component;
	}
	public void setComponent(ConfigComponent component) {
		this.component = component;
		this.component.addEditListener(new EditListener() {
			@Override
			public void contentChanged(EditEvent e) {
				String newVal = e.getContent().trim();
				if (!newVal.equals(value)) {
					setValue(newVal, false);
					checkValidity();
				}
			}
		});
	}
	public void addEditListener(EditListener listener) {
		if (component != null) {
			component.addEditListener(listener);
		}
	}
	@Override
	public int compareTo(ConfigElement o) {
		if (priority < o.getPriority()) return -1;
		if (priority > o.getPriority()) return 1;
		return key.compareTo(o.getKey());
	}
}
