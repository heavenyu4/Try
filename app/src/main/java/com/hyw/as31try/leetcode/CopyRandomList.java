package com.hyw.as31try.leetcode;

import java.util.HashMap;

/***
 * https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 * 138. 复制带随机指针的链表 深复制
 */
public class CopyRandomList {

    static class Node {
        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val,Node _next,Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }




    public Node copyRandomList(Node head) {
        if (head == null){
            return null;
        }

        HashMap<Node, Node> map = new HashMap<>();
        Node node = head;
        while (node != null){
            map.put(node, new Node(node.val, null, null));
            node = node.next;
        }

        node = head;
        while(node != null){
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }

        return map.get(head);

    }


    /*
    {
    "$id": "1",
    "next": {
        "$id": "2",
        "next": null,
        "random": {"$ref": "2"},
        "val": 2
    },
    "random": {"$ref": "2"},
    "val": 1
}
     */

    public static void main(String[] args) {
//        String input = "{\"$id\":\"1\",\"next\":{\"$id\":\"2\",\"next\":null,\"random\":{\"$ref\":\"2\"},\"val\":2},\"random\":{\"$ref\":\"2\"},\"val\":1}";
//        NodeTi parse = JSONObject.parseObject(input , NodeTi.class);
//        System.out.println();

        Node node2 = new Node(2, null, null);
        node2.random = node2;
        Node head = new Node(1, node2,node2);

        CopyRandomList copyRandomList = new CopyRandomList();
        copyRandomList.copyRandomList(head);


    }





}
