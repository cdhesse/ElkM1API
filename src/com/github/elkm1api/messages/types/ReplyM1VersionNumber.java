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
 * <quote> 4.36.2 Reply M1 Version Number (VN) 36 – Length as ASCII hex VN –
 * Reply with the M1’s version number data UUMMLL M1 version,
 * UU=Most,MM=Middle,LL=Least Significant Version Number,ASCII Hex as UU.MM.LL
 * version uummll – M1XEP version as ASCII hex, uu.mm.ll D[36] – 36 ASCII zeros
 * for future use 00 – future use CC – Checksum Example:
 * 36VN05010C0103020000000000000000000000000000000000000074 M1 version number
 * 05.01.12(0C), M1XEP version 01.03.02 </quote>
 * 
 * @author cdhesse
 * 
 */
public class ReplyM1VersionNumber extends Message {

	private String m1Most;
	private String m1Mid;
	private String m1Least;
	private String ethMost;
	private String ethMid;
	private String ethLeast;
	
	public ReplyM1VersionNumber(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		m1Most = message.substring(4, 6);
		m1Mid = message.substring(6, 8);
		m1Least = message.substring(8, 10);
		ethMost = message.substring(10, 12);
		ethMid = message.substring(12, 14);
		ethLeast = message.substring(14, 16);
	}

	public String toString() {
		return "M1 Version: " + m1Most + "." + m1Mid + "." + m1Least + "\n" +
				"M1XEP Version: " + ethMost + "." + ethMid + "." + ethLeast;
	}

	public String getM1Most() {
		return m1Most;
	}

	public String getM1Mid() {
		return m1Mid;
	}

	public String getM1Least() {
		return m1Least;
	}

	public String getEthMost() {
		return ethMost;
	}

	public String getEthMid() {
		return ethMid;
	}

	public String getEthLeast() {
		return ethLeast;
	}
}