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
 * 4.4.1 Alarm Reporting (AR)
 * 
 * Reporting of alarms through the built on serial port 0 consists of an ASCII string following
 * the same data format of the digital dialer’s Contact ID transmission. Programming one of
 * the telephone numbers with a dialer format set to “Ethernet” will enable the transmissions
 * of the alarm ASCII strings over the RS-232 serial port 0. Available in M1 Version 4.2.8
 * and after.
 * 
 * 16ARAAAAAACCCCGGZZZT00CC<cr><lf>
 * 
 * 16 – Length as ASCII hex, 22 bytes
 * AR – Alarm Reporting Command
 * AAAAAA – Account Number, 6 ASCII digits
 * CCCC - Alarm Code consists of 4 ASCII digits.
 * GG - Group/Partition Number consisting of 2 ASCII digits.
 * ZZZ - Zone/User Number consisting of 3 ASCII digits.
 * T – IP Address to send alarm on. Valid 1 to 8 on M1 Gold, 1 to 4 on M1 Standard and Ez8.
 * 00 - Future use 2 digits.
 * CC – checksum
 * 
 * Example: 16 AR 123456 1134 01 001 1 00 85 - Length 22 bytes, AR alarm reporting,
 * account 123456, CID – Burglar Entry/Exit 1, Area 01, Zone 001, use telephone/IP address
 * 1. Spaces are for readability only.
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class AlarmReporting  extends Message {

	private String accountNumber;
	private String alarmCode;
	private String area;
	private String zone;
	private String ipNumber;
	
	public AlarmReporting(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		accountNumber = message.substring(4, 10);
		alarmCode = message.substring(10, 14);
		area = message.substring(14, 16);
		zone = message.substring(16, 19);
		ipNumber = message.substring(19, 20);
	}

	public String toString() {
		return "Alarm Reporting - Account Number: " + accountNumber + ", Alarm Code: " + alarmCode
				+ ", Area: " + area + ", Zone: " + zone + ", IP Number: " + ipNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public String getArea() {
		return area;
	}

	public String getZone() {
		return zone;
	}

	public String getIpNumber() {
		return ipNumber;
	}
}
