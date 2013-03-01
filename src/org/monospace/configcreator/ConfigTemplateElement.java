package org.monospace.configcreator;

public abstract class ConfigTemplateElement {
	private String key;
	private String description;
	private int priority;
	public ConfigTemplateElement(String key, String desc, int priority) {
		this.setKey(key);
		this.setDescription(desc);
		this.setPriority(priority);
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
		this.key = key.trim().toUpperCase();
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
	public boolean validate(Config config) {
		return config != null && key.equals(config.getKey()) && config.getValue().trim().length() != 0;
	}
	@Override
	public boolean equals(Object that) {
		if (that instanceof ConfigTemplateElement) {
			return that.getClass() == getClass() && ((ConfigTemplateElement) that).getKey().equals(key);
		} else if (that instanceof Config) {
			return ((Config) that).getKey().equals(key);
		}
		return false;
	}
	@Override
	public String toString() {
		return key + " : " + description;
	}
}
