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
 * 4.41.3 Reply Programming Of Insteon Lighting Device Data (IP)
 * 
 * 0A – Length as ASCII hex
 * IP – Reply Programming of Insteon lighting device data
 * aaa – Starting Lighting device number 001 to 192
 * n - Number of devices being programmed, 1-8
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0AIP001400D1
 * 
 * Reply Acknowledge Programming Insteon lighting device ID data for
 * device 001 to 004.
 * 
 * Note: Insteon Lighting Device 193 to 256 corresponds to Insteon Groups 1 – 64.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyProgrammingInsteonLightingData extends Message {

	private int startingDeviceNumber;
	private int numberOfDevices;
	
	public ReplyProgrammingInsteonLightingData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		startingDeviceNumber = Integer.parseInt(message.substring(4, 7));
		numberOfDevices = Integer.parseInt(message.substring(7, 8));
	}
	
	public String toString() {
		return "Programing of Insteon Lighting Device Data recieved for device " 
				+ String.format("%03d", new Integer(startingDeviceNumber)) + " to "
				+ String.format("%03d", new Integer((startingDeviceNumber + numberOfDevices - 1)));
	}

	public int getStartingDeviceNumber() {
		return startingDeviceNumber;
	}

	public int getNumberOfDevices() {
		return numberOfDevices;
	}
}
