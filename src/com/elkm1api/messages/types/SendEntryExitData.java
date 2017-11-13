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
 * 4.15 Entry/Exit Time Data (EE)
 * 
 * This sends the entry 1 & 2 and exit 1 & 2 time data when the timers start by area.
 * When each exit timer expires an “EE” command is also transmitted. M1 Version
 * 4.1.12, 5.1.12 or later. Armed State available in 4.1.18, 5.1.18 or later.
 * 4.15.1 Send Entry/Exit Data (EE)
 * 0F – Length as ASCII hex
 * EE – Send exit/entry timer data.
 * A – Area, “1” to “8”.
 * D – Data Type. “0” = Exit, “1” = Entrance time.
 * M1 Control RS-232 ASCII String Protocol Page 29 of 68 Rev. 1.79 July 16, 2009
 * ttt – Timer 1 value in seconds. Range “000” to “255”
 * seconds.
 * TTT – Timer 2 value in seconds. Range “000” to “255”
 * seconds.
 * S – Armed State 0=Disarmed
 * 1=Armed Away
 * 2=Armed Stay
 * 3=Armed Stay Instant
 * 4=Armed Night
 * 5=Armed Night Instant
 * 6=Armed Vacation
 * 00 – future use
 * CC – Checksum
 * Example 1: 0FEE10060120100E5Area 1, Exit 1 Time =
 * 060, Exit 2 Time = 120 seconds started, Armed Away.
 * Example 2: 0FEE21030254200DD Area 2, Entrance 1
 * Time = 030, Entrance 2 Time = 254 seconds started,
 * Armed Stay.
 * 
 * @author cdhesse
 *
 */
public class SendEntryExitData extends Message {
	
	public static final Map<String, String> ARMED_STATES = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Disarmed");
					put("1", "Armed Away");
					put("2", "Armed Stay");
					put("3", "Armed Stay Instant");
					put("4", "Armed Night");
					put("5", "Armed Night Instant");
					put("6", "Armed Vacation");
				}
			});
	
	private String area;
	private String dataType;
	private String timerOne;
	private String timerTwo;
	private String armedState;
	private String armedStateText;
	private String entryExit;

	public SendEntryExitData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		area = message.substring(4, 5);
		dataType = message.substring(5, 6);
		if (dataType.equals("0")) {
			entryExit = "Exit";
		} else {
			entryExit = "Entrance";
		}
		timerOne = message.substring(6, 9);
		timerTwo = message.substring(9, 12);
		armedState = message.substring(12, 13);
		armedStateText = ARMED_STATES.get(armedState);
	}
	
	public String toString() {
		return "Area " + area + ", " + entryExit + " 1 time " + timerOne + " seconds, " 
									 + entryExit + " 2 time " + timerTwo + " seconds, " + armedStateText;
	}

	public String getArea() {
		return area;
	}

	public String getDataType() {
		return dataType;
	}

	public String getTimerOne() {
		return timerOne;
	}

	public String getTimerTwo() {
		return timerTwo;
	}

	public String getArmedState() {
		return armedState;
	}

	public String getArmedStateText() {
		return armedStateText;
	}

	public String getEntryExit() {
		return entryExit;
	}	
}
