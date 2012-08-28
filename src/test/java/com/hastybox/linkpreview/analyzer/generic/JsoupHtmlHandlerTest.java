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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.model.LinkPreview;
import com.hastybox.linkpreview.model.SimpleLinkPreview;
import com.hastybox.linkpreview.model.factory.LinkPreviewFactory;

/**
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
@SuppressWarnings("serial")
public class JsoupHtmlHandlerTest {

	/**
	 * test object
	 */
	private JsoupHtmlHandler handler;

	/**
	 * preview object
	 */
	private LinkPreview preview;

	/**
	 * factory mock
	 */
	private LinkPreviewFactory linkPreviewFactory;

	/**
	 * jsoup factory mock
	 */
	private JsoupDocumentFactory documentFactory;

	/**
	 * document mock
	 */
	private Document document;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		handler = new JsoupHtmlHandler();

		preview = new SimpleLinkPreview();
		linkPreviewFactory = mock(LinkPreviewFactory.class);

		when(linkPreviewFactory.create()).thenReturn(preview);

		documentFactory = mock(JsoupDocumentFactory.class);

		document = mock(Document.class);

		handler.setLinkPreviewFactory(linkPreviewFactory);
		handler.setJsoupDocumentFactory(documentFactory);
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#process(java.io.InputStream, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testProcess() throws Exception {
		JsoupHtmlHandler handler = new JsoupHtmlHandlerStub();
		handler.setLinkPreviewFactory(linkPreviewFactory);
		handler.setJsoupDocumentFactory(documentFactory);
		
		InputStream is = new ByteArrayInputStream(new byte[0]);
		String encoding = "UTF-8";
		String url = "http://test.com/";
		
		when(documentFactory.retrieveDocument(is, encoding, url)).thenReturn(document);
		
		LinkPreview result = handler.process(is, encoding, url);
		
		assertThat(result, is(not(nullValue())));
		
		assertThat(result.getDescription(), is(equalTo(JsoupHtmlHandlerStub.DESCRIPTION)));
		assertThat(result.getTitle(), is(equalTo(JsoupHtmlHandlerStub.TITLE)));
		assertThat(result.getUrl(), is(equalTo(JsoupHtmlHandlerStub.URL)));
		assertThat(result.getImage(), is(equalTo(JsoupHtmlHandlerStub.IMAGE_URL)));
		
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveImageUrl(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveImageUrl() {
		String imageUrl = "http://some.url/image.jpg";
		Tag linkTag = Tag.valueOf(JsoupHtmlHandler.LINK_TAG);
		final Element linkTagElement = new Element(linkTag, "");
		linkTagElement.attr(JsoupHtmlHandler.REL_ATTR,
				JsoupHtmlHandler.IMAGE_SRC_VAL);
		linkTagElement.attr(JsoupHtmlHandler.HREF_ATTR, imageUrl);

		Elements linkTags = new Elements(new ArrayList<Element>() {
			{
				add(linkTagElement);
			}
		});

		when(document.getElementsByTag(JsoupHtmlHandler.LINK_TAG)).thenReturn(
				linkTags);

		String result = handler.retrieveImageUrl(document);

		assertThat(result, is(not(nullValue())));
		assertThat(result, is(equalTo(imageUrl)));
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveImageUrl(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveImageUrlBodyImage() {
		String imageUrl = "http://some.url/image.jpg";

		Tag imageTag = Tag.valueOf(JsoupHtmlHandler.IMG_TAG);
		final Element imageTagElement = new Element(imageTag, "");
		imageTagElement.attr(JsoupHtmlHandler.SRC_ATTR, imageUrl);

		Elements imageTags = new Elements(new ArrayList<Element>() {
			{
				add(imageTagElement);
			}
		});

		when(document.getElementsByTag(JsoupHtmlHandler.LINK_TAG)).thenReturn(
				new Elements());
		when(document.getElementsByTag(JsoupHtmlHandler.IMG_TAG)).thenReturn(
				imageTags);

		String result = handler.retrieveImageUrl(document);

		assertThat(result, is(not(nullValue())));
		assertThat(result, is(equalTo(imageUrl)));
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#handleRelativeUrl(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testHandleRelativeUrl() throws Exception {
		String baseUrl = "http://www.hastybox.com/something.somethingelse/where/isthis.html?some=thing&and=more#bla";
		String relativeUrl;
		String result;

		// from root
		relativeUrl = "/images/image.png";

		result = handler.handleRelativeUrl(relativeUrl, baseUrl);

		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo("http://www.hastybox.com/images/image.png"));

		// from current document
		relativeUrl = "images/image.png";

		result = handler.handleRelativeUrl(relativeUrl, baseUrl);

		assertThat(result, is(not(nullValue())));
		assertThat(
				result,
				equalTo("http://www.hastybox.com/something.somethingelse/where/images/image.png"));

	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#handleRelativeUrl(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testHandleRelativeUrlWithoutHttp() throws Exception {
		String baseUrl = "www.hastybox.com/something.somethingelse/where/isthis.html?some=thing&and=more#bla";
		String relativeUrl;
		String result;

		// from root
		relativeUrl = "/images/image.png";

		result = handler.handleRelativeUrl(relativeUrl, baseUrl);

		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo("www.hastybox.com/images/image.png"));

		// from current document
		relativeUrl = "images/image.png";

		result = handler.handleRelativeUrl(relativeUrl, baseUrl);

		assertThat(result, is(not(nullValue())));
		assertThat(
				result,
				equalTo("www.hastybox.com/something.somethingelse/where/images/image.png"));

	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#handleRelativeUrl(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testHandleRelativeUrlNullValue() throws Exception {
		String baseUrl = "www.hastybox.com/something.somethingelse/where/isthis.html?some=thing&and=more#bla";
		String relativeUrl = null;

		String result = handler.handleRelativeUrl(relativeUrl, baseUrl);

		assertThat(result, is(nullValue()));

	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveUrl(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveUrl() {
		String canonicalUrl = "http://some.url/somewhere.html";

		Tag linkTag = Tag.valueOf(JsoupHtmlHandler.LINK_TAG);
		final Element linkTagElement = new Element(linkTag, "");
		linkTagElement.attr(JsoupHtmlHandler.REL_ATTR,
				JsoupHtmlHandler.CANONICAL_VAL);
		linkTagElement.attr(JsoupHtmlHandler.HREF_ATTR, canonicalUrl);

		Elements linkTags = new Elements(new ArrayList<Element>() {
			{
				add(linkTagElement);
			}
		});

		when(document.getElementsByTag(JsoupHtmlHandler.LINK_TAG)).thenReturn(
				linkTags);

		String result = handler.retrieveUrl(document);

		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo(canonicalUrl));
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveUrl(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveUrlNonCanonical() {

		Elements linkTags = new Elements();

		when(document.getElementsByTag(JsoupHtmlHandler.LINK_TAG)).thenReturn(
				linkTags);

		String result = handler.retrieveUrl(document);

		assertThat(result, is(nullValue()));
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveDescription(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveDescription() {
		String description = "SomeDescription";

		Tag metaTag = Tag.valueOf(JsoupHtmlHandler.META_TAG);
		final Element metaTagElement = new Element(metaTag, "");
		metaTagElement.attr(JsoupHtmlHandler.NAME_ATTR, JsoupHtmlHandler.DESCRIPTION_VAL);
		metaTagElement.attr(JsoupHtmlHandler.CONTENT_ATTR, description);
		
		Elements metaTags = new Elements(new ArrayList<Element>() {
			{
				add(metaTagElement);
			}
		});
		
		when(document.getElementsByTag(JsoupHtmlHandler.META_TAG)).thenReturn(
				metaTags);

		String result = handler.retrieveDescription(document);

		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo(description));
	}
	
	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveDescription(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveDescriptionBody() {
		String description = "SomeDescription";
		
		Tag bodyTag = Tag.valueOf(JsoupHtmlHandler.BODY_TAG);
		final Element bodyTagElement = new Element(bodyTag, "");
		
		Tag pTag = Tag.valueOf("p");
		final Element pTagElement = new Element(pTag, "");
		pTagElement.text(description);
		
		Elements bodyTags = new Elements(new ArrayList<Element>() {
			{
				add(bodyTagElement);
			}
		});
		
		bodyTagElement.appendChild(pTagElement);
		
		when(document.getElementsByTag(JsoupHtmlHandler.META_TAG)).thenReturn(
				new Elements());
		when(document.getElementsByTag(JsoupHtmlHandler.BODY_TAG)).thenReturn(
				bodyTags);
		
		String result = handler.retrieveDescription(document);
		
		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo(description));
	}

	/**
	 * Test method for
	 * {@link com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveTitle(org.jsoup.nodes.Document)}
	 * .
	 */
	@Test
	public void testRetrieveTitle() {
		String title = "SomeTitle";

		Tag titleTag = Tag.valueOf(JsoupHtmlHandler.TITLE_TAG);
		final Element titleTagElement = new Element(titleTag, "");
		titleTagElement.text(title);

		Elements titleTags = new Elements(new ArrayList<Element>() {
			{
				add(titleTagElement);
			}
		});

		when(document.getElementsByTag(JsoupHtmlHandler.TITLE_TAG)).thenReturn(
				titleTags);

		String result = handler.retrieveTitle(document);

		assertThat(result, is(not(nullValue())));
		assertThat(result, equalTo(title));
	}
	
	private class JsoupHtmlHandlerStub extends JsoupHtmlHandler {
		
		public static final String DESCRIPTION = "description";
		
		public static final String IMAGE_URL = "http://image.com/image.jpg";
		
		public static final String TITLE = "some title";
		
		public static final String URL = "http://test.com/";
		
		/* (non-Javadoc)
		 * @see com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#handleRelativeUrl(java.lang.String, java.lang.String)
		 */
		@Override
		protected String handleRelativeUrl(String relativeUrl, String baseUrl)
				throws LinkAnalyzerException {
			return relativeUrl;
		}
		
		/* (non-Javadoc)
		 * @see com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveDescription(org.jsoup.nodes.Document)
		 */
		@Override
		protected String retrieveDescription(Document document) {
			return DESCRIPTION;
		}
		
		/* (non-Javadoc)
		 * @see com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveImageUrl(org.jsoup.nodes.Document)
		 */
		@Override
		protected String retrieveImageUrl(Document document) {
			return IMAGE_URL;
		}
		
		/* (non-Javadoc)
		 * @see com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveTitle(org.jsoup.nodes.Document)
		 */
		@Override
		protected String retrieveTitle(Document document) {
			return TITLE;
		}
		
		/* (non-Javadoc)
		 * @see com.hastybox.linkpreview.analyzer.generic.JsoupHtmlHandler#retrieveUrl(org.jsoup.nodes.Document)
		 */
		@Override
		protected String retrieveUrl(Document document) {
			return URL;
		}
		
	}

}
