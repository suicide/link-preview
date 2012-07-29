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
package com.hastybox.linkpreview.model.linktype;

import com.hastybox.linkpreview.model.LinkPreviewType;

/**
 * Common LinkPreviewType implementation covering all types the library can identify.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public enum CommonLinkPreviewType implements LinkPreviewType {

	GENERIC("generic", "generic website");

	/**
	 * the name of this type. This is a technical name.
	 */
	private final String name;

	/**
	 * the description of this type. This value should be used as a label.
	 */
	private final String description;

	/**
	 * constructor
	 */
	private CommonLinkPreviewType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
