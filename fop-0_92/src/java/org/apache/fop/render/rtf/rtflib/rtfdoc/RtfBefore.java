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

package org.apache.fop.render.rtf.rtflib.rtfdoc;

/*
 * This file is part of the RTF library of the FOP project, which was originally
 * created by Bertrand Delacretaz <bdelacretaz@codeconsult.ch> and by other
 * contributors to the jfor project (www.jfor.org), who agreed to donate jfor to
 * the FOP project.
 */

import java.io.Writer;
import java.io.IOException;

/** The opposite of RtfAfter */
public class RtfBefore extends RtfAfterBeforeBase {
    /**RtfBefore attributes*/
    public static final String HEADER = "header";

    /** String array of attribute names */
    public static final String[] HEADER_ATTR = new String[]{
        HEADER
    };

    RtfBefore(RtfSection parent, Writer w, RtfAttributes attrs) throws IOException {
        super(parent, w, attrs);
    }

    /**
     * Write the attributes for this element
     * @throws IOException for I/O problems
     */
    protected void writeMyAttributes() throws IOException {
        writeAttributes(attrib, HEADER_ATTR);
    }
}