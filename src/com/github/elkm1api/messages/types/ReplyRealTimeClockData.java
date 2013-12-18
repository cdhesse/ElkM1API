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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.28.2 Reply Real Time Clock Data (RR)
 * 
 * also used:
 * 
 * 4.4.5 Ethernet Module Test (XK)
 * 
 * The “XK” command is automatically sent every 30 seconds. This is done
 * regardless of whether an M1XEP Ethernet Module is present or not. The XK
 * command serves two purposes. 1) It tests to see if a M1XEP Ethernet Module is
 * connected to the Control. 2) It includes the Control’s real time clock
 * information (current Date and Time) for general purpose use, including but
 * not limited to clock synchronization by a third party device.. The expected
 * response from a connected M1XEP Ethernet Module is “xk”. However, the Control
 * does not actually care or keep track of a missing “xk” response UNLESS or
 * UNTIL after a M1XEP Ethernet Module has been properly enrolled with the
 * Control. This can be accomplished via the Bus Module Enrollment process
 * (Keypad programming Menu 1) or by the RP software). Once an M1XEP has been
 * enrolled, the Control will now begin tracking each response to an “XK”
 * command, and it will display and log an “Ethernet Trouble” message if an “xk”
 * response is not received within 120 seconds of the “XK” command. This
 * response scenario is also true for alarm reporting commands “AT” and “AR”.
 * Basically, the M1XEP will withhold sending the “xk” response if it should
 * fail to complete an alarm or restoral transmission after 2 attempts. This
 * allows for 1 transmission miss. NOTE: Control Firmware Version 4.32 and after
 * includes the M1’s real time clock information.
 * 
 * 16 – Length as ASCII hex, 22 bytes
 * XK – Test Ethernet Module is alive
 * ss – second as two ASCII characters decimal, “00” to “59” 
 * mm – Minute as two ASCII characters decimal, “00” to “59” 
 * hh – Hour as two ASCII characters decimal, “00” to “23” 24 hour 
 * D – Day of week as one ASCII character, “1”=Sunday to “7”=Saturday 
 * DM – Day of month as two ASCII characters decimal, “01” to “31”
 * MM – Month as two ASCII characters decimal, “01” to “12” 
 * YY – Year as two ASCII characters decimal, “00” to “99” as in 2099 
 * S - Daylight Savings Time: “0”=Not active, “1”= Active. 
 * C - Clock Mode as one ASCII character, ‘1’ = 12 hour, 0 = 24 hour time mode 
 * T - Date Display Mode as one ASCII character, 0 = mm/dd, 1 = dd/mm 
 * 00 - Future use 2 digits. 
 * CC – checksum
 * 
 * Example: 16XK2636115020605110006F test signal to the M1XEP module. Real Time
 * Clock Value = Thursday, 11:36:26 PM, June 2, 2005, Daylite savings is active
 * for this time of year, Clock display mode is 12 hour, Date display mode is
 * month/day.
 * 
 * </quote>
 * 
 * @author cdhesse
 * 
 */
public class ReplyRealTimeClockData extends Message {

	private String seconds;
	private String minutes;
	private String hours;
	private String formatedHours;
	private String day;
	private String dayOfMonth;
	private String month;
	private String year;
	private boolean daylightSavingsActive = false;
	private boolean twentyFourHourClock = false;
	private boolean usDateFormat = false;
	private boolean pm = false;

	private static final Map<String, String> DAY_OF_WEEK = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("1", "Sunday");
					put("2", "Monday");
					put("3", "Tuesday");
					put("4", "Wednesday");
					put("5", "Thursday");
					put("6", "Friday");
					put("7", "Saturday");
				}

			});

	private static final Map<String, String> MONTHS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("01", "January");
					put("02", "February");
					put("03", "March");
					put("04", "April");
					put("05", "May");
					put("06", "June");
					put("07", "July");
					put("08", "August");
					put("09", "September");
					put("10", "October");
					put("11", "November");
					put("12", "Deccember");
				}

			});

	public ReplyRealTimeClockData(String cmd, String fullCommand) {
		super(cmd, fullCommand);

		seconds = message.substring(4, 6);
		minutes = message.substring(6, 8);
		hours = message.substring(8, 10);
		day = message.substring(10, 11);
		dayOfMonth = message.substring(11, 13);
		month = message.substring(13, 15);
		year = message.substring(15, 17);

		String rawData;

		// daylightSavingsFlag
		rawData = message.substring(17, 18);
		if (rawData.equals("1")) {
			daylightSavingsActive = true;
		}

		// clockModeFlag
		rawData = message.substring(18, 19);
		if (rawData.equals("0")) {
			twentyFourHourClock = true;
		}

		// dateDisplayModeFlag
		rawData = message.substring(19, 20);
		if (rawData.equals("0")) {
			usDateFormat = true;
		}

		int intHours = 0;
		intHours = Integer.parseInt(hours);

		if (intHours > 12) {
			pm = true;
		}

		if (!twentyFourHourClock && intHours > 12) {
			formatedHours = String.valueOf((intHours - 12));
		} else {
			formatedHours = hours;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getType());
		sb.append(" message. ");
		sb.append("Real time clock value: ");
		sb.append(DAY_OF_WEEK.get(day));
		sb.append(", ");
		sb.append(formatedHours);
		sb.append(":");
		sb.append(minutes);
		sb.append(":");
		sb.append(seconds);
		if (!twentyFourHourClock) {
			if (pm) {
				sb.append(" PM, ");
			} else {
				sb.append(" AM, ");
			}
		} else {
			sb.append(", ");
		}

		if (usDateFormat) {
			sb.append(MONTHS.get(month));
			sb.append(" ");
			sb.append(dayOfMonth);
		} else {
			sb.append(dayOfMonth);
			sb.append(" ");
			sb.append(MONTHS.get(month));
		}
		sb.append(", ");
		sb.append("20" + year);
		sb.append(", ");

		if (daylightSavingsActive) {
			sb.append("Daylight Savings Time");
		} else {
			sb.append("Standard Time");
		}

		return sb.toString();
	}

	public String getSeconds() {
		return seconds;
	}

	public String getMinutes() {
		return minutes;
	}

	public String getHours() {
		return hours;
	}

	public String getFormatedHours() {
		return formatedHours;
	}

	public String getDay() {
		return day;
	}

	public String getDayOfMonth() {
		return dayOfMonth;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public boolean isDaylightSavingsActive() {
		return daylightSavingsActive;
	}

	public boolean isTwentyFourHourClock() {
		return twentyFourHourClock;
	}

	public boolean isUsDateFormat() {
		return usDateFormat;
	}

	public boolean isPm() {
		return pm;
	}
}
