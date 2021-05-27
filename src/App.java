import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;

public class App {

    // input formulas in conjunctive normal form
    // we consider that the formulas are entered as follows --
    // First enter n, the number of clauses that conjunctively comprise the formula
    // Now in the next n lines, enter literals for each clause as --
    // x1x2x3....xm 
    // here each xi represents a literal 
    // negation of a literal is represented by adding a ! sign in front of it. Ex. !x means not of x.
    
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        outerloop: while(true){
            System.out.println("Enter n (number of clauses in the formula)");
            int n = Integer.parseInt(read.readLine().split("")[0]);
            String[] f = new String[n];
            for(int i = 1; i<=n; i++){
                System.out.println("Enter clause " + i + " in the format x1x2x3....xm");
                f[i-1] = read.readLine();               
            }           

            prove(f);

            innerloop: while(true){
                System.out.println();
                System.out.println("Press n to check for another formula. Press x to exit.");
                String cmd = read.readLine().split("")[0];
                if(cmd.compareTo("x")==0)
                    break outerloop;
                else if(cmd.compareTo("n")==0){
                    break innerloop;
                }
                else {
                    System.out.println("Enter valid command");
                }
            }           
        }
    }

    public static void prove(String[] f){
        //Using truth table method
        //Indentify all the distinct literals in the formula. Let there be z such distinct literals
        
        int l = 0; //number of literals

        HashMap<String, Integer> literals = new HashMap<>(); //hashmap used to store all the literals

        for(int i = 0; i<f.length; i++){
            inner: for(int j = 0; j<f[i].length(); j++){
                String curr_literal = f[i].substring(j, j+1);
                if(curr_literal.compareTo("!")==0){
                    continue inner;
                }
                if(!literals.containsKey(curr_literal)){
                    l++;
                    literals.put(curr_literal, l);
                }
            }
        }

        //Number of possible interpretations = 2^l
        int n_interpretations = (int)Math.pow(2, l); 
        System.out.println("Number of possible interpretations = " + n_interpretations);

        //make truth table
        //this truth table will have an array for each literal. Therefore l arrays. 
        //Plus (l+1)th array to store the truth value of the formula.
        boolean[][] truth_table = new boolean[l+1][n_interpretations];
        
        //fill the truth table
        //To fill the row of the ith interpretation (ith element of of each literal array), we first 
        //convert i to its l digit binary representation (l = number of literals).
        //Then if the mth digit of this binary representation is 1, then we replace 
        //the ith element of the mth row by true. 

        for(int i = 0; i<n_interpretations; i++){
            //first make the length of binary representation equal to l
            String[] bin = new String[l]; 
            String[] x = Integer.toBinaryString(i).split(""); 
            
            for(int m = 0; m<l; m++){
                if(m<l-x.length)
                    bin[m] = "0";
                else bin[m] = x[m-l+x.length];
            }

            //fill the table
            for(int j = 0; j<l; j++){
                if(bin[j].compareTo("1")==0){
                    truth_table[j][i] = true;
                }
            }
        }


        //For every interpretation, compute truth value of every clause.
        //Then compute truth value of the formula by taking conjunction of all the clauses.

        for(int k = 0; k<n_interpretations; k++){
            boolean[] clauses = new boolean[f.length]; //array that stores the truth value of each clause
            for(int i = 0; i<f.length; i++){ //iterating to compute for each clause
                boolean neg = false; //is the current literal in the clause negation?
                String[] ci = f[i].split(""); //ith clause
                innerloop: for(String j : ci){ //iterate over each term in the clause
                    if(j.compareTo("!")==0){ //this means the current term is negation of a literal
                        neg = true;
                    }
                    else if (neg){ //if negation then truth value of clause is true if truth value of literal is false
                        if(!truth_table[literals.get(j)-1][k]){
                            clauses[i] = true;
                            break innerloop;
                        }
                        neg = false;
                    }
                    else{ //the truth value of clause is true if truth value of any literal is true
                        if(truth_table[literals.get(j)-1][k]){
                            clauses[i] = true;
                            break innerloop;
                        }
                    }
                }
            }
            
            //Now we have the truth value for all the clauses in the formula for the given kth interpretation.
            //We just take the conjunction of all these clauses to get the final truth value of the formula.
            //Therefore, any clause is false, then the formula is false for the given interpretation.

            if(!hasFalse(clauses)){
                truth_table[l][k] = true;
            }            
        }
        //print the truth table. 
        //the final array represents the final truth value of the formula for all the interpretations
        print_tt(truth_table, literals);

        int n_true = 0; //number of true intrerpretations of the formula
        for(int i = 0; i<n_interpretations; i++){
            if(truth_table[l][i]){
                n_true++;
            }
        }

        if(n_true == 0){
            System.out.println("The formula is unsatisfiable (Zero models)");
        }
        else if(n_true == n_interpretations){
            System.out.println("The formula is logically valid (" + n_interpretations + " models)");
        }
        else {
            System.out.println("The formula is satisfiable (" + n_true + " models)");
        }
        
    }
    
    public static boolean hasFalse(boolean[] arr){
        for(int i = 0 ; i<arr.length; i++){
            if(!arr[i]){
                return true;
            }
        }
        return false;
    }

    public static void print_tt(boolean[][] table, HashMap<String, Integer> literals){
        for(String s : literals.keySet()){
            System.out.print("  " + s + "      |   ");
        }
        System.out.println("   f     |");
        System.out.println("----------------------------------------------------------");
        for(int i = 0; i<table[0].length; i++){
            for(int j = 0; j<table.length; j++){
                System.out.print(table[j][i] + "    |   ");
            }
            System.out.println();
        }

    }
}
