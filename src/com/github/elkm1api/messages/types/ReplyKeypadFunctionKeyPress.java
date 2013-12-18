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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.22 Keypad Function Key Press (KF)
 * 
 * This command simulates a function key being pressed on a keypad. This will only be
 * single key press even if the M1 Control is programmed for double function key press. M1
 * Version 4.2.5 and after. ‘*’ key M1 Version 4.2.6 and after, ‘C’ key M1 Version 4.3.2 and
 * after.
 * 
 * 4.22.1 Request Keypad Function Key Press (kf)
 * 09 – Length as ASCII hex
 * kf – Function key pressed
 * NN – Keypad 01 to 16
 * D - Which function key pressed, 1 to 6 ASCII, ‘*’ = 0x2A to
 * silence trouble beep on keypads. ‘C’ = 0x43 to control Chime,
 * ‘0’ function key value will only return the “KF” command
 * 00 – future use
 * CC – Checksum
 * Example: 09kf01100D4 Keypad 01, Function Key 1 to be pressed
 * 
 * 4.22.2 Reply Keypad Function Key Press (KF)
 * 11 – Length as ASCII hex
 * KF – Function key pressed
 * NN – Keypad 01 to 16
 * D - Which function key pressed, 1 to 6 ASCII, ‘*’ = 0x2A, ‘C’ =
 * Chime.
 * CM[8] – Chime mode for each area 1 to 8, ‘0’= Off, ‘1’=Chime only,
 * ‘2’ = Voice only, ‘3’= Chime and voice.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 11KF01C200000000087 Function key reply with Chime Mode set
 * to voice only in Area 1.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyKeypadFunctionKeyPress extends Message {

	private static final Map<String, String> CHIME_MODES = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Off");
					put("1", "Chime only");
					put("2", "Voice only");
					put("3", "Chime and voice");
				}

			});
	
	private int keypadNumber;
	private char functionKey;
	private char[] chimeModes = new char[8];
	
	public ReplyKeypadFunctionKeyPress(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		keypadNumber = Integer.parseInt(message.substring(4, 6));
		functionKey = message.charAt(6);
		chimeModes = message.substring(7, 15).toCharArray();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Keypad Function Key Pressed\n");
		for (int i = 0; i < 8; i++) {
			sb.append("Area ");
			sb.append(i + 1);
			sb.append(", chime mode = ");
			sb.append(CHIME_MODES.get(String.valueOf(chimeModes[i])));
			sb.append("\n");
		}
		return sb.toString();
	}

	public int getKeypadNumber() {
		return keypadNumber;
	}

	public char getFunctionKey() {
		return functionKey;
	}

	public char[] getChimeModes() {
		return chimeModes;
	}
}
