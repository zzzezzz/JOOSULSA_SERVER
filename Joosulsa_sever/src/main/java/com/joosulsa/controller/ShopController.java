package com.joosulsa.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joosulsa.entity.Tb_Product;
import com.joosulsa.repository.ShopRepository;

@Controller
@Transactional
public class ShopController {
   @Autowired
   private ShopRepository shopRepo;
   
   // 상품 정보 가져오기
   @PostMapping("/shop")
   @ResponseBody
   @Transactional
   public List<Tb_Product> product(){
      
        List<Tb_Product> result =shopRepo.findAll();
         System.out.println("상품 정보 "+result.size());
         
//         String path = "C://Users/User/Desktop/KakaoTalk_20230810_085548002.jpg";
         
         System.out.println( "경로확인" + result.get(0).getProdImg());
       
         try {
         for(int i=0; i<result.size(); i++) {
            String base64_img = null;
            // 경로를 이용하여 이미지 가져오기
            byte[] imageBytes = Files.readAllBytes(Paths.get(result.get(i).getProdImg()));
//            byte[] imageBytes = Files.readAllBytes(Paths.get(path));
            System.out.println("이미지"+imageBytes);
            // Base64로 인코딩
               base64_img = Base64.getEncoder().encodeToString(imageBytes);   
               System.out.println("인코딩 작업"+base64_img.length());
               // 인코딩한 이미지 다시 넣어주기.
               result.get(i).setProdImg(base64_img);              
         }
      } catch (Exception e) {
         System.out.println("너오류");
         
         
      }
         //C:Users/User/Desktop/KakaoTalk_20230810_085548002.jpg
         
         return result;
   }
   
   

}