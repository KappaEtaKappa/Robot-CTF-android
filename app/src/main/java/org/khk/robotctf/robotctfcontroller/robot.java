package org.khk.robotctf.robotctfcontroller;

/**
 * Created by Jack on 4/8/2015.
 */
public class robot {
    public final String name;
    public final String MAC;
    public final String team;
    public final int number;

    robot(String name, String MAC, String team, int number){
        this.name = name;
        this.MAC = MAC;
        this.team = team;
        this.number = number;
    }
}
