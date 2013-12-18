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

import java.io.IOException;

import com.github.elkm1api.ElkM1Controller;
import com.github.elkm1api.messages.Message;

public class SimpleConnect {

	private final static String TS_PATH = "C://<path>//jssecacerts";
	private final static String TS_PASS = "changeit";

	private final static String HOST = "<host>";
	private final static String PASS_CODE = "<passcode>";
	private final static String M1XEP_USER = "<username>";
	private final static String M1XEP_PASSWORD = "<password>";

	private final static boolean USE_SSO = true;

	public static void main(String[] args) throws IOException {

		ElkM1Controller m1 = new ElkM1Controller(HOST, USE_SSO, PASS_CODE,
				M1XEP_USER, M1XEP_PASSWORD, TS_PATH, TS_PASS);

		Message msg = m1.readCustomValue(3);
		System.out.println(msg.toString());

		m1.disconnect();
	}
}