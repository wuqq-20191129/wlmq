/*
 * Amendment History:
 *
 * Date          By                 Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao        Create the class
 * 2004-09-24    Chen Jiancheng     Modify the class
 */

package com.goldsign.frame.struts;

import com.goldsign.frame.env.FrameworkConstant;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.Globals;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Locale;
import java.io.Writer;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.apache.log4j.Logger;

/**
 * View action for creating XML.
 * If the XML need to linked with a XSL, please use XSLActionForward for this action.
 * Support Document(DOM) objects and JavaBean objects
 */
public class Action2XML extends BaseAction implements FrameworkConstant{
	
	/**
	 *  Request attribute key under which the linked xsl file name is stored
	 */
	public final static String XSL_FILE_KEY = Action2XML.class.getName() + ".XSL";
	
	/**
	 *  Request attribute key under which the "include parameters" flag is stored
	 */
	public final static String INCLUDE_PARM_KEY = Action2XML.class.getName() + ".INCLUDE_PARM";
	
	/**
	 *  XML document root element tag name
	 */
	protected final static String ROOT_TAG = "Service";
	
	/**
	 *  XML document "contexst root" subelement tag name
	 */
	protected final static String CONTEXT_ROOT_TAG = "ContextRoot";
	
	/**
	 *  XML document "User ID" subelement tag name
	 */
	protected final static String USER_ID_TAG = "UserID";
	
	/**
	 *  XML document "Token" subelement tag name
	 */
	protected final static String TOKEN_TAG = "Token";
	
	/**
	 *  XML document "Token->propery" subelement tag name
	 */
	protected final static String TOKEN_KEY_TAG = "Key";
	
	/**
	 *  XML document "Token->value" subelement tag name
	 */
	protected final static String TOKEN_VALUE_TAG = "Value";
	
	/**
	 *  XML document "Errors" subelement tag name
	 */
	protected final static String ERRORS_TAG = "Errors";
	
	/**
	 *  XML document "Errors->Error" subelement tag name
	 */
	protected final static String ERROR_TAG = "Error";
	
	/**
	 *  XML document "Messages" subelement tag name
	 */
	protected final static String MESSAGES_TAG = "Messages";
	
	/**
	 *  XML document "Messages->Message" subelement tag name
	 */
	protected final static String MESSAGE_TAG = "Message";
	
	/**
	 *  XML document "Parameters" subelement tag name
	 */
	protected final static String PARAMETERS_TAG = "Parameters";
	
	/**
	 *  XML document "Result" subelement tag name
	 */
	protected final static String RESULT_TAG = "Result";
	
	/**
	 *  XML document content type
	 */
	protected final static String CONTENT_TYPE = "text/xml; charset=GBK";
	
	/**
	 *  XML document "message" & "error" tag : "property" attribute name
	 */
	protected final static String PROPERTY_ATTR = "property";
	
	/**
	 *  XML document declaration
	 */
	protected final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"GBK\" ?>";
	
	/**
	 * CDATA Begin
	 */
	protected final static String CDATA_BEGIN = "<![CDATA[";
	
	/**
	 * CDATA End
	 */
	protected final static String CDATA_END = "]]>";
	
	/**
	 *  XML document tag name for "Value Parameters"
	 */
	protected final static String VALUE_PARM_TAG = "Value";
	
	/**
	 * Message logger
	 */
	private static Logger log = Logger.getLogger(Action2XML.class.getName());
	
	
	/**
	 * This member function is used to choose the XML handle method.
	 *
	 * Note : Because "OutOfMemory" Error may occur if we pass a StringWriter or a CharArrayWriter to
	 *        Castor function to generate the XML output, we pass the PrintWriter from the response object
	 *        to Castor function directly. If a exception occurs after the XML content output is started, user
	 *        can not be routed to the default_exception_page.
	 *
	 * @param   mapping     - The ActionMapping used to select this instance
	 * @param   actionForm  - The optional ActionForm bean for this request (if any)
	 * @param   request     - The HTTP request we are processing
	 * @param   response    - The HTTP response we are creating
	 *
	 * @return  NULL    (no further processing needed)
	 *
	 * @exception Exception  - if an exception occurs that interrupts the normal operation
	 *
	 */
	protected final ActionForward processLogic (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		
		// if the some content has been send out
		// this flag is used to determine if the request need to be forwarded to the default_exception_page
		boolean outputStarted = false;
		String XSLName;
//		string buffer for creating the return XML content
		StringBuffer outputXML = new StringBuffer();
		
		try {
			
			// write the result to the response object
			PrintWriter outWriter = response.getWriter();
			
			
			
			// -- start to print out the XML --
			outputXML.append(XML_DECLARATION);
			
			// Have a linked XSL file ?
			
			XSLName = getXSLFile(request);
			
			
			if (!XSLName.equals("")) {
				outputXML.append("<?xml-stylesheet type=\"text/xsl\" href=\"");
				outputXML.append(this.getContextRoot(request));
				outputXML.append(XSLName);
				outputXML.append("\"?>");
			}
			
			// root tag
			outputXML.append("<");
			outputXML.append(ROOT_TAG);
			outputXML.append(">");
			
			// context root of the application
			outputXML.append("<");
			outputXML.append(CONTEXT_ROOT_TAG);
			outputXML.append(">");
			outputXML.append(this.getContextRoot(request));
			outputXML.append("</");
			outputXML.append(CONTEXT_ROOT_TAG);
			outputXML.append(">");
			
			// current user ID
			outputXML.append("<");
			outputXML.append(USER_ID_TAG);
			outputXML.append(">");
			outputXML.append("APP_USERID");
			outputXML.append("</");
			outputXML.append(USER_ID_TAG);
			outputXML.append(">");
			
			// transaction token for avoiding double submit
			String token;
			token = this.getToken(request);
			
			if (!token.equals("")){
				outputXML.append("<");
				outputXML.append(TOKEN_TAG);
				outputXML.append(">");
				outputXML.append("<");
				outputXML.append(TOKEN_KEY_TAG);
				outputXML.append(">");
				outputXML.append(Globals.TRANSACTION_TOKEN_KEY);
				outputXML.append("</");
				outputXML.append(TOKEN_KEY_TAG);
				outputXML.append(">");
				outputXML.append("<");
				outputXML.append(TOKEN_VALUE_TAG);
				outputXML.append(">");
				outputXML.append(CDATA_BEGIN);
				outputXML.append(token);
				outputXML.append(CDATA_END);
				outputXML.append("</");
				outputXML.append(TOKEN_VALUE_TAG);
				outputXML.append(">");
				outputXML.append("</");
				outputXML.append(TOKEN_TAG);
				outputXML.append(">");
			}
			else {
				outputXML.append("<");
				outputXML.append(TOKEN_TAG);
				outputXML.append("/>");
			}
			
			// create "Errors" element
			String property, msgContent, attribute;
			Iterator msgs;
			ActionError err;
			Iterator props;
			Locale userLocale = this.getLocale(request);
			
			ActionErrors errors = this.getErrors(request);
			if (errors != null && !errors.isEmpty()) {
				
				outputXML.append("<");
				outputXML.append(ERRORS_TAG);
				outputXML.append(">");
				
				props = errors.properties();
				while (props.hasNext()) {
					property = (String) props.next();
					
					// loop over errors for each property
					msgs = errors.get(property);
					while (msgs.hasNext()) {
						// get msg object
						err = (ActionError) msgs.next();
						
						// get error content
						msgContent = this.getResources(request, FRAMEWORK_RESOURCE).getMessage(userLocale, err.getKey(), err.getValues());
						if (msgContent == null)
							msgContent = this.getResources(request).getMessage(userLocale, err.getKey(), err.getValues());
						if (msgContent == null)
						{
							//continue;
							msgContent = "Unresolved error message: " + err.getKey();
							log.error("Unresolved error message: " + err.getKey());
						}
						
						// add the error to the XML object
						if (property.equals(ActionErrors.GLOBAL_ERROR))
							attribute = "";
						else
							attribute = " " + PROPERTY_ATTR + "=\"" + property + "\"";
						
						outputXML.append("<");
						outputXML.append(ERROR_TAG);
						outputXML.append(attribute);
						outputXML.append(">");
						outputXML.append(CDATA_BEGIN);
						outputXML.append(msgContent);
						outputXML.append(CDATA_END);
						outputXML.append("</");
						outputXML.append(ERROR_TAG);
						outputXML.append(">");
						
						
					}// end while
				}// end while
				
				outputXML.append("</");
				outputXML.append(ERRORS_TAG);
				outputXML.append(">");
			}
			else {
				outputXML.append("<");
				outputXML.append(ERRORS_TAG);
				outputXML.append("/>");
			}
			
			// create "Messages" element
			ActionMessage msg;
			
			ActionMessages messages = this.getMessages(request);
			if (messages != null && !messages.isEmpty()) {
				
				outputXML.append("<");
				outputXML.append(MESSAGES_TAG);
				outputXML.append(">");
				
				props = messages.properties();
				while (props.hasNext()) {
					
					property = (String) props.next();
					// loop over messages for each property
					msgs = messages.get(property);
					
					
					while (msgs.hasNext()) {
						// get msg object
						msg = (ActionMessage) msgs.next();
						
						// get message content
						
						//       System.out.println("key="+msg.getKey()+":values="+ msg.getValues());
						msgContent = this.getResources(request, FRAMEWORK_RESOURCE).getMessage(userLocale, msg.getKey(), msg.getValues());
						if (msgContent == null)
							msgContent = this.getResources(request).getMessage(userLocale, msg.getKey(), msg.getValues());
						
						if(msgContent == null){
							Object[] ob = msg.getValues();
							if(ob != null)
								msgContent = (String)ob[0];
						}
						if (msgContent == null)
						{
							//continue;
							msgContent = "Unresolved message: " + msg.getKey();
							log.error("Unresolved message: " + msg.getKey());
						}
						
						// add the message to the XML object
						if (property.equals(ActionMessages.GLOBAL_MESSAGE))
							attribute = "";
						else
							attribute = " " + PROPERTY_ATTR + "=\"" + property + "\"";
						
						outputXML.append("<");
						outputXML.append(MESSAGE_TAG);
						outputXML.append(attribute);
						outputXML.append(">");
						outputXML.append(CDATA_BEGIN);
						outputXML.append(msgContent);
						outputXML.append(CDATA_END);
						outputXML.append("</");
						outputXML.append(MESSAGE_TAG);
						outputXML.append(">");
						
					}// end while
				}// end while
				
				outputXML.append("</");
				outputXML.append(MESSAGES_TAG);
				outputXML.append(">");
			}
			else {
				outputXML.append("<");
				outputXML.append(MESSAGES_TAG);
				outputXML.append("/>");
			}
			
			// create "Parameters" element
			Enumeration parms = request.getParameterNames();
			String includeParm = this.getIncludeParm(request);
			
			if (includeParm.equals(Action2XMLForward.INCLUDE_PARM_FALSE) || parms == null || !parms.hasMoreElements()) {
				outputXML.append("<");
				outputXML.append(PARAMETERS_TAG);
				outputXML.append("/>");
			}
			else {
				String[] parmValues;
				String parmName;
				
				outputXML.append("<");
				outputXML.append(PARAMETERS_TAG);
				outputXML.append(">");
				
				for (; parms.hasMoreElements(); ){
					parmName = (String) parms.nextElement();
					parmValues = request.getParameterValues(parmName);
					
					// check whether this parameter is a "Value" parameter (parameter name = "value(XXX)")
					boolean isValueParm = false;    // check if the parameter name like "value(XXXX)"
					int parmLength = parmName.length();
					if (parmLength >= 7 && parmName.substring(0, 6).equals("value(") && parmName.substring(parmLength - 1).equals(")")){
						isValueParm = true;
					}
					
					// skip this parameter ?
					if (isValueParm && includeParm.equals(Action2XMLForward.INCLUDE_PARM_SKIPVALUE)) {
						continue;
					}
					
					// convert "value(XXX)" to "XXX"
					if (isValueParm)
						parmName = parmName.substring(6, parmLength - 1);
					
					if (parmValues != null){
						
						if (isValueParm) {
							outputXML.append("<");
							outputXML.append(VALUE_PARM_TAG);
							outputXML.append(">");
						}
						
						for (int i = 0; i < parmValues.length; i++){
							outputXML.append("<");
							outputXML.append(parmName);
							outputXML.append(">");
							outputXML.append(CDATA_BEGIN);
							outputXML.append(parmValues[i]);
							outputXML.append(CDATA_END);
							outputXML.append("</");
							outputXML.append(parmName);
							outputXML.append(">");
						}// end for
						
						if (isValueParm) {
							outputXML.append("</");
							outputXML.append(VALUE_PARM_TAG);
							outputXML.append(">");
						}
					}// end if
				}// end for
				
				outputXML.append("</");
				outputXML.append(PARAMETERS_TAG);
				outputXML.append(">");
			}
			
			// create "Result" element
			Hashtable resultSet;
			resultSet = (Hashtable) request.getAttribute(BaseAction.RESULT_KEY);
			
			if (resultSet == null || resultSet.isEmpty()) {
				outputXML.append("<");
				outputXML.append(RESULT_TAG);
				outputXML.append("/>");
			}
			else{
				outputXML.append("<");
				outputXML.append(RESULT_TAG);
				outputXML.append(">");
				
				// print out the result
				String resultName;
				Object resultObj;
				StringWriter strWriter = new StringWriter();
				
				for (Enumeration keyList = resultSet.keys(); keyList.hasMoreElements();) {
					resultName = (String) keyList.nextElement();
					resultObj = resultSet.get(resultName);
					
					if (resultObj instanceof Document) {
						transDOM2XML(strWriter, resultName, (Document) resultObj);
					}
					else{
						outWriter.print(outputXML.toString());
						outWriter.flush();
						outputStarted = true;
						outputXML = new StringBuffer();
						transBean2XML(outWriter, resultName, resultObj);
						outWriter.flush();
					}
					
					strWriter.flush();
				}// end for
				
				outputXML.append(strWriter.toString());
				outputXML.append("</");
				outputXML.append(RESULT_TAG);
				outputXML.append(">");
			}
			
			// end of the XML
			outputXML.append("</");
			outputXML.append(ROOT_TAG);
			outputXML.append(">");
			
			// The response content type is XML
			response.setContentType(CONTENT_TYPE);
			
			outWriter.print(outputXML.toString());
			outWriter.flush();
			outputStarted = true;
			
		}
		catch (Exception e) {
			log.error("Exception occurs when transforming the result objects into XML!", e);
			log.error("outputXML="+outputXML.toString());
			if (outputStarted) {
				// because some of the content has been sent out. The request can not be routed to the exception page.
				return null;
			}
			else {
				return mapping.findForward(BaseAction.EXCEPTION_DEFAULT_PAGE);
			}
		}
		
		// no further forward
		return null;
		
		
	}// end processLogic()
	
	
	/**
	 * This member function is used to get the linked XSL with the XML result
	 *
	 * @param   request     - The HTTP request we are processing
	 *
	 * @return  NULL or the XSL file name
	 *
	 */
	protected final String getXSLFile(HttpServletRequest request){
		
		String xslFile = (String) request.getAttribute(XSL_FILE_KEY);
		if (xslFile == null)
			xslFile = "";
		
		xslFile = xslFile.replace('\\', '/');
		
		if (!xslFile.equals("") && !xslFile.startsWith("/"))
			xslFile = "/" + xslFile;
		
		return xslFile;
		
	}// end getXSLFile()
	
	
	/**
	 * This member function is used to get the "include parameters" flag
	 *
	 * @param   request     - The HTTP request we are processing
	 *
	 * @return  the "discard parameter" flag value
	 *
	 */
	protected final String getIncludeParm(HttpServletRequest request){
		
		String includeParm = (String) request.getAttribute(INCLUDE_PARM_KEY);
		
		if (includeParm == null)
			includeParm = "true";
		
		if (log.isDebugEnabled())
			log.debug("getIncludeParm() : " + includeParm);
		
		return includeParm;
		
	}// end getIncludeParm()
	
	
	/**
	 * This member function is used to get the context root of this application
	 *
	 * @param   request     - The HTTP request we are processing
	 *
	 * @return  NULL or the context root
	 *
	 */
	protected final String getContextRoot(HttpServletRequest request){
		
		return request.getContextPath();
		
	}// end getContextRoot()
	
	
	/**
	 * This member function is used to get token
	 *
	 * @param   request     - The HTTP request we are processing
	 *
	 * @return  NULL or the token
	 *
	 */
	protected final String getToken(HttpServletRequest request){
		
		String token = (String) request.getSession().getAttribute(Globals.TRANSACTION_TOKEN_KEY);
		if (token == null) token = "";
		
		return token;
		
	}// end getToken()
	
	
	/**
	 * This member function is used to get the ActionErrors object from the request
	 *
	 * @param   request     - The HTTP request we are processing
	 *
	 * @return  NULL or the ActionErrors object
	 *
	 */
	protected final ActionErrors getErrors(HttpServletRequest request){
		
		return (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
		
	}// end getErrors()
	
	
	/**
	 * This member function is used to transform a Document(DOM) object into XML
	 *
	 * @param   outWriter   - The output writer
	 * @param   resultName  - The name of the result object
	 * @param   resultObj   - The document object that need to be transformed into XML
	 *
	 * @exception TransformerException  - if an exception occurs that interrupts the normal operation
	 *
	 */
	private void transDOM2XML(Writer outWriter, String resultName, Document resultObj)
	throws TransformerException, IOException {
		
		if (resultObj != null && resultObj.getFirstChild().hasChildNodes()) {
			
			outWriter.write("<" + resultName + ">");
			
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING,"GB2312");
			
			StreamResult streamResult = new StreamResult(outWriter);
			DOMSource docSource;
			
			NodeList resultNodeList = resultObj.getFirstChild().getChildNodes();
			if (resultNodeList != null){
				for (int i = 0; i < resultNodeList.getLength(); i++) {
					docSource = new DOMSource(resultNodeList.item(i));
					transformer.transform(docSource, streamResult);
				}// end for
			}// end if
			
			outWriter.write("</" + resultName + ">");
		}
		else{
			outWriter.write("<" + resultName + "/>");
		}
	}// end transDOM2XML()
	
	
	/**
	 * This member function is used to transform a JavaBean object into XML
	 *
	 * @param   outWriter   - The output writer
	 * @param   resultName  - The name of the result object
	 * @param   resultObj   - The document object that need to be transformed into XML
	 *
	 * @exception IOException           - if an IO exception occurs that interrupts the normal operation
	 * @exception MarshalException      - if an Marshal exception occurs that interrupts the normal operation
	 * @exception ValidationException   - if an validation exception occurs that interrupts the normal operation
	 *
	 */
	private void transBean2XML(Writer outWriter, String resultName, Object resultObj)
	throws IOException, MarshalException, ValidationException {
		
		if (resultObj == null) {
			outWriter.write("<" + resultName + "/>");
		}
		else{
			Marshaller marshaller = new Marshaller(outWriter);
			marshaller.setMarshalAsDocument(false);
			marshaller.setRootElement(resultName);
			marshaller.setSuppressXSIType(true);
			marshaller.setValidation(false);
			
			//  this.printObj(resultName,resultObj);
			marshaller.marshal(resultObj);
		}
		
	}// end transBean2XML()
	/*
	public void printObj(String resultName,Object resultObj){
		if(resultName.equals("Stations")){
			java.util.Vector obj = (java.util.Vector)resultObj;
			for(int i=0;i<obj.size();i++){
				com.goldsign.iccs.vo.PubFlagVO vo = ( com.goldsign.iccs.vo.PubFlagVO)obj.get(i);
				String codeText = vo.getCodeText();
				codeText = com.goldsign.javacore.util.CharUtil.IsoToGbk(codeText);
				System.out.println("codeText="+codeText);
			}
		}
	}
	*/
	
	
}// end class Action2XML
