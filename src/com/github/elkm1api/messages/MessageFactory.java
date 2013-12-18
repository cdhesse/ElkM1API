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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MessageFactory
 */
public class MessageFactory implements MessageTypes {

	private static MessageFactory instance = null;

	public static final Map<String, String> MSG_CLASSES = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put(AS_REPLY_ARMING_STATUS_REPORT_DATA,
							"com.github.elkm1api.messages.types.ArmingStatusReport");
					put(AZ_REPLY_ALARM_BY_ZONE, 
							"com.github.elkm1api.messages.types.AlarmByZoneReportData");
					put(CA_REPLY_AUDIO_COMMAND,
							"com.github.elkm1api.messages.types.AudioDataReply");
					put(CC_REPLY_CONTROL_OUTPUT_CHANGE_UPDATE,
							"com.github.elkm1api.messages.types.OutputChangeUpdate");
					put(CD_REPLY_OUTGOING_AUDIO_EQUIP,
							"com.github.elkm1api.messages.types.OutgoingAudioCommand");
					put(CS_REPLY_CONTROL_OUTPUT_STATUS_REPORT,
							"com.github.elkm1api.messages.types.ControlOutputStatusReport");
					put(CR_REPLY_CUSTOM_VALUE_REPORT_DATA,
							"com.github.elkm1api.messages.types.ReplyCustomValue");
					//put(CU_REPLY_CHANGE_USER_CODE_REPLY,
					//		"com.github.elkm1api.messages.types.ReplyChangeUserCode");
					put(CV_REPLY_COUNTER_VALUE_DATA,
							"com.github.elkm1api.messages.types.ReplyCounterValueFormat");
					put(DS_REPLY_LIGHTING_POLL_RESPONSE,
							"com.github.elkm1api.messages.types.ReplyLightingDeviceStatus");
					put(EE_REPLY_ENTRY_EXIT_TIME_DATA,
							"com.github.elkm1api.messages.types.SendEntryExitData");
					put(EM_REPLY_EMAIL_TRIGGER_TO_M1XEP,
							"com.github.elkm1api.messages.types.SendEmailTriggerData");
					//put(IC_REPLY_SEND_INVALID_USER_CODE_DIGITS,
					//		"com.github.elkm1api.messages.types.SendValidOrInvalidUserCode");
					put(IE_REPLY_INSTALLER_PROGRAM_EXITED,
							"com.github.elkm1api.messages.types.SendInstallerModeExited");
					put(SD_REPLY_STRING_DESCRIPTIONS_REPORT_DATA,
							"com.github.elkm1api.messages.types.ReplyASCIIStringTextDescriptions");
					put(IR_REPLY_M1XSP_INSTEON_READ,
							"com.github.elkm1api.messages.types.ReplyReadInsteonLightingDeviceData");
					put(KA_REPLY_KEYPAD_AREAS_REPORT_DATA,
							"com.github.elkm1api.messages.types.ReplyKeypadAreas");					
					put(KC_REPLY_KEYPAD_KEY_CHANGE_UPDATE,
							"com.github.elkm1api.messages.types.KeypadKeyChangeUpdate");
					put(KF_REPLY_FUNCTION_KEY_PRESSED_DATA,
							"com.github.elkm1api.messages.types.ReplyKeypadFunctionKeyPress");
					// TODO:  LD_REPLY_LOG_DATA_WITH_INDEX
					put(LD_REPLY_LOG_DATA_WITH_INDEX,
							 "com.github.elkm1api.messages.types.ReplyLogUpdate");
					
					put(RE_REPLY_RESET_ETHERNET_MODULE,
							"com.github.elkm1api.messages.types.ResetEthernetModule");
					put(RP_REPLY_ELKRP_CONNECTED,
							"com.github.elkm1api.messages.types.ElkRPConnected");
					put(RR_REPLY_REAL_TIME_CLOCK_DATA,
							"com.github.elkm1api.messages.types.ReplyRealTimeClockData");
					
					put(TC_REPLY_TASK_CHANGE_UPDATE,
							"com.github.elkm1api.messages.types.TaskChangeUpdate");
					

					put(LW_REPLY_REPLY_TEMPERATURE_DATA,
							"com.github.elkm1api.messages.types.ReplyTemperatureData");
					
					put(TR_REPLY_THEMOSTAT_DATA_REPORT,
							"com.github.elkm1api.messages.types.ReplyThermostatData");
					put(PC_REPLY_PLC_CHANGE_UPDATE,
							"com.github.elkm1api.messages.types.ReplyPLCChangeUpdate");
					
					put(XK_ETHERNET_MODULE_TEST,
							"com.github.elkm1api.messages.types.ReplyRealTimeClockData");
					
					put(ZB_REPLY_ZONE_BYPASS_REPORT_DATA,
							"com.github.elkm1api.messages.types.BypassedZoneState");
					put(ZC_REPLY_ZONE_CHANGE_UPDATE,
							"com.github.elkm1api.messages.types.ZoneChangeUpdateReport");
					put(ZP_REPLY_ZONE_PARTITION_REPORT_DATA,
							"com.github.elkm1api.messages.types.ZonePartitionReply");
					put(ZS_REPLY_ZONE_STATUS_REPORT_DATA,
							"com.github.elkm1api.messages.types.ZoneStatusReport");
					put(ZV_REPLY_ZONE_ANALOG_VOLTAGE_DATA,
							"com.github.elkm1api.messages.types.ReplyZoneAnalogVoltageDatay");
				}

			});

	// Hide default constructor
	protected MessageFactory() {

	}

	public static MessageFactory getInstance() {
		if (instance == null) {
			instance = new MessageFactory();
		}

		return instance;
	}

	public Message getMessageClass(String msg) {
		String msgType = msg.substring(2, 4);

		String classStr = MSG_CLASSES.get(msgType);
		Message message = null;
		try {
			Class<Message> claz = (Class<Message>) Class.forName(classStr);
			Constructor constructor = claz.getConstructor(String.class,
					String.class);

			message = (Message) constructor.newInstance(null, msg);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}
}
