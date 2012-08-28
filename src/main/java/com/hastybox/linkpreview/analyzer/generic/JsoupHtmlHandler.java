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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.model.LinkPreview;
import com.hastybox.linkpreview.model.factory.LinkPreviewFactory;
import com.hastybox.linkpreview.model.factory.SimpleLinkPreviewFactory;

/**
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class JsoupHtmlHandler implements HtmlHandler {

	public static final String LINK_TAG = "link";

	public static final String IMG_TAG = "img";

	public static final String META_TAG = "meta";

	public static final String BODY_TAG = "body";

	public static final String P_TAG = "p";

	public static final String TITLE_TAG = "title";

	public static final String REL_ATTR = "rel";

	public static final String HREF_ATTR = "href";

	public static final String SRC_ATTR = "src";

	public static final String NAME_ATTR = "name";

	public static final String CONTENT_ATTR = "content";

	public static final String DESCRIPTION_VAL = "description";

	public static final String IMAGE_SRC_VAL = "image_src";

	public static final String CANONICAL_VAL = "canonical";

	/**
	 * Factory to create {@link LinkPreview}s
	 */
	protected LinkPreviewFactory linkPreviewFactory;

	/**
	 * Jsoup wrapper
	 */
	protected JsoupDocumentFactory jsoupDocumentFactory;

	/**
	 * Pattern to match for description text. Basically every type of text is
	 * ok.
	 */
	private Pattern descriptionPattern;

	/**
	 * Relative Url pattern, relative to current document
	 */
	private Pattern relativeUrlPattern;

	/**
	 * Relative Url pattern, relative to root
	 */
	private Pattern relativeUrlRootPattern;

	/**
	 * @param linkPreviewFactory
	 *            the linkPreviewFactory to set
	 */
	public void setLinkPreviewFactory(LinkPreviewFactory linkPreviewFactory) {
		this.linkPreviewFactory = linkPreviewFactory;
	}

	/**
	 * @param jsoupDocumentFactory
	 *            the jsoupDocumentFactory to set
	 */
	public void setJsoupDocumentFactory(
			JsoupDocumentFactory jsoupDocumentFactory) {
		this.jsoupDocumentFactory = jsoupDocumentFactory;
	}

	/**
	 * @param descriptionPattern
	 *            the descriptionPattern to set
	 */
	public void setDescriptionPattern(String descriptionPattern) {
		this.descriptionPattern = Pattern.compile(descriptionPattern);
	}

	/**
	 * default constructor
	 */
	public JsoupHtmlHandler() {
		linkPreviewFactory = new SimpleLinkPreviewFactory();
		jsoupDocumentFactory = new JsoupDocumentFactory();
		descriptionPattern = Pattern.compile(".+");

		relativeUrlPattern = Pattern
				.compile("^((?:https?://)?.+/)(?:[^/]+)?(?:[\\?#].*)??");
		relativeUrlRootPattern = Pattern
				.compile("^((?:https?://)?[^/]+)(?:/.*)?");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.analyzer.generic.HtmlHandler#process(java.io
	 * .InputStream, java.lang.String, java.lang.String)
	 */
	public LinkPreview process(InputStream htmlDataStream, String encoding,
			String url) throws LinkAnalyzerException {
		Document document;

		try {
			document = jsoupDocumentFactory.retrieveDocument(htmlDataStream,
					encoding, url);
		} catch (IOException e) {
			throw new LinkAnalyzerException(String.format(
					"Unable to parse HTML from %s with encoding %s", url,
					encoding), e);
		}

		LinkPreview preview = linkPreviewFactory.create();

		// set data to preview
		String title = retrieveTitle(document);
		String description = retrieveDescription(document);
		String linkUrl = retrieveUrl(document);

		String imageUrl = retrieveImageUrl(document);
		imageUrl = handleRelativeUrl(imageUrl, url);

		preview.setTitle(title);
		preview.setDescription(description);
		preview.setImage(imageUrl);

		if (linkUrl != null) {
			preview.setUrl(linkUrl);
		} else {
			// use original url
			preview.setUrl(url);
		}

		return preview;
	}

	/**
	 * @param document
	 * @return
	 */
	protected String retrieveImageUrl(Document document) {
		String imageUrl = null;

		Elements linkTags = document.getElementsByTag(LINK_TAG);

		for (Element linkTag : linkTags) {
			if (linkTag.hasAttr(REL_ATTR)
					&& IMAGE_SRC_VAL.equals(linkTag.attr(REL_ATTR))) {
				imageUrl = linkTag.attr(HREF_ATTR);
				break;
			}
		}
		
		// TODO add google way
		
		// meta property=og:image content=pic
		// meta name=thumbnail content=pic

		if (imageUrl == null) {
			// TODO find best pictures from body? - images with the most text next to it. also size
			Elements imageTags = document.getElementsByTag(IMG_TAG);

			for (Element imageTag : imageTags) {
				// just grab the first picture
				imageUrl = imageTag.attr(SRC_ATTR);
				break;
			}
		}

		return imageUrl;
	}

	protected String handleRelativeUrl(String relativeUrl, String baseUrl)
			throws LinkAnalyzerException {
		String absoluteUrl = null;

		// check for relatve Url
		if (relativeUrl != null) {
			if (!relativeUrl.startsWith("http")) {
				// relative Url
				Matcher matcher;
				if (relativeUrl.startsWith("/")) {
					// relative Url from root
					matcher = relativeUrlRootPattern.matcher(baseUrl);
				} else {
					matcher = relativeUrlPattern.matcher(baseUrl);
				}

				if (matcher.find()) {
					absoluteUrl = matcher.group(1) + relativeUrl;
				} else {
					throw new LinkAnalyzerException(String.format(
							"Could not create absolute Url from baseUrl %s",
							baseUrl));
				}

			} else {
				absoluteUrl = relativeUrl;
			}
		}

		return absoluteUrl;
	}

	/**
	 * @param document
	 * @return
	 */
	protected String retrieveUrl(Document document) {
		String canonicalUrl = null;

		Elements linkTags = document.getElementsByTag(LINK_TAG);
		for (Element linkTag : linkTags) {
			if (linkTag.hasAttr(REL_ATTR)
					&& CANONICAL_VAL.equals(linkTag.attr(REL_ATTR))) {
				canonicalUrl = linkTag.attr(HREF_ATTR);
				break;
			}
		}

		return canonicalUrl;
	}

	/**
	 * @param document
	 * @return
	 */
	protected String retrieveDescription(Document document) {
		String description = null;

		Elements metaTags = document.getElementsByTag(META_TAG);

		for (Element metaTag : metaTags) {
			if (metaTag.hasAttr(NAME_ATTR)
					&& DESCRIPTION_VAL.equals(metaTag.attr(NAME_ATTR))) {
				description = metaTag.attr(CONTENT_ATTR);
				break;
			}
		}

		// try to search from body
		if (description == null) {
			// match first paragraph with text
			Elements paragraphs = document.getElementsByTag(P_TAG);
			for (Element paragraph : paragraphs) {
				if (paragraph.hasText()) {
					description = paragraph.text();
					break;
				}
			}

			// match everything in the body having text. This might be very
			// expensive...
			Elements textElements = document.getElementsByTag(BODY_TAG).get(0)
					.getElementsMatchingOwnText(descriptionPattern);
			if (!textElements.isEmpty()) {
				description = textElements.get(0).ownText();
			}
		}

		return description;
	}

	protected String retrieveTitle(Document document) {
		String title = null;

		// get Head tag first? for performance?

		// Get title from title tag
		Elements titleTags = document.getElementsByTag(TITLE_TAG);
		if (!titleTags.isEmpty()) {
			// get text from first title tag found
			title = titleTags.get(0).text();
		}

		return title;
	}

}
