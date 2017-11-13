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

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;

import com.elkm1api.ElkM1Controller;
import com.elkm1api.messages.Message;

public class ClientTest implements HandshakeCompletedListener, Runnable {

	private final static String TS_PATH = "C://<path>//jssecacerts";
	private final static String TS_PASS = "changeit";

	private final static String HOST = "<host>";
	private final static String PASS_CODE = "<passcode>";
	private final static String M1XEP_USER = "<username>";
	private final static String M1XEP_PASSWORD = "<password>";

	private final static boolean USE_SSO = true;

	private ElkM1Controller m1;

	public static void main(String[] args) throws Exception {
		(new ClientTest()).run(args);
	}

	private void run(String[] args) throws Exception {

		m1 = new ElkM1Controller(HOST, USE_SSO, PASS_CODE, M1XEP_USER,
				M1XEP_PASSWORD, TS_PATH, TS_PASS);

		Thread t = new Thread(this); // start receiver thread
		t.start();

		t.join();
		m1.disconnect();
	}

	public void run() {
		Message msg = null;
		try {
			while ((msg = m1.readMessages(null)) != null) {
				System.out.println(msg.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			System.err.println("handshakeCompleted: " + e);
		}
	}
}