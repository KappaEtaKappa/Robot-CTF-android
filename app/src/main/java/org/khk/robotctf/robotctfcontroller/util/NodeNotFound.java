package org.khk.robotctf.robotctfcontroller.util;

/**
 * Created by Joe Dailey on 4/10/2015.
 */
public class NodeNotFound extends Exception{
    private static final long serialVersionUID = 7091727938827727023L;
    private final String node;
    public NodeNotFound(String node) {
        this.node = node;
    }
    @Override
    public String getMessage() {
        return node + " not found";
    }
}