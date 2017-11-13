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

public class ZonePartitionReply extends Message {

	public ZonePartitionReply(String cmd, String fullCommand) {
		super(cmd, fullCommand);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ZonePartitionReply:\n");
		for (int i=2; i<210; i++) {
			sb.append("Zone " + (i - 1) + " => Area " + cmdBody.substring(i, i+1));
			if (i % 2 == 0) {
				sb.append("\t\t");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
