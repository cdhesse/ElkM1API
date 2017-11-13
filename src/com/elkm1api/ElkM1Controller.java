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
package com.elkm1api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.net.SocketFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.elkm1api.messages.Message;
import com.elkm1api.messages.MessageFactory;
import com.elkm1api.messages.MessageTypes;

/**
 * Class ElkM1Control
 */
public class ElkM1Controller implements MessageTypes, HandshakeCompletedListener {

	private String host;
	private int port;
	private boolean useSSL;
	private String certPath;
	private String certPassword;
	private String code;
	private String userName;
	private String password;
	private Socket socket;
	private BufferedWriter out;
	private BufferedReader in;
	private LinkedList<Message> messages = new LinkedList<Message>();

	/**
	 * Constructor
	 * 
	 * @param host
	 * @param port
	 * @param useSSL
	 * @param code
	 * @param userName
	 * @param password
	 * @param pathToCert
	 * @param certPassword
	 */
	public ElkM1Controller(String host, String port, boolean useSSL, String code,
			String userName, String password, String pathToCert, String certPassword) {

		this.host = host;

		if (port == null) {
			if (useSSL) {
				this.port = 2601;
			} else {
				this.port = 2101;
			}
		} else {
			this.port = Integer.parseInt(port);
		}

		this.useSSL = useSSL;
		this.code = code;
		this.userName = userName;
		this.password = password;
		this.certPath = pathToCert;
		this.certPassword = certPassword;

		connect();
	}
	
	/**
	 * Constructor with default ports based on useSSL or not
	 * 
	 * @param host
	 * @param useSSL
	 * @param code
	 * @param userName
	 * @param password
	 * @param pathToCert
	 * @param certPassword
	 */
	public ElkM1Controller(String host, boolean useSSL, String code,
			String userName, String password, String pathToCert, String certPassword) {
		if (useSSL) {
			this.port = 2601;
		} else {
			this.port = 2101;
		}
		
		this.host = host;
		this.useSSL = useSSL;
		this.code = code;
		this.userName = userName;
		this.password = password;
		this.certPath = pathToCert;
		this.certPassword = certPassword;

		connect();
	}
	
	/**
	 * Connect to ElkM1 panel via the method specified in the member variables
	 */
	private void connect() {

		SocketFactory factory;
		if (useSSL) {
			System.setProperty("javax.net.ssl.trustStore", certPath);
			System.setProperty("javax.net.ssl.trustStorePassword", certPassword);

			factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		} else {
			factory = SocketFactory.getDefault();
		}

		try {
			socket = factory.createSocket(host, port);
			out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream(), "UTF8"));
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF8"));

			if (useSSL) {

				((SSLSocket) socket).addHandshakeCompletedListener(this);

				out.write(userName + "\r\n");
				out.write(password + "\r\n");
				out.flush();

				// Read back username and password
				System.out.println(in.readLine());
				System.out.println(in.readLine());
				System.out.println(in.readLine());
				System.out.println(in.readLine());
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Diconnect from panel
	 * 
	 */
	public void disconnect() {
		// If not SSL, cleanup input and output steams
		if (!useSSL) {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// In all cases, close the socket (SSL will cleanup in,out)
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("disconnected");
	}
	
	/**
	 * Send a command to the panel
	 * 
	 * @param m1Command
	 */
	public void sendCommand(String m1Command) {
		Message msg = new Message(m1Command, null);
		try {
			out.write(msg.getMessage() + Message.CR_LF);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Read messages from the panel
	 * The first message found in the LinkedList of messages is
	 * returned which matches the given command code.
	 * 
	 * @param m1Command
	 * @return message
	 * @throws IOException
	 */
	public Message readMessages(String m1Command) throws IOException {
		Message msg = null;
		Iterator<Message> itr = messages.iterator();
		while (itr.hasNext()) {
			msg = itr.next();
			if (msg.getType().equals(m1Command) || m1Command == null) {
				itr.remove();
				return msg;
			}
		}

		String s;
		MessageFactory mf = MessageFactory.getInstance();
		while ((s = in.readLine()) != null) {
			if (MessageFactory.MSG_CLASSES.containsKey(s.substring(2, 4))) {
				msg = mf.getMessageClass(s);
			} else {
				msg = new Message(null, s);
			}

			if (msg.getType().equals(m1Command) || m1Command == null) {
				return msg;
			} else {
				this.messages.add(msg);
			}
		}

		return null;
	}
		
	/**
	 * Section 4.2 Arm and Disarm Messages (a0)
	 * 
	 * @param area
	 */
	public void disarm(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a0_CMD_DISARM + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a1)
	 * 
	 * @param area
	 */
	public void armAway(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a1_CMD_ARM_AWAY + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}
	
	/**
	 * Section 4.2 Arm and Disarm Messages (a2)
	 * 
	 * @param area
	 */
	public void armStay(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a2_CMD_ARM_STAY + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a3)
	 * 
	 * @param area
	 */
	public void armStayInstant(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a3_CMD_ARM_STAY_INSTANT + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a4)
	 *  
	 * @param area
	 */
	public void armNight(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a4_CMD_ARM_NIGHT + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a5)
	 * 
	 * @param area
	 */
	public void armNightInstant(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a5_CMD_ARM_NIGHT_INSTANT + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}
	
	/**
	 * Section 4.2 Arm and Disarm Messages (a6)
	 * 
	 * @param area
	 */
	public void armVacation(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a6_CMD_ARM_VACATION + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a7)
	 * 
	 * @param area
	 */
	public void armStepNextAwayMode(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a7_CMD_ARM_STEP_NEXT_AWAY + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}

	/**
	 * Section 4.2 Arm and Disarm Messages (a8)
	 * 
	 * @param area
	 */
	public void armStepNextStayMode(String area) {
		if (area == null) {
			area = "1";
		}
		String fullCommand = a8_CMD_ARM_STEP_NEXT_STAY + area
				+ String.format("%06d", Integer.parseInt(code));
		sendCommand(fullCommand);
	}
	
	/**
	 * <quote>
	 * 4.2.10 Arming Status Request (as)
	 * 
	 * 4.2.11 Reply Arming Status Report Data (AS)
	 * </quote>
	 * 
	 * @return message of type ArmingStatusReport
	 */
	public Message requestArmingStatus() {
		sendCommand(as_CMD_REQUEST_ARMING_STATUS);
		try {
			return readMessages(AS_REPLY_ARMING_STATUS_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 4.3 Send ASCII String To IP Address(AP)
	 * 
	 * @param centralStationIPAddressNumber
	 * @param asciiMessage
	 */
	public void sendASCIIToIPAddress(String centralStationIPAddressNumber, String asciiMessage) {
		if (asciiMessage.length() > 200) {
			throw new IllegalArgumentException("Message can not be longer than 200 characters");
		}
		String cmd = "00" + AP_SEND_ASCII_STRING + centralStationIPAddressNumber + asciiMessage;
		sendCommand(cmd);
	}
	
	/**
	 * 4.4 Ethernet Central Station Reporting
	 * 
	 * 4.4.1 Alarm Reporting (AR)
	 * 
	 * @return message of type AlarmReporting
	 */
	public Message alarmReporting() {
		try {
			return readMessages(AR_REPLY_ALARM_REPORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 4.4 Ethernet Central Station Reporting
	 * 
	 * 4.4.2 Alarm Report Acknowledge (ar)
	 * 
	 * Is this needed?  Or intercommunication between ethernet and m1 panel only?
	 */
	public void alarmReportAcknowledge() {
		
	}
	
	/**
	 * 4.4 Ethernet Central Station Reporting
	 * 
	 * 4.4.3 Alarm Reporting Test (AT)
	 * 
	 * @return message of type AlarmReportingTest
	 */
	public Message alarmReportingTest() {		
		try {
			return readMessages(AT_ETHERNET_TEST_TO_IP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 4.4.5 Ethernet Module Test (XK)
	 * 
	 * @return message of type ReplyRealTimeClockData
	 */
	public Message ethernetModuleTest() {
		try {
			return readMessages(XK_ETHERNET_MODULE_TEST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.5 Alarm By Zone Request (az)
	 * 
	 * 4.5.1 Reply Alarm By Zone Report Data (AZ)
	 * 
	 * </quote>
	 * 
	 * @return message of type AlarmByZoneReportData
	 */
	public Message alarmByZoneRequest() {
		sendCommand(az_CMD_ALARM_BY_ZONE);
		try {
			return readMessages(AZ_REPLY_ALARM_BY_ZONE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.6.1 Request Audio Data (ca)
	 * 
	 * 4.6.2 Reply With Audio Data (CA)
	 * 
	 * </quote>
	 * 
	 * @return message of type AudioDataReply
	 */
	public Message requestAudioData() {
		sendCommand(ca_CMD_REQUEST_AUDIO_DATA);
		try {
			return readMessages(CA_REPLY_AUDIO_COMMAND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.8.1 Incoming Audio Command (cd)
	 * 
	 * 4.8.3 Outgoing Audio Command (CD)
	 * 
	 * </quote>
	 * 
	 * @return message of type OutgoingAudioCommand
	 */
	public Message audioCommand() {
		sendCommand(cd_CMD_INCOMING_AUDIO_EQUIP);
		try {
			return readMessages(CD_REPLY_OUTGOING_AUDIO_EQUIP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <quote>
	 * 
	 * 4.9.1 Control Output off (cf)
	 * 
	 * </quote>
	 * 
	 * @param outputNumber
	 */
	public void controlOutputOff(int outputNumber) {
		if (outputNumber < 1 || outputNumber > 208) {
			throw new IllegalArgumentException("output number must be between 1 - 208");
		}
		
		String fullCommand = cf_CMD_CONTROL_OUTPUT_OFF + String.format("%03d", new Integer(outputNumber));
		sendCommand(fullCommand);
	}

	/**
	 * <quote>
	 * 
	 * 4.9.2 Control Output On (cn)
	 * 
	 * </quote>
	 * 
	 * @param outputNumber
	 * @param duration
	 */
	public void controlOutputOn(int outputNumber, int duration) {
		if (duration < 0 || duration > 65535) {
			throw new IllegalArgumentException("duration must be between 0 - 65535");
		}
		if (outputNumber < 1 || outputNumber > 208) {
			throw new IllegalArgumentException("output number must be between 1 - 208");
		}
		
		String fullCommand = cn_CMD_CONTROL_OUTPUT_ON +String.format("%03d", new Integer(outputNumber) +
				String.format("%05d", new Integer(duration)));
		sendCommand(fullCommand);
	}

	/**
	 * <quote>
	 * 
	 * 4.9.3 Control Output Status Request (cs)
	 * 
	 * 4.9.4 Control Output Status Report (CS)
	 * 
	 * </quote>
	 * 
	 * @return message of type ControlOutputStatusReport
	 */
	public Message requestControlOutputStatus() {
		sendCommand(cs_CMD_CONTROL_OUTPUT_STATUS_REQUEST);
		try {
			return readMessages(CS_REPLY_CONTROL_OUTPUT_STATUS_REPORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.9.5 Control Output toggle (ct)
	 * 
	 * </quote>
	 * 
	 * @param outputNumber
	 */
	public void controlOutputToggle(int outputNumber) {
		if (outputNumber < 1 || outputNumber > 208) {
			throw new IllegalArgumentException("output number must be between 1 - 208");
		}
		
		String fullCommand = ct_CMD_CONTROL_OUTPUT_TOGGLE + String.format("%03d", new Integer(outputNumber));
		sendCommand(fullCommand);
	}

	/**
	 * <quote>
	 * 
	 * 4.10.1 Read Custom Value (cr)
	 * 
	 * 4.10.3 Reply With Custom Value (CR)
	 * 
	 * </quote>
	 * 
	 * @param customValueNumber
	 * @return message of type ReplyCustomValue
	 */
	public Message readCustomValue(int customValueNumber) {
		if (customValueNumber < 1 || customValueNumber > 20) {
			throw new IllegalArgumentException("custom value must be between 1 - 20");
		}
		
		String fullCommand = cr_CMD_REQUEST_CUSTOM_VALUE + String.format("%02d", new Integer(customValueNumber));
		sendCommand(fullCommand);
		
		try {
			return readMessages(CR_REPLY_CUSTOM_VALUE_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.10.4 Reply With ALL Custom Values (CR)
	 * 
	 * </quote>
	 * 
	 * @return message of type ReplyCustomValue
	 */
	public Message readAllCustomValues() {
		String fullCommand = cr_CMD_REQUEST_CUSTOM_VALUE + "00";
		sendCommand(fullCommand);
		
		try {
			return readMessages(CR_REPLY_CUSTOM_VALUE_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.10.5 Write Custom Value (cw)
	 * 
	 * 4.10.3 Reply With Custom Value (CR)
	 * 
	 * </quote>
	 *  
	 * @param customValueNumber
	 * @param customValue
	 * @param format
	 * @return message of type CustomValueReply
	 */
	public Message writeCustomValue(int customValueNumber, String customValue, String format) {
		String fullCommand = cw_CMD_WRITE_CUSTOM_VALUE_DATA + String.format("%02d", new Integer(customValueNumber)) + String.format("%06d", customValue) + format;
		sendCommand(fullCommand);
		
		try {
			return readMessages(CR_REPLY_CUSTOM_VALUE_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.10.5 Write Custom Value (cw)
	 * 
	 * 4.10.3 Reply With Custom Value (CR)
	 * 
	 * </quote>
	 * 
	 * @param customValueNumber
	 * @param time
	 * @return message of type CustomValueReply
	 */
	public Message writeCustomDateValue(int customValueNumber, Date time) {
		DateFormat sdfHours = new SimpleDateFormat("hh");
		DateFormat sdfMinutes = new SimpleDateFormat("mm");
		DateFormat sdfAmPm = new SimpleDateFormat("a");
		String hours = sdfHours.format(time);
		String minutes = sdfMinutes.format(time);
		String ampm = sdfAmPm.format(time);
		
		int iHours = Integer.valueOf(hours);
		int iMinutes = Integer.valueOf(minutes);
		if (ampm.equals("PM")) {
			iHours = iHours + 12;
		}
		
		String hexHours = Integer.toHexString(iHours);
		String hexMinutes = Integer.toHexString(iMinutes);
		
		int result = Integer.parseInt(hexHours + hexMinutes, 16);
		String finalResult = String.valueOf(result);
		
		String fullCommand = cw_CMD_WRITE_CUSTOM_VALUE_DATA + String.format("%02d", new Integer(customValueNumber)) + String.format("%06d", finalResult) + "2";
		sendCommand(fullCommand);
		
		try {
			return readMessages(CR_REPLY_CUSTOM_VALUE_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.11.1 Request Change User Code (cu)
	 * 
	 * 4.11.2 Reply Change User Code (CU)
	 * 
	 * </quote>
	 * 
	 * @param userCodeToChange
	 * @param validAreas
	 * @return message of type ReplyChangeUserCode
	 */
	public Message changeCurrentUserCode(String userCodeToChange, String validAreas) {
		// TODO:  Implement
		// use this.code;
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.11.1 Request Change User Code (cu)
	 * 
	 * 4.11.2 Reply Change User Code (CU)
	 * 
	 * </quote>
	 * 
	 * @param userCodeToChange
	 * @param masterUserCode
	 * @param validAreas
	 * @return message of type ReplyChangeUserCode
	 */
	public Message changeOtherUserCode(String userCodeToChange, String masterUserCode, String validAreas) {
		// TODO: Implement
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.12.1 Read Counter Value (cv)
	 * 
	 * 4.12.3 Reply With Counter Value Format (CV)
	 * 
	 * </quote>
	 * 
	 * @param counterNumber
	 * @return message of type ReplyCounterValueFormat
	 */
	public Message readCounterValue(int counterNumber) {
		if (counterNumber < 1 || counterNumber > 64) {
			throw new IllegalArgumentException("counter must be between 1 - 64");
		}
		String fullCommand = cv_CMD_REQUEST_COUNTER_VALUE + String.format("%02d", new Integer(counterNumber));

		sendCommand(fullCommand);
		try {
			return readMessages(CV_REPLY_COUNTER_VALUE_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.12.2 Write Counter Value (cx)
	 * 
	 * 4.12.3 Reply With Counter Value Format (CV)
	 * 
	 * </quote>
	 * 
	 * @param counterNumber
	 * @param counterValue
	 * @return message of type ReplyCounterValueFormat
	 */
	public Message writeCounterValue(int counterNumber, String counterValue) {
		if (counterNumber < 1 || counterNumber > 64) {
			throw new IllegalArgumentException("counter must be between 1 - 64");
		}
		String fullCommand = cx_CMD_WRITE_COUNTER_VALUE + String.format("%02d", new Integer(counterNumber)) + counterValue;

		sendCommand(fullCommand);
		try {
			return readMessages(CV_REPLY_COUNTER_VALUE_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.13 Display Text On LCD Screen (dm)
	 * 
	 * </quote>
	 * 
	 * @param keypadArea
	 * @param clearType
	 * @param beep
	 * @param timeoutSeconds
	 * @param lineOne
	 * @param lineTwo
	 */
	public void displayTextOnLCDScreen(int keypadArea, int clearType, int beep, int timeoutSeconds, String lineOne, String lineTwo) {
		if (keypadArea < 1 || keypadArea > 8) {
			throw new IllegalArgumentException("keypadArea must be 1 - 8");
		}
		if (clearType != 0 && clearType != 1 && clearType != 2) {
			throw new IllegalArgumentException("clearType can only be 0, 1, 2");
		}
		if (beep != 0 && beep != 1) {
			throw new IllegalArgumentException("beep must be 0 or 1");
		}
		if (timeoutSeconds < 1 || timeoutSeconds > 65535) {
			throw new IllegalArgumentException("timeoutSeconds must be 1 - 65535");
		}
		if (lineOne.length() > 16 || lineTwo.length() > 16) {
			throw new IllegalArgumentException("line length must not exceed 16 characters");
		}
		if (lineOne.length() < 16) {
			lineOne = lineOne + "^";
		}
		if (lineTwo.length() < 16) {
			lineTwo = lineTwo + "^";
		}
		lineOne = String.format("%1$-16s", lineOne).replace(' ', 'A');
		lineTwo = String.format("%1$-16s", lineTwo).replace(' ', 'A');
		
		String fullCommand = dm_CMD_DISPLAY_MESSAGE + keypadArea + clearType + beep + timeoutSeconds + lineOne + lineTwo;

		sendCommand(fullCommand);
	}
	
	/**
	 * <quote>
	 * 
	 * 4.14.1 Request Lighting Device Status (ds)
	 * 
	 * 4.14.2 Reply Lighting Device Status Data (DS)
	 * 
	 * </quote>
	 * 
	 * @param deviceNumber
	 * @return message of type ReplyLIghtingDeviceStatus
	 */
	public Message requestLightingDeviceStatus(int deviceNumber) {
		if (deviceNumber < 1 || deviceNumber > 192) {
			throw new IllegalArgumentException("device number must be between 1 - 192");
		}
		
		String fullCommand = ds_CMD_LIGHTING_POLL_REQUEST + String.format("%03d", deviceNumber);

		sendCommand(fullCommand);
		try {
			return readMessages(DS_REPLY_LIGHTING_POLL_RESPONSE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.19.1 Request ASCII Lighting Device Description (sd)
	 * 
	 * 4.19.2 Reply With ASCII String Text Descriptions (SD)
	 * 
	 * </quote>
	 * 
	 * @param deviceNumber
	 * @return message of type ReplyReadInsteonLightingDeviceData
	 */
	public Message requestASCIILightingDeviceDescription(int deviceNumber) {
		if (deviceNumber < 1 || deviceNumber > 192) {
			throw new IllegalArgumentException("device number must be between 1 - 192");
		}
		
		String fullCommand = sd_CMD_REQUEST_STRING_DESCRIPTIONS + "07" + String.format("%03d", deviceNumber);

		sendCommand(fullCommand);
		try {
			return readMessages(SD_REPLY_STRING_DESCRIPTIONS_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.19.3 Request Read Of Insteon Lighting Device Data (ir)
	 * 
	 * 4.19.4 Reply Read Of Insteon Lighting Device Data (IR)
	 * 
	 * </quote>
	 * 
	 * @param startingDevice
	 * @param numberOfDevices
	 * @return message of type ReplyReadInsteonLightingDeviceData
	 */
	public Message readInsteonLightingDeviceData(int startingDevice, int numberOfDevices) {
		if (startingDevice < 1 || startingDevice > 192) {
			throw new IllegalArgumentException("device number must be between 1 - 192");
		}
		if ((startingDevice + numberOfDevices) > 192) {
			throw new IllegalArgumentException("invalid startingDevice or numberOfDevices requested");
		}
		
		String fullCommand = ir_CMD_M1XSP_INSTEON_READ + String.format("%03d", startingDevice + numberOfDevices);

		sendCommand(fullCommand);
		try {
			return readMessages(IR_REPLY_M1XSP_INSTEON_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.19.5 Request Programming Of Insteon Lighting Device Data
	 * 
	 * 4.41.3 Reply Programming Of Insteon Lighting Device Data (IP)
	 * 
	 * </quote>
	 * 
	 * @param startingDevice
	 * @param deviceIDs
	 * @return message of type ReplyProgrammingInsteonLightingData
	 */
	public Message requestProgrammingOfInsteonLightingDevice(int startingDevice, String[] deviceIDs) {
		if (startingDevice < 1 || startingDevice > 192) {
			throw new IllegalArgumentException("device number must be between 1 - 192");
		}
		if (deviceIDs.length < 1) {
			throw new IllegalArgumentException("need to specify at least one deviceID");
		}
		String fullCommand = ip_CMD_M1XSP_INSTEON_PROGRAM + String.format("%03d", startingDevice + deviceIDs.length);
		for (int i = 0; i < deviceIDs.length; i++) {
			fullCommand = fullCommand + deviceIDs[i];
		}

		sendCommand(fullCommand);
		try {
			return readMessages(IP_REPLY_M1XSP_INSTEON_PROGRAM);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <quote>
	 * 
	 * 4.20.1 Request Keypad Area Assignment (ka)
	 * 
	 * 4.20.2 Reply With Keypad Areas (KA)
	 * 
	 * </quote> 
	 * 
	 * @return  message of type ReplyKeypadAreas
	 */
	public Message requestKeypadAreas() {
		String fullCommand = ka_CMD_REQUEST_KEYPAD_AREAS;

		sendCommand(fullCommand);
		try {
			return readMessages(KA_REPLY_KEYPAD_AREAS_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.21.1 Request Keypad Function Key Illumination Status (kc)
	 * 
	 * 4.21 Keypad KeyChange Update (KC)
	 * 
	 * </quote> 
	 * 
	 * @param keypadNumber
	 * @return message of type KeypadKeyChangeUpdate
	 */
	public Message requestKeypadFunctionKeyIlluminationStatus(int keypadNumber) {
		if (keypadNumber < 1 || keypadNumber > 16) {
			throw new IllegalArgumentException("keypadNumber must be between 1 and 16");
		}
		sendCommand(kc_CMD_REQUEST_F_KEY_ILLUMINATION_STATUS + String.format("%02d", keypadNumber));
		try {
			return readMessages(KC_REPLY_KEYPAD_KEY_CHANGE_UPDATE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * <quote>
	 * 
	 * 4.22.1 Request Keypad Function Key Press (kf)
	 * 
	 * 4.22.2 Reply Keypad Function Key Press (KF)
	 * 
	 * </quote>
	 * 
	 * @param keypadNumber
	 * @param functionKey
	 * @return message of type ReplyKeypadFunctionKeyPress
	 */
	public Message requestKeypadFunctionKeyPress(int keypadNumber, String functionKey) {
		if (keypadNumber < 1 || keypadNumber > 16) {
			throw new IllegalArgumentException("keypadNumber must be between 1 and 16");
		}
		if (functionKey.length() > 1) {
			throw new IllegalArgumentException("functionKey must be 1 character");
		}
		sendCommand(kf_CMD_REQUEST_SIMULATED_FUNCTION_KEY_PRESS + String.format("%02d", keypadNumber) + functionKey);
		try {
			return readMessages(KF_REPLY_FUNCTION_KEY_PRESSED_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO:  ld_CMD_REQUEST_LOG_DATA_WITH_INDEX
	
	// TODO:  le_CMD_WRITE_LOG_DATA_ENTRY
	
	/**
	 * <quote>
	 * 
	 * 4.24.1 Request Temperature Data (lw)
	 * 
	 * 4.28.2 Reply Real Time Clock Data (RR)
	 * 
	 * </quote>
	 */
	public void requestTemperatureData() {
		sendCommand(lw_CMD_REQUEST_TEMPERATURE_DATA);
	}
	
	/**
	 * 4.28.1 Request Real Time Clock Data (rr)
	 * 
	 * 
	 * @return
	 */
	public Message requestRealTimeClockData() {
		sendCommand(rr_CMD_REQUEST_REAL_TIME_CLOCK_DATA);
		try {
			return readMessages(RR_REPLY_REAL_TIME_CLOCK_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO: rw

	// TODO: sd
	
	// TODO: SS
	public Message requestSystemTroubleStatus() {
		sendCommand(ss_CMD_REQUEST_SYSTEM_TROUBLE_STATUS);
		try {
			return readMessages(SS_REPLY_SYSTEM_TROUBLE_STATUS_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO: st ST
	
	// TODO: sp
	
	// TODO: TC
	
	// TODO: tn TN
	
	// TODO: tr TR
	
	// TODO: ts TS
	
	/**
	 * 4.36.1 Request M1 Version Number (vn)
	 * 
	 * 4.36.2 Reply M1 Version Number (VN)
	 * 
	 * @return
	 */
	public Message requestM1VersionNumber() {
		sendCommand(vn_CMD_REQEUST_VERSION_NUMBER_OF_M1);
		try {
			return readMessages(VN_REPLY_VERSION_NUMBER_OF_M1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// TODO:  ua and UA
	
	/**
	 * 4.39.1 Zone Bypass Request (zb)
	 * 
	 * 4.39.2 Reply With Bypassed Zone State (ZB)
	 * 
	 * @param zone
	 * @param area
	 * @return message of type BypassedZoneState
	 */
	public Message requestBypassZone(String zone, String area) {
		if (area == null) {
			area = "1";
		}

		String fullCommand = zb_CMD_ZONE_BYPASS_REQUEST + zone + area
				+ String.format("%06d", Integer.parseInt(code));

		sendCommand(fullCommand);
		try {
			return readMessages(ZB_REPLY_ZONE_BYPASS_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 4.39.3 Zone Partition Request (zp)
	 * 
	 * 4.39.4 Zone Partition Report (ZP)
	 * 
	 * @return message of type ZonePartitionReply
	 */
	public Message requestZonePartitions() {
		sendCommand(zp_CMD_ZONE_PARTITION_REQUEST);
		try {
			return readMessages(ZP_REPLY_ZONE_PARTITION_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 4.39.5 Zone Status Request (zs)
	 * 
	 * 4.39.6 Zone Status Report (ZS)
	 * 
	 * @return message of type ZoneStatusReport
	 */
	public Message requestZoneStatus() {
		sendCommand(zs_CMD_ZONE_STATUS_REQUEST);
		try {
			return readMessages(ZS_REPLY_ZONE_STATUS_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 4.40.1 Request Zone Definition (zd)
	 * 
	 * 4.40.2 Reply Zone Definition Data (ZD)
	 * 
	 * @return message of type ZoneDefinitionData
	 */
	public Message requestZoneDefinitions() {
		sendCommand(zd_CMD_REQUEST_ZONE_DEFINITION_DATA);
		try {
			return readMessages(ZD_REPLY_ZONE_DEFINITION_REPORT_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 4.41.1 Request Zone Voltage (zv)
	 * 
	 * 4.41.2 Reply Zone Analog Voltage Data (ZV)
	 * 
	 * @param zoneNumber
	 * @return message of type ZoneAnalogVoltageReply
	 */
	public Message requestZoneVoltage(int zoneNumber) {
		if (zoneNumber < 1 || zoneNumber > 208) {
			throw new IllegalArgumentException("invalid zoneNumber specified.  (1 - 208)");
		}
		sendCommand(zv_CMD_REQUEST_ZONE_ANALOG_VOLTAGE + String.format("%03d", zoneNumber));
		try {
			return readMessages(ZV_REPLY_ZONE_ANALOG_VOLTAGE_DATA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Getters/Setters  
	 * 
	 */	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isUseSSL() {
		return useSSL;
	}

	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * SSL Handshake Completed Listener 
	 * 
	 * Log the handshake
	 */
	public void handshakeCompleted(HandshakeCompletedEvent event) {
		System.out.println("handshake completed");

		try {
			System.out.println("getCipherSuite: " + event.getCipherSuite());

			SSLSession session = event.getSession();
			System.out.println("getProtocol: " + session.getProtocol());
			System.out.println("getPeerHost: " + session.getPeerHost());

			java.security.cert.Certificate[] certs = event
					.getPeerCertificates();
			for (int i = 0; i < certs.length; i++) {
				if (!(certs[i] instanceof java.security.cert.X509Certificate))
					continue;
				java.security.cert.X509Certificate cert = (java.security.cert.X509Certificate) certs[i];
				System.out.println("Cert #" + i + ": "
						+ cert.getSubjectDN().getName());
			}
		} catch (Exception e) {
			System.err.println("handshakeCompletedError: " + e);
		}
	}
}
