package org.monospace.configcreator;

public class ConfigTextElement extends ConfigElement {
	private boolean allowWhitespace;
	public ConfigTextElement(String key, String desc, int priority) {
		super(key, desc, priority);
		allowWhitespace = true;
	}
	public void setAllowWhitespace(boolean b) {
		allowWhitespace = b;
	}
	@Override
	public boolean isValid() {
		if (!allowWhitespace && getValue().contains("\b\t\n\r\f")) return false;
		return super.isValid();
	}
	@Override
	public ConfigComponent createComponent() {
		return new ConfigTextComponent(this);
	}
}