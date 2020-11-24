package com.goldsign.rwcommu.connection;

/* @(#)SerialConnection.java	1.6 98/07/17 SMI
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import com.goldsign.rwcommu.exception.SerialException;
import com.goldsign.rwcommu.vo.SerialParameters;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.comm.*;
import org.apache.log4j.Logger;

/**
A class that handles the details of a serial connection. Reads from one 
TextArea and writes to a second TextArea. 
Holds the state of the connection.
*/
public class SerialConnection {
  
    protected SerialParameters parameters;
    protected OutputStream os;
    protected InputStream is;

    protected CommPortIdentifier portId;
    protected SerialPort sPort;

    private boolean open;
    
    private static final Logger logger = Logger.getLogger(SerialConnection.class.getName());
    /**
    Creates a SerialConnection object and initilizes variables passed in
    as params.

    @param parent A SerialDemo object.
    @param parameters A SerialParameters object.
    @param messageAreaOut The TextArea that messages that are to be sent out
    of the serial port are entered into.
    @param messageAreaIn The TextArea that messages comming into the serial
    port are displayed on.
    */
    public SerialConnection(SerialParameters parameters) {
	
	this.parameters = parameters;
	open = false;
   }

   /**
   Attempts to open a serial connection and streams using the parameters
   in the SerialParameters object. If it is unsuccesfull at any step it
   returns the port to a closed state, throws a 
   <code>SerialConnectionException</code>, and returns.

   Gives a timeout of 30 seconds on the portOpen to allow other applications
   to reliquish the port if have it open and no longer need it.
   */
   public void open() throws SerialException {

	// Obtain a CommPortIdentifier object for the port you want to open.
	try {
	    portId = 
		 CommPortIdentifier.getPortIdentifier(parameters.getPortName());
	} catch (NoSuchPortException e) {
            e.printStackTrace();
	    throw new SerialException(e.getMessage());
	}

	// Open the port represented by the CommPortIdentifier object. Give
	// the open call a relatively long timeout of 30 seconds to allow
	// a different application to reliquish the port if the user 
	// wants to.
	try {
	    sPort = (SerialPort)portId.open("Serial", 30000);
	} catch (PortInUseException e) {
             e.printStackTrace();
	    throw new SerialException(e.getMessage());
	}

	// Set the parameters of the connection. If they won't set, close the
	// port before throwing an exception.
	try {
	    setParameters();
	} catch (SerialException e) {	
	    sPort.close();
	    throw e;
	}

	// Open the input and output streams for the connection. If they won't
	// open, close the port before throwing an exception.
	try {
	    os = sPort.getOutputStream();
	    is = sPort.getInputStream();
	} catch (IOException e) {
	    sPort.close();
	    throw new SerialException("Error opening i/o streams");
	}

	// Set receive timeout to allow breaking out of polling loop during
	// input handling.
	try {
	    sPort.enableReceiveTimeout(30);
	} catch (UnsupportedCommOperationException e) {
	}
	open = true;
    }

    /**
    Sets the connection parameters to the setting in the parameters object.
    If set fails return the parameters object to origional settings and
    throw exception.
    */
    public void setParameters() throws SerialException {

	// Set connection parameters, if set fails return parameters object
	// to original state.
	try {
	    sPort.setSerialPortParams(parameters.getBaudRate(),
				      parameters.getDatabits(),
				      parameters.getStopbits(),
				      parameters.getParity());
	} catch (UnsupportedCommOperationException e) {
	    
	    throw new SerialException("Unsupported parameter");
	}

	// Set flow control.
	try {
	    sPort.setFlowControlMode(parameters.getFlowControlIn() 
			           | parameters.getFlowControlOut());
	} catch (UnsupportedCommOperationException e) {
	    throw new SerialException("Unsupported flow control");
	}
        //hwj add 20140306 不要缓冲
        sPort.setOutputBufferSize(0);
        sPort.setInputBufferSize(0);
    }

    /**
    Close the port and clean up associated elements.
    */
    public void close() {
	// If port is alread closed just return.
	if (!open) {
	    return;
	}

	// Check to make sure sPort has reference to avoid a NPE.
	if (sPort != null) {
	    try {
		// close the i/o streams.
	    	os.close();
	    	is.close();
	    } catch (IOException e) {
		System.err.println(e);
	    }

	    // Close the port.
	    sPort.close();

	}

	open = false;
    }

    /**
    Reports the open status of the port.
    @return true if port is open, false if port is closed.
    */
    public boolean isOpen() {
	return open;
    }
 
}
