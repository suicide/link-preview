/* Copyright 2011-2012 The Cellador Hastybox Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hastybox.linkpreview.common;

/**
 * General Link Preview Exception
 * 
 * @author Patrick Sy (psy@get-it.us)
 *
 */
public class LinkPreviewException extends Exception {
	
	/**
	 * serial uid
	 */
	private static final long serialVersionUID = -3321193963120420014L;

	/**
	 * constructor
	 */
	public LinkPreviewException(String message) {
		super(message);
	}
	
	/**
	 * constructor
	 */
	public LinkPreviewException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * constructor
	 */
	public LinkPreviewException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
