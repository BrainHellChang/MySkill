package kiosk;

import java.util.Scanner;

public class NewScreen extends Screen{
	   static int number = 0;
	   static int num3;
	   Scanner sc = new Scanner(System.in);
	   Text   txt1 = new Text("Milk Tea");       Title tit1 = new Title("잠시만 기다려주세요.");
	   Text   txt2 = new Text("Drink");      Title tit2 = new Title("맛있는 음식이 주문되었습니다.");
	   Text   txt3 = new Text("Bread");      Title txt4 = new Title("TOPPING을 추가하겠습니까?");
	   Bar      bar = new Bar();
	   Title tit = new Title("결제중");

	   public NewScreen() {                    //메뉴판,(이름,가격)(토핑이름,가격)
	      //         <MENU>                              <TOPPING'S MENU>
	      Array.add("mnu:블랙밀크티:1500 => 0");         ArrayTopping.add("mnu: 펄         =>500 =>   0");
	      Array.add("mnu:얼그레이밀크티:3500 => 1");         ArrayTopping.add("mnu: 코코넛    =>500=>    1");
	      Array.add("mnu:자스민 그린밀크티  : 3500 => 2");         ArrayTopping.add("mnu: 알로에    =>500=>    2");
	      Array.add("mnu:타로밀크티: 2500 => 3");         ArrayTopping.add("mnu: 화이트넛 =>500=>    3");
	      Array.add("mnu:제주 그린밀크티: 5500 => 4");         ArrayTopping.add("mnu: 추가 안함         =>    4");
	                                          //      <TOPPING'S Price>
	      Array.add("mnu:자몽쥬스   : 10000 => 5");         ArrayToppingPrice.add(500);
	      Array.add("mnu:오렌지쥬스: 9000  => 6");         ArrayToppingPrice.add(500);
	      Array.add("mnu:포도 쥬스  : 10000 => 7");         ArrayToppingPrice.add(500);
	      Array.add("mnu:토마토쥬스: 7000  => 8");         ArrayToppingPrice.add(500);
	      Array.add("mnu:딸기 쥬스  : 15000 => 9");         ArrayToppingPrice.add(0);

	      Array.add("mnu:생크림케익: 30000 => 10");
	      Array.add("mnu:초코케익   : 35000 => 11");
	      Array.add("mnu:딸기케익   : 37000 => 12");
	      Array.add("mnu:샌드위치   : 25000 => 13");
	      Array.add("mnu:브 런 치    : 55000 => 14");
	   //  <MENU NAME>                   <MENU Price>
	      ArrayName.add("블랙밀크티");      ArrayPrice.add(1500);
	      ArrayName.add("얼그레이밀크티");      ArrayPrice.add(3500);
	      ArrayName.add("자스민 그린밀크티");      ArrayPrice.add(3500);
	      ArrayName.add("타로밀크티");      ArrayPrice.add(2500);
	      ArrayName.add("제주 그린밀크티");      ArrayPrice.add(5500);

	      ArrayName.add("자몽쥬스");         ArrayPrice.add(10000);
	      ArrayName.add("오렌지쥬스");      ArrayPrice.add(9000);
	      ArrayName.add("포도쥬스");         ArrayPrice.add(10000);
	      ArrayName.add("토마토쥬스");      ArrayPrice.add(7000);
	      ArrayName.add("딸기 쥬스");      ArrayPrice.add(15000);

	      ArrayName.add("생크림케익");      ArrayPrice.add(30000);
	      ArrayName.add("초코케익");         ArrayPrice.add(35000);
	      ArrayName.add("딸기케익");         ArrayPrice.add(37000);
	      ArrayName.add("샌드위치");         ArrayPrice.add(25000);
	      ArrayName.add("브 런 치");         ArrayPrice.add(55000);

	   }

	   public void mat() {            //카테고리 ---->메뉴판으로 넘어갑니다.
	      number = sc.nextInt();
	      if(number == 10)
	      {   
	         System.out.format(txt1.toString());System.out.println();
	         num3=0;
	         for(int i =0;i<5;i++) {

	         System.out.format("%s\n",Array.get(i));

	         }
	         showbar();

	      }
	      else if(number ==20) {
	         System.out.format(txt2.toString());System.out.println();
	         num3=4; //Topping's price =0
	         for(int i=5;i<10;i++) {

	         System.out.format("%s\n",Array.get(i));
	         }
	         showbar();
	      }
	      else if(number == 30){
	         System.out.format(txt3.toString());System.out.println();
	         num3=4;
	         for(int i=10;i<15;i++) {

	         System.out.format("%s\n",Array.get(i));

	         }
	         showbar();
	      }
	      else {
	         System.out.println("잘못 입력하셨습니다.");
	         showbtn();
	         showbar();
	         mat();   
	      }

	   }

	   public void receipt() {         //메뉴판에서 수량과 개수를 입력후 결과값을 도출합니다.
	      Scanner sc = new Scanner(System.in);
	      int sum =0;


	      while(true) {
	          System.out.println("메뉴 번호와 수량 입력하세요");
	          showbar();
	          int num1 = sc.nextInt();      //메뉴(0~14)버튼
	          int num2 = sc.nextInt();      //수량

	             if(number == 10 && (0<=num1)&&(num1<5)) {          //Milk Tea선택시, Topping 추가하시겠습니까?
	                System.out.format(txt4.toString());System.out.println(""); 
	                for(int i =0;i<5;i++){
	               System.out.format("%s\n",ArrayTopping.get(i));  
	                }
	                showbar();
	                 num3 = sc.nextInt();

	             }

	             if(number == 10 && !((0 <= num1) && (num1 < 5))) {
	             System.out.println("메뉴번호를 잘못 입력하셨습니다.");
	             continue;
	                }
	             else if(number == 20 && !((5 <= num1) && (num1 < 10))) {
	             System.out.println("메뉴번호를 잘못 입력하셨습니다.");
	             continue;
	                }
	             else if (number == 30 && !((10 <= num1) && (num1 < 15))) {
	             System.out.println("메뉴번호를 잘못 입력하셨습니다.");
	             continue;
	                }


	          int price = (num2 *(int)ArrayPrice.get(num1))+((int)ArrayToppingPrice.get(num3)*(int)num2);
	          sum += price;
	          Integer.toString(num1);
	          Integer.toString(num2);
	          ArrayOrder.add("ord:"+(String)ArrayName.get(num1)+"의 수량은 "+ num2 + "개 이고 가격은 "+ price + "원 입니다.\n");

	          System.out.println("다른메뉴선택 '1',주문완료'2' 입력하세요. ");
	          showbar();
	          int word1 = sc.nextInt();

	          if(word1 == 1) {
	             showbtn();
	             mat();                    //(1)선택시, 다시 카테고리로 돌아갑니다.
	          }
	          else if(word1 == 2) 
	             break;                  //주문완료하겠습니까?

	      }    
	          for(int i=0;i<ArrayOrder.size();i++) {       //주문내역
	            System.out.format("%s",ArrayOrder.get(i));
	      }
	      Value  val = new Value("합계", sum);
	      System.out.format(val.toString()); System.out.println("");

	}

	   public void Buy()  {                   //결제를 완료할지,취소할지 결정합니다.
	      showbar();

	      System.out.println("결제를 원하면 아무버튼 클릭하세요!");
	      System.out.println("주문취소 : 99");
	      int cancel = sc.nextInt();
	      if(cancel==99){
	         System.out.println("처음화면으로 돌아갑니다!");
	         }
	      else {
	         System.out.format(tit.toString());System.out.println("");
	     	try {	

				Thread.sleep(2000); // 결제 딜레이

			} catch (InterruptedException e) {

	      }
	     	 System.out.format(tit1.toString());System.out.println("");
	         System.out.format(tit2.toString());System.out.println("");

	   }

	   }
	}