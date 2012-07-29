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
 * A Link Preview, containing all information for a preview.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class SimpleLinkPreview implements LinkPreview {

	/**
	 * generated Id
	 */
	private Long id;

	/**
	 * url to link to
	 */
	private String url;

	/**
	 * target host to show
	 */
	private String host;

	/**
	 * title of the target
	 */
	private String title;

	/**
	 * description
	 */
	private String description;

	/**
	 * link to image on page
	 */
	private String image;

	/**
	 * the type of this preview
	 */
	private LinkPreviewType type;

	/**
	 * creation date of preview
	 */
	private Date createdAt;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getId()
	 */
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getUrl()
	 */
	public String getUrl() {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getHost()
	 */
	public String getHost() {
		return host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#setHost(java.lang.String)
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.model.LinkPreview#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.model.LinkPreview#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getImage()
	 */
	public String getImage() {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.model.LinkPreview#setImage(java.lang.String)
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getType()
	 */
	public LinkPreviewType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.model.LinkPreview#setType(com.hastybox.linkpreview
	 * .model.LinkPreviewType)
	 */
	public void setType(LinkPreviewType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hastybox.linkpreview.model.LinkPreview#getCreatedAt()
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.model.LinkPreview#setCreatedAt(java.util.Date)
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
