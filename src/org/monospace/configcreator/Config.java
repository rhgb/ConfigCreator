package org.monospace.configcreator;

public class Config implements Comparable<Config> {
	private String key;
	private String value;
	private int priority;
	/**
	 * @param key
	 * @param value
	 */
	public Config(String key, String value) {
		this.key = key;
		this.value = value;
	}

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
	public boolean equals(Object other) {
		if (!(other instanceof Config)) return false;
		return ((Config) other).key.equals(key);
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
	 * @return the priority value, used for arrange
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * Compare to another <tt>Config</tt> by <tt>priority</tt>, break ties with <tt>key</tt>.
	 */
	@Override
	public int compareTo(Config o) {
		if (o.priority < priority) return -1;
		if (o.priority > priority) return 1;
		return key.compareToIgnoreCase(o.key);
	}
}