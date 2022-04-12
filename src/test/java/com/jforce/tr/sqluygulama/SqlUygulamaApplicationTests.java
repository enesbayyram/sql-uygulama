package com.jforce.tr.sqluygulama;

import java.util.List;
import java.util.Scanner;

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
				+ 		 "2- Elma";
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
		}
		
		
	}

}
