/* 
    ERROR CHECKING: rt does not check for R10-R32
                    offset should be from 0000-0FFF
*/

package Controller;

import Model.*;
import java.util.*;

public class Controller {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Code> code = new ArrayList<>();
        
    public static void main(String args[]){
        getInput();
    }
    
    public static void getInput(){
        String line;
        String error =" ";
        Code c;
        ArrayList<String> inst = new ArrayList<String>();
        boolean stop = false;
        do{
            System.out.println("Enter your code (press '?' when done):");
            do{
                line = sc.nextLine();
                if(!line.equals("?")){
                    inst.add(line);
                }
                else {
                    stop = true;
                }
            }while(!stop);
            
            error = errorCheck(inst);
            if(!error.contentEquals(" ")) { //if there's an error
                System.out.println(error); //will print the error
                inst.clear(); //reset arraylist
                stop = false;
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
    
    private static String errorCheck(ArrayList<String> code){
        int size = code.size();         //get the size of array
        String splitIns[], splitReg1[];
        int ctr = 0;
        String branchName[] = new String[100];
        
        for(int br = 0; br < size; br++) {
            String array = code.get(br);    //the index of array
            String temp[];
            
            int alength = array.length();
            int k = 0, exit = 0;
            
            while (k < alength && exit == 0) {
                if ((array.charAt(k)== ':'))
                    exit = 1;
                k++;
            }
            
            if (exit == 1){
                temp = array.split(": ", 2);
                branchName[ctr] = temp[0];
                ctr++;
            }
        }
        
        for(int i = 0; i < size; i++) {
            String array = code.get(i);    //the index of array
            String oldStr;
            String temp[];
            
            //For Branch Name
            int alength = array.length();
            int k = 0, exit = 0;
            
            while (k < alength && exit == 0) {
                if ((array.charAt(k)== ':'))
                    exit = 1;
                k++;
            }
            if (exit == 1){
                temp = array.split(": ", 2);
                oldStr = temp[1];
            }
            else
                oldStr = array;
            
            //Check if instruction is correct
            if(!(oldStr.startsWith("LD ") || oldStr.startsWith("SD ") || oldStr.startsWith("DADDIU ") || oldStr.startsWith("XORI ") || oldStr.startsWith("BLTZ ") || oldStr.startsWith("DADDU ") || oldStr.startsWith("SLT ") || oldStr.startsWith("BC "))) {
                return "Instruction does not exist";
            }
            
            //For LD rt and BLTZ rs ONLY
            if(oldStr.startsWith("LD") || oldStr.startsWith("BLTZ")) {
                if (oldStr.startsWith("LD"))
                    splitIns = oldStr.split("LD ", 2);                  //Split LD rt,offset(base) to [0] = "LD " and [1] = "rt,offset(base)"
                else
                    splitIns = oldStr.split("BLTZ ", 2);
                splitReg1 = splitIns[1].split(",", 2);                //The [1] = "rt,offset(base)" of above got split to [0]="rt" and [1] got the rest
                
                int length, m = 0, result;                              //Length of the splited instruction(ex. rt, off, base)
                                                                    //m is index for the length of string(ex. rt, off, base)
                                                                    //result is the integer value of the register
                length = splitReg1[0].length();                       //length of Rt
                
                boolean isValidReg = true;
                if (splitIns[1].startsWith("R") || splitIns[1].startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[0].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                                     //jString is used for storing the numeric numbers of the string
                char reg1[] = new char[length];
                splitReg1[0].getChars(0, length, reg1, 0);
                
                int j = 1;                                          //This is for R1 to R31
                int exit1 = 0;                            //If its R1 to R31, it will tell you to exit
                
                while (m < length) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);             //jString is the numbers found in the string and converted to integer Result
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)                //If result is between 1 to 31, then exit is true
                        exit1 = 1;
                    j++;
                }
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg1 for "+ oldStr;
                }
            }
            //For DADDIU rt, XORI rt, DADDU rd and SLT rd ONLY
            if(oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                if (oldStr.startsWith("DADDIU"))
                    splitIns = oldStr.split("DADDIU ", 2);                  
                else if (oldStr.startsWith("XORI"))
                    splitIns = oldStr.split("XORI ", 2);
                else if (oldStr.startsWith("DADDU"))
                    splitIns = oldStr.split("DADDU ", 2);
                else
                    splitIns = oldStr.split("SLT ", 2);
                
                splitReg1 = splitIns[1].split(",", 3);
                
                int length1, m = 0, result;
                length1 = splitReg1[0].length();
                
                boolean isValidReg = true;
                if (splitIns[1].startsWith("R") || splitIns[1].startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[0].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                 
                char reg1[] = new char[length1];
                splitReg1[0].getChars(0, length1, reg1, 0);
                
                int j = 1;                      
                int exit1 = 0;
                
                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)               
                        exit1 = 1;
                    j++;
                }
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg1 for "+ oldStr;
                }
            }
            //For SD rt ONLY
            if(oldStr.startsWith("SD")) {
                splitIns = oldStr.split("SD ", 2);                  //Split LD rt,offset(base) to [0] = "LD " and [1] = "rt,offset(base)"
                splitReg1 = splitIns[1].split(",", 2);                //The [1] = "rt,offset(base)" of above got split to [0]="rt" and [1] got the rest
                
                int length, m = 0, result;                              //Length of the splited instruction(ex. rt, off, base)
                                                                    //m is index for the length of string(ex. rt, off, base)
                                                                    //result is the integer value of the register
                length = splitReg1[0].length();                       //length of Rt
                
                boolean isValidReg = true;
                if (splitIns[1].startsWith("R") || splitIns[1].startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[0].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                                     //jString is used for storing the numeric numbers of the string
                char reg1[] = new char[length];
                splitReg1[0].getChars(0, length, reg1, 0);
                
                int j = 0;                                          //This is for R1 to R31
                int exit1 = 0;                            //If its R1 to R31, it will tell you to exit
                
                while (m < length) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);             //jString is the numbers found in the string and converted to integer Result
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)                //If result is between 1 to 31, then exit is true
                        exit1 = 1;
                    j++;
                }
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg1 for "+ oldStr;
                }
            }
            //For DADDIU rs, XORI rs, DADDU rs and SLT rs ONLY
            if(oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI") || oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                if (oldStr.startsWith("DADDIU"))
                    splitIns = oldStr.split("DADDIU ", 2);                  
                else if (oldStr.startsWith("XORI"))
                    splitIns = oldStr.split("XORI ", 2);
                else if (oldStr.startsWith("DADDU"))
                    splitIns = oldStr.split("DADDU ", 2);
                else
                    splitIns = oldStr.split("SLT ", 2);
                
                splitReg1 = splitIns[1].split(", ", 3); //[0]=rt, [1]=rs, [2]=imm
                
                int length1, m = 0, result;
                length1 = splitReg1[1].length();    //rs.length
                
                boolean isValidReg = true;
                if (splitReg1[1].startsWith("R") || splitReg1[1].startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[1].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                 
                char reg1[] = new char[length1];
                splitReg1[1].getChars(0, length1, reg1, 0);
                
                int j = 0;                      
                int exit1 = 0;
                
                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)               
                        exit1 = 1;
                    j++;
                }
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg2 for "+ oldStr;
                }
            }
            //For DADDU rt and SLT rt ONLY
            if(oldStr.startsWith("DADDU") || oldStr.startsWith("SLT")) {
                if (oldStr.startsWith("DADDU"))
                    splitIns = oldStr.split("DADDU ", 2);
                else
                    splitIns = oldStr.split("SLT ", 2);
                
                splitReg1 = splitIns[1].split(", ", 3); //[0]=rt, [1]=rs, [2]=imm
                
                int length1, m = 0, result;
                length1 = splitReg1[2].length();    //rs.length
                
                boolean isValidReg = true;
                if (splitReg1[2].startsWith("R") || splitReg1[2].startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(splitReg1[2].substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                 
                char reg1[] = new char[length1];
                splitReg1[2].getChars(0, length1, reg1, 0);
                
                int j = 0;                      
                int exit1 = 0;        
                
                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)               
                        exit1 = 1;
                    j++;
                }
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg3 for "+ oldStr;
                }
            }
            //For LD offset and SD offset ONLY
            if(oldStr.startsWith("LD") || oldStr.startsWith("SD")) {
                if (oldStr.startsWith("LD"))
                    splitIns = oldStr.split("LD ", 2);
                else
                    splitIns = oldStr.split("SD ", 2);
                
                splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset(base)
                
                String a = splitReg1[1].substring(0, 4);
                
                String hex = "0000";
                Integer value;

                int exit1 = 0;
                boolean checking;
                while (!(hex.equals("1000")) && exit1 == 0){
                    //Check if a is equal to hex
                    checking = a.equals(hex.toUpperCase());
                    //Change hex to int value
                    value = Integer.parseInt(hex, 16);	
                    //Increase value
                    value++;
                    //Change value to hex
                    hex = Integer.toHexString(value);
                    //Zero extend hex
                    char zeroExtend[] = new char[4];
                    int extend = 4 - hex.length();
                    for (int zero = 0; zero < extend; zero++) {
                        zeroExtend[zero] = '0';
                    }
                    char tempChar[] = new char[4-extend];
                    hex.getChars(0, 4-extend, tempChar, 0);
                    
                    for (int zero = 3; zero >= extend; zero--) {
                        zeroExtend[zero] = tempChar[zero-extend];
                    }
                    
                    hex = String.valueOf(zeroExtend);
                    if(checking)
                        exit1 = 1;
                }
                
                if(exit1 == 0) {
                    return "Syntax Error reg2 for "+ oldStr;
                }
            }
            //For LD base and SD base ONLY
            if(oldStr.startsWith("LD") || oldStr.startsWith("SD")) {
                if (oldStr.startsWith("LD"))
                    splitIns = oldStr.split("LD ", 2);
                else
                    splitIns = oldStr.split("SD ", 2);
                
                splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset(base)
                
                int length1, m = 0, result;
                length1 = splitReg1[1].length();    //rs.length
                
                boolean isValidReg = true;
                String a = splitReg1[1].substring(5, length1-1);
                
                if (a.startsWith("R") || a.startsWith("r")) {
                /* Remove first("R") to get the INTEGER part */
                    try {
                        Integer.parseInt(a.substring(1));
                    } catch (NumberFormatException e) {
                        isValidReg = false;
                        }
                } else {
                    isValidReg = false;
                }
                
                StringBuilder sb = new StringBuilder(40);
                String jString;                 
                length1 = a.length();
                char reg1[] = new char[length1];
                a.getChars(0, length1, reg1, 0);
                
                int j = 0;                      
                int exit1 = 0;
                
                while (m < length1) {
                    if (reg1[m] >= '0' && reg1[m] <= '9') {
                        sb.append(reg1[m]);
                    }
                    m++;
                }
                jString = sb.toString();
                result = Integer.parseInt(jString);
                
                while(j < 32 && exit1 == 0) {
                    if (result == j)               
                        exit1 = 1;
                    j++;
                }
                
                if(exit1 == 0 || !isValidReg) {
                    return "Syntax Error reg3 for "+ oldStr;
                }
            }
            //For DADDIU Imm and XORI Imm ONLY
            if(oldStr.startsWith("DADDIU") || oldStr.startsWith("XORI")) {
                if (oldStr.startsWith("DADDIU"))
                    splitIns = oldStr.split("DADDIU ", 2);
                else
                    splitIns = oldStr.split("XORI ", 2);
                
                splitReg1 = splitIns[1].split(", ", 3); //[0]=rt, [1]=rs, [2]=#FFFF
                
                if (!(splitReg1[2].substring(0, 1).equals("#")))
                    return "Syntax Error reg3 for "+ oldStr;
                
                String a = splitReg1[2].substring(1, 5);
                String hex = "0000";
                Integer value;

                int exit1 = 0;
                boolean checking;
                while (!(hex.equals("1000")) && exit1 == 0){
                    //Check if a is equal to hex
                    checking = a.equals(hex.toUpperCase());
                    //Change hex to int value
                    value = Integer.parseInt(hex, 16);	
                    //Increase value
                    value++;
                    //Change value to hex
                    hex = Integer.toHexString(value);
                    //Zero extend hex
                    char zeroExtend[] = new char[4];
                    int extend = 4 - hex.length();
                    for (int zero = 0; zero < extend; zero++) {
                        zeroExtend[zero] = '0';
                    }
                    char tempChar[] = new char[4-extend];
                    hex.getChars(0, 4-extend, tempChar, 0);
                    
                    for (int zero = 3; zero >= extend; zero--) {
                        zeroExtend[zero] = tempChar[zero-extend];
                    }
                    
                    hex = String.valueOf(zeroExtend);
                    if(checking)
                        exit1 = 1;
                }
                
                if(exit1 == 0) {
                    return "Syntax Error reg3 for "+ oldStr;
                }
            }
            //For BLTZ offset and BC offset ONLY
            if(oldStr.startsWith("BLTZ") || oldStr.startsWith("BC")) {
                String a;
                if (oldStr.startsWith("BLTZ")) {
                    splitIns = oldStr.split("BLTZ ", 2);
                    splitReg1 = splitIns[1].split(", ", 2); //[0]=rt, [1]=offset
                    a = splitReg1[1];
                }
                else {
                    splitIns = oldStr.split("BC ", 2); //[1] = offset
                    a = splitIns[1];
                }
                
                int exit1 = 0;
                int why = 0;
                while (why < ctr && exit1 == 0) {
                    if (branchName[why].equals(a))
                        exit1 = 1;
                    why++;
                }
                
                if(exit1 == 0) {
                    return "Syntax Error reg3 for "+ oldStr;
                }
            }
        }
        return " ";
    }

    private static String getOpcode(String code, ArrayList<String> line){
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
                    IR25_21 = temp.substring(0, 5);
                    IR20_16 = temp.substring(6, 10);
                    IR15_11 = temp.substring(11, 15);
                    IR10_6 = temp.substring(16, 20);
                    IR5_0 = temp.substring(21, 25);
                } else if ( opcodeTable[ctr][0].equals("format3") ) {
                    String temp = opcode_format3(code);
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
    
    private static String opcode_format1(String code, ArrayList<String> line){
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
        
        char[] off = {' ', ' '};
        
        for(int j = 0; j < 26 - length; j++){
            off[j] = append;
        }
        
        i = 0;
        for(int k = off.length - 1; k < length; k++){
            off[k] = offset.charAt(i);
            i++;
        }
        
        return off.toString();
    }
    
    private static String opcode_format2(String code){
        
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
        
        char[] Rs = {' ', ' '};
        
        for(int j = 0; j < 5 - splitRs[0].length(); j++){
            Rs[j] = append;
        }
        
        i = 0;
        for(int k = Rs.length - 1; k < splitRs[0].length(); k++){
            Rs[k] = splitRs[0].charAt(i);
            i++;
        }
        
        splitRs[0] = Rs.toString();
        
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
        
        char[] Rt = {' ', ' '};
        
        for(int j = 0; j < 5 - splitRt[0].length(); j++){
            Rt[j] = append;
        }
        
        i = 0;
        for(int k = Rt.length - 1; k < splitRt[0].length(); k++){
            Rt[k] = splitRt[0].charAt(i);
            i++;
        }
        
        splitRt[0] = Rt.toString();
        
        splitImm = Integer.toBinaryString(Integer.parseInt(splitImm, 16));
        
        return splitRs[0] + splitRt[0] + splitImm;
    }
    
    private static String opcode_format3(String code){
        /*
            opcode base rt offset LD rt, offset(base)
            LD rt = R1-R31
            SD rt = R0-R31
            offset = 1000-1FFF
        */
        
        String splitIns[], splitRt[], splitOff[], splitBase[];
        
        if(code.contains("LD"))
            splitIns = code.split("LD ", 2); 
        else
            splitIns = code.split("SD ", 2);
        
        splitRt = splitIns[1].split(",", 2);
        splitOff = splitRt[1].split("(",2);
        splitBase = splitOff[1].split(")",2);
        
        boolean found = false;
        int i = 0;
        
        while(!found){
            if(splitRt[0].contains(Integer.toString(i))){
                splitRt[0] = Integer.toBinaryString(i);
                found = true;
            }
            i++;
        }
        
        char append;
        
        if(i - 1 < 8)
            append = '0';
        else
            append = '1';
        
        char[] Rt = {' ', ' '};
        
        for(int j = 0; j < 5 - splitRt[0].length(); j++){
            Rt[j] = append;
        }
        
        i = 0;
        for(int k = Rt.length - 1; k < splitRt[0].length(); k++){
            Rt[k] = splitRt[0].charAt(i);
            i++;
        }
        
        splitRt[0] = Rt.toString();
        
        String splitOffset = Integer.toBinaryString(Integer.parseInt(splitOff[0], 16));
        
        i = 0;
        found = false;
        
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
        
        char[] base = {' ', ' '};
        
        for(int j = 0; j < 5 - splitBase[0].length(); j++){
            base[j] = append;
        }
        
        i = 0;
        for(int k = base.length - 1; k < splitBase[0].length(); k++){
            base[k] = splitBase[0].charAt(i);
            i++;
        }
        
        splitBase[0] = base.toString();
        
        return splitBase[0] + splitRt[0] + splitOffset;
        
    }
}
