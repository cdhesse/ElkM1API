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
 * 4.16 Email Trigger (EM)
 * 
 * This command allows the triggering of email transmissions from the M1XEP
 * Ethernet interface. This command originates in the M1 and is sent to the M1XEP
 * through serial port 0 only. M1 Version 4.2.8 and after.
 * 
 * 4.16.1 Send Email Trigger Data (EM)
 * 
 * 09 – Length as ASCII hex
 * EM – Send email trigger command to M1XEP.
 * DDD – The address of the email message to send. This
 * corresponds to the email messages that are stored in
 * the M1XEP Ethernet interface.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 09EM0010014 Send email message 001 trigger to the M1XEP
 * Ethernet interface. The M1XEP will then send the email on the Ethernet to the email
 * address that is associated with the message.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class SendEmailTriggerData extends Message {

	private String addressNumber;
	
	public SendEmailTriggerData(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		addressNumber = message.substring(4, 7);
	}

	public String toString() {
		return "Send email message to address " + addressNumber;
	}

	public String getAddressNumber() {
		return addressNumber;
	}
}
