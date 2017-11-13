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
 * 4.11.2 Reply Change User Code (CU)
 * 
 * 0B – Length as ASCII hex
 * CU – Reply Lighting Device Status data
 * ccc – 000 = User code change denied due to invalid
 * authorization code, 001 to 254 indicates user code
 * that was changed. 255 = new code is a duplicate and
 * change is denied.
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 09CU005000A User code 005 was changed.
 * 
 * Example: 09CU000000F User code denied due to invalid authorization code.
 * 
 * Example: 09CU2550003 User code denied due to duplicate code.
 * 
 * Note: Setting the first future use byte to “1” in the “cu” command will set the user code
 * restriction which will prevent the code from being used. Setting the first future use byte to
 * “0”, enables the code to be used.
 * To M1: 23cu0050000030405060000080807062110BB If you send the first Future
 * Use byte as a ‘1’, the code will not be programmed, but the restriction on the code will
 * be enabled.
 * From M1: 09CU0051009 Response from the M1 that the code is restricted.
 * To M1: 23cu0050000030405060000080807062120BA Sending a ‘2’ value in the first
 * Future Use byte takes the code restriction away.
 * From M1: 09CU005000A Response from M1 with the code restriction cleared.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class ReplyChangeUserCode extends Message {

	public ReplyChangeUserCode(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}

	public String toString() {
		return "";
	}
}
