/*>$File$ -- $Id$ -- 

 ============================================================================
                   The Apache Software License, Version 1.1
 ============================================================================
 
    Copyright (C) 1999 The Apache Software Foundation. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without modifica-
 tion, are permitted provided that the following conditions are met:
 
 1. Redistributions of  source code must  retain the above copyright  notice,
    this list of conditions and the following disclaimer.
 
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.
 
 3. The end-user documentation included with the redistribution, if any, must
    include  the following  acknowledgment:  "This product includes  software
    developed  by the  Apache Software Foundation  (http://www.apache.org/)."
    Alternately, this  acknowledgment may  appear in the software itself,  if
    and wherever such third-party acknowledgments normally appear.
 
 4. The names "Fop" and  "Apache Software Foundation"  must not be used to
    endorse  or promote  products derived  from this  software without  prior
    written permission. For written permission, please contact
    apache@apache.org.
 
 5. Products  derived from this software may not  be called "Apache", nor may
    "Apache" appear  in their name,  without prior written permission  of the
    Apache Software Foundation.
 
 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS  FOR A PARTICULAR  PURPOSE ARE  DISCLAIMED.  IN NO  EVENT SHALL  THE
 APACHE SOFTWARE  FOUNDATION  OR ITS CONTRIBUTORS  BE LIABLE FOR  ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL,  EXEMPLARY, OR CONSEQUENTIAL  DAMAGES (INCLU-
 DING, BUT NOT LIMITED TO, PROCUREMENT  OF SUBSTITUTE GOODS OR SERVICES; LOSS
 OF USE, DATA, OR  PROFITS; OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON
 ANY  THEORY OF LIABILITY,  WHETHER  IN CONTRACT,  STRICT LIABILITY,  OR TORT
 (INCLUDING  NEGLIGENCE OR  OTHERWISE) ARISING IN  ANY WAY OUT OF THE  USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
 This software  consists of voluntary contributions made  by many individuals
 on  behalf of the Apache Software  Foundation and was  originally created by
 James Tauber <jtauber@jtauber.com>. For more  information on the Apache 
 Software Foundation, please see <http://www.apache.org/>.
 
 */
package org.apache.xml.fop.fo.pagination;

// FOP
import org.apache.xml.fop.fo.*;
import org.apache.xml.fop.fo.properties.*;
import org.apache.xml.fop.fo.flow.Flow;
import org.apache.xml.fop.fo.flow.StaticContent;
import org.apache.xml.fop.layout.Area;
import org.apache.xml.fop.layout.AreaContainer;
import org.apache.xml.fop.layout.AreaTree;
import org.apache.xml.fop.layout.Page;
import org.apache.xml.fop.layout.PageMaster;
import org.apache.xml.fop.layout.PageMasterFactory;
import org.apache.xml.fop.apps.FOPException;				   

// Java
import java.util.Hashtable;
import java.util.Vector;

public class PageSequence extends FObj { 

    public static class Maker extends FObj.Maker {
	public FObj make(FObj parent, PropertyList propertyList)
	    throws FOPException {
	    return new PageSequence(parent, propertyList);
	}
    }

    public static FObj.Maker maker() {
	return new PageSequence.Maker();
    }

    protected Root root;
    protected SequenceSpecification sequenceSpecification;
    protected Flow flow;
    protected StaticContent staticBefore;
    protected StaticContent staticAfter;
    protected LayoutMasterSet layoutMasterSet;

    protected Page currentPage;
    protected int currentPageNumber = 0;

    protected PageSequence(FObj parent, PropertyList propertyList)
	throws FOPException {
	super(parent, propertyList);
	this.name = "fo:page-sequence";

	if (parent.getName().equals("fo:root")) {
	    this.root = (Root) parent;
	    this.root.addPageSequence(this);
	} else {
	    throw
		new FOPException("page-sequence must be child of root, not "
				 + parent.getName());
	}
	layoutMasterSet = root.getLayoutMasterSet();
    }

    protected Page makePage(AreaTree areaTree) throws FOPException {
	PageMaster pageMaster;
	// layout this page sequence
		
	// while there is still stuff in the flow, ask the
	// sequence-specification for a new page 
	    
	if (this.sequenceSpecification == null) {
	    throw new FOPException("page-sequence is missing an"
				   + " sequence-specification");
	}
		
	PageMasterFactory pmf =
	    this.sequenceSpecification.getFirstPageMasterFactory();

	pageMaster = pmf.getNextPageMaster();

	while (pageMaster == null) {
	    /* move on to next sequence specifier */
	    pmf = pmf.getNext();
	    if (pmf == null) {
		throw new FOPException("out of sequence specifiers"
				       + " (FOP will eventually allow this)");
	    }
	    pageMaster = pmf.getNextPageMaster();
	}
	return pageMaster.makePage(areaTree);
    }

    public void format(AreaTree areaTree) throws FOPException {
	int status = OK;

	do {
	    currentPage = makePage(areaTree);
	    currentPage.setNumber(++this.currentPageNumber);
	    System.err.print(" [" + currentPageNumber);
	    if ((this.staticBefore != null) &&
		(currentPage.getBefore() != null)) {
		AreaContainer beforeArea = currentPage.getBefore();
		this.staticBefore.layout(beforeArea);
	    }
	    if ((this.staticAfter != null) &&
		(currentPage.getAfter() != null)) {
		AreaContainer afterArea = currentPage.getAfter();
		this.staticAfter.layout(afterArea);
	    }
	    if ((status == FORCE_PAGE_BREAK_EVEN) &&
		((currentPageNumber % 2) == 1)) {
	    } else if ((status == FORCE_PAGE_BREAK_ODD) &&
		       ((currentPageNumber % 2) == 0)) {
	    } else {
		AreaContainer bodyArea = currentPage.getBody();
		status = this.flow.layout(bodyArea);
	    }
	    System.err.print("]");
	    areaTree.addPage(currentPage);
	} while (status != OK);
	System.err.println();
    }

    public void setFlow(Flow flow) {
	this.flow = flow;
    }
    
    protected void setSequenceSpecification(SequenceSpecification sequenceSpecification) {
	this.sequenceSpecification = sequenceSpecification;
	sequenceSpecification.setLayoutMasterSet(this.layoutMasterSet);
    }
    
    public void setStaticContent(String name, StaticContent staticContent) {
	if (name.equals("xsl-before")) {
	    this.staticBefore = staticContent;
	} else if (name.equals("xsl-after")) {
	    this.staticAfter = staticContent;
	} else {
	    System.err.println("WARNING: this version of FOP only supports "
			       + "static-content in region-before and region-after"); 
	}
    }
}
