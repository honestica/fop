/*
 * $Id$
 * ============================================================================
 *                    The Apache Software License, Version 1.1
 * ============================================================================
 * 
 * Copyright (C) 1999-2003 The Apache Software Foundation. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modifica-
 * tion, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment: "This product includes software
 *    developed by the Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself, if
 *    and wherever such third-party acknowledgments normally appear.
 * 
 * 4. The names "FOP" and "Apache Software Foundation" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    apache@apache.org.
 * 
 * 5. Products derived from this software may not be called "Apache", nor may
 *    "Apache" appear in their name, without prior written permission of the
 *    Apache Software Foundation.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLU-
 * DING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ============================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation and was originally created by
 * James Tauber <jtauber@jtauber.com>. For more information on the Apache
 * Software Foundation, please see <http://www.apache.org/>.
 */ 
package org.apache.fop.svg;

import java.util.StringTokenizer;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.font.FontRenderContext;

//import org.apache.fop.fo.*;
//import org.apache.fop.datatypes.*;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

import org.apache.batik.dom.svg.SVGDOMImplementation;

/**
 * Some utilities for creating svg DOM documents and elements.
 */
public class SVGUtilities {
    private static final String SVG_NS = SVGDOMImplementation.SVG_NAMESPACE_URI;

    /**
     * Create a new svg document with batik.
     * @param width the width of the root svg element
     * @param height the height of the root svg element
     * @return a new SVG Document
     */
    public static final Document createSVGDocument(float width,
            float height) {
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        Document doc = impl.createDocument(SVG_NS, "svg", null);

        Element svgRoot = doc.getDocumentElement();
        svgRoot.setAttributeNS(null, "width", "" + width);
        svgRoot.setAttributeNS(null, "height", "" + height);
        return doc;
    }

    /**
     * Get the string width for a particular string given the font.
     * @param str the string
     * @param font the font
     * @return the width of the string in the given font
     */
    public static final float getStringWidth(String str, java.awt.Font font) {
        Rectangle2D rect =
            font.getStringBounds(str, 0, str.length(),
                                 new FontRenderContext(new AffineTransform(),
                                 true, true));
        return (float)rect.getWidth();
    }

    /**
     * Get the string height for a particular string given the font.
     * @param str the string
     * @param font the font
     * @return the height of the string in the given font
     */
    public static final float getStringHeight(String str,
                                              java.awt.Font font) {
        Rectangle2D rect =
            font.getStringBounds(str, 0, str.length(),
                                 new FontRenderContext(new AffineTransform(),
                                 true, true));
        return (float)rect.getHeight();
    }

    /**
     * Get the string bounds for a particular string given the font.
     * @param str the string
     * @param font the font
     * @return the bounds of the string
     */
    public static final Rectangle2D getStringBounds(String str,
            java.awt.Font font) {
        return font.getStringBounds(str, 0, str.length(),
                                    new FontRenderContext(new AffineTransform(),
                                    true, true));
    }

    /**
     * Create an SVG Line
     * @param doc the document to create the element
     * @param x the start x position
     * @param y the start y position
     * @param x2 the end x position
     * @param y2 the end y position
     * @return the new line element
     */
    public static final Element createLine(Document doc, float x, float y,
                                           float x2, float y2) {
        Element ellipse = doc.createElementNS(SVG_NS, "line");
        ellipse.setAttributeNS(null, "x1", "" + x);
        ellipse.setAttributeNS(null, "x2", "" + x2);
        ellipse.setAttributeNS(null, "y1", "" + y);
        ellipse.setAttributeNS(null, "y2", "" + y2);
        return ellipse;
    }

    /**
     * Create an SVG Ellipse
     * @param doc the document to create the element
     * @param cx the centre x position
     * @param cy the centre y position
     * @param rx the x axis radius
     * @param ry the y axis radius
     * @return the new ellipse element
     */
    public static final Element createEllipse(Document doc, float cx,
                                              float cy, float rx, float ry) {
        Element ellipse = doc.createElementNS(SVG_NS, "ellipse");
        ellipse.setAttributeNS(null, "cx", "" + cx);
        ellipse.setAttributeNS(null, "rx", "" + rx);
        ellipse.setAttributeNS(null, "cy", "" + cy);
        ellipse.setAttributeNS(null, "ry", "" + ry);
        return ellipse;
    }

    /**
     * Create an SVG Path.
     * @param doc the document to create the element
     * @param str the string for the d attribute on the path
     * @return the new path element
     */
    public static final Element createPath(Document doc, String str) {
        Element path = doc.createElementNS(SVG_NS, "path");
        path.setAttributeNS(null, "d", str);
        return path;
    }

    /**
     * Create an SVG Text object.
     * @param doc the document to create the element
     * @param x the start x position
     * @param y the start y position
     * @param str the string
     * @return the new text element
     */
    public static final Element createText(Document doc, float x, float y,
                                           String str) {
        Element textGraph = doc.createElementNS(SVG_NS, "text");
        textGraph.setAttributeNS(null, "x", "" + x);
        textGraph.setAttributeNS(null, "y", "" + y);
        org.w3c.dom.Text text = doc.createTextNode(str);
        textGraph.appendChild(text);
        return textGraph;
    }

    /**
     * Create an SVG Rectangle.
     * @param doc the document to create the element
     * @param x the start x position
     * @param y the start y position
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return the new rectangle element
     */
    public static final Element createRect(Document doc, float x, float y,
                                           float width, float height) {
        Element border = doc.createElementNS(SVG_NS, "rect");
        border.setAttributeNS(null, "x", "" + x);
        border.setAttributeNS(null, "y", "" + y);
        border.setAttributeNS(null, "width", "" + width);
        border.setAttributeNS(null, "height", "" + height);
        return border;
    }

    /**
     * Create an SVG G.
     * @param doc the document to create the element
     * @return the new g element
     */
    public static final Element createG(Document doc) {
        Element border = doc.createElementNS(SVG_NS, "g");
        return border;
    }

    /**
     * Create an SVG Clip.
     * @param doc the document to create the element
     * @param els the child elements that make the clip
     * @param id the id of the clipping path
     * @return the new clip element
     */
    public static final Element createClip(Document doc, Element els,
                                           String id) {
        Element border = doc.createElementNS(SVG_NS, "clipPath");
        border.setAttributeNS(null, "id", id);
        border.appendChild(els);
        return border;
    }

    /**
     * Create and svg image element.
     * @param doc the document to create the element
     * @param ref the href link to the image
     * @param width the width to set on the image
     * @param height the height to set on the image
     * @return a new image element
     */
    public static final Element createImage(Document doc, String ref,
                                            float width, float height) {
        Element border = doc.createElementNS(SVG_NS, "image");
        border.setAttributeNS("http://www.w3.org/1999/xlink", "href",
                              ref);
        border.setAttributeNS(null, "width", "" + width);
        border.setAttributeNS(null, "height", "" + height);
        return border;
    }

    /**
     * Create some SVG text that is wrapped into a specified width.
     * @param doc the document to create the elements
     * @param str the string to wrap
     * @param font the font
     * @param width the width to wrap
     * @return the new element containing the wrapped text
     */
    public static final Element wrapText(Document doc, String str,
                                         java.awt.Font font, float width) {
        Element g = createG(doc);
        Element text;
        StringTokenizer st = new StringTokenizer(str, " \t\r\n");
        float totalWidth = 0;
        String totalStr = "";
        int line = 0;
        float height = getStringHeight(str, font);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            float strwidth = getStringWidth(token, font);
            totalWidth += strwidth;
            if (totalWidth > width) {
                if (totalStr.equals("")) {
                    totalStr = token;
                    token = "";
                    strwidth = 0;
                }
                text = createText(doc, 0, line * (height + 5), totalStr);
                g.appendChild(text);
                totalStr = token;
                totalWidth = strwidth;
                line++;
            } else {
                totalStr = totalStr + " " + token;
            }
        }

        return g;
    }

}
