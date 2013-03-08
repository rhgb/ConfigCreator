package org.monospace.configcreator;

import java.util.EventListener;

public interface EditListener extends EventListener {
	void contentChanged(String content);
}