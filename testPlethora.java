public class testPlethora{
   public static void main(String[] args){
      long dumb =0;
      long smart=0;
      int dif =0;
      int difSum = 0;
      int[][] temp = new int[9][9];
      for(int i =0; i < 100; i++){
         dif =((int) (Math.random()*3));
         difSum+=dif;
         temp = SudokuGame.generateBoard(dif);
         dumb += (DumbAi.test(temp));
         smart += (SmartAi.test(temp));
         System.out.println(i);
      }
      dumb = dumb/100;
      smart = smart/100;
      difSum = difSum/100;
      System.out.println("Average Diffuculty: " + difSum);
      System.out.println("Average time for Smart Ai: " + smart + " milliseconds");
      System.out.println("Average time for Dumb Ai: " + dumb + " milliseconds");
   }
}
