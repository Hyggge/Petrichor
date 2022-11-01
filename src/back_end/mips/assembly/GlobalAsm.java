package back_end.mips.assembly;

public class GlobalAsm extends Assembly{
    public static class Asciiz extends GlobalAsm {
        private String name;
        private String content;

        public Asciiz(String name, String content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public String toString() {
            return name + ": .asciiz \"" + content.replace("\n", "\\n") + "\"";
        }
    }

    public static class Word extends GlobalAsm{
        private String name;
        private int value;

        public Word(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return name + ": .word " + value;
        }
    }


    public static class Space extends GlobalAsm{
        private String name;
        private int size;

        public Space(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String toString() {
            return name + ": .space " + size;
        }
    }



}
