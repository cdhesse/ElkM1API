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
package com.elkm1api.messages.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.21 Keypad KeyChange Update (KC)
 * 
 * 19KCNNDDLLLLLLCPPPPPPPP00CC
 * 19 – Length as ASCII hex, (0A in M1 revision before 4.2.8, 11 in
 * revision before 4.3.2)
 * KC – Keypad Change Message Command
 * NN – Keypad Number, 1 based
 * DD – Key Number from Key Table Below
 * L[6] – Array of 6 ASCII bytes, indicating the Keypad Function Key’s
 * illumination status. L[0] = Function Key 1’s LED status. “0”
 * = Off, “1” = On constant, “2” = On Blinking. L[5]= F6 LED. In
 * M1 software rev. 4.2.8.
 * C – Code required to bypass if = “1”
 * P[8] – Beep and chime mode. Version 4.3.2 and after
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 19KC01112010000200000000010 Keypad change – Keypad 1,
 * “*” Key pressed, F1 LED blinking, F3 LED is On, all other F Key
 * LED’s are Off, No code required to bypass, Area 1 is constantly
 * beeping.
 * This transmission update option transmits the updated status
 * whenever it changes and is enabled by setting the location TRUE in the
 * M1 Control Global Programming Location 40. Example: “Xmit Keypad Key
 * Chgs” (Yes or No)
 * 
 * 4.21.1 Request Keypad Function Key Illumination Status (kc)
 * 
 * 08kc010009
 * The function key illumination request returns the Keypad KeyChange Update
 * (KC) data with the Key Number set to zero (0). This command allows automation
 * equipment to request the illumination status of the keypad function keys for building
 * virtual keypads on a PC.
 * 08 – Length as ASCII hex
 * kc – Request Keypad Function Key Illumination Status Command
 * NN – Keypad Number, 1 based
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 08kc010009 Request keypad 1’s illumination status.
 * M1 Control RS-232 ASCII String Protocol Page 36 of 68 Rev. 1.79 July 16, 2009
 * Returns: 11KC01001000000009E Keypad 1’s Function Key F1 is illuminated as
 * described in “KC” command above.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class KeypadKeyChangeUpdate extends Message {

	public static final Map<String, String> KEY_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "No");
					put("00", "No");
					put("11", "*");
					put("12", "#");
					put("13", "F1 Key");
					put("14", "F2 Key");
					put("15", "F3 Key");
					put("16", "F4 Key");
					put("17", "Stay");
					put("18", "Exit");
					put("19", "Chime");
					put("20", "Bypass");
					put("21", "Elk");
					put("22", "Down");
					put("23", "Up");
					put("24", "Right");
					put("25", "Left");
					put("26", "F6 Key");
					put("27", "F5 Key");
					put("28", "<datakeymode>");
				}
			});

	public static final Map<String, String> ILLUMINATION_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Off");
					put("1", "On");
					put("2", "Blinking");
				}
			});

	public static final Map<String, String> BEEP_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Chime and Beep Off");
					put("1", "Single Beep");
					put("2", "Constant Beep");
					put("4", "Chime");
				}
			});

	public KeypadKeyChangeUpdate(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}

	private String getKeypadNumber() {
		return cmdBody.substring(2, 4);
	}

	private boolean isCodeRequiredToBypass() {
		String codeRequired = cmdBody.substring(12, 13);
		if (codeRequired.equals("1")) {
			return true;
		}
		return false;
	}

	private String getKeyNumber() {
		String keyNumber = cmdBody.substring(4, 6);
		return KEY_MAP.get(keyNumber);
	}

	private String getIlluminationStatus(int i) {
		String key = cmdBody.substring(i, i + 1);
		return ILLUMINATION_MAP.get(key);
	}

	private String getChimeAndBeepMode(int i) {
		String beeps = cmdBody.substring(13, 21);
		return BEEP_MAP.get(beeps.substring(i, i + 1));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("KeypadKeyChangeUpdate: keypad=" + getKeypadNumber()
				+ ", key pressed=" + getKeyNumber());
		for (int i = 6; i < 12; i++) {
			if (!getIlluminationStatus(i).equals("Off")) {
				sb.append(", F" + (i - 5) + " key is "
						+ getIlluminationStatus(i));
			}
		}

		sb.append(", codeRequiredToBypass=" + isCodeRequiredToBypass());

		String beeps = cmdBody.substring(13, 21);
		for (int i = 0; i < 8; i++) {
			if (beeps.charAt(i) != '0') {
				sb.append(", Area " + (i + 1) + "=" + getChimeAndBeepMode(i));
			}
		}

		return sb.toString();
	}
}
