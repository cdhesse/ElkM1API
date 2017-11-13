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
 * <quote>4.38 Zone Change Update (ZC) 0AZCZZZS00CC 0A – Length as ASCII hex ZC
 * – Zone Change Message Command ZZZ – Zone Number, 1 based S – Zone Status,
 * ASCII HEX, see Zone Status Table below 00 – future use CC – Checksum </quote>
 * 
 * @author cdhesse
 * 
 */
public class ZoneChangeUpdateReport extends Message {

	private static final Map<String, String> ZONE_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Unconfigured");
					put("1", "Open");
					put("2", "EOL");
					put("3", "Short");
					put("4", "no used");
					put("5", "Trouble Open");
					put("6", "Trouble EOL");
					put("7", "Trouble Short");
					put("8", "not used");
					put("9", "Violated Open");
					put("A", "Violated EOL");
					put("B", "Violated Short");
					put("C", "not used");
					put("D", "Bypassed Open");
					put("E", "Bypassed EOL");
					put("F", "Bypassed Short");
				}

			});

	private int zoneNumber;
	private String status;

	public ZoneChangeUpdateReport(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zoneNumber = Integer.parseInt(message.substring(4, 7));
		status = message.substring(7, 8);
	}

	public String toString() {
		return "Zone Number " + String.format("%03d", zoneNumber) + "\t"
				+ ZONE_STATUS.get(status);
	}

	public int getZoneNumber() {
		return zoneNumber;
	}

	public String getStatus() {
		return status;
	}
}
