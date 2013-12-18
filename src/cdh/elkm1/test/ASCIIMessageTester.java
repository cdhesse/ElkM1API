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
package cdh.elkm1.test;

import com.github.elkm1api.messages.Message;
import com.github.elkm1api.messages.types.AlarmByZoneReportData;
import com.github.elkm1api.messages.types.AlarmReporting;
import com.github.elkm1api.messages.types.AlarmReportingTest;
import com.github.elkm1api.messages.types.AudioDataReply;
import com.github.elkm1api.messages.types.ElkRPConnected;
import com.github.elkm1api.messages.types.ReplyM1VersionNumber;
import com.github.elkm1api.messages.types.ReplyRealTimeClockData;
import com.github.elkm1api.messages.types.KeypadKeyChangeUpdate;
import com.github.elkm1api.messages.types.OutgoingAudioCommand;
import com.github.elkm1api.messages.types.OutputChangeUpdate;
import com.github.elkm1api.messages.types.ReplyASCIIStringTextDescriptions;
import com.github.elkm1api.messages.types.ReplyCustomValue;
import com.github.elkm1api.messages.types.ReplyKeypadAreas;
import com.github.elkm1api.messages.types.ReplyKeypadFunctionKeyPress;
import com.github.elkm1api.messages.types.ReplyProgrammingInsteonLightingData;
import com.github.elkm1api.messages.types.ReplyReadInsteonLightingDeviceData;
import com.github.elkm1api.messages.types.ResetEthernetModule;
import com.github.elkm1api.messages.types.SendEntryExitData;
import com.github.elkm1api.messages.types.ZoneAnalogVoltageReply;
import com.github.elkm1api.messages.types.ZoneChangeUpdateReport;
import com.github.elkm1api.messages.types.ZoneDefinitionData;

/**
 * These messages are the samples which are documented in the ELKM1XEP API doc
 * ELK-M1_RS232_PROTOCOL.pdf
 * 
 * @author cdhesse
 *
 */
public class ASCIIMessageTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// AT
		Message msg = new AlarmReportingTest(null, "07AT10073");
		//System.out.println(msg.toString());
		
		// AT
		msg = new AlarmReporting(null, "16AR12345611340100110085");
		//System.out.println(msg.toString());
		
		// AZ
		msg = new AlarmByZoneReportData(null, "D6AZ00000000900000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000082");
		//System.out.println(msg.toString());
		
		// CA
		msg = new AudioDataReply(null, "20CA0110205006004010500000000000C1");
		//System.out.println(msg.toString());
		
		// CC
		msg = new OutputChangeUpdate(null, "0ACC003100E5");
		//System.out.println(msg.toString());
		
		// CD
		msg = new OutgoingAudioCommand(null, "0FCD02030400000EA");
		//System.out.println(msg.toString());
		
		// CR
		msg = new ReplyCustomValue(null, "0ECR06001230000A");
		//System.out.println(msg.toString());
		
		// EE
		msg = new SendEntryExitData(null, "0FEE10060120100E5");
		//System.out.println(msg.toString());
		
		// IR
		msg = new ReplyReadInsteonLightingDeviceData(null, "22IR0014123456ABCDEF987654A1B2C3006F");
		//System.out.println(msg.toString());
		
		// SD
		msg = new ReplyASCIIStringTextDescriptions(null, "1DSD07001Hall Light      0089D2");
		//System.out.println(msg.toString());
		
		// IP
		msg = new ReplyProgrammingInsteonLightingData(null, "0AIP001400D1");
		//System.out.println(msg.toString());
		
		// KA
		msg = new ReplyKeypadAreas(null, "16KA12345678111111110081");
		//System.out.println(msg.toString());
		
		// KC
		msg = new KeypadKeyChangeUpdate(null, "19KC01112010000200000000010");
		//System.out.println(msg.toString());
		
		// KF
		msg = new ReplyKeypadFunctionKeyPress(null, "11KF01C200000000087");
		//System.out.println(msg.toString());
		
		// RE
		msg = new ResetEthernetModule(null, "07RE00072");
		//System.out.println(msg.toString());
				
		// RP
		msg = new ElkRPConnected(null, "08RP000036");
		//System.out.println(msg.toString());
		
		// RR
		msg = new ReplyRealTimeClockData(null, "16RR0059107251205110006E");
		//System.out.println(msg.toString());
				
		// XK
		msg = new ReplyRealTimeClockData(null, "16XK54021813012120000078");
		//System.out.println(msg.toString());
		
		// VN
		msg = new ReplyM1VersionNumber(null, "36VN05010C0103020000000000000000000000000000000000000074");
		System.out.println(msg.toString());
		
		// ZV
		msg = new ZoneAnalogVoltageReply(null, "0CZV123072004E");
		//System.out.println(msg.toString());
		
		// ZC
		msg = new ZoneChangeUpdateReport(null, "0AZC002500CB");
		//System.out.println(msg.toString());
		
		// ZD
		msg = new ZoneDefinitionData(null, "D6ZDGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@ABCGH123T@A00A2");
		//System.out.println(msg.toString());
	}

}
