package org.monospace.configcreator;

public class ConfigIPAddrElement extends ConfigTextElement {
    private static final String IPADDRESS_PATTERN = 
    		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
    		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public ConfigIPAddrElement(String key, String desc, int priority) {
		super(key, desc, priority);
	}
	@Override
	public boolean isValid() {
		return super.isValid() && getValue().matches(IPADDRESS_PATTERN);
	}
}