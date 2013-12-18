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
 * 4.20 Request Keypad Area Assignments (KA)
 * This request command allows automation equipment to request the Areas that all keypads
 * are assigned to. The return string contains a 16 byte array with keypad 1’s area at array index
 * 0 and keypad 15’s area in array index 15. M1 Version 4.2.5 and after.
 * 
 * 4.20.1 Request Keypad Area Assignment (ka)
 * 06 – Length as ASCII hex
 * ka – Request keypad areas
 * 00 – future use
 * 6E – Checksum
 * 
 * Example: 06ka006E Request keypad areas.
 * 
 * 4.20.2 Reply With Keypad Areas (KA)
 * 16 – Length as ASCII hex
 * KA – Returned Keypad Areas Command
 * D[16] – array of 16 ascii bytes with the first byte corresponding
 * to keypad 1’s area assignment (area ‘1’ to ‘8’), last byte is
 * keypad 16’s area assignment.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 16KA12345678111111110081
 * Returned value = keypads 1 to 8 are assigned to areas 1 to 8.
 * Keypads 9 to 16 are assigned to area 1.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyKeypadAreas extends Message {

	private char[] keypadAreas; 
	
	public ReplyKeypadAreas(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		keypadAreas = message.substring(4, 20).toCharArray();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Keypad Areas:\n");
		for (int i = 0; i < 16; i++) {
			sb.append("Keypad ");
			sb.append(i);
			sb.append(", Area Assignment ");
			sb.append(keypadAreas[i]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public char[] getKeypadAreas() {
		return keypadAreas;
	}
}
