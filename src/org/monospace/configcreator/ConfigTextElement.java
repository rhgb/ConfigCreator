package org.monospace.configcreator;

public class ConfigTextElement extends ConfigElement {
	public ConfigTextElement(String key, String desc, int priority) {
		super(key, desc, priority);
	}
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	@Override
	public ConfigComponent createComponent() {
		return new ConfigTextComponent(this);
	}
}