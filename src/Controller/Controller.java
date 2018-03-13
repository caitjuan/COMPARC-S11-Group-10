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
                c = new Code(inst.get(i), Integer.toHexString(4096 + (i * 4)), getOpcode(inst.get(i), inst)); //initialize model
            code.add(c);
        }
    }
    
    private String errorCheck(ArrayList<String> code){
        boolean isError = false;
        int size = code.size();

        for(int i = 0; i < size; i++)
        {
            String oldStr = code.get(i);
            String delStr;

            int length = oldStr.length();
            int x = 0;
            int k = 0;
            int exit = 0;


            while (k < length && exit == 0)
            {
                if (!(oldStr.charAt(k)== ':'))
                    x++;
                else
                    exit = 1;


                k++;
            }


            if (exit == 1)
            {
                delStr = oldStr.substring(0, k);
                oldStr = oldStr.replace(delStr, "");
            }
            
            if (oldStr.startsWith("LD")) /*LD rt, offset(base)*/ 
            {
                String jString;
                String rt = oldStr.substring(3, 5);
                String offset = oldStr.substring(7, 11);
                String base  = oldStr.substring(12);

                int j = 1;
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
                String hex = "1000";
                int value;

                /*offset part*/
                while (!(hex.equals("1FFF")) && exit2 == 0)
                {
                    checking = offset.contains(hex);

                    value = Integer.parseInt(hex, 16);	
                    value++;
                    hex = Integer.toHexString(value);


                    if(checking)
                        exit2 = 1;

                }

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
            
            else if (oldStr.startsWith("SD")) /*SD rt, offset(base)*/
            {
                String jString;
                String rt = oldStr.substring(3, 5);
                String offset = oldStr.substring(7, 11);
                String base  = oldStr.substring(12);

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
                String hex = "1000";
                int value;

                /*offset part*/
                while (!(hex.equals("1FFF")) && exit2 == 0)
                {
                    checking = offset.contains(hex);

                    value = Integer.parseInt(hex, 16);	
                    value++;
                    hex = Integer.toHexString(value);


                    if(checking)
                        exit2 = 1;

                }

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
          
            else if (oldStr.startsWith("DADDIU"))		/*DADDIU rt, rs, immediate. DADDIU R3, R3, #0002*/
            {
                String jString;
                String rt = oldStr.substring(7, 9);
                String rs = oldStr.substring(11, 14);
                String imm  = oldStr.substring(15);

                int j = 1;
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
                String hex = "0000";
                int value;
                
                /*imm part*/
                while (!(hex.equals("0FFF")) && exit3 == 0)
                {
                    checking = imm.contains(hex);

                    value = Integer.parseInt(hex, 16);	
                    value++;
                    hex = Integer.toHexString(value);


                    if(checking)
                        exit3 = 1;

                }

                if(!checking){
                    return "Syntax Error: immediate should be from R0 to R31";
                }
            }
            
            else if (oldStr.startsWith("XORI"))		/*XORI rt, rs, immediate. XORI R10, R2, #FFFF*/
            {
                String jString;
                String rt = oldStr.substring(5, 7);
                String rs = oldStr.substring(9, 12);
                String imm  = oldStr.substring(13);

                int j = 1;
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
                String hex = "0000";
                int value;
                
                /*imm part*/
                while (!(hex.equals("0FFF")) && exit3 == 0)
                {
                    checking = imm.contains(hex);

                    value = Integer.parseInt(hex, 16);	
                    value++;
                    hex = Integer.toHexString(value);


                    if(checking)
                        exit3 = 1;

                }

                if(!checking){
                    return "Syntax Error: immediate should be from R0 to R31";
                }
            }
            
            else if (oldStr.startsWith("BLTZ"))		/*BLTZ rs, offset. BLTZ R1, L1 */
            {
                String jString;
                String rs = oldStr.substring(5, 7);
                String offset = oldStr.substring(9);

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

                /*offset part*/
                for(k = 0; k < code.size(); k++){
                    if(code.get(k).startsWith(offset))
                        checking = true;
                }

                if(!checking){
                    return "Syntax Error: offset does not exist";
                }
            }
            
            else if (oldStr.startsWith("DADDU"))		/*DADDU rd, rs, rt. DADDU R4, R1, R2*/
            {
                String jString;
                String rd = oldStr.substring(6, 8);
                String rs = oldStr.substring(10, 13);
                String rt  = oldStr.substring(14);

                int j = 1;
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
            
            else if (oldStr.startsWith("SLT"))		/*SLT rd, rs, rt. SLT R3, R1, R2*/
            {
                String jString;
                String rd = oldStr.substring(4, 6);
                String rs = oldStr.substring(8, 10);
                String rt  = oldStr.substring(12);

                int j = 1;
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
            
            else if (oldStr.startsWith("BC"))			/*BC offset. BC L2*/
            {
                String jString;
                String offset = oldStr.substring(3);

                int j = 0;
                int exit1;
                boolean checking = false;

                exit1 = 0;

                /*offset part*/
                for(k = 0; k < code.size(); k++){
                    if(code.get(k).startsWith(offset))
                        checking = true;
                }

                if(!checking){
                    return "Syntax Error: offset does not exist";
                }
            }
        }

        return " "; //no error
    }

    
    private String getOpcode(String code, ArrayList<String> line){
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
        
        String 	IR31_26 = " ",
                IR25_21 = " ",
                IR20_16 = " ",
                IR15_11 = " ",
                IR10_6 = " ",
                IR5_0 = " ";
        
        for (ctr = 0; ctr < 8; ctr++ ) {
            if (code.contains(opcodeTable[ctr][1])) {
		/* Initialize IR31 to IR 26 */
                IR31_26 = opcodeTable[ctr][2];
                
                if ( opcodeTable[ctr][0].equals("format1") ) {
                    String temp = opcode_format1(code, line);
                    IR25_21 = temp.substring(0, 5);
                    IR20_16 = temp.substring(6, 10);
                    IR15_11 = temp.substring(11, 15);
                    IR10_6 = temp.substring(16, 20);
                    IR5_0 = temp.substring(21, 25);
                } else if ( opcodeTable[ctr][0].equals("format2") ) {
                    String temp = opcode_format2(code);
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
        
        return IR31_26 + IR25_21 + IR20_16 + IR15_11 + IR10_6 + IR5_0;
    }
    
    private String opcode_format1(String code, ArrayList<String> line){
        int i = 0;
        String offset = code.substring(9);
        int ctr1 = 0;
        int ctr2 = 0;
        
        do {
            if(!line.get(i).contentEquals(code))
                ctr1++;
            i++;
        }while(!line.get(i-1).contentEquals(code));
        
        do {
            if(!line.get(ctr1).contains(offset))
                ctr2++;
            ctr1++;
        }while(!line.get(ctr1).contains(offset));
        
        offset = Integer.toBinaryString(ctr2);
        int length = offset.length();
        char append;
        
        if(ctr2 < 8)
            append = '0';
        else
            append = '1';
        
        for(int j = 0; j < 26 - length; j++){
            offset += append;
        }
        
        char[] in = offset.toCharArray();
        int begin = 0;
        int end = in.length-1;
        char temp;
        
        while(end > begin){
            temp = in[begin];
            in[begin] = in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        
        offset = in.toString();
        
        return offset;
    }
    
    private String opcode_format2(String code){
        
        String splitIns[], splitRs[], splitRt[], splitImm;
        
        if(code.contains("DADDIU"))
            splitIns = code.split("DADDIU ", 2); //split[0] = "DADDIU ", split[1] = "rs, rt, imm"
        else
            splitIns = code.split("XORI ", 2);
        
        splitRs = splitIns[1].split(",", 2);
        splitRt = splitRs[1].split(",", 2);
        splitImm = splitRt[1].substring(0,3);
        
        boolean found = false;
        int i = 0;
        
        while(!found){
            if(splitRs[0].contains(Integer.toString(i))){
                splitRs[0] = Integer.toBinaryString(i);
                found = true;
            }
            i++;
        }
        
        char append;
        
        if(i - 1 < 8)
            append = '0';
        else
            append = '1';
        
        for(int j = 0; j < 5 - splitRs[0].length(); j++){
            splitRs[0] += append;
        }
        
        char[] in = splitRs[0].toCharArray();
        int begin = 0;
        int end = in.length-1;
        char temp;
        
        while(end > begin){
            temp = in[begin];
            in[begin] = in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        
        splitRs[0] = in.toString();
        
        found = false;
        i = 0;
        
        while(!found){
            if(splitRt[0].contains(Integer.toString(i))){
                splitRt[0] = Integer.toBinaryString(i);
                found = true;
            }
            i++;
        }
        
        if(i - 1 < 8)
            append = '0';
        else
            append = '1';
        
        for(int j = 0; j < 5 - splitRt[0].length(); j++){
            splitRt[0] += append;
        }
        
        in = splitRt[0].toCharArray();
        begin = 0;
        end = in.length-1;
        
        while(end > begin){
            temp = in[begin];
            in[begin] = in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        
        splitRt[0] = in.toString();
        
        splitImm = Integer.toBinaryString(Integer.parseInt(splitImm, 16));
        
        return splitRs[0] + splitRt[0] + splitImm;
    }
}
