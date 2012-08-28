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
package com.hastybox.linkpreview.model.factory;

import java.util.Date;

import com.hastybox.linkpreview.model.LinkPreview;
import com.hastybox.linkpreview.model.SimpleLinkPreview;

/**
 * Simple implementation of {@link LinkPreviewFactory} that returns instances of {@link LinkPreview}
 * 
 * @author Patrick Sy (psy@get-it.us)
 *
 */
public class SimpleLinkPreviewFactory implements LinkPreviewFactory {

	/* (non-Javadoc)
	 * @see com.hastybox.linkpreview.model.factory.LinkPreviewFactory#create()
	 */
	public LinkPreview create() {
		// create a new instance.
		SimpleLinkPreview preview = new SimpleLinkPreview();
		preview.setCreatedAt(new Date());
		
		return preview;
	}

}
