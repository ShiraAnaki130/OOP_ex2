package gameClient;

public class Ex2 {
    public static void main(String[] args){
        int _id = 208375600; //Integer.parseInt(args[0]);
        int _scenario_num = 11; //Integer.parseInt(args[1]);
        Thread game1 = new Thread(new Ex2_Client(_id,_scenario_num));
        game1.start();
    }

}
