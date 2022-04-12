package com.jforce.tr.sqluygulama;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jforce.tr.dao.UrunDao;
import com.jforce.tr.model.Stok;
import com.jforce.tr.model.Urun;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT , classes = SqlUygulamaApplication.class)
public class SqlUygulamaApplicationTests {

	
	@Autowired
	private UrunDao urunDaoImpl;
	
	@Test
	public void getUruns() {
		
//		List<Urun> urunListesi =  urunDaoImpl.getUrunList();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Lütfen bir seçim yapınız.");
		System.out.println("-------------------------------------");
		String message = "1- Üzüm \n"
				+ 		 "2- Elma\n"
				+ 		 "3- Güncel stok bilgisini maille gönder";
		System.out.println(message);
		
		int islem = scanner.nextInt();
		if(islem==1) {
			//Üzüm seçilmiştir.
			System.out.print("Kaç kilo üzüm almak istersin : ");
			int kilo = scanner.nextInt();
			
			urunDaoImpl.update(1, kilo);
			Urun urun = urunDaoImpl.getUrunById(1);
			Stok stok = urunDaoImpl.getStok();

			System.out.println("Üzüm kilogram : " +kilo);
			System.out.println("Kalan Üzüm kilogram : " + urun.getMiktar());
			System.out.println("Stokta kalan kilogram : " + stok.getKilo());
			
		}else if(islem==2) {
			//Elma seçilmiştir.
			System.out.print("Kaç kilo elma almak istersin : ");
			int kilo = scanner.nextInt();
			
			urunDaoImpl.update(2, kilo);
			Urun urun = urunDaoImpl.getUrunById(2);
			Stok stok = urunDaoImpl.getStok();

			System.out.println("Elma kilogram : " +kilo);
			System.out.println("Kalan Elma kilogram : " + urun.getMiktar());
			System.out.println("Stokta kalan kilogram : " + stok.getKilo());
			
		}else if(islem==3) {
			List<Urun> urunList = urunDaoImpl.getUrunList();
			Stok stok = urunDaoImpl.getStok();
			String content = urunList.toString()+"\n"
					+ stok.toString();
			sendMail("enes.bayram@jforce.com", content);
		}
		
		
	}
	
	public void sendMail(String alici , String icerik) {
		//Mail gönderen kişinin bilgileri
				String gonderenMail = "nisantasiuniversitesi55@gmail.com";
				String gonderenPassword = "enes1212.";
				
				
					String host = "smtp.gmail.com";
					Properties prop = new Properties();
				    prop.put("mail.smtp.auth", true);
				    prop.put("mail.smtp.starttls.enable", "true");
				    prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
				    prop.put("mail.smtp.host", host);
				    prop.put("mail.smtp.port", 587);
				    prop.put("mail.smtp.ssl.trust", host);
				
				    Session  session = Session.getInstance(prop, new Authenticator() {
				    	@Override
				    	protected PasswordAuthentication getPasswordAuthentication() {
				    		return new PasswordAuthentication(gonderenMail, gonderenPassword);
				    	}
				    });
				    
				    //From : gönderen kişidir.
				    //To : Alan kişi.
				    try {
						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(gonderenMail));
						message.setRecipients(RecipientType.TO, InternetAddress.parse(alici));
						message.setSubject("Stok Güncel Durum");
						message.setText(icerik);
						
						//*******************************************************************
						//Ek göndermek için kullanılıyoruz

//						MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
////					    DataSource source = new FileDataSource(new File("D://cat.jpg"));  
//						DataSource source = new FileDataSource(new File("D://deneme.xlsx"));
//						
//					    messageBodyPart2.setDataHandler(new DataHandler(source));  
//					    messageBodyPart2.setFileName(source.getName());  
//
//					    Multipart multipart = new MimeMultipart();  
//					    multipart.addBodyPart(messageBodyPart2);
//					    message.setText("dfsdfs");
//					    message.setContent(multipart);
						//*******************************************************************
					    
					    
						Transport.send(message);
						System.out.println("Mail başarılı bir şekilde gönderilmiştir.!!");
					} catch (Exception e) {

						System.out.println("Mail gönderilirken hata oluştu :"+e.getMessage());
						
					}
		
	}

}
