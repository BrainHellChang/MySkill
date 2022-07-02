package kiosk;

public class Program {

   public static void main(String[] args) {
      while(true) {


         Screen sc = new Screen();
         sc.showtit();    //tit 공차
         sc.showbtn();    //btn 커피 음료 빵


         NewScreen ns = new NewScreen();
         ns.mat();       //메뉴판
         ns.receipt();    //주문내역
         ns.Buy();       // 결제 or주문취소


      }

   }   
}  