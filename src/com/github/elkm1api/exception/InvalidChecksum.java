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
package com.github.elkm1api.exception;

public class InvalidChecksum extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2833681958911742205L;

	public InvalidChecksum() {
	}

	public InvalidChecksum(String message) {
		super(message);
	}

	public InvalidChecksum(Throwable cause) {
		super(cause);
	}

	public InvalidChecksum(String message, Throwable cause) {
		super(message, cause);
	}
}