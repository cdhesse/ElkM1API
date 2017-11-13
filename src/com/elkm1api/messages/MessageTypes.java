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
package com.elkm1api.messages;

public interface MessageTypes {
	public final static String a0_CMD_DISARM = "a0";
	public final static String a1_CMD_ARM_AWAY = "a1";
	public final static String a2_CMD_ARM_STAY = "a2";
	public final static String a3_CMD_ARM_STAY_INSTANT = "a3";
	public final static String a4_CMD_ARM_NIGHT = "a4";
	public final static String a5_CMD_ARM_NIGHT_INSTANT = "a5";
	public final static String a6_CMD_ARM_VACATION = "a6";
	public final static String a7_CMD_ARM_STEP_NEXT_AWAY = "a7";
	public final static String a8_CMD_ARM_STEP_NEXT_STAY = "a8";	
	public final static String AP_SEND_ASCII_STRING = "AP";
	public final static String ar_ALARM_REPORTING_ACKNOWLEDGE = "ar";
	public final static String AR_REPLY_ALARM_REPORT = "AR";    //Alarm Reporting to Ethernet;
	public final static String as_CMD_REQUEST_ARMING_STATUS = "as";    //Request arming status;
	public final static String AS_REPLY_ARMING_STATUS_REPORT_DATA = "AS";    //Arming status report data;
	//public final static String at_CMD_ETHERNET_TEST_AK = "at";    //Ethernet Test Acknowledge;  Does not apply as it is between elk and eth
	public final static String AT_ETHERNET_TEST_TO_IP = "AT";    //Ethernet Test to IP;
	public final static String az_CMD_ALARM_BY_ZONE = "az";    //Alarm by zone request;
	public final static String AZ_REPLY_ALARM_BY_ZONE = "AZ";    //Alarm by zone reply;
	public final static String ca_CMD_REQUEST_AUDIO_DATA = "ca";    //Request Touchscreen audio command;
	public final static String CA_REPLY_AUDIO_COMMAND = "CA";    //Reply Touchscreen audio command;
	public final static String CC_REPLY_CONTROL_OUTPUT_CHANGE_UPDATE = "CC";    //Control output change update;
	public final static String cd_CMD_INCOMING_AUDIO_EQUIP = "cd";    //Incoming Audio Equip Command;
	public final static String CD_REPLY_OUTGOING_AUDIO_EQUIP = "CD";    //Outgoing Audio Equip Command;
	public final static String cf_CMD_CONTROL_OUTPUT_OFF = "cf";    //Control output OFF;
	public final static String cn_CMD_CONTROL_OUTPUT_ON = "cn";    //Control output ON;
	public final static String cp_CMD_REQUEST_ALL_CUSTOM_VALUES = "cp";    //Request ALL custom values;
	public final static String cr_CMD_REQUEST_CUSTOM_VALUE = "cr";    //Request custom value;
	public final static String CR_REPLY_CUSTOM_VALUE_REPORT_DATA = "CR";    //Custom value report data;
	public final static String cs_CMD_CONTROL_OUTPUT_STATUS_REQUEST = "cs";    //Control output status request;
	public final static String CS_REPLY_CONTROL_OUTPUT_STATUS_REPORT = "CS";    //Control output status report data;
	public final static String ct_CMD_CONTROL_OUTPUT_TOGGLE = "ct";    //Control output TOGGLE;
	public final static String cu_CMD_CHANGE_USER_CODE_REQUEST = "cu";    //Change user code request;
	public final static String CU_REPLY_CHANGE_USER_CODE_REPLY = "CU";    //Change user code reply;
	public final static String cw_CMD_WRITE_CUSTOM_VALUE_DATA = "cw";    //Write custom value data;
	public final static String cv_CMD_REQUEST_COUNTER_VALUE = "cv";    //Request Counter value;
	public final static String CV_REPLY_COUNTER_VALUE_DATA = "CV";    //Counter Value Data;
	public final static String cx_CMD_WRITE_COUNTER_VALUE = "cx";    //Write counter value;
	public final static String dm_CMD_DISPLAY_MESSAGE = "dm";    //Display message;
	public final static String ds_CMD_LIGHTING_POLL_REQUEST = "ds";    //Lighting Poll Request;
	public final static String DS_REPLY_LIGHTING_POLL_RESPONSE = "DS";    //Lighting Poll Response;
	//public final static String DK_REPLY_DISPLAY_KP_LCD_DATA = "DK";    //Display KP LCD Data, not used;
	public final static String EE_REPLY_ENTRY_EXIT_TIME_DATA = "EE";    //Entry/Exit Time Data;
	public final static String EM_REPLY_EMAIL_TRIGGER_TO_M1XEP = "EM";    //Email Trigger to M1XEP;
	public final static String IC_REPLY_SEND_INVALID_USER_CODE_DIGITS = "IC";    //Send invalid user code digits;
	public final static String IE_REPLY_INSTALLER_PROGRAM_EXITED = "IE";    //Installer program exited;
	public final static String IP_REPLY_M1XSP_INSTEON_PROGRAM = "IP";    //M1XSP Insteon Program;
	public final static String ip_CMD_M1XSP_INSTEON_PROGRAM = "ip";    //M1XSP Insteon Program;
	public final static String IR_REPLY_M1XSP_INSTEON_READ = "IR";    //M1XSP Insteon Read;
	public final static String ir_CMD_M1XSP_INSTEON_READ = "ir";    //M1XSP Insteon Read;
	public final static String ka_CMD_REQUEST_KEYPAD_AREAS = "ka";    //Request keypad areas;
	public final static String KA_REPLY_KEYPAD_AREAS_REPORT_DATA = "KA";    //Keypad areas report data;
	public final static String kc_CMD_REQUEST_F_KEY_ILLUMINATION_STATUS = "kc";    //Request F Key illumination status;
	public final static String KC_REPLY_KEYPAD_KEY_CHANGE_UPDATE = "KC";    //Keypad key change update;
	public final static String kf_CMD_REQUEST_SIMULATED_FUNCTION_KEY_PRESS = "kf";    //Request simulated function key press;
	public final static String KF_REPLY_FUNCTION_KEY_PRESSED_DATA = "KF";    //Function key pressed data;
	public final static String LD_REPLY_LOG_DATA_WITH_INDEX = "LD";    //Log data with index;
	public final static String ld_CMD_REQUEST_LOG_DATA_WITH_INDEX = "ld";    //Request log data, with index;
	public final static String le_CMD_WRITE_LOG_DATA_ENTRY = "le";    //Write Log Data Entry;
	public final static String lw_CMD_REQUEST_TEMPERATURE_DATA = "lw";    //Request temperature data;
	public final static String LW_REPLY_REPLY_TEMPERATURE_DATA = "LW";    //Reply temperature data;
	public final static String NS_REPLY_SOURCE_NAME = "NS";    //Reply Source Name;
	public final static String NZ_REPLY_ZONE_NAME = "NZ";    //Reply Zone Name;
	public final static String pc_CMD_CONTROL_ANY_PLC_DEVICE = "pc";    //Control any PLC device;
	public final static String PC_REPLY_PLC_CHANGE_UPDATE = "PC";    //PLC change update;
	public final static String pf_CMD_TURN_OFF_PLC_DEVICE = "pf";    //Turn OFF PLC device;
	public final static String pn_CMD_TURN_ON_PLC_DEVICE = "pn";    //Turn ON PLC device;
	public final static String ps_CMD_REQUEST_PLC_STATUS = "ps";    //Request PLC status;
	public final static String PS_REPLY_PLC_STATUS_REPORT_DATA = "PS";    //PLC status report data;
	public final static String pt_CMD_TOGGLE_PLC_DEVICE = "pt";    //Toggle PLC device;
	public final static String RE_REPLY_RESET_ETHERNET_MODULE = "RE";    //Reset Ethernet Module;
	public final static String RP_REPLY_ELKRP_CONNECTED = "RP";    //ELKRP connected;
	public final static String rr_CMD_REQUEST_REAL_TIME_CLOCK_DATA = "rr";    //Request Real Time Clock Read;
	public final static String RR_REPLY_REAL_TIME_CLOCK_DATA = "RR";    //Real Time Clock Data;
	public final static String rs_CMD_USED_BY_TOUCHSCREEN = "rs";    //Used by Touchscreen;
	public final static String rw_CMD_REAL_TIME_CLOCK_WRITE = "rw";    //Real Time Clock Write;
	public final static String sd_CMD_REQUEST_STRING_DESCRIPTIONS = "sd";    //Request text string descriptions;
	public final static String SD_REPLY_STRING_DESCRIPTIONS_REPORT_DATA = "SD";    //Text string description report data;
	public final static String sp_CMD_SPEAK_PHRASE = "sp";    //Speak phrase;
	public final static String ss_CMD_REQUEST_SYSTEM_TROUBLE_STATUS = "ss";    //Request System Trouble Status;
	public final static String SS_REPLY_SYSTEM_TROUBLE_STATUS_DATA = "SS";    //System Trouble Status data;
	public final static String st_CMD_REQUEST_TEMPERATURE = "st";    //Request temperature;
	public final static String ST_REPLY_TEMPERATURE_REPORT_DATA = "ST";    //Temperature report data;
	public final static String sw_CMD_SPEAK_WORD = "sw";    //Speak word;
	public final static String t2_CMD_REQUEST_OMNISTAT2_DATA = "t2";    //Request Omnistat 2 data;
	public final static String T2_REPLY_OMNISTAT2_DATA = "T2";    //Reply Omnistat 2 data;
	public final static String TC_REPLY_TASK_CHANGE_UPDATE = "TC";    //Task change update;
	public final static String tn_CMD_TASK_ACTIVATION = "tn";    //Task activation;
	public final static String tr_CMD_REQUEST_THEMOSTAT_DATA = "tr";    //Request thermostat data;
	public final static String TR_REPLY_THEMOSTAT_DATA_REPORT = "TR";    //Thermostat data report;
	public final static String ts_CMD_SET_THEMOSTAT_DATA = "ts";    //Set thermostat data;
	public final static String ua_CMD_REQUEST_USER_CODE_AREAS = "ua";    //Request user code areas;
	public final static String UA_REPLY_USER_CODE_AREAS_REPORT_DATA = "UA";    //User code areas report data;
	public final static String vn_CMD_REQEUST_VERSION_NUMBER_OF_M1 = "vn";    //request Version Number of M1;
	public final static String VN_REPLY_VERSION_NUMBER_OF_M1 = "VN";    //Reply Version Number of M1;
	//public final static String XB_REPLY_RESERVED_BY_ELK_XB = "XB";    //reserved by ELKRP;
	//public final static String xk_CMD_REPLY_FROM_ETHERNET_TEST = "xk";    //Reply from Ethernet test;
	public final static String XK_ETHERNET_MODULE_TEST = "XK";    //Request Ethernet test;
	public final static String zb_CMD_ZONE_BYPASS_REQUEST = "zb";    //Zone bypass request;
	public final static String ZB_REPLY_ZONE_BYPASS_REPORT_DATA = "ZB";    //Zone bypass report data;
	public final static String ZC_REPLY_ZONE_CHANGE_UPDATE = "ZC";    //Zone change update;
	public final static String zd_CMD_REQUEST_ZONE_DEFINITION_DATA = "zd";    //Request zone definition data;
	public final static String ZD_REPLY_ZONE_DEFINITION_REPORT_DATA = "ZD";    //Zone definition report data;
	public final static String zp_CMD_ZONE_PARTITION_REQUEST = "zp";    //Zone partition request;
	public final static String ZP_REPLY_ZONE_PARTITION_REPORT_DATA = "ZP";    //Zone partition report data;
	public final static String zs_CMD_ZONE_STATUS_REQUEST = "zs";    //Zone status request;
	public final static String ZS_REPLY_ZONE_STATUS_REPORT_DATA = "ZS";    //Zone status report data;
	public final static String zv_CMD_REQUEST_ZONE_ANALOG_VOLTAGE = "zv";    //Request Zone analog voltage;
	public final static String ZV_REPLY_ZONE_ANALOG_VOLTAGE_DATA = "ZV";    //Zone analog voltage data;

}
