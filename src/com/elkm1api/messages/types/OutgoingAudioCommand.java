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
 * <quote>
 * 
 * 4.8 Audio Equipment Command (CD)
 * 
 * An M1XEP Ethernet Module is required to intercept the “cd” command. These
 * commands are sent into the M1XEP via the Ethernet connection and passed
 * through to the M1. The M1XEP builds custom audio commands to control the
 * audio equipment and sends these commands to the audio equipment via ethernet.
 * An IP232 Ethernet Module may be required at the audio equipment to received the
 * commands if no Ethernet Port is available on the audio equipment. The incoming
 * “cd” command can trigger Rules in the M1 which can fire outgoing audio
 * equipment “CD” commands or other control Rules. ELKRP downloads the Audio
 * Equipment Command tables into the M1XEP according to the Audio Equipment
 * Manufacturer. M1 version 4.1.11, 5.1.11 or later is required.
 * 
 * 4.8.1 Incoming Audio Command (cd)
 * 
 * 0F – Length as ASCII hex
 * cd – Request Audio Command
 * NN – Which audio command from Audio Command Table.
 * SS – Audio Source Information
 * ZZ – Audio Zone Information
 * VVV – Audio State or Value
 * 00 – future use
 * CC – Checksum
 * The “cd” command may or may not use the audio source
 * and zone information.
 * 
 * Example: 0Fcd01020300000AD Audio command 1, Source 2, Zone 3, Power
 * On
 * 
 * Example: 0Fcd10020305000A8 Audio command 10, Source 2, Zone 3,
 * Volume Level = 50
 * 
 * 4.8.2 Audio Command Table (used by M1XEP)
 * 
 * Function Description (numeric NN value)
 * 
 * Power Off 0-Turns the zone power Off.
 * Power On 1-Turns the zone power On.
 * Power Toggle On/Off 2-Toggles Power state of a zone.
 * Next Source 3-Steps to next source
 * Source 4-Select a source input.
 * Previous Select 5-Steps backward to previous available.
 * Next Select 6-Advances forward, next.
 * Volume Down 7-Decrement the zone volume.
 * Volume Up 8-Increment the zone volume.
 * Mute Audio 9-Toggles the mute on/off of a zone.
 * Volume set 10-Set zone volume to a level.
 * M1 Control RS-232 ASCII String Protocol Page 22 of 68 Rev. 1.79 July 16, 2009
 * Play 11-Starts source Play
 * Pause 12-Pauses Play
 * Stop 13-Stop Play
 * Select Favorite #1 14-Executes Favorite Playlist 1.
 * Select Favorite #2 15-Executes Favorite Playlist 2.
 * Minus 16-Down Select
 * Plus 17-Up Select
 * All Zones Off 18-Turn all zones off
 * All Zones On 19-Turn all zones on
 * Audio System Manufacturer 20-Version number,
 * 
 * VVV value: 0 = No audio configured
 * 1 = Russound
 * 2 = Nuvo
 * 3 = Proficient
 * 99 = IP Failure
 * 
 * 4.8.3 Outgoing Audio Command (CD)
 * 
 * 0F – Length as ASCII hex
 * CD – Request Audio Command
 * NN – Which audio command from Audio Command
 * Table.
 * SS – Audio Source Information
 * ZZ – Audio Zone Information
 * VVV – Audio State or Value
 * 00 – future use
 * CC – Checksum
 * 
 * Example: 0FCD02030400000EA Audio command 2, Source 3, Zone 4, Toggle
 * Power.
 * 
 * Example: 0FCD09030400000E3 Audio command 09, Source 3, Zone 4, Toggle
 * Mute.
 * 
 * </quote>
 * 
 * @author cdhesse
 *
 */
public class OutgoingAudioCommand extends Message {

	private static final Map<String, String> AUDIO_COMMANDS = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("00", "Power Off");
					put("01", "Power On");
					put("02", "Toggle Power");
					put("03", "Next Source");
					put("04", "Source");
					put("05", "Previous Select");
					put("06", "Next Select");
					put("07", "Volume Down");
					put("08", "Volume Up");
					put("09", "Toggle Mute");
					put("10", "Volume Set");
					put("11", "Play");
					put("12", "Pause");
					put("13", "Stop");
					put("14", "Select Favorite 1");
					put("15", "Select Favorite 2");
					put("16", "Minus");
					put("17", "Plus");
					put("18", "All Zones Off");
					put("19", "All Zones On");
					put("20", "Version Number");
				}

			});
	
	private static final Map<String, String> AUDIO_STATES = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("000", "No Audio Configured");
					put("001", "Russound");
					put("002", "Nuvo");
					put("003", "Proficient");
					put("099", "IP Failure");
				}

			});
	
	private String audioCommand;
	private String source;
	private String zone;
	private String audioState;
	
	public OutgoingAudioCommand(String cmd, String fullCommand) {
		super(cmd, fullCommand);
		audioCommand = message.substring(4, 6);
		source = message.substring(6, 8);
		zone = message.substring(8, 10);
		audioState = message.substring(10, 13);
	}
	
	public String getAudioCommand(String cmd) {
		return AUDIO_COMMANDS.get(cmd);
	}
	
	public String getAudioState(String state) {
		return AUDIO_STATES.get(state);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Audio Command ");
		sb.append(audioCommand);
		sb.append(", source ");
		sb.append(source);
		sb.append(", zone ");
		sb.append(zone);
		sb.append(", ");
		sb.append(getAudioCommand(audioCommand));		
		
		return sb.toString();
	}

	public String getAudioCommand() {
		return audioCommand;
	}

	public String getSource() {
		return source;
	}

	public String getZone() {
		return zone;
	}

	public String getAudioState() {
		return audioState;
	}
}
