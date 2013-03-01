package org.monospace.configcreator;

public class Config {
	private String key;
	private String value;
	private ConfigTemplateElement template;
	/**
	 * @param key
	 * @param value
	 */
	public Config(String key, String value) {
		this.key = key;
		this.value = value;
	}
	/**
	 * @param str String to be converted
	 * @return converted <tt>Config</tt> object
	 */
	public static Config parse(String str) {
		String[] segs = str.split("=", 2);
		if (segs.length != 2) return null;
		String key = segs[0].trim();
		String value = segs[1].trim();
		if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
			value = value.substring(1, value.length() - 1);
		}
		return new Config(key, value.trim());
	}
	/**
	 * @return formatted string of config (no newline at the end)
	 */
	@Override
	public String toString() {
		return key + "=\"" + value + "\"";
	}
	/**
	 * @param other the object to be compared
	 * @return <tt>false</tt> if <tt>other</tt> not an instance of <tt>Config</tt>.
	 * <tt>true</tt> if {@code other.key} equals to <tt>key</tt>. <tt>false</tt> elsewise.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Config) {
			return ((Config) other).key.equals(key);
		}
		if (other instanceof ConfigTemplateElement) {
			return ((ConfigTemplateElement) other).getKey().equals(key);
		}
		return false;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value.trim();
	}
	/**
	 * @return the template
	 */
	public ConfigTemplateElement getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(ConfigTemplateElement template) {
		this.template = template;
	}
	/**
	 * validate the config.
	 * @return <tt>true</tt> if <tt>template</tt> is not <tt>null</tt> and the config is valid. <tt>false</tt> otherwise.
	 */
	public boolean validate() {
		return template == null ? false : template.validate(this);
	}
}