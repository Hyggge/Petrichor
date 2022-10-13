package front_end.lexer;

import java.util.ArrayList;

public class TokenStream {
    private ArrayList<Token> tokenList;
    private int pos;
    private int watchPoint;

    public TokenStream(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
        this.pos = 0;
        this.watchPoint = 0;
    }

    public Token read() {
        if (pos >= tokenList.size()) {
            pos++;
            return null;
        }
        return tokenList.get(pos++);
    }

    public void unread() {
        if (pos - 1 >= 0) {
            pos--;
        }
    }

    public Token look(int step) {
        if (pos + step - 1 >= tokenList.size()) return null;
        return tokenList.get(pos + step - 1);
    }

    public void setWatchPoint() {
        watchPoint = pos;
    }

    public Token backToWatchPoint() {
        pos = watchPoint - 1;
        return read();
    }

    public int getPos() {
        return pos;
    }
}
