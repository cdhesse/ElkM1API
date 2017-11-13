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
 * 4.17 Send Valid User Number And Invalid User Code (IC)
 * This ASCII Data Packet will be sent when a user code is entered and a valid code
 * is found. Only the valid user code number will be returned. If a user code is not
 * found in the M1’s User Code Data Base, the code that was enter will be sent. If the
 * User Code Length is set to 4 digits, the invalid data packet will be sent after 4 digits
 * are entered, then repeated for each additional invalid user code digit. If the User
 * Code Length is set to 6 digits, the invalid data packet will be sent after 6 digits are
 * entered, then repeated for each additional invalid user digit. If prox card data is
 * enter, the packet will be sent immediately. This data can be used by automation
 * equipment with its own user code data base. The automation equipment would sent
 * the appropriate arm/disarm command (“a0” to “a6”) or output relay control
 * commands (“cn”, “cf”, or “ct”) back to the M1 after it has verified the proper code is
 * in its data base. Modified for 26 bit Weigand data cards and available in M1 Version
 * 4.2.8 and after.
 * 
 * 4.17.1 Send Valid Or Invalid User Code Format (IC)
 * 
 * 17 – Length as ASCII hex. 12 in M1 software versions
 * before 4.3.2
 * IC – Send Invalid User Code digits
 * DDDDDDDDDDDD – 12 characters of ASCII Hex (0 to F) user
 * code data. High nibble and low nibble of each
 * code data byte. 4 & 6 digit codes are left
 * padded with zeros. Set to all zeros if code is
 * valid.
 * UUU - 3 characters of ASCII decimal User Code Number 001
 * to 103, indicating which valid user code was
 * entered. Version 4.3.2 and later.
 * NN - Keypad number, 01 to 16, that generated the code.
 * 00 – future use
 * CC – Checksum
 * Version 4.4.2 and later, user code 201 = Program Code, 202
 * = ELK RP Code, 203 = Quick Arm, no code.
 * Example 1: 17IC 00 00 03 04 05 06 000 01 00CC Invalid user keypad
 * code 3456. Keypad entered codes only use the low nibble of the 6 bytes of code
 * data. Spaces in this example are for reading clarity only.
 * 17 – Length as ASCII hex
 * IC – Command
 * 00 – high and low nibble of byte one in high and low ASCII character.
 * 00 – high and low nibble of byte two in high and low ASCII character.
 * 03 – high and low nibble of byte three in high and low ASCII character.
 * Low nibble has first character of keypad code entry.
 * 04 – high and low nibble of byte four in high and low ASCII character.
 * Low nibble has second character of keypad code entry.
 * 05 – high and low nibble of byte five in high and low ASCII character.
 * Low nibble has third character of keypad code entry.
 * 06 – high and low nibble of byte six in high and low ASCII character.
 * M1 Control RS-232 ASCII String Protocol Page 31 of 68 Rev. 1.79 July 16, 2009
 * Low nibble has fourth character of keypad code entry.
 * 000 – Valid user code number. Set to 0 for an invalid user code.
 * 01 - Keypad number 01 generated the code.
 * 00 - Future Use
 * CC – Checksum
 * 
 * Example 2: 17IC 123456789012 000 01 004B Invalid 26 bit Weigand
 * prox card code. Prox card codes use the high and low nibbles of the 6 bytes of
 * code data. Spaces in this example are for reading clarity only.
 * 17IC – length and command
 * 123456789012 - Example prox card code. ASCII Hex (0 to F).
 * 000 – Valid user code number. Set to 0 for an invalid user code.
 * 01 – Keypad number that generated code.
 * 004B - not used byte characters and checksum
 * Example 3: 17IC 000000000000 003 01 0078 Valid user code. Prox
 * card codes use the high and low nibbles of the 6 bytes of code data. Spaces in
 * this example are for reading clarity only.
 * 17IC – length and command
 * 000000000000 – Invalid user code data is set to all zeros on a valid user
 * code. This hides all valid codes.
 * 003 – Valid user code number 3.
 * 01 – Keypad number that generated code.
 * 0078 - not used byte characters and checksum
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class SendValidOrInvalidUserCode extends Message {

	public SendValidOrInvalidUserCode(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}
	// TODO:  implement!
	/*public String toString() {
		return "";
	}*/
}
