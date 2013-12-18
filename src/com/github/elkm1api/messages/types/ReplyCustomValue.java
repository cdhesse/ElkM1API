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
 * 4.10.3 Reply With Custom Value (CR)
 * 
 * 0E – Length as ASCII hex Length fixed in M1 Version
 * 4.3.1 and later
 * CR – Returned custom value
 * NN – Which Custom Value to be returned (2 decimal ASCII
 * digits, 1 based). ie.”16” = custom value 16. Range 1 to
 * 20.
 * DDDDD – 16 bit Custom Value returned ( 5 decimal ASCII
 * digits)
 * If Format = 2, Time of day, this value should be
 * converted to a hexidecimal value. The low two bytes
 * will display the minutes in hexidecimal, the third
 * and fourth bytes will display the hours in
 * hexidecimal.
 * F - Custom value format. 0=Number, 1=Timer, 2=Time of day
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0ECR01001230000F Returned value = 123, Number format, from Custom
 * Value 1
 * 
 * Example: 0ECR010541620003 Convert 5416 to hex = 15 28. Convert 15 to decimal
 * = 21. Convert 28 to hex = 40. Therefore time = 21:40 or 9:40 PM.
 * 
 * 4.10.4 Reply With ALL Custom Values (CR)
 * 
 * 80 – Length as ASCII hex Length fixed in M1 Version
 * 4.3.6 and later
 * CR – Returned custom value
 * NN = 00. 00 implies all 20 Custom Values returned.
 * DDDDD… – 16 bit Custom Value returned ( 5 decimal ASCII
 * digits)
 * If Format = 2, Time of day, this value should be
 * converted to a hexidecimal value. The low two bytes
 * will display the minutes in hexidecimal, the third
 * and fourth bytes will display the hours in
 * hexidecimal.
 * F - Custom value format. 0=Number, 1=Timer, 2=Time of day
 * … DDDDD and F above is repeated 19 more times
 * corresponding to each of the 20 custom values.
 * 00 – future use
 * CC – Checksum
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyCustomValue extends Message {

	private static final Map<String, String> FORMATS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Number");
					put("1", "Timer");
					put("2", "Time of Day");
				}

			});
	
	private String customValueNumber;
	private String[] customValue = new String[20];
	private String[] format = new String[20];
	private String[] formattedTime = new String[20];
	
	public ReplyCustomValue(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		customValueNumber = message.substring(4, 6);
		int j;
		int timeValue;
		String hex;
		String hours;
		String minutes;
		String ampm;
		int iHours;
		int iMinutes;
		int max = 20;
		if (!customValueNumber.equals("00")) {
			max = 1;
		}
		for (int i = 0; i < max; i++) {
			j = i + 1;
			customValue[i] = message.substring(6 * j, (6 * j) + 5);
			format[i] = message.substring((6 * j) + 5, (6 * j) + 6);
			
			if (format[i].equals("2")) {
				// This is a time
				timeValue = Integer.parseInt(customValue[i]);
				hex = Integer.toHexString(timeValue);
				hours = hex.substring(0, 2);
				minutes = hex.substring(2, 4);
					
				iHours = Integer.parseInt(hours,  16);
				if (iHours > 12) {
					iHours = iHours - 12;
					ampm = "PM";
				} else {
					ampm = "AM";
				}
				iMinutes = Integer.parseInt(minutes, 16);
				formattedTime[i] = String.format("%02d", new Integer(iHours)) + ":" + String.format("%02d", new Integer(iMinutes)) + " " + ampm;
				if (!customValueNumber.equals("00")) {
					i = 21;
				}
			}
		}
	}
	
	public String getFormat(String value) {
		return FORMATS.get(value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Custom Value(s):\n");
		
		for (int i = 0; i < 20; i++) {
			if (customValue[i] != null) {
				if (!customValueNumber.equals("00")) {
					sb.append("Value " + customValueNumber + ": ");
				} else {
					sb.append("Value " + (i + 1) + ": ");
				}
				if (format[i].equals("2")) {
					sb.append(formattedTime[i]);
				} else {
					sb.append(customValue[i]);
				}
				sb.append(" Format: ");
				sb.append(format[i] + " - " + getFormat(format[i]));
				sb.append("\n");
			}
		}
		
		return sb.toString();
	}

	public String getCustomValueNumber() {
		return customValueNumber;
	}

	public String[] getCustomValue() {
		return customValue;
	}

	public String[] getFormat() {
		return format;
	}

	public String[] getFormattedTime() {
		return formattedTime;
	}
}
