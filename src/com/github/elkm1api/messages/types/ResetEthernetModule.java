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
 * 4.26 Reset Ethernet Module (RE) 
 * 
 * This command is originated from the
 * M1 and causes the M1XEP Ethernet Module to reset its processor and/or its IP
 * address to: 192.168.0.251. This can be used when the IP address of the M1XEP
 * is set to an unknown value. This is accessed through the M1’s Keypad Global
 * Installer Programming, Option 45, then enter 96. M1 Version 4.3.7 and after.
 * 
 * 07 – Length as ASCII hex 
 * RE – Request Request Ethernet Reset
 * D – “0”= Reset the Ethernet Module, “1”= Reset the Ethernet IP Address and Reset Module. 
 * 00 – future use 
 * CC – Checksum 
 * 
 * 4.26.1 Reset Ethernet IP Address(RE)
 * 07RE00072 Reset the Ethernet Module. 
 * 07RE10071 Reset the Ethernet Module and set the IP Address to 192.168.0.251. 
 * 
 * </quote>
 * 
 * @author cdhesse
 * 
 */
public class ResetEthernetModule extends Message {

	private String resetType;
	
	public ResetEthernetModule(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		resetType = message.substring(4, 5);
	}

	public String toString() {
		if (resetType.equals("0")) {
			return "Reset the Ethernet Module was requested";
		} else {
			return "Reset the Ethernet IP Address and Reset Module was requested";
		}
	}

	public String getResetType() {
		return resetType;
	}
}
