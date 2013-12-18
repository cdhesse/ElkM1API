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
 * 4.7 Output Change Update (CC)
 * 
 * 0ACCZZZS00CC
 * 0A – Length as ASCII hex
 * CC – Zone Change Message Command
 * ZZZ – Output Number, 1 based
 * S – Output State, 0 = OFF, 1 = ON
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0ACC003100E5 Output change – Output 3, changed to ON
 * This transmission update option transmits the updated status
 * whenever it changes and is enabled by setting the location TRUE in
 * the M1 Control Global Programming Locations 37. Example: “Xmit
 * OutputChgs–ASCII” (Yes or No)
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class OutputChangeUpdate extends Message {

	private static final Map<String, String> ON_OFF = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("1", "On");
					put("0", "Off");
				}
			});

	private String outputNumber;
	private String outputState;
	
	public OutputChangeUpdate(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		outputNumber = message.substring(4, 7);
		outputState = message.substring(7, 8);
	}

	public boolean isOn() {
		if (outputState.equals('0')) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isOff() {
		return !isOn();
	}

	public String getOnOffText(String value) {
		return ON_OFF.get(value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("OutputChange: Output " + outputNumber
				+ " changed to " + getOnOffText(outputState));
		return sb.toString();
	}

	public String getOutputNumber() {
		return outputNumber;
	}

	public String getOutputState() {
		return outputState;
	}
}
