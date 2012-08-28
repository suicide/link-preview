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
package com.hastybox.linkpreview.analyzer.generic;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hastybox.linkpreview.analyzer.LinkAnalyzer;
import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.model.LinkPreview;
import com.hastybox.linkpreview.model.linktype.CommonLinkPreviewType;

/**
 * This class is thread safe.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class GenericLinkAnalyzer implements LinkAnalyzer {
	
	/**
	 * Logger
	 */
	private static final Logger LOGGER;
	
	static {
		LOGGER = LoggerFactory.getLogger(GenericLinkAnalyzer.class);
	}

	/**
	 * generic URL pattern to match all kinds of web urls
	 */
	private Pattern urlPattern;
	
	/**
	 * {@link HtmlHandler} to process the loaded HTML source code
	 */
	private HtmlHandler htmlHandler;
	
	/**
	 * {@link HttpClient} to use to grab HTML from websites
	 */
	private HttpClient httpClient;
	
	/**
	 * @param urlPattern
	 *            the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = Pattern.compile(urlPattern);
	}
	
	/**
	 * @param htmlHandler the htmlHandler to set
	 */
	public void setHtmlHandler(HtmlHandler htmlHandler) {
		this.htmlHandler = htmlHandler;
	}
	
	/**
	 * @param httpClient the httpClient to set
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * default constructor with default configuration
	 */
	public GenericLinkAnalyzer() {
		// TODO better pattern
		urlPattern = Pattern
				.compile("^(?:https?://)?[a-zA-Z0-9][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		
		httpClient = new DefaultHttpClient();
		
		htmlHandler = new JsoupHtmlHandler();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.analyzer.LinkAnalyzer#canProcess(java.lang.String
	 * )
	 */
	public boolean canProcess(String url) {
		LOGGER.debug("Trying to match url {}", url);
		
		Matcher matcher = urlPattern.matcher(url);
		return matcher.find();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.analyzer.LinkAnalyzer#process(java.lang.String)
	 */
	public LinkPreview process(String url) throws LinkAnalyzerException {

		LOGGER.debug("Processing URL {}", url);
		
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {

			response = httpClient.execute(get);
			
		} catch (ClientProtocolException e) {
			throw new LinkAnalyzerException("A HTTP protocol error occured", e);
		} catch (IOException e) {
			throw new LinkAnalyzerException("A connection problem occured", e);
		}
		// TODO handle Status codes
		// TODO handle content types, only html and images are allowed
		Header encodingHeader = response.getEntity().getContentEncoding();
		
		String encoding = null;
		if (encodingHeader != null) {
			encoding = encodingHeader.getValue();
		}
		
		InputStream content;
		
		try {
			content = response.getEntity().getContent();
		} catch (IllegalStateException e) {
			// TODO
			throw new LinkAnalyzerException(e);
		} catch (IOException e) {
			// TODO
			throw new LinkAnalyzerException(e);
		}
		
		// TODO Proper baseUrl
		LinkPreview preview = htmlHandler.process(content, encoding, url);
		
		preview.setType(CommonLinkPreviewType.GENERIC);
		// TODO set host
		
		return preview;
	}
	
	public static void main(String[] args) throws Exception {
		new GenericLinkAnalyzer().process("http://google.com");
	}

}
