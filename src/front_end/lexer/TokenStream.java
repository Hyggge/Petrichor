package front_end.lexer;

import java.util.ArrayList;

public class TokenStream {
    private ArrayList<Token> tokenList;
    private int pos;

    public TokenStream(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
        this.pos = 0;
    }

    public Token read() {
        if (pos >= tokenList.size()) return null;
        return tokenList.get(pos++);
    }

    public void unread() {
        if (pos - 1 >= 0) {
            pos--;
        }
    }

    public void unread(int step) {
        if (pos - step >= 0) {
            pos -= step;
        }
    }

    public int getPos() {
        return pos;
    }
}
