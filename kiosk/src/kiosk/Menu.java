package kiosk;

public class Menu extends Button{

	   public int mnu;

	   
	public Menu(String txt, int mnu, int btn) {
	      super(txt, btn);
	      this.mnu = mnu;
	   }

	   
	   @Override
	   public String toString() {
	      return "mnu:" +txt+":"+ mnu+"=>" +btn;
	   }


	}
	


