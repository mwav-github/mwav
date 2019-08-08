package net.mwav.common.module;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 *@class name : MailLib.java
 *@description : It's MailLib
 *				 This lib has the ability to send or verify email. 
 *				 MailLib made Singleton Design Patterm. 
 *				 To use MailLib, Create getInstence() method. 
 *
 *				 Default Properties Settings
 *@author : (정) 공태현
            (부)
 *@since : 2019. 7. 17.
 *@version : v1.0
 *@see
 * @history :
   ----------------------------------------
   * Modification Information(개정이력)
   ----------------------------------------
           수정일                  수정자                         수정내용
   --------    --------    ----------------
   2019. 7. 17.     John    
 */
public class MailLib {

	private Properties property;
	private Session session;
	private JavaMailSenderImpl javaMailSender;
	private VelocityConfigurer velocityConfig;
	
	private String fromAddress;
	private String fromAddressName = "thKong";
	private String host;
	private String post;
	
	private final String encoding = "UTF-8";
	private final String contentEncoding = "text/html; charset=UTF-8";
	
	private MailLib() {
		this.javaMailSender = new JavaMailSenderImpl();
		this.velocityConfig = new VelocityConfigurer();
		
		velocityConfig.setResourceLoaderPath("/Templates");
		
		//------------------------------------------------------
		try {
			property = new Properties();
			
		    // 기본 경로 : /mwav/src/main/resources/googleProperties/googleAccout.properties
		    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("googleProperties/googleAccout.properties");
		    
		    //가져온 property를 읽고 load
		    property.load(inputStream);
		    
		    //전역변수로 등록
		    fromAddress = property.getProperty("mail.smtp.username");
		    fromAddressName = property.getProperty("mail.smtp.nickname");
		    host = property.getProperty("mail.smtp.host");
		    post = property.getProperty("mail.smtp.port");
		    
		    
		    //람다가 안됨; https://stackoverflow.com/questions/26875226/using-a-lambda-expression-in-session
		    //구글 인증 객체 생성
		    session = Session.getDefaultInstance(property, new Authenticator(){
		    	public PasswordAuthentication getPasswordAuthentication() {
		    		return new PasswordAuthentication(property.getProperty("mail.smtp.username"), property.getProperty("mail.smtp.password"));
		    	}
		    });
		    
		    javaMailSender.setSession(session);
		    javaMailSender.setUsername(property.getProperty("mail.smtp.username"));
		    javaMailSender.setPassword(property.getProperty("mail.smtp.password"));
		    javaMailSender.setJavaMailProperties(property);
		    
			System.out.println("Success, First create MailLib getInstence! And Fist Setting");
			
		} catch (IOException e) {
			System.out.println("Error Fail, First Setting. Please Check your googleProperties/googleAccout.properties !");
			e.printStackTrace();
		}
		
		//------------------------------------------------------		
		
	}
	
	private static class MailLibSingleton{
		private static final MailLib instance = new MailLib();
	}
	
	public static MailLib getInstance() {
		return MailLibSingleton.instance;
//		return new MailLib();
	}
	
	/** 
	 * @method name : createMimeMessage
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : org.springframework.mail.javamail.JavaMailSenderImpl.createMimeMessage()
	 * @description : Create an MimeMessage instance with JavaMailSenderImpl
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param :
	 * @return : MimeMessage
	 * @throws : 
	 <pre>
	 * {@code : 
	 * createMimeMessage();
	 * } 
	 </pre>
	*/
	private MimeMessage createMimeMessage(){
		return javaMailSender.createMimeMessage();
	}
	
	/** 
	 * @method name : getVeloTemplate
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : org.springframework.ui.velocity.VelocityEngineUtils.mergeTemplateIntoString()
	 *        org.springframework.ui.velocity.VelocityEngineUtils.createVelocityEngine()
	 * @description : HTML and *.vm file rendering for TemplateEngine
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param : map - key, value
	 * 			templatePath - HTML and *.vm file Path
	 * @return : String
	 * @throws : 
	 <pre>
	 * {@code : 예제 코드 작성
	 * DateUtil.addYearMonthDay("19810828", 0, 0, 19)  = "19810916"
	 * } 
	 </pre>
	*/
	private String getVeloTemplate(Map map, String templatePath) throws VelocityException, IOException{
		return VelocityEngineUtils.mergeTemplateIntoString(velocityConfig.createVelocityEngine(), templatePath, encoding, map);
	}
	
	/** 
	 * @method name : send
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : org.springframework.mail.javamail.MimeMessageHelper.getMimeMessage()
	 *        org.springframework.mail.javamail.JavaMailSenderImpl.send()
	 * @description : finally send email total. 
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param : msg - MimeMessageHelper
	 * @return :
	 * @throws : MailException
	 <pre>
	 * {@code : 예제 코드 작성
	 * sendEmail(msg);
	 * } 
	 </pre>
	*/
	private void send(MimeMessageHelper msg) throws MailException{
		javaMailSender.send(msg.getMimeMessage());
	}
	
	/** 
	 * @method name : emailRequiredSet
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : org.springframework.mail.javamail.JavaMailSenderImpl.*
	 * @description : require minimal setting for send.
	 * 				  overloading emailRequiredSet method
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param : msg - MimeMessageHelper
	 *          toAddress - Recipient Email Address
	 *          subject - email subject
	 *          contents - html format code or String
	 *          htmlYN - true or false
	 * @return :
	 * @throws : 
	 <pre>
	 * {@code : 
	 * emailRequiredSet(msg, "GildongHong@gmail.com", "This is Testing email", "<h1>Testing email.</h1>", true);
	 * } 
	 </pre>
	*/
	private void emailRequiredSet(MimeMessageHelper msg, String toAddress
			, String subject, String contents, boolean htmlYN) throws UnsupportedEncodingException, MessagingException{
		msg.setFrom(fromAddress, fromAddressName);
		msg.setSubject(subject);
		msg.setTo(toAddress);
		msg.setText(contents, htmlYN);
	}

	//이메일 발신을 위한 최소한의 셋팅, Template을 렌더링 후 반환값으로 메일에 적용 
	//모든 발신 메소드들은 이 메소드를 필수로 실행해야한다. emailRequiredSet OVERLOADING
	/** 
	 * @method name : emailRequiredSet
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : org.springframework.mail.javamail.JavaMailSenderImpl.*
	 * @description : require minimal setting for send.
	 * 				  rendering templatePath
	 * 				  overloading emailRequiredSet method.
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param : msg - MimeMessageHelper
	 *          toAddress - Recipient Email Address
	 *          subject - email subject
	 *          templatePath - html or *.vm file Path
	 *          map - template Variable
	 * @return :
	 * @throws : 
	 <pre>
	 * {@code : 
	 * emailRequiredSet(msg, "GildongHong@gmail.com", "This is Testing email", "/GeneralMail/PWSeekEmail.vm", map);
	 * } 
	 </pre>
	*/
	private void emailRequiredSet(MimeMessageHelper msg, String toAddress, 
			String subject, String templatePath, Map map) throws MessagingException, VelocityException, IOException {
		msg.setFrom(fromAddress, fromAddressName);
		msg.setSubject(subject);
		msg.setTo(toAddress);
		msg.setText(getVeloTemplate(map, templatePath), true);
	}
	
	/** 
	 * @method name : sendEmail
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 8. 2.
	 * @version : v1.0
	 * @see : java.util.Map
	 *        org.springframework.mail.javamail.MimeMessageHelper
	 * @description : Set the email to send.
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
		  수정일 	          수정자    		        수정내용
	   --------    --------    ----------------
	   2019. 8. 2.     John     
	 * @param : toAddress - Recipient Email Address
	 *          subject - email subject
	 *          templatePath - html or *.vm file Path
	 *          map - template Variable
	 * @return : boolean
	 * @throws MessagingException 
	 * @throws IOException 
	 * @throws VelocityException 
	 * @throws : 
	 <pre>
	 * {@code : 예제 코드 작성
	 * Map<String, String> map = new HashMap<String, String>():
	 * contents.put("TempPw", "encryptPassword");
	 * sendEmail("Member@naver.com", "Mwav 홈페이지 Member님 [임시비밀번호]발급 메일입니다.", "/GeneralMail/PWSeekEmail.vm", map)
	 * } 
	 </pre>
	*/
	public boolean sendEmail(String toAddress, String subject, String templatePath, Map<String, String> map) throws MessagingException, VelocityException, IOException{
		boolean check = false;
		
		MimeMessageHelper msg = new MimeMessageHelper(createMimeMessage(), true, encoding);
		
		//email require setting
		emailRequiredSet(msg, toAddress, subject, templatePath, map);
		
		//send emails
		send(msg);
	
		//if return check a false, error send a email.
		check = true;
		
		return check;
	}
	
	/** 
	 * @method name : sendEmailOrigin
	 * @author : (정) 공태현
	             (부)
	 * @since  : 2019. 7. 22.
	 * @version : v1.0
	 * @see : MailLib.createMimeMessage()
	 *        
	 * @description : Send mail to "jusung.kim@mwav.net".
	 * 				  Do not have Template. Original Text Message 
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	   수정일     수정자        수정내용
	   --------    --------    ----------------
	   2019. 7. 22.     gong tae hyun     
	 * @param : title - mail subject
	 *          toAddress - Recipient Email Address
	 * @return : boolean
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 * @throws : 
	 <pre>
	 * {@code : 
	 * sendEmailOrigin("문의가 접수되었습니다.", "jusung.kim@mwav.net", "<h1>Success send email</h1>");
	 * } 
	 </pre>
	*/
	public boolean sendEmailOrigin(String title, String toAddress, String contents) throws MessagingException, UnsupportedEncodingException{
		
		boolean check = false;
		
		MimeMessageHelper msg = new MimeMessageHelper(createMimeMessage(), true, encoding);
		
		//"jusung.kim@mwav.net"
		//email require setting
		emailRequiredSet(msg, toAddress, title, contents, true);
		
		//send emails
		send(msg);
		
		//if return check a false, error send a email.
		check = true;
		
		return check;
	}
	
	
}

