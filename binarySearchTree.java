/**
 * This Java program implements a Binary Search Tree (BST) with custom insertion rules,allowing only values between 0 and 999. 
 * Duplicate values are added to the left if empty, otherwise to the right. 
 * Users can insert nodes, change the root, and view the BST structure.
 * Author : Suhani Mathur
 * Created on : 11/11/2024
 */


package dsa;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class BSTNode {
    int data;
    BSTNode left, right;

    public BSTNode(int data) {
        this.data = data;
        left = right = null;
    }
}

class BST {
    private BSTNode root;

    public BST() {
        root = null;
    }

    public void insertRoot(int data) {
        root = insertRec(root, data);
    }

    private BSTNode insertRec(BSTNode root, int data) {
        if (root == null) {
            root = new BSTNode(data);
            return root;
        }  
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        } else {
            
            if (root.left == null) {
                root.left = new BSTNode(data);
            } else {
                root.right = insertRec(root.right, data);
            }
        }
        return root;
    }

    public void changeRoot(int newRootData) {
        BSTNode newRoot = findNode(root, newRootData);
        if (newRoot == null) {           
            System.out.println("Node with value " + newRootData + " not found. Inserting it as the new root.");
            insertRoot(newRootData);
            newRoot = findNode(root, newRootData);  
        }


        List<Integer> allNodes = new ArrayList<>();
        collectNodes(root, allNodes);

        
        root = newRoot;
        root.left = null;
        root.right = null;

        for (int node : allNodes) {
        	insertRoot(node);
        }

        System.out.println(Constants.NEW_BST + newRootData + ":");
        printTree();  
    }

    private BSTNode findNode(BSTNode node, int data) {
        if (node == null) return null;
        if (node.data == data) return node;
        if (data < node.data) return findNode(node.left, data);
        return findNode(node.right, data);
    }

    private void collectNodes(BSTNode node, List<Integer> allNodes) {
        if (node == null) return;
        collectNodes(node.left, allNodes);
        allNodes.add(node.data);
        collectNodes(node.right, allNodes);
    }

    public void printTree() {
    	List<List<String>> lines = new ArrayList<>();
        List<BSTNode> level = new ArrayList<>();
        List<BSTNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;
        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;
            for (BSTNode n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = "[" + n.data + "]";
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);
                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<BSTNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perPiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    if (line.get(j) == null) {
                        for (int k = 0; k < perPiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perPiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perPiece / 2f - f.length() / 2f);

                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perPiece /= 2;
        }
    }
}

public class binarySearchTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST bst = new BST();       

        while (true) {
            System.out.println(Constants.MENU);
            System.out.println(Constants.MENU_OPTION_1);
            System.out.println(Constants.MENU_OPTION_2);
            System.out.println(Constants.MENU_OPTION_3);
            System.out.println(Constants.MENU_OPTION_4);

            String input = scanner.nextLine().trim();

            switch (input) {
            case "1":
                System.out.println(Constants.NODE_INPUT);
                while (true) {
                    String nodeInput = scanner.nextLine().trim();
                    if (nodeInput.equalsIgnoreCase("exit")) break;
                    try {
                        int node = Integer.parseInt(nodeInput);
                        if(node < 0 || node > 999) {
                        	System.out.println(Errors.RANGE_INPUT);
                        }else {
                        bst.insertRoot(node);
                        System.out.println(Constants.AFTER_INSERTION + node + ":");
                        bst.printTree();
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println(Errors.INVALID_INPUT);
                    }
                }
                break;
                case "2":
                    System.out.println(Constants.NEW_NODE);
                    String rootInput = scanner.nextLine().trim();
                    try {
                        int newRoot = Integer.parseInt(rootInput);
                        if(newRoot < 0 || newRoot > 999) {
                        	System.out.println(Errors.RANGE_INPUT);
                        }else {
                        bst.changeRoot(newRoot);
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println(Errors.INVALID_INPUT);
                    }
                    break;
                case "3":
                    bst.printTree();
                    break;
                case "4":
                    System.out.println(Constants.EXITING);
                    scanner.close();
                    return;
                default:
                    System.out.println(Errors.INVALID_OPTION);
            }
        }
    }
}
