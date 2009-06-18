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
 
package org.apache.fop.fonts.truetype;

/**
 * The CMap entry contains information of a Unicode range and the
 * the glyph indexes related to the range
 */
public class TTFCmapEntry {

    private int unicodeStart;
    private int unicodeEnd;
    private int glyphStartIndex;

    TTFCmapEntry() {
        unicodeStart = 0;
        unicodeEnd = 0;
        glyphStartIndex = 0;
    }

    TTFCmapEntry(int unicodeStart, int unicodeEnd, int glyphStartIndex) {
        this.unicodeStart = unicodeStart;
        this.unicodeEnd = unicodeEnd;
        this.glyphStartIndex = glyphStartIndex;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (o instanceof TTFCmapEntry) {
            TTFCmapEntry ce = (TTFCmapEntry)o;
            if (ce.unicodeStart == this.unicodeStart
                    && ce.unicodeEnd == this.unicodeEnd
                    && ce.glyphStartIndex == this.glyphStartIndex) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the glyphStartIndex.
     * @return int
     */
    public int getGlyphStartIndex() {
        return glyphStartIndex;
    }

    /**
     * Returns the unicodeEnd.
     * @return int
     */
    public int getUnicodeEnd() {
        return unicodeEnd;
    }

    /**
     * Returns the unicodeStart.
     * @return int
     */
    public int getUnicodeStart() {
        return unicodeStart;
    }

    /**
     * Sets the glyphStartIndex.
     * @param glyphStartIndex The glyphStartIndex to set
     */
    public void setGlyphStartIndex(int glyphStartIndex) {
        this.glyphStartIndex = glyphStartIndex;
    }

    /**
     * Sets the unicodeEnd.
     * @param unicodeEnd The unicodeEnd to set
     */
    public void setUnicodeEnd(int unicodeEnd) {
        this.unicodeEnd = unicodeEnd;
    }

    /**
     * Sets the unicodeStart.
     * @param unicodeStart The unicodeStart to set
     */
    public void setUnicodeStart(int unicodeStart) {
        this.unicodeStart = unicodeStart;
    }

}
