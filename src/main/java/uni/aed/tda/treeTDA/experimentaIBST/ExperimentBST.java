/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uni.aed.tda.treeTDA.experimentaIBST;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import uni.aed.tda.treeTDA.BstTDA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import uni.aed.tda.treeTDA.BstTDA;


public class ExperimentBST {

    public static void main(String[] args) throws IOException {
        int[] heights = {8, 16, 32, 64};
        int trialsPerConfig = 3; // repeticiones por configuración
        String outCsv = "experiment_results.csv";
        
            System.out.println("seedType,delType,combo,targetHeight,trial,initialNodes,initialHeight,initialIPL,finalNodes,finalHeight,finalIPL,operations");
            
            
            for (int h : heights) {
                for (int trial = 1; trial <= trialsPerConfig; trial++) {
                    // dos generadores de números aleatorios (diferente semilla / algoritmo)
                    runConfig(h, trial, "seedA", new Random(12345 + trial));
                    runConfig(h, trial, "seedB", new Random(System.nanoTime() + trial));
                }
            }
        
    }

    private static void runConfig(int targetHeight, int trial, String seedName, Random rng) throws IOException {
        
        BstTDA<Integer> tree = new BstTDA<>();
        int attempts = 0;
        while (tree.calculateHeight() < targetHeight) {
            int value = Math.abs(rng.nextInt());
            tree.add(value);
            attempts++;
            if (attempts % 100000 == 0) System.out.println("seed " + seedName + " building h=" + targetHeight + " attempts=" + attempts + " curHeight=" + tree.calculateHeight());
        
        }

        long initialNodes = tree.countNodes();
        long initialHeight = tree.calculateHeight();
        long initialIPL = tree.calcularIPL();

     
        String[] delTypes = {"sym", "asym"};
        for (String delType : delTypes) {
            
            int operations = (int)Math.max(1000, initialNodes); // evitar demasiado corto
          
            BstTDA<Integer> treeCopy = cloneTreeWithRandomInsert(targetHeight, trial, seedName, rng); // rebuild
            long initNodes2 = treeCopy.countNodes();
            long initHeight2 = treeCopy.calculateHeight();
            long initIPL2 = treeCopy.calcularIPL();

            
            performOperations(treeCopy, operations, rng, delType, true); // strict alternating
            long finalNodes = treeCopy.countNodes();
            long finalHeight = treeCopy.calculateHeight();
            long finalIPL = treeCopy.calcularIPL();
            System.out.println(seedName + "," + delType + ",strictAlt," + targetHeight + "," + trial + "," + initNodes2 + "," + initHeight2 + "," + initIPL2 + "," + finalNodes + "," + finalHeight + "," + finalIPL + "," + operations); 

            
            treeCopy = cloneTreeWithRandomInsert(targetHeight, trial+100, seedName, rng);
            long initNodes3 = treeCopy.countNodes();
            long initHeight3 = treeCopy.calculateHeight();
            long initIPL3 = treeCopy.calcularIPL();
            performOperations(treeCopy, operations, rng, delType, false);
            finalNodes = treeCopy.countNodes();
            finalHeight = treeCopy.calculateHeight();
            finalIPL = treeCopy.calcularIPL();
            System.out.println(seedName + "," + delType + ",randomAlt," + targetHeight + "," + trial + "," + initNodes3 + "," + initHeight3 + "," + initIPL3 + "," + finalNodes + "," + finalHeight + "," + finalIPL + "," + operations);
            

            
            System.out.println(tree.toString()); 
        }
    }

    private static BstTDA<Integer> cloneTreeWithRandomInsert(int targetHeight, int trial, String seedName, Random rng) {
        BstTDA<Integer> t = new BstTDA<>();
        while (t.calculateHeight() < targetHeight) {
            t.add(Math.abs(rng.nextInt()));
        }
        return t;
    }

    private static void performOperations(BstTDA<Integer> tree, int operations, Random rng, String delType, boolean strictAlternate) {
        // maintain a small reservoir of values for deletes
        int reservoirSize = 1000;
        int[] reservoir = new int[reservoirSize];
        for (int i = 0; i < reservoirSize; i++) reservoir[i] = Math.abs(rng.nextInt());

        int rIdx = 0;
        boolean doInsertNext = true;
        for (int op = 0; op < operations; op++) {
            if (strictAlternate) {
                if (doInsertNext) {
                    tree.add(Math.abs(rng.nextInt()));
                } else {
                    int valToDelete = reservoir[rIdx % reservoirSize];
                    if (delType.equals("sym")) tree.deleteByMerging(valToDelete);
                    else tree.deleteByMergingAsymmetric(valToDelete);
                    rIdx++;
                }
                doInsertNext = !doInsertNext;
            } else {
                // choose randomly insert or delete
                if (rng.nextBoolean()) {
                    tree.add(Math.abs(rng.nextInt()));
                } else {
                    int valToDelete = reservoir[rIdx % reservoirSize];
                    if (delType.equals("sym")) tree.deleteByMerging(valToDelete);
                    else tree.deleteByMergingAsymmetric(valToDelete);
                    rIdx++;
                }
            }
        }
    }
}
