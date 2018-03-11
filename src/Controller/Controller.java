package Controller;

import Model.*;
import java.util.*;

public class Controller {
    Scanner sc = new Scanner(System.in);
    ArrayList<String> inst = new ArrayList<String>();
    
    public void main(String[] args) {
        getInput();
    }
    
    public void getInput(){
        String code;
        String error =" ";
        System.out.println("Enter your code (press '?' when done):");
        do{ 
            do{
                code = sc.next();
                if(!code.equals('?')){
                    inst.add(code);
                }
            }while(!sc.next().equals('?'));
            error = errorCheck(inst);
        }while(error.contentEquals(" "));
    }
    
//    private String errorCheck(ArrayList<String> code){
//        boolean isError = false;
//        int size = code.size();
//        
//        for(int i = 0; i < size; i++){
//            if (code.get(i).startsWith("LD") || code.get(i).startsWith("SD")){ /*LD rt, offset(base)*/ /*SD rt, offset(base)*/
//            
//            String jString;
//            String rt = /*DONT KNOW. ASK CAIT/JAI HOW TO GET JUST THE RT PART. R0-R31*/
//            String base  = ^
//
//            int j = 0;
//            int exit1 = exit2 = 0;
//
//            /*rt part*/
//            while(j < 32 && exit1 == 0)
//            {
//                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
//                    checking = rt.endsWith(jString);
//
//                    if (checking)
//                            exit1 = 1;
//            }
//
//            if (checking)
//            {
//                    j = 0;
//                    /*base part*/
//                    while(j < 32 && exit2 == 0)
//                    {
//                            jString = Integer.toString(j);		/*Only number. No "R" of R1*/
//                            checking = base.endsWith(jString);
//
//                            if (checking)
//                                    exit2 = 1;
//
//                    } /*No Code for checking offset yet. Offset is 1000-1FFF? so I have to make sure its only between 1000-1FFF?*/
//            }
//            else
//            {
//                    System.out.print("Syntax Error");
//            }
//        }
//        else if (line.startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R1, R0, #0002*/
//        else if (line.startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
//        else if (line.startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
//        else if (line.startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
//        else if (line.startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
//        else if (line.startsWith("BC"))			/*BC offset. BC L2*/
//
//        /*How to check LD/SD offset, immediate, branch offset*/
//            
    }
}