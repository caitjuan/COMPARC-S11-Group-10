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
                c = new Code(inst.get(i), null, null); //initialize model
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

            //        else if (line.startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R1, R0, #0002*/
            //        else if (line.startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
            //        else if (line.startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
            //        else if (line.startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
            //        else if (line.startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
            //        else if (line.startsWith("BC"))			/*BC offset. BC L2*/
            
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

    
    private String getOpcode(String code){
        /*  FORMAT 1:   opcode  offset 
            FORMAT 2:   opcode  rs  	rt      imm
            FORMAT 3:   opcode  base  	rt/ft  	offset
            FORMAT 4:	opcode	rs      sat     offset
            FORMAT 5:	opcode	rs      rt      rd       sat    func	
        */
        String opcodeTable[][] = {
            /* format	instruc.    opcode      sat      func */
            {"format1", "BC",       "110010",	"",      ""},
            {"format2", "DADDIU",   "011001",	"",      ""},
            {"format2", "XORI",     "001110",	"",      ""},
            {"format3", "LD",       "110111",	"",      ""},
            {"format3", "SD",       "111111",	"",      ""},
            {"format4", "BLTZ",     "000001",	"00000", ""},
            {"format5", "DADDU",    "011001",	"00000", "101101"},
            {"format5", "SLT",       "000000",	"00000", "101010"},
        };
    
        int ctr;
        
        String 	IR31_26,
                IR25_21,
                IR20_16,
                IR15_11,
                IR10_6,
                IR5_0;
        
        for (ctr = 0; ctr < ; ctr++ ) {
            if (code.contains(opcodeTable[ctr][1])) {
		/* Initialize IR31 to IR 26 */
                IR31_26 = opcodeTable[ctr][2];
                
                if ( opcodeTable[ctr][0].equals("format1") ) {

                } else if ( opcodeTable[ctr][0].equals("format2") ) {

                } else if ( opcodeTable[ctr][0].equals("format3") ) {

                } else if ( opcodeTable[ctr][0].equals("format4") ) {
                    IR20_16 = opcodeTable[ctr][3];
                } else if ( opcodeTable[ctr][0].equals("format5") ) {
                    IR10_6 = opcodeTable[ctr][3];
                    IR5_0 = opcodeTable[ctr][4];
                } 
				
                break;
            }
        }
    
}
