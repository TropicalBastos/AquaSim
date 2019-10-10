package com.tropicalbastos.boids.settings;

public final class Platform {
   private static String OS = null;
   public static String getOsName() {
      if(OS == null) { 
         OS = System.getProperty("os.name"); 
      }
      return OS;
   }
   
   public static boolean isMac() {
		return (getOsName().toLowerCase().indexOf("mac") >= 0);
	}
}