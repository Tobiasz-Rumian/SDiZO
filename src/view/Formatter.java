package view;

public class Formatter {

   public static String toProgress(Integer currentValue, Integer endValue) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("[");
      Integer percent = (int) ((currentValue * 100.0f) / endValue);
      for (int i = 0; i <= percent; i++) {
         stringBuilder.append("=");
      }
      for (int i = 0; i <= 100 - percent; i++) {
         stringBuilder.append(" ");
      }
      stringBuilder.append("]");
      stringBuilder.append(" ").append(percent).append("%");
      return stringBuilder.toString();
   }

   public static String toTitle(String title) {
      return "===========" + title.toUpperCase() + "===========\n";
   }

}
