import java.lang.reflect.Array;
import java.security.KeyPair;
import java.util.*;


public class Cache {

    private LinkedList<Block> LastAddresses = new LinkedList<>(); //Queue para LRU

    public static int MAXIMUN_LRU = 4;
    private ArrayList<ArrayList<Block>> Positions;//Array de blocos da cache.
    String Address;

    private int block;
    private int sets;

    public static boolean powerOfTwo(int n)
    {
        return (n & n-1)==0; //Lógica para verificar se é potência de 2 usando bitwise
    }

    public int getSets(){
        return this.sets;
    }
    public static int getCacheBlockNumFromUser(Scanner keyboard){ // Loop para pegar do usuário o número de blocos ( tem que ser potência de 2)
        System.out.println("Digite o número de blocos da cache (potência de 2):");
        int CacheNum = 0;
        do{
            CacheNum = keyboard.nextInt();
            if(!powerOfTwo(CacheNum))
                System.out.println("Inválido!");
        }while (!powerOfTwo(CacheNum));
        return CacheNum;
    }
    public static int getAssociativeNumber(Scanner keyboard,int block){ // Loop para pegar do usuário o número de blocos ( tem que ser potência de 2)
        System.out.println("Digite o número de tags presentes no index:");
        int sets = 0;

        sets = keyboard.nextInt();

        while(sets>block){
            System.out.println("A Associatividade não pode ser maior que o número de blocos!");
            sets = keyboard.nextInt();

        }
        return sets;
    }


        public int getBlocks(){
        return Positions.size();
    }

    Cache(int block,int associative){
        this.block = block;
        this.sets = associative;


        //Iniciar a lista de posições com arrays de valor 0;
        Positions = new ArrayList<>();
        for(int i = 0;i < (block/sets);i++){
            Positions.add(i,new ArrayList<>());
            for(int j=0;j<sets;j++){
                Positions.get(i).add(new Block(j,0,i));
            }
        }
    }

    public boolean accessAddress(int Address){

        boolean notInLRU = false;

        if(block<1){
            System.out.println("Não há blocos em cache alocados");
            return false;
        }
        //System.out.println("Número de blocos: "+block+"\nNúmero de associatividade: "+sets+"\nA Posição na memória do endereço "+Address+" é "+Address%(block/sets));
        if (Positions.get(Address%(block/sets)).contains(new Block(0,Address,Address%(block/sets)))){//Se a posição estiver no cache, returna true
            return true;
        } //Para cada associatividade, ir para o conjunto específico e ver se o endereço está lá

            for(int i = 0; i < sets;i++){
                if (!LastAddresses.contains(Positions.get(Address%(block/sets)).get(i))){

                    Positions.get(Address % (block/sets)).get(i).setValue(Address);//Posição numa cache é Endereço % QTD de blocos/Conjunto. O valor na cache é o próprio endereço
                    if(LastAddresses.size() == MAXIMUN_LRU){
                        LastAddresses.pollLast();
                    }

                    LastAddresses.addFirst(Positions.get(Address % (block/sets)).get(i));
                    return false;
                }


        }
        Iterator<Block> it = LastAddresses.descendingIterator(); //Se chegar aqui, todas as posições salvas naquele bloco estão no LRU, então devemos remover a menos usada no LRU
        int itValue = -1;
        Block temp;

        boolean isFoundValueInLRU = false;
        while(it.hasNext() || !isFoundValueInLRU) {
            temp = it.next();
            for (int i = 0; i < sets; i++) { // Achar o endereço dentro da LRU menos usado
                if (temp == Positions.get(Address % (block/sets)).get(i)){
                    Positions.get(Address % (block/sets)).get(i).setValue(Address);
                    LastAddresses.remove(temp);
                    LastAddresses.addFirst(Positions.get(Address % (block/sets)).get(i));
                    isFoundValueInLRU = true;
                }
            }
            if(isFoundValueInLRU)
                break;
        }
        return false;
    }

    public void setAddress(String input){
        this.Address = input;
    }

    public void executionProtocol(){ //Pega os endereços separados por virgula, transforma em int, e coloca na cache. Depois imprime os acertos
        List<String> Addresses = Arrays.asList(this.Address.split(","));
        ArrayList<Boolean> Acertos = new ArrayList();
        for(String Address : Addresses){
            if(this.accessAddress(Integer.parseInt(Address)))
                Acertos.add(true);
        }

        System.out.println("O Número de acertos foi: "+Acertos.size());
    }

    public void ImprimeMatriz(){//Imprime os Blocos/Conjuntos, e os endereços salvos nela;
        for(int i = 0;i < (block/sets);i++){
            System.out.println(((this.sets==1)?"Bloco ":"Conjunto ")+(i+1)+":");
            for(int j=0;j<sets;j++){
                System.out.print(" "+Positions.get(i).get(j));
            }
            System.out.println("");
        }
    }

    public ArrayList<Block> returnDirtyBitsBlocksAndTags(){
        ArrayList<Block> dirtyBlocks = new ArrayList<>();
        for(ArrayList<Block> listOfBlocks : Positions){
            for(Block b : listOfBlocks){
                if(b.dirtyBit){
                    dirtyBlocks.add(b);
                }
            }
        }
        return dirtyBlocks;
    }
}
