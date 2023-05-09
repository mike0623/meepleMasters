package tw.com.eeit162.meepleMasters.jack.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {

	private String userMail = "blingw1031@gmail.com";

	private String password = "";

	private String memberMail = "meeplemasters@gmail.com";

	private String title = "Meeple Masters會員管理中心";

	private String content = "您好! 歡迎您加入Meeple Masters，<br/>" + 
							 "這封信是由Meeple Masters的會員註冊系統所寄出，<br/>" + 
							 "請點選下面的網址來進行註冊的下一個步驟：<br/>" + 
							 "https://www.gamer.com.tw/" + "<br/>" + "※ 如果您無法連結信中網址，請讓我們知道。<br/>"+ 
							 "<br/>" + 
							 "Meeple Masters團隊 敬上<br/>";

	public void sendMail() {

		Properties prop = new Properties();

		// 這定連線方式為smtp
		prop.setProperty("mail.transport.protocol", "smtp");
		// host name : smtp.gmail.com (gmail主機名稱)
		prop.setProperty("mail.host", "smtp.gmail.com");
		// host port : 465
		prop.put("mail.smtp.port", "465");
		// 寄件者帳號需要驗證：是
		prop.put("mail.smtp.auth", "true");
		// 需要安全資料傳輸層 (SSL)：是 請JavaMail實作SSL連線
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// 設定socketFactory port
		prop.put("mail.smtp.socketFactory.port", "465");
		// 顯示連線資訊
		prop.put("mail.debug", "true");

		// 帳號驗證
		// Session Javamail api 將上述設定丟給Session(javax.mail)
		Session session = Session.getDefaultInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userMail, password);
			}
		});

		// Message 放入基本資料
		MimeMessage message = new MimeMessage(session);
		try {
			// 寄件者
			message.setSender(new InternetAddress(userMail));
			// 收件者
			message.setRecipient(RecipientType.TO, new InternetAddress(memberMail));
			// 標題
			message.setSubject(title);
			// 內容/格式
			message.setContent(content, "text/html;charset=UTF-8");

			// Transport將Message傳出去
			Transport transport = session.getTransport();
			transport.send(message);

			transport.close();

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
