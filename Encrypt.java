 /**
  *   >> Github : AsemNajee .~
  *   << BY : Asem Al-Mekhlafi >>
  */

package asemlibrary;

/**
 * @Coder Asem Al-Mekhlafi
 * @author Asem Najee
 */
public class Encrypt {
    private Tree tree;
    
    private class Tree {
        boolean root;
        Tree left;
        Tree right;
        int wight;
        char character;
    }
    
    
    public Encrypt(String text){
        tree = getTree(text);
    }
    
    /**
     * get the encrypt of text .
     * @param text the text which be convert to encrypt .
     * @return encript or null if text cannot encrypting
     */
    public String getCode(String text){
        String code="";
        for (int i = 0; i < text.length(); i++) {
            String temp = getCode(text.charAt(i));
            if(temp == null){
                code = null;
                break;
            }else{
                code += temp;
            }
        }
        return code;
    }
    private String getCode(char ch){
        String code = "";
        return getCode(ch,tree,code);
    }
    private String getCode(char ch,Tree t,String code){
        String c ;
        if(t.root){
            if((c = getCode(ch,t.left,code+"1")) != null){
                return c;
            }else if ((c = getCode(ch,t.right,code+"0")) != null) {
                return c;
            }else{
                return null;
            }
        }else if (t.character == ch) {
            return code;
        }else{
            return null;
        }
    }
    
    private Tree getTree(String text){
        return toTree(getRepeat(text.toCharArray()));
    }
    
    /**
     * Read the encrypt and extact the text from it .
     * @param code the encrypt that will be read .
     * @return text if encrypt is true, or null if encrypt is wrong .
     */
    public String read(String code){
        String text = "",temp = "";
        for (int i = 0; i < code.length(); i++) {
            temp += code.charAt(i);
            String tempText = readTree(temp,tree);
            if("tree".equals(tempText)){
                continue;
            }else if ("Exception".equals(tempText)) {
                continue;
            }else if ("false".equals(tempText)) { 
                text = null;
                break;
            }else{
                text += tempText;
                temp = "";
            }
        }
        return text;
    }
    
    
    private String readTree(String code,Tree t){
        try{
            if (code.equals("") || code.length() == 0) {
                if (t.root) {
                    return "tree";
                } else {
                    return Character.toString(t.character);
                }
            }
            switch (code.charAt(0)) {
                case '1' -> {
                    return readTree(code.substring(1), t.left);
                }
                case '0' -> {
                    return readTree(code.substring(1), t.right);
                }
                default -> {
                    System.out.println(code);
                    return "false";
                }
            }
        }catch(Exception e){
            return "Exception";
        }
    }
    
    /**
     * print all tree .
     */
    public void printTree(){
        printTree(this.tree,"");
    }
    private void printTree(Tree t,String tap){
        String newTap  = tap+"  ";
        if(t != null){
            if(t.root){
                System.out.println(tap+"left:("+t.left.wight+"){");
                printTree(t.left,newTap);
                System.out.println(tap+"},\n"+tap+"right:("+t.right.wight+"){");
                printTree(t.right,newTap);
                System.out.println(tap+"}");
            }else{
                System.out.println(newTap+"char: "+t.character+",");
                System.out.println(newTap+"wight: "+t.wight);
            }
        }
    }
    
    
    
    private Tree toTree(int [][] data){
        int index = 0;
        Tree[] tree = new Tree[data.length];
        for (int i = 0; i < data.length; i++) {
            tree[index] = new Tree();
            tree[index].character = (char)data[i][0];
            tree[index].wight = (int)data[i][1];
            tree[index++].root = false;
        }
        return toTree(tree,1)[0];
    }
    
    private Tree[] toTree(Tree[] tree, int index){
        index --;
        if(tree.length == 1)
            return tree;
        if(index == tree.length-1)
            return tree;
        Tree[] temp = new Tree[tree.length-1];
        Tree t = new Tree();
        t.left = tree[index];
        t.right = tree[index + 1];
        t.root = true;
        try {
            t.wight = tree[index].wight + tree[index + 1].wight;
        } catch (Exception e) {
            t.wight = tree[index].wight;
        }
        temp[0] = t;

        for (int i = 1; i < temp.length; i++) {
            temp[i] = tree[i+1];
        }
        return toTree(order(temp),(++index));
    }
    
    private Tree[] order(Tree[] tree){
        for (int i = 0; i < tree.length; i++) {
            for (int j = i; j < tree.length; j++) {
                try{
                if(tree[i].wight < tree[j].wight){
                    Tree temp = tree[i];
                    tree[i] = tree[j];
                    tree[j] = temp;
                }
                }catch(Exception e){}
            }
        }
        return tree;
    }
    
    
    
    private int[][] getRepeat(char [] ch){
        int count = 0;
        boolean isExists;
        for (int i = 0; i < ch.length; i++) {
            isExists = false;
            for (int j = 0; j < i; j++) {
                if(ch[i] == ch[j]){
                    isExists = true;
                }
            }
            if(!isExists){
                count++;
            }
        }
        int data [][] = new int[count][2]; // [letter , repeated]
        int index = 0;
        loop : for (int i = 0; i < ch.length; i++) {
            for (int j = 0; j < index; j++) {
                if(ch[i] == data[j][0]){
                    data[j][1]++;
                    continue loop;
                }
            }
            data[index][0] = ch[i];
            data[index][1] = 1;
            index++;
        }
        for (int i = 0; i < data.length; i++) {
            int [] temp;
            for (int j = i; j < data.length; j++) {
                if(data[i][1] < data[j][1]){
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
//        for (int i = 0; i < data.length; i++) {
//            System.out.print("["+(char)data[i][0]+" ,"+data[i][1]+"]");
//        }
//        System.out.println();
        return data;
    }
}
