package kiosk;

public class Button extends Text{         
	   
	   public int btn;
	      public Button(String txt, int btn) {
	      super(txt);
	      this.btn = btn;      
	   }
        @Override
	      public String toString() {
	         return "btn:"+txt + btn;
	               
	      }
	      
	   }