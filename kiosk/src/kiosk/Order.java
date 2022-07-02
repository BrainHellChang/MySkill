package kiosk;

public class Order extends Menu {

	public int num1;
	public int num2;
	public int num3;
	

	public Order(String txt, int btn, int mnu, int num1, int num2, int num3) {
		super(txt, btn, mnu);
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
	}

    @Override
	public String toString() {
		 return "Order:"+txt+":"+mnu+":"+num1+":"+num2+ ":" +num3;
	}
	
}
	
	
	
	
	


	
	
	

