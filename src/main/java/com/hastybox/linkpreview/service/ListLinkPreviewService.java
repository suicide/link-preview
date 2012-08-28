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
package com.hastybox.linkpreview.service;

import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hastybox.linkpreview.analyzer.LinkAnalyzer;
import com.hastybox.linkpreview.analyzer.ShortUrlResolver;
import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.common.LinkPreviewException;
import com.hastybox.linkpreview.model.LinkPreview;

/**
 * Analyzes given URLs and creates {@link LinkPreview}s based on a {@link List}
 * of {@link LinkAnalyzer}. Each of the {@link LinkAnalyzer} is probed to
 * process the given URL until one is found.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class ListLinkPreviewService implements LinkPreviewService {

	/**
	 * Logger
	 */
	private static final Logger LOGGER;

	static {
		LOGGER = LoggerFactory.getLogger(ListLinkPreviewService.class);
	}

	/**
	 * List of analyzers to use.
	 */
	private List<LinkAnalyzer> analyzers;

	/**
	 * the URL pattern all URLs to be previewed must comply to.
	 */
	private Pattern urlPattern;

	/**
	 * resolves ShortUrls
	 */
	private ShortUrlResolver shortUrlResolver;

	/**
	 * The {@link List} of {@link LinkAnalyzer}s to use for processing. The
	 * analyzers are probed from first to last until one is found that can
	 * process the given URL.
	 * 
	 * @param analyzers
	 *            the analyzers to set
	 */
	public void setAnalyzers(List<LinkAnalyzer> analyzers) {
		this.analyzers = analyzers;
	}

	/**
	 * tries to resolve given short URLs to the actual or final URLs in order to
	 * use a more proper {@link LinkAnalyzer} to process the content.
	 * 
	 * @param shortUrlResolver
	 *            the shortUrlResolver to set
	 */
	public void setShortUrlResolver(ShortUrlResolver shortUrlResolver) {
		this.shortUrlResolver = shortUrlResolver;
	}

	/**
	 * constructor
	 */
	public ListLinkPreviewService() {
		// URL pattern. Only http and https protocol
		// TODO better pattern
		urlPattern = Pattern
				.compile("^(?:https?://)?[a-zA-Z0-9][-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.service.LinkPreviewService#loadPreview(java.
	 * lang.String)
	 */
	public LinkPreview loadPreview(String url) throws LinkPreviewException {
		LOGGER.debug("Analyzing URL: {}", url);

		if (!validateUrl(url)) {
			throw new IllegalArgumentException(String.format(
					"%s is not a processable URL", url));
		}

		LinkPreview preview = null;
		String contentUrl;
		
		try {
			contentUrl = shortUrlResolver.trace(url);
		} catch (LinkAnalyzerException e) {
			throw new LinkPreviewException(String.format("Could not resolve ShortUrl from %s", url), e);
		}

		// TODO pre-handle Short URLs
		for (LinkAnalyzer analyzer : analyzers) {
			// check if analyzrer can provess the URL
			if (analyzer.canProcess(contentUrl)) {
				LOGGER.debug("Anazyzing URL {} using Analyzer {}", contentUrl,
						analyzer);

				try {
					preview = analyzer.process(contentUrl);
				} catch (LinkAnalyzerException e) {
					throw new LinkPreviewException(String.format(
							"%s could not be processed", contentUrl), e);
				}
			}
		}

		return preview;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.service.LinkPreviewService#loadPreview(java.
	 * net.URL)
	 */
	public LinkPreview loadPreview(URL url) throws LinkPreviewException {

		return loadPreview(url.toExternalForm());
	}

	/**
	 * checks for processable URL
	 * 
	 * @param url
	 * @return
	 */
	protected boolean validateUrl(String url) {
		Matcher matcher = urlPattern.matcher(url);

		return matcher.matches();
	}

}
