public class Block {
    int tag;
    int value;
    int position;

    boolean dirtyBit;

    Block(int tag, int value,int position){
        this.tag = tag;
        this.value = value;
        this.position = position;
        dirtyBit = false;
    }

    public void setValue(int value){
        if(this.value != 0)
            dirtyBit = true;
        this.value = value;
    }

    public boolean equals(Object Address) {
        return (Address instanceof Block) && (this.value == ((Block) Address).value);

    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
