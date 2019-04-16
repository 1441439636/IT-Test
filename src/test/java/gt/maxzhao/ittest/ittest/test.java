package gt.maxzhao.ittest.ittest;

import org.junit.Test;

import java.util.Arrays;

public class test {
    public enum Color {
        RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private Color(String name, int index) {
            System.out.println("------------------"+name);
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(int index) {
            for (Color c : Color.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
    @Test
    public void t(){
        System.out.println( Color.BLANK.index);
        System.out.println(Arrays.toString(Color.values()));
        System.out.println( Color.valueOf("RED").getName());

    }
}
