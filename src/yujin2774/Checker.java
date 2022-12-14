package yujin2774;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Checker
{

   private static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
   private static final String divisor = "100000100110000010001110110110111";
   private static final Random rand = new Random();
   
   public static void main(String[] args) throws IOException
   {
      System.out.println("* Input Data: ");
      String data = buffer.readLine();
      int data_len = data.getBytes().length;
      System.out.println("* Data length: " + data_len);
      String dataword = ToBin(data);
      System.out.println("* Data to Binary: " + dataword);
      String paritybit = GetParityBit(dataword);
      System.out.println("* Paritybit: " + paritybit);
      
      
      String codeword = dataword + paritybit;
      System.out.println("* Codeword: " + codeword);
      
      String err_codeword = CreateErr(codeword);
      System.out.println("* Error Codeword: " + err_codeword);
      
      
      String status = GetBitStatus(codeword);
      String err_status = GetBitStatus(err_codeword);
      System.out.println("* Codeword status: " + status);
      System.out.println("* Error codeword status: " + err_status);
   }
   private static String CreateErr(String codeword)
   {
      int rand_index = rand.nextInt(codeword.length());
      String err_index = codeword.charAt(rand_index) == '1' ? "0" : "1";
      return codeword.substring(0, rand_index) + err_index + codeword.substring(rand_index+1, codeword.length());
   }
   public static String ToBin(String s)
   {
        byte[] bytes = s.getBytes();
        StringBuilder strb = new StringBuilder();
        for (byte x : bytes)
        {
            GetBit(strb, x);
        }
        return strb.toString().trim();
   }   
   private static String GetBitStatus(String codeword)
   {
      boolean[] codeword_bin = new boolean[codeword.length()];
      for(int i = 0; i < codeword_bin.length; i ++)
      {
          codeword_bin[i] = (codeword.charAt(i) == '1' ? true : false);
      }
      int index = 0;
      while(index < codeword_bin.length-divisor.length()+1)
      {
         boolean lsb = codeword_bin[index];
         for(int i = index; i < index+divisor.length(); i ++)
         {
            if(lsb)
            {
               codeword_bin[i] ^= (divisor.charAt(i-index) == '1' ? true : false);
            }
            else
            {
               codeword_bin[i] ^= false;
            }
         }
         index++;
      }
      String status = "";
      for(int i = codeword_bin.length-divisor.length()+1; i < codeword_bin.length; i ++)
      {
          status += codeword_bin[i] ? 1 : 0;	  
      }
      return status; 
   }
   private static void GetBit(StringBuilder strb, byte x)
   {
        for (int i = 0; i < 8; i++)
        {
            strb.append((x & 128) == 0 ? 0 : 1);
            x <<= 1;
        }
   }
   private static String GetParityBit(String dataword)
   {
      boolean[] dataword_bin = new boolean[dataword.length()+divisor.length()-1];
      for(int i = 0; i < dataword.length(); i ++) 
      {
          dataword_bin[i] = dataword.charAt(i) == '1' ? true : false;
      }
      int index = 0;
      while(index < dataword.length())
      {
         boolean lsb = dataword_bin[index];
         for(int i = index; i < index+divisor.length(); i ++)
         {
            if(lsb)
            {
               dataword_bin[i] ^= (divisor.charAt(i-index) == '1' ? true : false);
            }
            else
            {
               dataword_bin[i] ^= false;
            }
         }
         index ++;
      }  
      String paritybit = "";
      for(int i = dataword_bin.length-divisor.length()+1; i < dataword_bin.length; i ++)
      {
          paritybit += dataword_bin[i] ? 1 : 0;
      }
      return paritybit;
   }
}      
   