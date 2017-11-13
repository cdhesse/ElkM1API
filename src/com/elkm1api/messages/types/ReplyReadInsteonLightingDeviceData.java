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
 * 4.19.4 Reply Read Of Insteon Lighting Device Data (IR)
 * 
 * XX – Length as ASCII hex
 * IR – Reply read of Insteon lighting device data
 * aaa – Starting Lighting device number 001 to 192
 * n - Number of devices being returned, 1-8
 * AAAAAA Insteon Device ID with 6 ASCII Hex bytes
 * BBBBBB per device. Number of devices determined
 * CCCCCC by “n” above…
 * EEEEEE
 * FFFFFF
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 22IR0014123456ABCDEF987654A1B2C3006F
 * Reply Insteon lighting device ID data for device 001 to 004.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyReadInsteonLightingDeviceData extends Message {

	private int startingDeviceNumber;
	private int numberOfDevices;
	private String[] deviceIDs = new String[10];
	
	public ReplyReadInsteonLightingDeviceData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		startingDeviceNumber = Integer.parseInt(message.substring(4, 7));
		numberOfDevices = Integer.parseInt(message.substring(7, 8));
		int j = 8;
		for (int i = 0; i < numberOfDevices; i++) {
			deviceIDs[i] = message.substring(j, j + 6);
			j = j + 6;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Insteon Lighting Devices:\n");
		int device = startingDeviceNumber;
		for (int i = 0; i < numberOfDevices; i++) {
			sb.append("Device Number ");
			sb.append(device);
			sb.append(" Insteon ID ");
			sb.append(deviceIDs[i]);
			sb.append(".\n");
			device++;
		}
		return sb.toString();
	}
	
	public int getStartingDeviceNumber() {
		return startingDeviceNumber;
	}

	public int getNumberOfDevices() {
		return numberOfDevices;
	}

	public String[] getDeviceIDs() {
		return deviceIDs;
	}
}
