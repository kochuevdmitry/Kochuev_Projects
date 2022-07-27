package Tree;

import java.util.LinkedList;
import java.util.List;

public class PathSumTree {

    public static void runInit(){
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);

        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);

        System.out.println(pathSum(root, 22));
    }

    private static List<List<Integer>> pathSum(TreeNode root, int sum){
        List<List<Integer>> result  = new LinkedList<List<Integer>>();
        List<Integer> currentResult  = new LinkedList<Integer>();
        pathSumK(root,sum,currentResult,result);
        return result;
    }

    private static void pathSumK(TreeNode root, int sum, List<Integer> currentResult,
                        List<List<Integer>> result) {

        if (root == null)
            return;
        currentResult.add(new Integer(root.val));
        if (root.left == null && root.right == null && sum == root.val) {
            result.add(new LinkedList(currentResult));
            currentResult.remove(currentResult.size() - 1);//don't forget to remove the last integer
            return;
        } else {
            pathSumK(root.left, sum - root.val, currentResult, result);
            pathSumK(root.right, sum - root.val, currentResult, result);
        }
        currentResult.remove(currentResult.size() - 1);
    }
}


