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
 * 4.9.4 Control Output Status Report (CS)
 * 
 * D6CSD…00CC(CR-LF)
 * The control panel sends this message in response to a Control Output Status
 * Request. The data portion of this message is 208 characters long, one character for
 * each output in order. The value will be: 0 (Off), 1 (On).
 * 
 * Example: With control output 1 off, output 2 on, output 3 and output 4 off, the
 * message would begin D6CS0100…
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ControlOutputStatusReport extends Message {

	private static final Map<String, String> ON_OFF = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "On");
					put("1", "Off");
				}
			});

	public ControlOutputStatusReport(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}

	public boolean isOn(int index) {
		String value = cmdBody.substring(index, index + 1);
		if (value.equals('0')) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isOff(int index) {
		return !isOn(index);
	}

	public String getOnOffText(String value) {
		return ON_OFF.get(value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Control Output Status Report:\n");
		for (int i = 2; i < 210; i++) {
			sb.append("Control " + (i - 1) + ": "
					+ getOnOffText(cmdBody.substring(i, i + 1)));
			if (i % 2 == 0) {
				sb.append("\t\t");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
