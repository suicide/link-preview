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
package com.hastybox.linkpreview.model;

import java.util.Date;

/**
 * A Link Preview. An interface is used so that developers can provide their own
 * implementation if needed, e.g. if you want to use JPA.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public interface LinkPreview {

	/**
	 * @return the generated id
	 */
	Long getId();

	/**
	 * @param id
	 *            the id
	 */
	void setId(Long id);

	/**
	 * might be the canonical url
	 * 
	 * @return the url
	 */
	String getUrl();

	/**
	 * might be the canonical url
	 * 
	 * @param url
	 *            the url to set
	 */
	void setUrl(String url);

	/**
	 * host the linked resource is located at. Used to give a quick view at the
	 * service providing the resource.
	 * 
	 * @return the host
	 */
	String getHost();

	/**
	 * Host the linked resource is located at. Used to give a quick view at the
	 * service providing the resource.
	 * 
	 * @param host
	 *            the host to set
	 */
	void setHost(String host);

	/**
	 * Title of the linked resource
	 * 
	 * @return the title
	 */
	String getTitle();

	/**
	 * Title of the linked resource
	 * 
	 * @param title
	 *            the title to set
	 */
	void setTitle(String title);

	/**
	 * Description of the linked resource
	 * 
	 * @return the description
	 */
	String getDescription();

	/**
	 * Description of the linked resource
	 * 
	 * @param description
	 *            the description to set
	 */
	void setDescription(String description);

	/**
	 * Url to the image to use as thumbnail
	 * 
	 * @return the image
	 */
	String getImage();

	/**
	 * Url to the image to use as thumbnail
	 * 
	 * @param image
	 *            the image to set
	 */
	void setImage(String image);

	/**
	 * time of creation
	 * 
	 * @return the createdAt
	 */
	Date getCreatedAt();

	/**
	 * time of creation
	 * 
	 * @param createdAt
	 *            the createdAt to set
	 */
	void setCreatedAt(Date createdAt);

	/**
	 * preview type of this preview
	 * 
	 * @param type
	 */
	void setType(LinkPreviewType type);

	/**
	 * preview type of this preview
	 * 
	 * @return
	 */
	LinkPreviewType getType();

}