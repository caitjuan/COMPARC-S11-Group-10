package Controller;

import Model.*;
import java.util.*;

public class Controller {
    Scanner sc = new Scanner(System.in);
    ArrayList<Code> code = new ArrayList<>();
    
    public void main(String[] args) {
        getInput();
    }
    
    public void getInput(){
        String line;
        String error =" ";
        Code c;
        ArrayList<String> inst = new ArrayList<String>();
        
        System.out.println("Enter your code (press '?' when done):");
        
        do{ 
            do{
                line = sc.next();
                if(!line.equals('?')){
                    inst.add(line);
                }
            }while(!sc.next().equals('?'));
            error = errorCheck(inst);
            if(!error.contentEquals(" ")) { //if there's an error
                System.out.println(error); //will print the error
                inst.clear(); //reset arraylist
            }
        }while(!error.contentEquals(" "));
        
        for(int i = 0; i < inst.size(); i++){
            if(inst.get(i).startsWith(";"))
                c = new Code(inst.get(i), null, getOpcode(inst.get(i))); //initialize model
            else
                c = new Code(inst.get(i), Integer.toHexString(4096 + (i * 4)), getOpcode(inst.get(i))); //initialize model
            code.add(c);
        }
    }
    
    private String errorCheck(ArrayList<String> code){
        boolean isError = false;
        int size = code.size();
        
        for(int i = 0; i < size; i++){
            if (code.get(i).startsWith("LD") || code.get(i).startsWith("SD")){ /*LD rt, offset(base)*/ /*SD rt, offset(base)*/
            
                String jString;
                String rt = code.get(i).substring(2, 5);
                String base  = code.get(i).substring(12);

                int j = 0;
                int exit1, exit2;
                boolean checking = false;

                exit1 = exit2 = 0;

                /*rt part*/
                while(j < 32 && exit1 == 0) {
                        jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                        checking = rt.contains(jString);

                        if(checking)
                            exit1 = 1;
                }

                if(!checking) {
                    return "Syntax Error: rt should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*base part*/
                while(j < 32 && exit2 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = base.contains(jString);

                    if(checking)
                        exit2 = 1;
                }

                if(!checking){
                    return "Syntax Error: base should be from R0 to R31";
                }
            }
            //        else if (line.startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R1, R0, #0002*/
            //        else if (line.startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
            //        else if (line.startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
            //        else if (line.startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
            //        else if (line.startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
            //        else if (line.startsWith("BC"))			/*BC offset. BC L2*/
            else if(code.get(i).startsWith("BLTZ")) {
                String offset = code.get(i).substring(6);
                boolean exist = false;
                
                for(int j = 0; j < code.size(); j++){
                    if(code.get(j).startsWith(offset))
                        exist = true;
                }
                
                if(!exist){
                    return "Syntax Error! The offset does not exist";
                }
            }
            
            else if(code.get(i).startsWith("BC")) {
                String offset = code.get(i).substring(6);
                boolean exist = false;
                
                for(int j = 0; j < code.size(); j++){
                    if(code.get(j).startsWith(offset))
                        exist = true;
                }
                
                if(!exist){
                    return "Syntax Error! The offset does not exist";
                }
            }

            /*How to check LD/SD offset, immediate, branch offset*/
        }
        
        return " "; //no error
    }
    
    private String getOpcode(String code){
        
    }
    
}
