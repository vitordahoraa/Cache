import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryFile {
    String nameOfCache;
    Map<String,Integer> map = new HashMap<>();

    MemoryFile(ArrayList<Block> blocks,String name){
        this.nameOfCache = name;
        for(Block b : blocks){
            map.put("Bloco "+b.position+", tag: "+b.tag,b.value);
        }
    }

    public void imprimeMemoryFile(){
        System.out.println(nameOfCache+"\n");

        for(Map.Entry<String,Integer> e : map.entrySet()){
            System.out.println(e.getKey() + " tem o endereço "+ e.getValue());
        }
    }


}
