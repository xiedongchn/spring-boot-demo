package com.xd.springboot.common.util;

/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.zip.GZIPInputStream;

/**
 * A data class representing HTTP Response
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Response {

    private int statusCode;

    private String responseAsString = null;

    private InputStream is;

    private HttpURLConnection con;

    private boolean streamConsumed = false;

    public Response() {

    }

    public Response(HttpURLConnection con) throws IOException {
        this.con = con;
        this.statusCode = con.getResponseCode();
        if (null == (is = con.getErrorStream())) {
            is = con.getInputStream();
        }
        if (null != is && "gzip".equals(con.getContentEncoding())) {
            // the response is gzipped
            is = new GZIPInputStream(is);
        }
    }

    // for test purpose
    /*package*/ Response(String content) {
        this.responseAsString = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String name) {
        if (con != null) {
            return con.getHeaderField(name);
        } else {
            return null;
        }
    }

    /**
     * Returns the response stream.<br>
     * This method cannot be called after calling asString() or asDcoument()<br>
     * It is suggested to call disconnect() after consuming the stream.
     * <p>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body stream
     * @throws WeiboException
     * @see #disconnect()
     */
    public InputStream asStream() {
        if (streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        }
        return is;
    }

    /**
     * Returns the response body as string.<br>
     * Disconnects the internal HttpURLConnection silently.
     *
     * @return response body
     */
    public String asString() {
        if (null == responseAsString) {
            BufferedReader br;
            try {
                InputStream stream = asStream();
                if (null == stream) {
                    return null;
                }
                br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer buf = new StringBuffer();
                String line;
                while (null != (line = br.readLine())) {
                    buf.append(line).append("\n");
                }
                this.responseAsString = buf.toString();
                stream.close();
                con.disconnect();
                streamConsumed = true;
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
        return responseAsString;
    }

    public String getResponseAsString() {
        return responseAsString;
    }

    public void setResponseAsString(String responseAsString) {
        this.responseAsString = responseAsString;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
