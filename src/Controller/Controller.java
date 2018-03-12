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
            if (code.get(i).startsWith("LD") || code.get(i).startsWith("SD")) /*LD rt, offset(base)*/ /*SD rt, offset(base)*/
            {
                String jString;
                String rt = code.get(i).substring(3, 5);
                String offset = code.get(i).substring(7, 11);
                String base  = code.get(i).substring(12);

                int j = 0;
                int exit1, exit2, exit3;
                boolean checking = false;

                exit1 = exit2 = exit3 = 0;

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

                /*offset part*/
                /*Insert offset part. Dont forget exit2*/

                if(!checking){
                    return "Syntax Error: offset does not exist";
                }

                checking = false;
                j = 0;

                /*base part*/
                while(j < 32 && exit3 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = base.contains(jString);

                    if(checking)
                        exit3 = 1;
                }

                if(!checking){
                    return "Syntax Error: base should be from R0 to R31";
                }
            }
<<<<<<< HEAD
            //        else if (line.startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R1, R0, #0002*/
            //        else if (line.startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
            //        else if (line.startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
            //        else if (line.startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
            //        else if (line.startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
            //        else if (line.startsWith("BC"))			/*BC offset. BC L2*/
            else if(code.get(i).startsWith("BLTZ")) {
                String offset = code.get(i).substring(9);
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
                String offset = code.get(i).substring(3);
                boolean exist = false;
                
                for(int j = 0; j < code.size(); j++){
                    if(code.get(j).startsWith(offset))
                        exist = true;
                }
                
                if(!exist){
                    return "Syntax Error! The offset does not exist";
=======
            else if (code.get(i).startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R3, R3, #0002*/
            {
                String jString;
                String rt = code.get(i).substring(7, 9);
                String rs = code.get(i).substring(11, 14);
                String imm  = code.get(i).substring(15);

                int j = 0;
                int exit1, exit2, exit3;
                boolean checking = false;

                exit1 = exit2 = exit3 = 0;

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

                /*rs part*/
                while(j < 32 && exit2 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rs.contains(jString);

                    if(checking)
                        exit2 = 1;
                }

                if(!checking){
                    return "Syntax Error: rs should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*imm part*/
                while(j < 32 && exit3 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = imm.contains(jString);

                    if(checking)
                        exit3 = 1;
                }

                if(!checking){
                    return "Syntax Error: immediate should be from R0 to R31";
                }
            }
            else if (code.get(i).startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
            {
                String jString;
                String rt = code.get(i).substring(5, 7);
                String rs = code.get(i).substring(9, 12);
                String imm  = code.get(i).substring(13);

                int j = 0;
                int exit1, exit2, exit3;
                boolean checking = false;

                exit1 = exit2 = exit3 = 0;

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

                /*rs part*/
                while(j < 32 && exit2 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rs.contains(jString);

                    if(checking)
                        exit2 = 1;
                }

                if(!checking){
                    return "Syntax Error: rs should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*imm part*/
                while(j < 32 && exit3 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = imm.contains(jString);

                    if(checking)
                        exit3 = 1;
                }

                if(!checking){
                    return "Syntax Error: immediate should be from R0 to R31";
>>>>>>> 4e7de254066b07a1d641e154a2ff2f319b6d3f83
                }
            }
            else if (code.get(i).startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
            {
                String jString;
                String rs = code.get(i).substring(5, 7);
                String offset = code.get(i).substring(9);

                int j = 0;
                int exit1, exit2;
                boolean checking = false;

                exit1 = exit2 = 0;

                /*rs part*/
                while(j < 32 && exit1 == 0) {
                        jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                        checking = rs.contains(jString);

                        if(checking)
                            exit1 = 1;
                }

                if(!checking) {
                    return "Syntax Error: rs should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*offset part*/
                for(int k = 0; k < code.size(); k++){
                    if(code.get(k).startsWith(offset))
                        checking = true;
                }

                if(!checking){
                    return "Syntax Error: offset does not exist";
                }
            }
            else if (code.get(i).startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
            {
                String jString;
                String rd = code.get(i).substring(6, 8);
                String rs = code.get(i).substring(10, 13);
                String rt  = code.get(i).substring(14);

                int j = 0;
                int exit1, exit2, exit3;
                boolean checking = false;

                exit1 = exit2 = exit3 = 0;

                /*rd part*/
                while(j < 32 && exit1 == 0) {
                        jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                        checking = rd.contains(jString);

                        if(checking)
                            exit1 = 1;
                }

                if(!checking) {
                    return "Syntax Error: rd should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*rs part*/
                while(j < 32 && exit2 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rs.contains(jString);

                    if(checking)
                        exit2 = 1;
                }

                if(!checking){
                    return "Syntax Error: rs should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*rt part*/
                while(j < 32 && exit3 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rt.contains(jString);

                    if(checking)
                        exit3 = 1;
                }

                if(!checking){
                    return "Syntax Error: rt should be from R0 to R31";
                }
            }
            else if (code.get(i).startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
            {
                String jString;
                String rd = code.get(i).substring(4, 6);
                String rs = code.get(i).substring(8, 10);
                String rt  = code.get(i).substring(12);

                int j = 0;
                int exit1, exit2, exit3;
                boolean checking = false;

                exit1 = exit2 = exit3 = 0;

                /*rd part*/
                while(j < 32 && exit1 == 0) {
                        jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                        checking = rd.contains(jString);

                        if(checking)
                            exit1 = 1;
                }

                if(!checking) {
                    return "Syntax Error: rd should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*rs part*/
                while(j < 32 && exit2 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rs.contains(jString);

                    if(checking)
                        exit2 = 1;
                }

                if(!checking){
                    return "Syntax Error: rs should be from R0 to R31";
                }

                checking = false;
                j = 0;

                /*rt part*/
                while(j < 32 && exit3 == 0) {
                    jString = Integer.toString(j);		/*Only number. No "R" of R1*/
                    checking = rt.contains(jString);

                    if(checking)
                        exit3 = 1;
                }

                if(!checking){
                    return "Syntax Error: rt should be from R0 to R31";
                }
            }
            else if (code.get(i).startsWith("BC"))			/*BC offset. BC L2*/
            {
                String jString;
                String offset = code.get(i).substring(3);

                int j = 0;
                int exit1;
                boolean checking = false;

                exit1 = 0;

                /*offset part*/
                for(int k = 0; k < code.size(); k++){
                    if(code.get(k).startsWith(offset))
                        checking = true;
                }

                if(!checking){
                    return "Syntax Error: offset does not exist";
                }
            }
            //        else if Branch Name ex. L1, L2

            /*How to check LD/SD offset, immediate, branch offset*/
        }

    return " "; //no error
    }

    
    private String getOpcode(Code code){
        
    }
    
}
