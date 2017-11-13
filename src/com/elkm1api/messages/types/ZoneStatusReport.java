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
 * <quote> 4.39.6 Zone Status Report (ZS) D6ZSD...000CC(CR-LF) The control panel
 * sends this message in response to a Zone Status Request. The data portion of
 * this message is 208 characters long, one character for each zone in order.
 * Each character is the sum of all applicable status values, expressed in
 * hexadecimal, using ASCII characters 0-9 and A-F. Status Values: Bits 0 & 1
 * binary values are the physical zone state • 0 Unconfigured • 1 Open • 2 EOL •
 * 3 Short Bits 2 & 3 binary values are the logical zone status • 0 Normal • 1
 * Trouble • 2 Violated • 3 Bypassed 4.39.7 Zone Status Table Hex Value 0=
 * Normal Unconfigured 0000 1= Normal Open 0001 2= Normal EOL 0010 M1 Control
 * RS-232 ASCII String Protocol Page 55 of 68 Rev. 1.79 July 16, 2009 3= Normal
 * Short 0011 4= not used 5= Trouble Open 0101 6= Trouble EOL 0110 7= Trouble
 * Short 0111 8= not used 9= Violated Open 1001 A(10)= Violated EOL 1010 B(11)=
 * Violated Short 1011 C(12)= Soft Bypassed 1000 temporary bypass of zone until
 * normal D(13)= Bypassed Open 1101 not implemented through M1 version 4.2.6
 * E(14)= Bypassed EOL 1110 not implemented through M1 version 4.2.6 F(15)=
 * Bypassed Short 1111 not implemented through M1 version 4.2.6 Example: a Zone
 * Status Report for a system in which: Zone 1 is Normal, EOL Zone 2 is Trouble,
 * Open the rest Normal, Unconfigured D6ZS 2 5 0 0.... </quote>
 * 
 * @author cdhesse
 * 
 */
public class ZoneStatusReport extends Message {

	private static final Map<String, String> PHYSICAL_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Normal");
					put("1", "Troubled");
					put("2", "Violated");
					put("3", "Bypassed");
				}

			});

	private static final Map<String, String> LOGICAL_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Unconfigured");
					put("1", "Open");
					put("2", "EOL");
					put("3", "Short");
				}

			});

	public ZoneStatusReport(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}

	private String getPhysicalZoneStatusText(int i) {
		return PHYSICAL_STATUS.get(getPhysicalZoneStatus(i));
	}

	private String getLogicalZoneStatusText(int i) {
		return LOGICAL_STATUS.get(getLogicalZoneStatus(i));
	}

	private String getLogicalZoneStatus(int i) {
		int status = Integer.valueOf(cmdBody.substring(i, i + 1));
		status = status & 0x03;
		return String.valueOf(status);
	}

	private String getPhysicalZoneStatus(int i) {
		int status = Integer.valueOf(cmdBody.substring(i, i + 1));
		status = status >> 2;
		return String.valueOf(status);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ZoneStatusReport:\n");
		for (int i = 2; i < 210; i++) {
			sb.append("Zone " + (i - 1) + ": " + getPhysicalZoneStatusText(i)
					+ " " + getLogicalZoneStatusText(i));
			if (i % 2 == 0) {
				sb.append("\t\t");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
