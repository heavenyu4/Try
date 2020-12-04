package com.thunder.as31try;

class TestRepeatData {

    public static void main(String[] args) {
        String[] in = {"a", "a", "a"};
        String old = "";
        for (int i = 0; i < in.length; i++) {
            System.out.println("input: " + in[i]);
            try {
                if (in[i].isEmpty()) {
                    System.out.println(in[i]);
                    continue;
                }

                if (old.equals(in[i])) {
                    System.out.println(in[i]);
                    continue;
                }
                old = in[i];
                System.out.println("old: " + old);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
