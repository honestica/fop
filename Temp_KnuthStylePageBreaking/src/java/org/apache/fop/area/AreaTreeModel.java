/*
 * Copyright 1999-2004 The Apache Software Foundation.
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

/* $Id$ */
 
package org.apache.fop.area;

// Java
import java.util.List;

// XML
import org.xml.sax.SAXException;

// Apache
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the model for the area tree object.
 * The model implementation can handle the page sequence,
 * page and off-document items.
 * The methods to access the page viewports can only
 * assume the PageViewport is valid as it remains for
 * the life of the area tree model.
 */
public class AreaTreeModel {
    private List pageSequenceList = null;
    private List currentPageSequencePageList;
    private List offDocumentItems = new java.util.ArrayList();

    protected static Log log = LogFactory.getLog(AreaTreeModel.class);

    /**
     * Create a new store pages model
     */
    public AreaTreeModel() {
        pageSequenceList = new java.util.ArrayList();
    }

    /**
     * Start a page sequence on this model.
     * @param title the title of the new page sequence
     */
    public void startPageSequence(LineArea title) {
        currentPageSequencePageList = new java.util.ArrayList();
        pageSequenceList.add(currentPageSequencePageList);
    }

    /**
     * Add a page to this moel.
     * @param page the page to add to the model.
     */
    public void addPage(PageViewport page) {
        currentPageSequencePageList.add(page);
    }

    /**
     * Handle an OffDocumentItem 
     * @param ext the extension to handle
     */
    public void handleOffDocumentItem(OffDocumentItem ext) {};

    /**
     * Signal the end of the document for any processing.
     */
    public void endDocument() throws SAXException {};

    /**
     * Get the page sequence count.
     * @return the number of page sequences in the document.
     */
    public int getPageSequenceCount() {
        return pageSequenceList.size();
    }

    /**
     * Get the page count.
     * @param seq the page sequence to count.
     * @return returns the number of pages in a page sequence
     */
    public int getPageCount(int seq) {
        List sequence = (List) pageSequenceList.get(seq - 1);
        return sequence.size();
    }

    /**
     * Get the page for a position in the document.
     * @param seq the page sequence number
     * @param count the page count in the sequence
     * @return the PageViewport for the particular page
     */
    public PageViewport getPage(int seq, int count) {
        List sequence = (List) pageSequenceList.get(seq - 1);
        return (PageViewport) sequence.get(count);
    }
}
