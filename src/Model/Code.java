package Model;

public class Code {
    private String instruction;
    private String opcode;
    private String address;
    
    public Code(String instruction, String address) {
        this.instruction = instruction;
        this.address = address;
    }
      
    public void setOpcode(){
        //I-type
        if(instruction.startsWith("LD")){
            //TODO set opcode
        }
        else if(instruction.startsWith("SD")){
            //TODO set opcode
        }
        else if(instruction.startsWith("DADDIU")){
            //TODO set opcode
        }
        else if(instruction.startsWith("XORI")){
            //TODO set opcode
        }
        else if(instruction.startsWith("BLTZ")){
            //TODO set opcode
        }
        
        //R-type
        else if(instruction.startsWith("DADDU")){
            //TODO set opcode
        }
        else if(instruction.startsWith("SLT")){
            //TODO set opcode
        }
        
        //J-type
        else if(instruction.startsWith("BC")){
            //TODO set opcode
        }
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getInstruction() {
        return instruction;
    }
    
    public String getOpcode(){
        return opcode;
    }
}
