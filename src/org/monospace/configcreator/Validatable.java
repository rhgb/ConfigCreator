/**
 * 
 */
package org.monospace.configcreator;

public interface Validatable {
	/**
	 * Validate the value.
	 * @return <tt>false</tt> if value is not valid or is not validatable. <tt>true</tt> if value is valid.
	 */
	public boolean isValid();
}
