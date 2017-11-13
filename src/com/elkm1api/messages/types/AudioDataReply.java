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
 * 4.6 Touchscreen Audio Command (CA)
 * 
 * This command is used by the touchscreens to request audio status data from the
 * M1XEP for audio control display. It is not used by the M1. The M1XEP intercepts
 * the command packet and sends translated commands to the audio equipment. This
 * command is available on ELKRMS touchscreen software.
 * 
 * 4.6.1 Request Audio Data (ca)
 * 
 * 08 – Length as ASCII hex
 * ca – Read custom value
 * NN – Which zone to request data on. ASCII decimal value
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 08ca010003 Request audio zone 1 data.
 * 
 * 4.6.2 Reply With Audio Data (CA)
 * 
 * 20CAnnpssvvvbbbtttlaaamd00000000CC
 * 0123456789012345678901234567890
 * 
 * nn - zone (“01” - “18”)
 * p - zone power (‘0’ = off, ‘1’ = on)
 * ss - source (“01” – “12”)
 * vvv - volume (“000” – “100”)
 * bbb - bass (“000”=down(-), “050”= center, “100”=up(+))
 * ttt - treble (“000”=down(-), “050”= center), “100”=up(+))
 * l - loudness (‘0’=off, ‘1’=on)
 * aaa - balance (“000”=left, “050”=center, “100”=right)
 * m - party mode (‘0’=off, ‘1’=on, ‘2’=master)
 * d - do not disturb (‘0’=off, ‘1’=on)
 * 00000000 - Eight future use bytes
 * CC - checksum
 * 
 * Example: 20CA0110205006004010500000000000C1 Audio Zone 1,
 * Audio Zone Power On, source 2, volume 50%, bass 60=10 right of
 * center, treble 40=10 left of center,, loudness on, balance
 * center, party mode off, do not disturb off.
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class AudioDataReply extends Message {

	private String zone;
	private boolean zonePower = false;
	private String source;
	private String volume;
	private String bass;
	private String treble;
	private boolean loudness = false;
	private String balance;
	private String partyMode;
	private boolean doNotDisturb = false;
	
	public AudioDataReply(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		zone = message.substring(4, 6);
		if (message.substring(6,7).equals("1")) {
			zonePower = true;
		}
		source = message.substring(7,9);
		volume = message.substring(9,12);
		if (volume.startsWith("0")) {
			volume = volume.substring(1, 3);
		}
		bass = message.substring(12,15);
		treble = message.substring(15, 18);
		if (message.substring(18, 19).equals("1")) {
			loudness = true;
		}
		balance = message.substring(19,22);
		partyMode = message.substring(22,23);
		if (message.substring(23,24).equals("1")) {
			doNotDisturb = true;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Audio Zone ");
		sb.append(zone);
		sb.append(", Audio Zone Power ");
		if (zonePower) {
			sb.append("on, ");
		} else {
			sb.append("off, ");
		}
		sb.append("source ");
		sb.append(source);
		sb.append(", volume ");
		sb.append(volume);
		sb.append("%, bass ");
		sb.append(bass);
		sb.append(", treble ");
		sb.append(treble);
		if (loudness) {
			sb.append(", loudness on, ");
		} else {
			sb.append(", loudness off, ");
		}
		if (balance.equals("000")) {
			sb.append("balance left, ");
		} else if (balance.equals("050")) {
			sb.append("balance center, ");
		} else if (balance.equals("100")) {
			sb.append("balance right, ");
		}
		if (partyMode.equals("0")) {
			sb.append("party mode off, ");
		} else if (partyMode.equals("1")) {
			sb.append("party mode on, ");
		} else if (partyMode.equals("2")) {
			sb.append("party mode master, ");
		}
		if (doNotDisturb) {
			sb.append("do not disturb on.");
		} else {
			sb.append("do not disturb off.");
		}
		
		return sb.toString();
	}

	public String getZone() {
		return zone;
	}

	public boolean isZonePower() {
		return zonePower;
	}

	public String getSource() {
		return source;
	}

	public String getVolume() {
		return volume;
	}

	public String getBass() {
		return bass;
	}

	public String getTreble() {
		return treble;
	}

	public boolean isLoudness() {
		return loudness;
	}

	public String getBalance() {
		return balance;
	}

	public String getPartyMode() {
		return partyMode;
	}

	public boolean isDoNotDisturb() {
		return doNotDisturb;
	}
}
