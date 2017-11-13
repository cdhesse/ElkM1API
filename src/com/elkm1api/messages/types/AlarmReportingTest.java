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
 * 4.4.3 Alarm Reporting Test (AT)
 * 
 * The Alarm Reporting Test string is sent every 15 minutes from the M1 as a keep alive
 * message to the M1XEP Ethernet Module along with which IP address to test. All IP addresses
 * are set into the M1XEP module.
 * 
 * 07 – Length as ASCII hex, 7 bytes
 * AT – Alarm Reporting Command
 * T – IP Address to test, 1-8
 * 00 - Future use 2 digits.
 * CC – checksum
 * 
 * Example: 07 AT 1 00 73 Send keep alive signal to the M1XEP and test IP address 1. Spaces
 * are for readability only.
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class AlarmReportingTest extends Message {

	private String ipNumber;
	
	public AlarmReportingTest(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		ipNumber = message.substring(4, 5);
	}

	public String toString() {
		return "Test string was sent to IP " + ipNumber;
	}

	public String getIpNumber() {
		return ipNumber;
	}
}
