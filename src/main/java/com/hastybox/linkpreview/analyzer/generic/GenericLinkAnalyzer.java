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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hastybox.linkpreview.analyzer.LinkAnalyzer;
import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.model.LinkPreview;

/**
 * This class if thread safe.
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
	 * @param urlPattern
	 *            the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = Pattern.compile(urlPattern);
	}

	/**
	 * 
	 */
	public GenericLinkAnalyzer() {
		// TODO better pattern
		urlPattern = Pattern
				.compile("^(?:https?://)?[a-zA-Z0-9][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
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

		// TODO thread safe, configurable
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {

			response = client.execute(get);
			
		} catch (ClientProtocolException e) {
			// TODO
			throw new LinkAnalyzerException(e);
		} catch (IOException e) {
			// TODO
			throw new LinkAnalyzerException(e);
		}
		// TODO handle Status codes
		// TODO handle content types
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
		
		Document document;
		
		try {
			document = Jsoup.parse(content, encoding, url);
		} catch (IOException e) {
			throw new LinkAnalyzerException(e);
		}
		
		Elements title = document.getElementsByTag("title");
		

		return null;
	}
	
	public static void main(String[] args) throws Exception {
		new GenericLinkAnalyzer().process("http://google.com");
	}

}
