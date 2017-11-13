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
 * <quote> 4.39.2 Reply With Bypassed Zone State (ZB)
 * 
 * 0A – Length as ASCII hex ZB – Returned Bypassed Zone Command ZZZ – Zone
 * number N – Zone bypass state. ‘0’ = unbypassed, ‘1’ = bypassed 00 – future
 * use CC – Checksum
 * 
 * Example: 0AZB123100CC Returned value = zone 123 is bypassed. </quote>
 * 
 * @author cdhesse
 * 
 */
public class BypassedZoneState extends Message {

	private static final Map<String, String> BYPASS_STATUS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("0", "Unbypassed");
					put("1", "Bypassed");
				}

			});

	private String zone;
	private String status;

	public BypassedZoneState(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zone = cmdBody.substring(2, 5);
		status = cmdBody.substring(5, 6);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Zone " + zone + " " + BYPASS_STATUS.get(status));

		return sb.toString();
	}

	public String getZone() {
		return zone;
	}

	public String getStatus() {
		return status;
	}
}
