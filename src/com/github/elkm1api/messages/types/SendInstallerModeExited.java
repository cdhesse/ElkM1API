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
 * 4.18 Installer Program Mode Exited (IE)
 * 
 * This command is automatically sent through serial port 0 only when the installer
 * mode is terminated. This is done by pressing the “*” key three times or the installer
 * timer runs out. This command is used by the M1XEP Ethernet Interface to know
 * when to reload M1 program data after an installer has done any keypad programming.
 * M1 Version 4.2.8 and after.
 * 4.18.1 Send Installer Mode Exited (IE)
 * 
 * 06 – Length as ASCII hex
 * IE – Send installer mode exited.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 06IE00AC Send installer mode has exited.
 * The “IE” is sent out when ELK RP disconnects. See section 4.33 ELKRP
 * Connected (RP) for additional information.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class SendInstallerModeExited extends Message {

	public SendInstallerModeExited(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}

	public String toString() {
		return "ELKRP/installer mode exited.";
	}
}
