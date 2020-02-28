package astar;

import java.util.ArrayList;

public class testRemove {

    public static void main(String[] args) {
        ArrayList<Spot> arr = new ArrayList<>();
        ArrayList<Spot> copy = new ArrayList<>();

        Spot toRemove = null;

        for(int i = 0; i<10; i++){
            Spot newSpot = new Spot(i,i,false);
            if(i == 5){
                toRemove = newSpot;
            }
            arr.add(newSpot);
        }

        for(Spot s : arr){
            if(!arr.contains(s)){
                copy.add(s);
            }
        }





        for(Spot s : arr){
            System.out.println(s);
        }
        System.out.println("printing copy");
        for(Spot s : copy){
            System.out.println(s);
        }



    }
}
