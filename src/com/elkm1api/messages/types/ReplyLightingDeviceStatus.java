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

import com.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.14.2 Reply Lighting Device Status Data (DS)
 * 
 * 0B – Length as ASCII hex
 * DS – Reply Lighting Device Status data
 * aaa – Lighting device number 001 to 256, base 1, device A1= 001
 * ss – Lighting status. 00 = Off, 01 = Full On, 2 to 99 = Dim level
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0BDS001990094 Reply lighting status of device
 * 001, set to a dim level of 99%.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyLightingDeviceStatus extends Message {

	private String lightingDeviceNumber;
	private String lightingStatus;
	private String textStatus;
	
	public ReplyLightingDeviceStatus(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		lightingDeviceNumber = message.substring(4, 7);
		lightingStatus = message.substring(7, 9);
		if (lightingStatus.equals("00")) {
			textStatus = "off";
		} else if (lightingStatus.equals("01")) {
			textStatus = "full on";
		} else {
			textStatus = "dim, level" + lightingStatus;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Lighting Device ");
		sb.append(lightingDeviceNumber);
		sb.append(" is ");
		sb.append(textStatus);
		return sb.toString();
	}

	public String getLightingDeviceNumber() {
		return lightingDeviceNumber;
	}

	public String getLightingStatus() {
		return lightingStatus;
	}

	public String getTextStatus() {
		return textStatus;
	}
}
