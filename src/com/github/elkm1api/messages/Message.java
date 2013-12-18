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
package com.github.elkm1api.messages;

import com.github.elkm1api.exception.InvalidChecksum;

/**
 * Class Message
 */
public class Message {

	public final static String CR_LF = "\r\n";
	private final static String FUTURE_USE = "00";

	protected String cmdBody;
	protected String message;
	private String checkSum;
	private String hexLength;
	private String type;

	public Message(String cmd, String fullCommand) {
		if (cmd != null && fullCommand != null) {
			throw new IllegalArgumentException(
					"Provide only one argument: cmd or fullCommand, not both");
		}

		if (cmd != null) {
			this.cmdBody = cmd + FUTURE_USE;
			this.hexLength = String.format("%02X", this.cmdBody.length() + 2);
			this.checkSum = calculateChecksum();
			this.message = hexLength + this.cmdBody + checkSum;
			this.type = cmd.substring(0, 2);
		} else {
			this.message = fullCommand;
			this.hexLength = message.substring(0, 2);
			int length = message.length();
			this.checkSum = message.substring(length - 2, length);
			this.cmdBody = message.substring(2, length - 2);
			this.type = message.substring(2, 4);
			try {
				validateChecksum();
			} catch (InvalidChecksum e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void validateChecksum() throws InvalidChecksum {
		String calculatedChecksum = calculateChecksum();
		if (!calculatedChecksum.equals(this.checkSum)) {
			throw new InvalidChecksum(calculatedChecksum + " does not equal "
					+ this.checkSum);
		}
	}

	public String calculateChecksum() {
		char[] inputChars = (hexLength + cmdBody).toCharArray();
		int checkSum = 0;
		for (char aChar : inputChars) {
			checkSum = (checkSum + (int) aChar) % 256;
		}
		checkSum = (checkSum ^ 0xff) + 1;
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toHexString(checkSum).toUpperCase());
		if (sb.length() < 2) {
			sb.insert(0, '0'); // pad with leading zero if needed
		}
		return sb.toString();
	}
 
	public String toString() {
		return "Raw Message:  " + message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
