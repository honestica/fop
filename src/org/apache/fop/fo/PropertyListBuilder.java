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
package org.apache.xml.fop.fo;

import org.apache.xml.fop.fo.properties.*;
import org.apache.xml.fop.svg.*;

import org.apache.xml.fop.apps.FOPException;

import org.xml.sax.AttributeList;

import java.util.Hashtable;

public class PropertyListBuilder {
  private Hashtable propertyTable;

  public PropertyListBuilder() {
    this.propertyTable = new Hashtable();

    propertyTable.put("end-indent",EndIndent.maker());
    propertyTable.put("page-master-name",PageMasterName.maker());
    propertyTable.put("page-master-first",PageMasterFirst.maker());
    propertyTable.put("page-master-repeating",PageMasterRepeating.maker());
    propertyTable.put("page-master-odd",PageMasterOdd.maker());
    propertyTable.put("page-master-even",PageMasterEven.maker());
    propertyTable.put("margin-top",MarginTop.maker());
    propertyTable.put("margin-bottom",MarginBottom.maker());
    propertyTable.put("margin-left",MarginLeft.maker());
    propertyTable.put("margin-right",MarginRight.maker());
    propertyTable.put("extent",Extent.maker());
    propertyTable.put("page-width",PageWidth.maker());
    propertyTable.put("page-height",PageHeight.maker());
    propertyTable.put("flow-name",FlowName.maker());
    propertyTable.put("font-family",FontFamily.maker());
    propertyTable.put("font-style",FontStyle.maker());
    propertyTable.put("font-weight",FontWeight.maker());
    propertyTable.put("font-size",FontSize.maker());
    propertyTable.put("line-height",LineHeight.maker());
    propertyTable.put("text-align",TextAlign.maker());
    propertyTable.put("text-align-last",TextAlignLast.maker());
    propertyTable.put("space-before.optimum",SpaceBeforeOptimum.maker());
    propertyTable.put("space-after.optimum",SpaceAfterOptimum.maker());
    propertyTable.put("start-indent",StartIndent.maker());
    propertyTable.put("end-indent",EndIndent.maker());
    propertyTable.put("provisional-distance-between-starts",ProvisionalDistanceBetweenStarts.maker());
    propertyTable.put("provisional-label-separation",ProvisionalLabelSeparation.maker());
    propertyTable.put("rule-thickness",RuleThickness.maker());
    propertyTable.put("color",Color.maker());
    propertyTable.put("wrap-option",WrapOption.maker());
    propertyTable.put("white-space-treatment",WhiteSpaceTreatment.maker());
    propertyTable.put("break-before",BreakBefore.maker());
    propertyTable.put("break-after",BreakAfter.maker());
    propertyTable.put("text-indent",TextIndent.maker());
    propertyTable.put("href",HRef.maker());
    propertyTable.put("column-width",ColumnWidth.maker());
    propertyTable.put("height",SVGLength.maker());
    propertyTable.put("width",SVGLength.maker());
    propertyTable.put("x",SVGLength.maker());
    propertyTable.put("y",SVGLength.maker());
    propertyTable.put("x1",SVGLength.maker());
    propertyTable.put("x2",SVGLength.maker());
    propertyTable.put("y1",SVGLength.maker());
    propertyTable.put("y2",SVGLength.maker());
  }

  public Property computeProperty(PropertyList propertyList, String propertyName) {

    Property p = null;
	
    Property.Maker propertyMaker = (Property.Maker)propertyTable.get(propertyName);
    if (propertyMaker != null) {
      p = propertyMaker.compute(propertyList);
    } else {
      //System.err.println("WARNING: property " + propertyName + " ignored");
    }
    return p;
  }

  public boolean isInherited(String propertyName) {
    boolean b;
	
    Property.Maker propertyMaker = (Property.Maker)propertyTable.get(propertyName);
    if (propertyMaker != null) {
      b = propertyMaker.isInherited();
    } else {
      //System.err.println("WARNING: Unknown property " + propertyName);
      b = true;
    }
    return b;
  }

  public PropertyList makeList(AttributeList attributes, PropertyList parentPropertyList) throws FOPException {
	
    PropertyList p = new PropertyList(parentPropertyList);
    p.setBuilder(this);
	
    for (int i = 0; i < attributes.getLength(); i++) {
      String attributeName = attributes.getName(i);
      Property.Maker propertyMaker = (Property.Maker)propertyTable.get(attributeName);
      if (propertyMaker != null) {
        p.put(attributeName,propertyMaker.make(p,attributes.getValue(i)));
      } else {
        //System.err.println("WARNING: property " + attributeName + " ignored");
      }
    }

    return p;
  }

  public Property makeProperty(PropertyList propertyList, String propertyName) throws FOPException {

    Property p = null;
	
    Property.Maker propertyMaker = (Property.Maker)propertyTable.get(propertyName);
    if (propertyMaker != null) {
      p = propertyMaker.make(propertyList);
    } else {
      //System.err.println("WARNING: property " + propertyName + " ignored");
    }
    return p;
  }
}
