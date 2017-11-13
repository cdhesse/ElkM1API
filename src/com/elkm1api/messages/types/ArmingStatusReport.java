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
 * 4.2.11 Reply Arming Status Report Data (AS)
 * 
 * 1E – Length as ASCII hex
 * AS – Reply with zone definition data
 * S[8] - Array of 8 area armed status.
 * U[8] - Array of 8 area arm up state.
 * A[8] - Array of 8 area alarm state.
 * 00 – future use, M1 Version 4.11 and later, contains the first
 * found Exit time if U[x] = ‘3’ or Entrance time if A[x] = ‘1’ as
 * two digits hex in seconds.
 * CC – Checksum
 * 
 * Example: 1EAS100000004000000030000000000E Area 1 is armed
 * away, and the area is in full fire alarm.
 * 
 * Example: 1EAS1000000031111111000000000902 Exit time set to 9 seconds.
 * If the control’s area status changes, this message will be sent if Global Option “Transmit Keypad Keys” is
 * enabled.
 * * </quote>
 * 
 * @author cdhesse
 *
 */
public class ArmingStatusReport extends Message {

	private static final Map<String, String> AREA_ARMED_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Disarmed");
					put("1", "Armed Away");
					put("2", "Armed Stay");
					put("3", "Armed Stay Instant");
					put("4", "Armed to Night");
					put("5", "Armed to Night Instant");
					put("6", "Armed to Vacation");
				}

			});

	private static final Map<String, String> ARM_UP_STATE = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Not Ready to Arm");
					put("1", "Ready to Arm");
					put("2", "Ready to Arm, but a zone is violated and can be Force Armed");
					put("3", "Armed with Exit Timer working");
					put("4", "Armed Fully");
					put("5", "Force Armed with a force arm zone violated");
					put("6", "Armed with a bypass");
				}

			});

	private static final Map<String, String> ALARM_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "no alarm active");
					put("1", "entry delay active");
					put("2", "alarm abort delay active");
					put("3", "fire alarm active");
					put("4", "medical alarm active");
					put("5", "police alarm active");
					put("6", "burglar alarm active");
					put("7", "aux1 alarm active");
					put("8", "aux2 alarm active");
					put("9", "aux3 alarm active");
					put(":", "aux4 alarm active");
					put(";", "carbon monoxide alarm active");
					put("<", "emergency alarm active");
					put("=", "freeze alarm active");
					put(">", "gas alarm active");
					put("?", "heat alarm active");
					put("@", "water alarm active");
					put("A", "fire supervisory alarm active");
					put("B", "verify fire alarm active");
				}

			});

	private char[] alarmCharArray;
	private char[] areaArmUpCharArray;
	private char[] armedCharArray;

	public ArmingStatusReport(String cmd, String fullMessage) {
		super(cmd, fullMessage);
		alarmCharArray = message.substring(20, 28).toCharArray();
		areaArmUpCharArray = message.substring(12, 20).toCharArray();
		armedCharArray = message.substring(4, 12).toCharArray();
	}

	public String getAlarmStatus(int area) {
		return String.valueOf(alarmCharArray[area - 1]);
	}

	public String getAlarmStatusText(int area) {
		return ALARM_STATUS.get(getAlarmStatus(area));
	}

	public String getArmUpStatus(int area) {
		return String.valueOf(areaArmUpCharArray[area - 1]);
	}

	public String getArmUpStateText(int area) {
		return ARM_UP_STATE.get(getArmUpStatus(area));
	}

	public String getArmedStatus(int area) {
		return String.valueOf(armedCharArray[area - 1]);
	}

	public String getArmedStatusText(int area) {
		return AREA_ARMED_STATUS.get(getArmedStatus(area));
	}

	public String getStatus(int area) {
		StringBuffer sb = new StringBuffer();
		sb.append(getArmedStatusText(area) + " " + getArmUpStateText(area)
				+ " " + getAlarmStatusText(area));

		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ArmingStatusReport:\n");
		for (int i = 1; i <= 8; i++) {
			sb.append("Area " + i + ": " + getStatus(i) + "\n");
		}
		return sb.toString();
	}
}
