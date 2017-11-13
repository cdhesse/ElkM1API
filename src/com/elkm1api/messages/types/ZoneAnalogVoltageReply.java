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

import com.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.41 Zone Analog Voltage (ZV) This command allows automation equipment to
 * request a zone analog voltage level. M1 Version 4.2.8 and after. 4.41.1
 * Request Zone Voltage (zv) 09 – Length as ASCII hex zv – Get command for zone
 * analog voltage data ZZZ – Zone number 001 to 208 as 3 ASCII characters
 * decimal 00 – future use CC – Checksum Example: 09zv12300B1 Zone 123 analog
 * voltage request data 4.41.2 Reply Zone Analog Voltage Data (ZV) 0C – Length
 * as ASCII hex ZV – Reply with zone definition data ZZZ – Zone number 001 to
 * 208 as 3 ASCII characters decimal DDD – Zone voltage data as 3 decimal ASCII
 * characters. Divide data value by 10. Right character is the tenths decimal
 * place. 00 – future use CC – Checksum Example: 0CZV123072004E Zone 123 ,
 * voltage is 7.2 volts
 * 
 * </quote> 
 * 
 * @author cdhesse
 * 
 */
public class ZoneAnalogVoltageReply extends Message {

	private int zoneNumber;
	private float zoneVoltage;
	
	public ZoneAnalogVoltageReply(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zoneNumber = Integer.parseInt(message.substring(4, 7));
		zoneVoltage = Float.parseFloat(message.substring(7, 10))/10;
	}
	
	public String toString() {
		return "Zone " +  String.format("%03d", zoneNumber) + ", voltage = " + zoneVoltage;
	}

	public int getZoneNumber() {
		return zoneNumber;
	}

	public float getZoneVoltage() {
		return zoneVoltage;
	}
}
