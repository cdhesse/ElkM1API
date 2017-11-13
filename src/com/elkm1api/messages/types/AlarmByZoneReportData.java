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
 * 4.5.1 Reply Alarm By Zone Report Data (AZ)
 * 
 * D6 – Length as ASCII hex M1 version 4.3.9 and later
 * AZ – Reply with zone definition data
 * Z[208] - Array of 208 bytes showing alarm by zone. A value
 * of ‘0’ or 0x30 indicates the zone is not in alarm. A value greater
 * than ‘0’ indicates the zone has been triggered into alarm. The zone
 * value will be reset back to ‘0’ when an authorization code is entered
 * to acknowledge the alarm. The value in the zone byte is the same as
 * the zone function value plus 0x30 or 48. See table below.
 * 00 – future use
 * CC – Checksum
 * 
 * Zone Definitions in Alarm By Zone string:
 * Disabled = ‘0’
 * Burglar Entry/Exit 1 = ‘1’
 * Burglar Entry/Exit 2 = ‘2’
 * Burglar Perimeter Instant = ‘3’
 * Burglar Interior = ‘4’
 * Burglar Interior Follower = ‘5’
 * Burglar Interior Night = ‘6’
 * Burglar Interior Night Delay = ‘7’
 * Burglar 24 Hour = ‘8’
 * Burglar Box Tamper = ‘9’
 * Fire Alarm = ‘:’
 * Fire Verified = ‘;’
 * Fire Supervisory = ‘<’
 * Aux Alarm 1 = ‘=’
 * Aux Alarm 2 = ‘>’
 * Key fob = ‘?’ //not used
 * Non Alarm = ‘@’ //not used
 * Carbon Monoxide = ‘A’
 * Emergency Alarm = ‘B’
 * Freeze Alarm = ‘C’
 * Gas Alarm = ‘D’
 * Heat Alarm = ‘E’
 * Medical Alarm = ‘F’
 * Police Alarm = ‘G’
 * Police No Indication = ‘H’
 * Water Alarm = ‘I’
 * 
 * Subtract 0x30 or 48 from the ASCII value to get the
 * numeric decimal value.
 * 
 * Example:
 * D6AZ00000000900000000000000000000000000000000000000000000000000000000  68
 * 0000000000000000000000000000000000000000000000000000000000000000000000 69
 * 0000000000000000000000000000000000000000000000000000000000000000000000 69
 * 0000082
 * Zone 9 is in alarm and is defined as a “Burglar Box Tamper” zone definition 9 + 0x30 or 57
 * decimal.
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class AlarmByZoneReportData extends Message {

	private static final Map<String, String> ALARM_BY_ZONE = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Disabled");
					put("1", "Burglar Entry/Exit 1");
					put("2", "Burglar Entry/Exit 2");
					put("3", "Burglar Perimeter Instant");
					put("4", "Burglar Interior");
					put("5", "Burglar Interior Follower");
					put("6", "Burglar Interior Night");
					put("7", "Burglar Interior Night Delay");
					put("8", "Burglar 24 Hour");
					put("9", "Burglar Box Tamper");
					put(":", "Fire Alarm");
					put(";", "Fire Verified");
					put("<", "Fire Supervisory");
					put("=", "Aux Alarm 1");
					put(">", "Aux Alarm 2");
					put("?", "not used");
					put("@", "not used");
					put("A", "Carbon Monoxide");
					put("B", "Emergency Alarm");
					put("C", "Freeze Alarm");
					put("D", "Gas Alarm");
					put("E", "Heat Alarm");
					put("F", "Medical Alarm");
					put("G", "Police Alarm");
					put("H", "Police No Indication");
					put("I", "Water Alarm");
				}

			});
	
	private char[] zoneCharArray;
	
	public AlarmByZoneReportData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zoneCharArray = message.substring(4, 213).toCharArray();
	}
	
	private String getText(char c) {
		return ALARM_BY_ZONE.get(String.valueOf(c));
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Alarm By Zone Report:\n");
		for (int i = 0; i < 208; i++) {
			//if (zoneCharArray[i] != '0') {
				sb.append("Zone " + (i + 1) + ": "
						+ getText(zoneCharArray[i]));
				if (i % 2 == 0) {
					sb.append("\t\t");
				} else {
					sb.append("\n");
				}
			//}
		}
		return sb.toString();
	}

	public char[] getZoneCharArray() {
		return zoneCharArray;
	}
}
