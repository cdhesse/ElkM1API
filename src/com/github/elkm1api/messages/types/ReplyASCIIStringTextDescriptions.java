/**
 * Copyright 2012 Christopher D. Hesse
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.elkm1api.messages.types;

import com.github.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.19.2 Reply With ASCII String Text Descriptions (SD)
 * 
 * Reply format:
 * 1B – Length as ASCII hex
 * SD – Reply ASCII Lighting Device Command
 * 07 – Lighting device description
 * NNN – Which lighting device description being returned,
 * 001 – 192
 * Text[16] – 16 ASCII characters, “space” character
 * (20 hex) filled if less than 16 characters.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 1DSD07001Hall Light 0089D2
 * Lighting Device 001 Description – “Hall Light”
 * 
 * Note: The high bit of the first character in the text string may be set as the “Show
 * On Keypad” bit. Mask out the high bit for proper ASCII display.
 * If the first character in a requested name is a “space” or less, then the next
 * names are searched until a name is found whose first character is greater than
 * “space” or the “Show On Keypad” bit is set. If no valid names are found, a “000”
 * for the NNN address is returned. This speeds up the loading of names so that invalid
 * names are not returned.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyASCIIStringTextDescriptions extends Message {

	private String lightingDevice;
	private String description;
	
	public ReplyASCIIStringTextDescriptions(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		lightingDevice = message.substring(6, 9);
		description = message.substring(9, 25);
		description = description.trim();
	}
	
	public String toString() {
		return "Lighting device " + lightingDevice + " description \"" + description + "\".";
	}

	public String getLightingDevice() {
		return lightingDevice;
	}

	public String getDescription() {
		return description;
	}
}
