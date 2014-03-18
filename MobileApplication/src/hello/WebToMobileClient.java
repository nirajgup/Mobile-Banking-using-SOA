//GEN-BEGIN:Client
/**
 * This file is generated. Please do not change
 */
package hello;

import java.util.*;
import java.io.*;
import javax.microedition.io.*;

/**
 *
 */
public class WebToMobileClient {

    /** The URL of the servlet gateway */
    private String serverURL;

    /** The session cookie of this client */
    private String sessionCookie;

    /**
     * Empty array used for no-argument calls, and to represent the value "void"
     */

    private final static Object[] _ = new Object[0];

    /**
     * Constructs a new WebToMobileClient
     * and initializes the URL to the servlet gateway from a hard-coded value.
     */
    public WebToMobileClient() {
        this("http://localhost:8084/JSPExamples/servlet/gfgf.WebToMobileServlet");
    }

    /**
     * Constructs a new WebToMobileClient
     * and initializes the URL to the servlet gateway from the given value
     *
     * @param serverURL URL of the deployed servlet
     */
    public WebToMobileClient(String serverURL) {
        this.serverURL = serverURL;
    }
    public void finishResponse() throws IOException {
        invokeServer(4, _, null);
    }
    public void setDebugLevel(int debug) throws IOException {
        Object params[] = new Object[] {
            new Integer(debug)
        };
        int paramIDs[] = new int[] {
            5
        };
        invokeServer(3, params, paramIDs);
    }
    public void setCompressionThreshold(int threshold) throws IOException {
        Object params[] = new Object[] {
            new Integer(threshold)
        };
        int paramIDs[] = new int[] {
            5
        };
        invokeServer(2, params, paramIDs);
    }
    public void setContentType(String contentType) throws IOException {
        Object params[] = new Object[] {
            (String)contentType
        };
        int paramIDs[] = new int[] {
            7
        };
        invokeServer(1, params, paramIDs);
    }
    public void flushBuffer() throws IOException {
        invokeServer(5, _, null);
    }
    public void setContentLength(int length) throws IOException {
        Object params[] = new Object[] {
            new Integer(length)
        };
        int paramIDs[] = new int[] {
            5
        };
        invokeServer(6, params, paramIDs);
    }
    public String[] getItems() throws IOException {
        return (String[])invokeServer(9, _, null);
    }
    public void setItem(String name) throws IOException {
        Object params[] = new Object[] {
            (String)name
        };
        int paramIDs[] = new int[] {
            7
        };
        invokeServer(7, params, paramIDs);
    }
    public void setSubmit(String s) throws IOException {
        Object params[] = new Object[] {
            (String)s
        };
        int paramIDs[] = new int[] {
            7
        };
        invokeServer(8, params, paramIDs);
    }

    /**
     *  This method performes a dynamic invocation on the server. It is generic in
     *  order to reduce the code size.
     *
     *@param  requestID        The id of the server service (method) we wish to
     *      invoke.
     *@param  parameters       The parameters that should be passed to the server
     *      (type safety is not checked by this method!)
     *@param  returnType       Is used to indicate the return type we should read
     *      from the server
     *@return                  The return value from the invoked service
     *@exception  IOException  When a communication error or a remove exception
     * occurs
     */
    private Object invokeServer(int requestID, Object[] parameters, int[] paramIDs ) throws IOException {
        HttpConnection connection = (HttpConnection) Connector.open( serverURL );
        connection.setRequestMethod(HttpConnection.POST);
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Accept", "application/octet-stream");

        if (sessionCookie == null) {
            // if this is the first time this client contatcs the server,
            // verify that the version matches
            connection.setRequestProperty("version", "???");
        } else {
            connection.setRequestProperty("cookie", sessionCookie);
        }

        DataOutputStream output = connection.openDataOutputStream();

        // Write invocation code
        output.writeShort( 1 );

        /* Write the byte signifying that only one call
         * is being made.
         */
        // TODO This is not reflected on server now
        //output.writeShort(1 /* one call to be made to the server */);

        output.writeInt(requestID);
        for (int i = 0; i < parameters.length; i++ ) {
            writeObject(output, parameters[i], paramIDs[i]);
        }

        output.close();

        int response;
        try {
            response = connection.getResponseCode();
        } catch (IOException e) {
            throw new IOException("No response from " + serverURL);
        }
        if (response != 200) {
            throw new IOException(response + " " + connection.getResponseMessage());
        }
        DataInputStream input = connection.openDataInputStream();
        String sc = connection.getHeaderField("set-cookie");
        if (sc != null) {
            sessionCookie = sc;
        }
        short errorCode = input.readShort();
        if (errorCode != 1) {
            // there was a remote exception
            throw new IOException(input.readUTF());
        }

        Object returnValue = readObject(input);

        input.close();
        connection.close();
        return returnValue;
    }

    /**
     * Serializes object to the stream with given type id
     *
     * @param out
     * @param o object to be serialized to the stream
     * @param id idetification code of the serialized object
     */
    private void writeObject( DataOutputStream out, Object o, int id ) throws IOException {
        if( o == null ) {
            // write null type to the stream
            out.writeShort( -1 );
            return;
        }
        switch( id ) {
            case 7:
                // String
                out.writeShort(7);
                out.writeUTF((String)o);
                break;
            default:
                // default if a data type is not supported
                throw new IllegalArgumentException("Unsupported parameter type: " + o.getClass());
        }
    }

    private static Object readObject(DataInput in) throws IOException {
        int type = in.readShort();
        Object result;
        switch (type) {
            case 6:
                // void
                return _;
            case 7:
                // String
                result = in.readUTF();
                return result;
            case 9:
                // String[]
                int a_size_9 = in.readInt();
                String[] a_result_9 = new String[ a_size_9 ];
                for( int a_i_9 = 0; a_i_9 < a_size_9; a_i_9++ ) {
                    a_result_9[a_i_9] = (String)readObject( in );
                }
                result = a_result_9;

                return result;
            case -1: /* NULL */
                return null;
        }
        throw new IllegalArgumentException("Unsupported return type (" + type + ")");
    }
}
//GEN-END:Client
