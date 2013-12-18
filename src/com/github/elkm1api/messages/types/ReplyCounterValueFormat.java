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

import com.github.elkm1api.messages.Message;

/**
 * <quote>
 * 
 * 4.12.3 Reply With Counter Value Format (CV)
 * 
 * 0D – Length as ASCII hex
 * CV – Returned Counter value command
 * NN – Which Counter Value to be returned (2 decimal ASCII
 * digits, 1 based). ie.”16” = counter value 16. Range 1 to
 * 64.
 * DDDDD – 16 bit Counter Value returned (Five decimal ASCII
 * digits)
 * Range 0 to 65535.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0DCV0100123003C Returned value = 123, from Counter
 * number 1
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyCounterValueFormat extends Message {

	private String counterNumber;
	private String counterValue;
	
	public ReplyCounterValueFormat(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		counterNumber = message.substring(4, 6);
		counterValue = message.substring(6, 11);
	}

	public String toString() {
		return "Counter number " + counterNumber + " has value " + counterValue;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public String getCounterValue() {
		return counterValue;
	}
}
