/**
* $RCSfile$
* $Revision$
* $Date$
*
* Copyright (C) 2002-2004 Jive Software. All rights reserved.
* ====================================================================
* The Jive Software License (based on Apache Software License, Version 1.1)
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* 1. Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in
*    the documentation and/or other materials provided with the
*    distribution.
*
* 3. The end-user documentation included with the redistribution,
*    if any, must include the following acknowledgment:
*       "This product includes software developed by
*        Jive Software (http://www.jivesoftware.com)."
*    Alternately, this acknowledgment may appear in the software itself,
*    if and wherever such third-party acknowledgments normally appear.
*
* 4. The names "Smack" and "Jive Software" must not be used to
*    endorse or promote products derived from this software without
*    prior written permission. For written permission, please
*    contact webmaster@jivesoftware.com.
*
* 5. Products derived from this software may not be called "Smack",
*    nor may "Smack" appear in their name, without prior written
*    permission of Jive Software.
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL JIVE SOFTWARE OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
* ====================================================================
*/

package org.jivesoftware.smackx.workgroup.packet;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.xmlpull.v1.XmlPullParser;


/**
 * A packet extension that contains information about the user and agent in a
 * workgroup chat. The packet extension is attached to group chat invitations.
 */
public class WorkgroupInformation implements PacketExtension {

    /**
     * Element name of the packet extension.
     */
    public static final String ELEMENT_NAME = "workgroup";

    /**
     * Namespace of the packet extension.
     */
    public static final String NAMESPACE = "xmpp:workgroup";

    private String userID;
    private String agentID;

    protected WorkgroupInformation(String userID, String agentID) {
        this.userID = userID;
        this.agentID = agentID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAgentID() {
        return agentID;
    }

    public String getElementName() {
        return ELEMENT_NAME;
    }

    public String getNamespace() {
        return NAMESPACE;
    }

    public String toXML() {
        StringBuffer buf = new StringBuffer();

        buf.append('<').append(ELEMENT_NAME);
        buf.append(" user=\"").append(userID).append("\"");
        buf.append(" agent=\"").append(agentID);
        buf.append("\" xmlns=\"").append(NAMESPACE).append("\" />");

        return buf.toString();
    }

    public static class Provider implements PacketExtensionProvider {

        /**
         * PacketExtensionProvider implementation
         */
        public PacketExtension parseExtension (XmlPullParser parser)
            throws Exception {
            String user = parser.getAttributeValue("", "user");
            String agent = parser.getAttributeValue("", "agent");

            // since this is a start and end tag, and we arrive on the start, this should guarantee
            //      we leave on the end
            parser.next();

            return new WorkgroupInformation(user, agent);
        }
    }
}