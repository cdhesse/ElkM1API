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
 * 4.40.2 Reply Zone Definition Data (ZD) D6 – Length as ASCII hex ZD – Reply
 * with zone definition data D[208] - Array of all 208 zones with the zone
 * definition. Subtract 48 decimal or 0x30 hex from each array element to get
 * the zone definition number as described below. 00 – future use CC – Checksum
 * Example: D6ZD123….00CC Zone 1 Definition = Burglar Entry/Exit 1, Zone
 * Definition 2 = Burglar Entry/Exit 2, Zone Definition 3 = Burglar Perimeter
 * Instant… M1 Control RS-232 ASCII String Protocol Page 56 of 68 Rev. 1.79 July
 * 16, 2009 4.40.3 Zone Definition Number List: Character - Definition Number
 * ‘0’ – 00 = Disabled ‘1’ – 01 = Burglar Entry/Exit 1 ‘2’ – 02 = Burglar
 * Entry/Exit 2 ‘3’ – 03 = Burglar Perimeter Instant ‘4’ – 04 = Burglar Interior
 * ‘5’ – 05 = Burglar Interior Follower ‘6’ – 06 = Burglar Interior Night ‘7’ –
 * 07 = Burglar Interior Night Delay ‘8’ – 08 = Burglar 24 Hour ‘9’ – 09 =
 * Burglar Box Tamper ‘:’ - 10 = Fire Alarm ‘;’ - 11 = Fire Verified ‘<’ - 12 =
 * Fire Supervisory ‘=’ - 13 = Aux Alarm 1 ‘>’ - 14 = Aux Alarm 2 ‘?’ – 15 =
 * Keyfob ‘@’ - 16 = Non Alarm ‘A’ – 17 = Carbon Monoxide ‘B’ – 18 = Emergency
 * Alarm ‘C’ – 19 = Freeze Alarm ‘D’ – 20 = Gas Alarm ‘E’ - 21 = Heat Alarm ‘F’
 * - 22 = Medical Alarm ‘G’ - 23 = Police Alarm ‘H’ - 24 = Police No Indication
 * ‘I’ - 25 = Water Alarm ‘J’ - 26 = Key Momentary Arm / Disarm ‘K’ - 27 = Key
 * Momentary Arm Away ‘L’ - 28 = Key Momentary Arm Stay ‘M’ - 29 = Key Momentary
 * Disarm ‘N’ - 30 = Key On/Off ‘O’ - 31 = Mute Audibles ‘P’ - 32 = Power
 * Supervisory ‘Q’ - 33 = Temperature ‘R’ - 34 = Analog Zone ‘S’ - 35 = Phone
 * Key ‘T’ - 36 = Intercom Key </quote>
 * 
 * @author cdhesse
 * 
 */
public class ZoneDefinitionData extends Message {

	private String zones;

	private static final Map<String, String> ZONE_DEFINITIONS = Collections
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
					put("?", "Key Fob");
					put("@", "Non Alarm");
					put("A", "Carbon Monoxide");
					put("B", "Emergency Alarm");
					put("C", "Freeze Alarm");
					put("D", "Gas Alarm");
					put("E", "Heat Alarm");
					put("F", "Medical Alarm");
					put("G", "Police Alarm");
					put("H", "Police No Indication");
					put("I", "Water Alarm");
					put("J", "Key Momentary Arm / Disar");
					put("K", "Key Momentary Arm Away");
					put("L", "Key Momentary Arm Stay");
					put("M", "Key Momentary Disarm");
					put("N", "Key On/Off");
					put("O", "Mute Audibles");
					put("P", "Power Supervisory");
					put("Q", "Temperature");
					put("R", "Analog Zone");
					put("S", "Phone Key");
					put("T", "Intercom Key");
				}

			});

	public ZoneDefinitionData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zones = message.substring(4, 212);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Zone Definition Data:\n");
		for (int i = 0; i < 208; i++) {
			sb.append("Zone ");
			sb.append(i + 1);
			sb.append("\t");
			sb.append(ZONE_DEFINITIONS.get(zones.substring(i, i + 1)));
			sb.append("\n");
		}
		return sb.toString();
	}

	public String getZones() {
		return zones;
	}
}